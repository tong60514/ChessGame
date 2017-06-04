/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame;

import aibreakthroughchessgame.DataSructure.LeaveChess;
import java.util.LinkedList;

/**
 *
 * @author user
 */
public class ChessBitBoard {             //0 1 2 3 4 5 6 7
    public static final int [] InitArray =  {2,2,2,2,2,2,2,2,//7
                                           2,2,2,2,2,2,2,2,//6
                                           0,0,0,0,0,0,0,0,//5
                                           0,0,0,0,0,0,0,0,//4
                                           0,0,0,0,0,0,0,0,//3
                                           0,0,0,0,0,0,0,0,//2
                                           1,1,1,1,1,1,1,1,//1
                                           1,1,1,1,1,1,1,1};//0
    private final RowColumnTable RowColumn;
    
    
    private long BlackBitBoard =0;
    private long WhiteBitBoard =0;
    
    private final long[] ChessBitBoard = new long[3];
    public static long[] IndexBitBoard;
    
    private int[] IndexArray;
    private final int MoveType_Attack = 0;
    private final int MoveType_NoneAttack = 1;
    private final ChessType[] MoveGeneratorTable = {null ,new WhiteGenerator(),new BlackGenerator()};
    public ChessBitBoard(){
        this.IndexArray = InitArray;
        ChessBitBoard[ChessBoard.WhitePawn] = WhiteBitBoard;
        ChessBitBoard[ChessBoard.BlackPawn] = BlackBitBoard;
        caculateBitBoard(IndexArray);
        RowColumn = new RowColumnTable();
        IndexBitBoard = InitIndexBitBoard();
    }
    private long empty(){
        return ~(ChessBitBoard[1]|ChessBitBoard[2]);
    }
    public long getBitBoard(int Color){
        return this.ChessBitBoard[Color];
    }
    public long[] getChessBitBoard(){
        return this.ChessBitBoard;
    }
    public long[] getIndexBitBoard(){
        return this.IndexBitBoard;
    }
    public void move(int Index , int toIndex){
        int Color = IndexArray[Index];
        moveBitBoard(Index,toIndex,Color);
        moveIndexArray(Index, toIndex);
    }
    public int[] getIndexArray(){
        return this.IndexArray;
    }
    public LinkedList getLegalMove(int index,int color){
        LinkedList<Integer> moves = new LinkedList();
        this.MoveGeneratorTable[color].generateMove(index, moves);
        return moves;
    }
    public LinkedList getLegalCapture(int index,int color){
        LinkedList<Integer> captures = new LinkedList();
        this.MoveGeneratorTable[color].generateCaptires(index, captures);
        return captures;
    }
    private void creatLeaveChessTable(){
        int count=0;
        
    }
    
    
    
    public static void main(String[] arg){
        int[] board = {2,2,2,2,2,2,2,2
                      ,2,2,2,2,2,2,2,2,
                       0,0,0,0,0,0,0,0,
                       0,0,0,0,0,0,0,0,
                       0,0,0,0,0,0,0,0,
                       0,0,0,0,0,0,0,0,
                       1,1,1,1,1,1,1,1,
                       1,1,1,1,1,1,1,1};
        ChessBitBoard test = new ChessBitBoard();
        for(int chess : test.getIndexArray())
           System.out.println(chess);
        test.move(51, 40);
        System.out.println(Long.toBinaryString(test.getBitBoard(1)));
        System.out.println(Long.toBinaryString(test.getBitBoard(2)));
        for(int chess : test.getIndexArray())
           System.out.println(chess);
    }
    private void caculateBitBoard(int[] IndexArray){
        for(int i=0;i<IndexArray.length;i++){
            this.ChessBitBoard[IndexArray[i]] = ( this.ChessBitBoard[IndexArray[i]] | (1L<<IndexToBitBoardShiftValue(i)) );
        }   
        this.BlackBitBoard = ChessBitBoard[ChessBoard.BlackPawn];
        this.WhiteBitBoard = ChessBitBoard[ChessBoard.WhitePawn];
    }
    private long[] InitIndexBitBoard(){
        long[] AllPoint = new long[64];
        for(int i=0;i<AllPoint.length;i++){
            AllPoint[i] = (1L<<IndexToBitBoardShiftValue(i));
        }
        return AllPoint;
    }
    
