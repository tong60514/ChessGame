/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame;

import aibreakthroughchessgame.DataSructure.Node;
import aibreakthroughchessgame.ChessBitBoard.RowColumnTable;
import static aibreakthroughchessgame.ChessBoard.BlackPawn;
import static aibreakthroughchessgame.ChessBoard.WhitePawn;
import aibreakthroughchessgame.DataSructure.LeaveChess;
import static aibreakthroughchessgame.DataSructure.Node.GenerateCapture;
import static aibreakthroughchessgame.DataSructure.Node.GenerateMove;
import aibreakthroughchessgame.DataSructure.StructBoard;
import aibreakthroughchessgame.DataSructure.StructList;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public abstract class AIBrain extends Thread{
    /*protected final AITask[] AIPerformance = {()->{
        
    }};*/
    
    protected Controler controler;
    protected LinkedList<Node> TopBranches = new LinkedList();
    protected Node best;
    protected long[] TwoBitBoard ;
    protected static final long[] IndexBitBoard = Transform.InitIndexBitBoard() ;
    protected StructBoard structBoard;
    protected Generator[] generator = {null,new WhiteGenerator(),new BlackGenerator()};
    protected Node root;
    protected Node currentSearchNode = null;
    protected LinkedList<AIConcurrency>  aicontainer; 
    
    protected int depth=5;
    public static final RowColumnTable WallTable = new RowColumnTable();
    
    
    protected Lock TurnLock = new ReentrantLock();
    
    protected Lock joinLock = new ReentrantLock();
    protected Condition unjoin = joinLock.newCondition();
    protected Condition notYourTurn = TurnLock.newCondition();
    
    public AIBrain(Controler controler){
        this.controler = controler;
        this.TwoBitBoard = controler.getChessContainer().getChessBitBoard().getChessBitBoard().clone();
        this.structBoard = new StructBoard(controler.getChessContainer().getChessBitBoard().getIndexArray().clone());
        this.aicontainer = new LinkedList();
        InitDepth();
    }
    public AIBrain(long[] TwoBitBoard,StructBoard structBoard ,Node root){
        this.TwoBitBoard = TwoBitBoard;
        this.structBoard = structBoard;
        this.root = root;
        this.currentSearchNode = root;
    }
    public AIBrain(){}
    public long[] getBitBoard(){
        return this.TwoBitBoard;
    }
    public StructBoard getStructBoard(){
        return this.structBoard;
    }
    
    public Node getCurrentSearch(){
        return this.currentSearchNode;
    }
    public int getCurrentDepth(){
        return this.depth;
    }
    public void SortBranches(Node node){
        sortingNode(this.TopBranches,node);
    }
    
    
    
    protected void ColorSelet(int color){
        this.root = new RootNode(color);
        this.currentSearchNode = root;
    }
    
    public void UpdateInfo(Node nextStep){
        this.root = nextStep;
        this.currentSearchNode = nextStep;
        InitDepth();
        this.Search(nextStep);
    }
    public AIBrain(long[] TwoBitBoardClone ,StructBoard structBoard){
        this.TwoBitBoard = TwoBitBoardClone;
        this.structBoard = structBoard;
    }
    
    private long empty(){
        return ~(this.TwoBitBoard[1]|this.TwoBitBoard[2]);
    }
    
    private  abstract class Move{
        public abstract void generate(int index,int toIndex,Node Super);
    }
    private  final class LegalMove extends Move{
        public void generate(int index,int toIndex,Node Super){
           GenerateMove(index,toIndex,Super);
        }
    }
    private final class IllegalMove extends Move{
        public void generate(int index,int toIndex,Node Super){
            return;
        }
    }
    private final class LegalCapture extends Move{
        public void generate(int index,int toIndex,Node Super){
           GenerateCapture(index,toIndex,Super);
        }
    }
    private final class IllegalCapture extends Move{
        public void generate(int index,int toIndex,Node Super){
            return;
        }
    }
    
    abstract class Generator {
        protected int SrcIndex;
        protected final Move[] MoveGenerator = {new IllegalMove(),new LegalMove()};
        protected final Move[] CaptureGenerator = {new IllegalCapture(),new LegalCapture()};
        protected abstract int leftMove(long Src,int SrcIndex);
        protected abstract int leftCapture(long Src,int SrcIndex);
        protected abstract int forwardMove(long Src,int SrcIndex);
        protected abstract int rightMove(long Src,int SrcIndex);
        protected abstract int rightCapture(long Src,int SrcIndex);
        protected abstract void generateMoves(int SrcIndex,Node superNode);
        protected abstract void generateCaptires(int SrcIndex,Node superNode);
    }
    class BlackGenerator extends Generator{
        protected int leftMove(long Src,int SrcIndex){
            return (int) ( ( (Src>>>9)&empty()&~(WallTable.getColumnBitBoard(0)) )>>>(63-SrcIndex-9) );
        }
        protected int forwardMove(long Src,int SrcIndex){
            return (int) (( (Src>>>8) & empty() ) >> (63-SrcIndex-8) );
        }
        protected int rightMove(long Src,int SrcIndex){
            return (int) ( ((Src>>>7)&empty()&~(WallTable.getColumnBitBoard(7)) )>>>(63-SrcIndex-7) );
        }
        protected void generateMoves(int SrcIndex,Node superNode){
            long Chess = IndexBitBoard[SrcIndex];
            this.SrcIndex = SrcIndex;
            this.MoveGenerator[leftMove(Chess,SrcIndex)].generate(SrcIndex,SrcIndex+9, superNode);
            this.MoveGenerator[forwardMove(Chess,SrcIndex)].generate(SrcIndex,SrcIndex+8, superNode);
            this.MoveGenerator[rightMove(Chess,SrcIndex)].generate(SrcIndex,SrcIndex+7, superNode);
        }
        

        @Override
        protected int leftCapture(long Src,int SrcIndex) {
            return (int) ( ((Src>>>9)&TwoBitBoard[ChessBoard.WhitePawn]&~(WallTable.getColumnBitBoard(0)) )>>>(63-SrcIndex-9) );
        }

        @Override
        protected int rightCapture(long Src,int SrcIndex) {
           return (int) ( ((Src>>>7)&TwoBitBoard[ChessBoard.WhitePawn]&~(WallTable.getColumnBitBoard(7)))>>>(63-SrcIndex-7) );
        }
        @Override
        protected void generateCaptires(int SrcIndex,Node superNode){
            long Chess = IndexBitBoard[SrcIndex];
            this.CaptureGenerator[leftCapture(Chess,SrcIndex)].generate(SrcIndex,SrcIndex+9,superNode);
            this.CaptureGenerator[rightCapture(Chess,SrcIndex)].generate(SrcIndex,SrcIndex+7,superNode);
        }
    }
    class WhiteGenerator extends Generator{
        protected int leftMove(long Src,int SrcIndex){
            return (int) ( ((Src<<9)&empty()&~(WallTable.getColumnBitBoard(7)))>>>(63-SrcIndex+9) );
        }
        protected int forwardMove(long Src,int SrcIndex){
           return (int) ( ((Src<<8)&empty() )>>(63-SrcIndex+8) );
        }
        protected int rightMove(long Src,int SrcIndex){
            return (int) ( ((Src<<7)&empty()&~(WallTable.getColumnBitBoard(0)) )>>>(63-SrcIndex+7) );
        }
        protected void generateMoves(int SrcIndex,Node superNode){
            long Chess = IndexBitBoard[SrcIndex];
            this.SrcIndex = SrcIndex;
            this.MoveGenerator[leftMove(Chess,SrcIndex)].generate(SrcIndex,SrcIndex-9, superNode);
            this.MoveGenerator[forwardMove(Chess,SrcIndex)].generate(SrcIndex,SrcIndex-8, superNode);
            this.MoveGenerator[rightMove(Chess,SrcIndex)].generate(SrcIndex,SrcIndex-7, superNode);
        }
        @Override
        protected int leftCapture(long Src,int SrcIndex) {
            return (int) ( ((Src<<9)&TwoBitBoard[ChessBoard.BlackPawn]&~(WallTable.getColumnBitBoard(7)))>>>(63-SrcIndex+9) );
        }

        @Override
        protected int rightCapture(long Src,int SrcIndex) {
            return (int) ( ((Src<<7)&TwoBitBoard[ChessBoard.BlackPawn]&~(WallTable.getColumnBitBoard(0)))>>>(63-SrcIndex+7) );
        }
        @Override
        protected void generateCaptires(int SrcIndex,Node superNode) {
            long Chess = IndexBitBoard[SrcIndex];
            this.CaptureGenerator[leftCapture(Chess,SrcIndex)].generate(SrcIndex,SrcIndex-9, superNode);
            this.CaptureGenerator[rightCapture(Chess,SrcIndex)].generate(SrcIndex,SrcIndex-7, superNode);
        }
    }
    public final int Nega_Max(int alpha,int beta,Node node,int depth){
        int Score;
        if(depth==0)
            return -quiesceSearch(-beta,-alpha,node);
        expandBranches(node);
        for(Node exp : node.getNodes()){
            Search(exp);
            
            Score = -Nega_Max(-beta,-alpha,exp,depth-1);
            BackTrack();
            if(Score>=beta){
                killBranches(node);
                return beta;
            }
            if(Score>alpha)
                alpha = Score;
        }
        killBranches(node);
        return alpha;
    }
    
    private final int quiesceSearch(int alpha,int beta,Node node){
        int Score;
        expandCapture(node);
        if(node.getNodes().isEmpty()){
            switch(node.getColor()){
                case WhitePawn:
                    return -Transform.Evaluate(this.structBoard,this.TwoBitBoard);
                case BlackPawn:
                    return  Transform.Evaluate(this.structBoard,this.TwoBitBoard);
            }
        }
        for(Node exp : node.getNodes()){
            Search(exp);
            Score = -quiesceSearch(-beta,-alpha,exp);
            BackTrack();
            if(Score>=beta){
                killBranches(node);
                return beta;
            }
            if(Score>alpha)
                alpha = Score;
        }
        killBranches(node);
        return alpha;
    }
    
    private final void addMove(int index,Node node){
        this.generator[node.getColor()].generateMoves(index,node);
    }
    private final void addCapture(int index,Node node){
        this.generator[node.getColor()].generateCaptires(index,node);
    }
    public final void Search(Node node){
        node.searchNode(structBoard, TwoBitBoard);
        this.currentSearchNode = node;
    }
    public final void BackTrack(){
        this.currentSearchNode.backtrack(structBoard, TwoBitBoard);
        this.currentSearchNode = this.currentSearchNode.getSuper();
    }
    public final void expandMove(Node searchNode){
        StructList list = this.structBoard.structList[searchNode.getColor()];
        for(int i=0;i<list.Size();i++){
            LeaveChess chess = list.getCurrent();
            addMove(chess.getIndex(),searchNode);
        }
    }
    public static void killBranches(Node node){
        node.setBranchNode(new LinkedList());
    }
    
    private final void expandCapture(Node searchNode){
        StructList list = this.structBoard.structList[searchNode.getColor()];
        for(int i=0;i<list.Size();i++){
            LeaveChess chess = list.getCurrent();
            addCapture(chess.getIndex(),searchNode);
        }
    }
    public abstract void MoveSignal(Node node);
    private final void expandCaptureNodes(Node searchNode){
        expandCapture(searchNode);
    }
    
    public final void expandBranches(Node searchNode){
        expandMove(searchNode);
        expandCapture(searchNode);
    }
    
    private void InitDepth(){
        this.depth = 2;
    }
    public LinkedList IterativeAlphaBetaNegaMax(LinkedList<Node> steplist,int depth){
        LinkedList<Node> SortedList = new LinkedList();
        for(Node node : steplist){
            Search(node);
            int tmpScore = Nega_Max(-10000,10000,node,depth);
            node.setNodeScore(tmpScore);
            killBranches(node);
            BackTrack();
            sortingNode( SortedList , node );
        }
        System.out.println("finish");
        return SortedList;
    }
    public void parellelCompute(LinkedList<Node> nodes , int depth){
        
        
            for( Node node : nodes){
                // seperate thread
                Search(node);
                this.aicontainer.add(new AIConcurrency(
                        this,this.TwoBitBoard.clone(),StructBoard.cloneBoard(structBoard),depth-1));
                this.aicontainer.getLast().start();
                BackTrack();
            }
        waitJoin(nodes.size());
        this.aicontainer = new LinkedList();
        // wait thread join 
        return;
    }
    private void sortingNode(LinkedList<Node> stepList, Node node ){
        if(stepList.isEmpty() || stepList.getFirst().getNodeScore()>node.getNodeScore())
            stepList.add(node);
        else{
            stepList.addFirst(node);
        }
    }
    public void addBranches(Node node){
        this.joinLock.lock();
        try{
            if(TopBranches.isEmpty() || TopBranches.getFirst().getNodeScore()>node.getNodeScore())
                TopBranches.add(node);
            else{
                TopBranches.addFirst(node);
            }
        }finally{
            this.unjoin.signal();
            this.joinLock.unlock();
        }
    }
    
    
    
    public void run(){
        int currentdepth = 3;
        this.expandBranches(this.root);
        //while(true){
            parellelCompute(this.root.getNodes(),7);
            System.exit(0);
            currentdepth++;
            this.root.setBranchNode(TopBranches);
            this.best = TopBranches.getFirst();
            TopBranches= new LinkedList();
            this.controler.AIMove(best);
        //}
        
        IterativeAlphaBetaNegaMax(this.root.getNodes(),7);
        System.exit(0);
        /*while(true){
           this.TurnLock.lock();
           try{
                while(this.controler.WhoseTurn!=this.root.getColor()){
                    try {
                        notYourTurn.await();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AIBrain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                this.controler.AIMove(this.TopBranches.getFirst());
            }finally{
                this.TurnLock.unlock();
            }
        }*/
    }
    
    
    private void yourTurn (){
        this.TurnLock.lock();
        try{
            this.notYourTurn.signal();
        }finally{
            this.TurnLock.unlock();
        }
    }
    
    public void waitJoin(int finalbranches){
        this.joinLock.lock();
        try{
            while(this.TopBranches.size()!=finalbranches)
                try {
                    System.out.println("wait!!");
                    unjoin.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(AIBrain.class.getName()).log(Level.SEVERE, null, ex);
                }
        }finally{
             this.joinLock.unlock();
             System.out.println("Go!!");
        }
    }
    static final class RootNode extends Node {
        public RootNode(int color){
            super(null,0,0,color);
        }
        @Override
        public void searchNode(StructBoard StructBoard,long[] board){}

        @Override
        public void backtrack(StructBoard StructBoard, long[] board) {}
    }
}