    private int IndexToBitBoardShiftValue(int index){
        return (64-index-1);
    }
    public static int getOppisiteColor(int Color){
        return 3-Color;
    }
    private void moveIndexArray(int Index , int toIndex){
        int IndexChess = this.IndexArray[Index];
        this.IndexArray[Index] = 0 ;
        this.IndexArray[toIndex] = IndexChess;
    }
    private long removeChess(long BitBoard,int index){
        return (BitBoard & (~this.IndexBitBoard[index]));
    }
    private void moveBitBoard(int Index , int toIndex ,int Color ){
        long thisColorBitBoard = this.ChessBitBoard[Color] ;
        long toIndexBitBoard = this.IndexBitBoard[toIndex];
        long IndexBitBoard = this.IndexBitBoard[Index];
        this.ChessBitBoard[Color] = removeChess( (thisColorBitBoard | toIndexBitBoard) , Index );
        this.ChessBitBoard[getOppisiteColor(Color)] = removeChess(this.ChessBitBoard[getOppisiteColor(Color)],toIndex);
    }
    
    
    private abstract class MoveGeneration{
        public abstract void generate(int toIndex,LinkedList<Integer> moves);
    }
    private final class LegalMove extends MoveGeneration{
        public void generate(int toIndex,LinkedList<Integer> moves){
            moves.add(toIndex);
        }
    }
    private final class IllegalMove extends MoveGeneration{
        public void generate(int toIndex,LinkedList<Integer> moves){
            return;
        }
    }
    abstract class ChessType {
        protected int SrcIndex;
        protected final MoveGeneration[] Generator = {new IllegalMove(),new LegalMove()};
        protected abstract int leftMove(long Chess);
        protected abstract int leftCapture(long Chess);
        protected abstract int forwardMove(long Chess);
        protected abstract int rightMove(long Chess);
        protected abstract int rightCapture(long Chess);
        protected abstract void generateMove(int SrcIndex, LinkedList<Integer> moves);
        protected abstract void generateCaptires(int SrcIndex, LinkedList<Integer> moves);
    }
    class BlackGenerator extends ChessType{
        protected int leftMove(long Chess){
            return (int) ( ((Chess>>>9)&empty()&~(RowColumn.getColumnBitBoard(0)))>>>(63-this.SrcIndex-9) );
        }
        protected int forwardMove(long Chess){
            return (int) ( ((Chess>>>8)& empty() )>>(63-this.SrcIndex-8) );
        }
        protected int rightMove(long Chess){
            return (int) ( ((Chess>>>7)&empty()&~(RowColumn.getColumnBitBoard(7)) )>>>(63-this.SrcIndex-7) );
        }
        protected void generateMove(int SrcIndex, LinkedList<Integer> moves){
            long Chess = IndexBitBoard[SrcIndex];
            this.SrcIndex = SrcIndex;
            this.Generator[leftMove(Chess)].generate(SrcIndex+9, moves);
            this.Generator[forwardMove(Chess)].generate(SrcIndex+8, moves);
            this.Generator[rightMove(Chess)].generate(SrcIndex+7, moves);
        }

        @Override
        protected int leftCapture(long Chess) {
            return (int) ( ((Chess>>>9)&ChessBitBoard[ChessBoard.WhitePawn]&~(RowColumn.getColumnBitBoard(0)))>>>(63-this.SrcIndex-9) );
        }

        @Override
        protected int rightCapture(long Chess) {
            return (int) ( ((Chess>>>7)&ChessBitBoard[ChessBoard.WhitePawn]&~(RowColumn.getColumnBitBoard(7)) )>>>(63-this.SrcIndex-7) );
        }

        @Override
        protected void generateCaptires(int SrcIndex, LinkedList<Integer> moves) {
            long Chess = IndexBitBoard[SrcIndex];
            this.Generator[leftCapture(Chess)].generate(SrcIndex+9, moves);
            this.Generator[rightCapture(Chess)].generate(SrcIndex+7, moves);
        }
    }
    class WhiteGenerator extends ChessType{
        protected int leftMove(long Chess){
            return (int)( (Chess<<9&empty()&~(RowColumn.getColumnBitBoard(7)))>>>(63-this.SrcIndex+9) );
        }
        protected int forwardMove(long Chess){
            return (int)( ((Chess<<8)&empty())>>(63-this.SrcIndex+8) );
        }
        protected int rightMove(long Chess){
            return (int)( (Chess<<7&empty()&~(RowColumn.getColumnBitBoard(0)))>>>(63-this.SrcIndex+7) );
        }
        protected void generateMove(int SrcIndex, LinkedList<Integer> moves){
            long Chess = IndexBitBoard[SrcIndex];
            this.SrcIndex = SrcIndex;
            this.Generator[leftMove(Chess)].generate(SrcIndex-9, moves);
            this.Generator[forwardMove(Chess)].generate(SrcIndex-8, moves);
            this.Generator[rightMove(Chess)].generate(SrcIndex-7, moves);
        }
        @Override
        protected int leftCapture(long Chess) {
            return (int)( (Chess<<9&ChessBitBoard[ChessBoard.BlackPawn]&~(RowColumn.getColumnBitBoard(7)))>>>(63-this.SrcIndex+9) );
        }

        @Override
        protected int rightCapture(long Chess) {
            return (int)( (Chess<<7&ChessBitBoard[ChessBoard.BlackPawn]&~(RowColumn.getColumnBitBoard(0)))>>>(63-this.SrcIndex+7) );
        }

        @Override
        protected void generateCaptires(int SrcIndex, LinkedList<Integer> moves) {
            long Chess = IndexBitBoard[SrcIndex];
            this.Generator[leftCapture(Chess)].generate(SrcIndex-9, moves);
            this.Generator[rightCapture(Chess)].generate(SrcIndex-7, moves);
        }
    }
    

    
    
    
    
    
    
    
    static class RowColumnTable{
        private final long[] Row ;
        private final long   UnitRow     = 0b11111111L;
        private final long[] Column ;
        private final long  UnitColumn  = 0b0000000100000001000000010000000100000001000000010000000100000001L;
        public RowColumnTable(){
            Row = new long [8] ;
            setRow(Row);
            Column = new long [8] ;
            setColumn(Column);
        }
        private void setRow(long[] Row){
            for(int i=0;i<Row.length;i++){
                Row[i] = this.UnitRow<<(i*8);
            }
        }
        private void setColumn(long[] Column){
            for(int i=0;i<Column.length;i++){
                Column[i] = this.UnitColumn<<(7-i);
            }
        }
        public long getRowBitBoard(int Row){
            return this.Row[Row];
        }
        public long getColumnBitBoard(int Column){
            return this.Column[Column];
        }
    }
            
    
}
