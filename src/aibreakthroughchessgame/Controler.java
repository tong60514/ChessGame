/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame;

import static aibreakthroughchessgame.ChessBoard.BlackPawn;
import static aibreakthroughchessgame.ChessBoard.WhitePawn;
import aibreakthroughchessgame.DataSructure.CaptureNode;
import aibreakthroughchessgame.DataSructure.MoveNode;
import aibreakthroughchessgame.DataSructure.Node;
import java.awt.BorderLayout;
import java.awt.Point;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 *
 * @author user
 */
public class Controler extends javax.swing.JFrame {
    private Chess WaitingChess = null;
    private final FinalGame[] GameState = { null, new WhiteTerminate() , new BlackTerminate()};
    private LinkedList<Integer> HintMoveList;
    private LinkedList<Integer> HintCaptureList;
    private LinkedList<Turn>    TurnList;
    private MyCounter timer=null;
    private int AIChessType;
    private Turn[] TurnTable ;
    private int stepCount = 0;
    public int WhoseTurn = 1;
    private Lock TurnLock = new ReentrantLock();
    private JDesktopPane desktop = new JDesktopPane();
    private static AIBrain[] AI = new AIBrain[3];
    public int ThinkingTime=30;
    /**
     * Creates new form Controler
     */
    public Controler() {
        initComponents();
        Turn[] TurnTable = {null, new WhiteTurn(0,0,0),new BlackTurn(0,0,0)};
        this.TurnTable = TurnTable;
        this.TurnList = new LinkedList();
        this.chessContainer1.setBounds(100,100,800,800);
        desktop.add(chessContainer1);
        this.setSize(1600,1000);
        this.unVisibleImage();
        this.chessContainer1.setVisible(true);
        this.GameOverInfo.setVisible(false);
        this.setVisible(true);
        this.setContentPane(jDesktopPane1);
        this.jDesktopPane1.add(chessContainer1);
        setUpGame();
        
    }
    private void setAI(){
        AIBrain[] brains = {null,new WhiteBrain(this),new BlackBrain(this)};
        Controler.AI = brains;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        BackGroundBoard = new javax.swing.JPanel();
        WhiteTurn = new javax.swing.JPanel();
        BlackTurn = new javax.swing.JPanel();
        GameOverInfo = new javax.swing.JPanel();
        WinTextr = new javax.swing.JLabel();
        ControlPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Recorder = new javax.swing.JEditorPane();
        Computer2 = new javax.swing.JButton();
        Computer1 = new javax.swing.JButton();
        ClosAI = new javax.swing.JButton();
        AIPK = new javax.swing.JButton();
        chessContainer1 = new aibreakthroughchessgame.ChessContainer();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        BackGroundBoard.setBackground(new java.awt.Color(255, 0, 51));

        WhiteTurn.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout WhiteTurnLayout = new javax.swing.GroupLayout(WhiteTurn);
        WhiteTurn.setLayout(WhiteTurnLayout);
        WhiteTurnLayout.setHorizontalGroup(
            WhiteTurnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        WhiteTurnLayout.setVerticalGroup(
            WhiteTurnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        BlackTurn.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout BlackTurnLayout = new javax.swing.GroupLayout(BlackTurn);
        BlackTurn.setLayout(BlackTurnLayout);
        BlackTurnLayout.setHorizontalGroup(
            BlackTurnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        BlackTurnLayout.setVerticalGroup(
            BlackTurnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        GameOverInfo.setBackground(new java.awt.Color(0, 255, 51));
        GameOverInfo.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(0, 0, 0)));

        WinTextr.setBackground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout GameOverInfoLayout = new javax.swing.GroupLayout(GameOverInfo);
        GameOverInfo.setLayout(GameOverInfoLayout);
        GameOverInfoLayout.setHorizontalGroup(
            GameOverInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GameOverInfoLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(WinTextr, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        GameOverInfoLayout.setVerticalGroup(
            GameOverInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GameOverInfoLayout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(WinTextr, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        ControlPanel.setBackground(new java.awt.Color(0, 204, 204));

        Recorder.setEditable(false);
        jScrollPane1.setViewportView(Recorder);

        Computer2.setBackground(new java.awt.Color(255, 255, 0));
        Computer2.setFont(new java.awt.Font("新細明體", 0, 18)); // NOI18N
        Computer2.setText("Black_Com");
        Computer2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(255, 153, 0), null, null));
        Computer2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Computer2ActionPerformed(evt);
            }
        });

        Computer1.setBackground(new java.awt.Color(255, 255, 0));
        Computer1.setFont(new java.awt.Font("新細明體", 0, 18)); // NOI18N
        Computer1.setText("White_Com");
        Computer1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(255, 153, 0), null, null));
        Computer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Computer1ActionPerformed(evt);
            }
        });

        ClosAI.setText("CloseAI");
        ClosAI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClosAIActionPerformed(evt);
            }
        });

        AIPK.setBackground(new java.awt.Color(255, 204, 204));
        AIPK.setText("AIStart");
        AIPK.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), new java.awt.Color(204, 0, 0), new java.awt.Color(102, 102, 255)));
        AIPK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AIPKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ControlPanelLayout = new javax.swing.GroupLayout(ControlPanel);
        ControlPanel.setLayout(ControlPanelLayout);
        ControlPanelLayout.setHorizontalGroup(
            ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ControlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Computer1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Computer2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
            .addGroup(ControlPanelLayout.createSequentialGroup()
                .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ControlPanelLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ControlPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ClosAI)
                        .addGap(18, 18, 18)
                        .addComponent(AIPK)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ControlPanelLayout.setVerticalGroup(
            ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ControlPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ClosAI)
                    .addComponent(AIPK))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Computer2, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(Computer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout BackGroundBoardLayout = new javax.swing.GroupLayout(BackGroundBoard);
        BackGroundBoard.setLayout(BackGroundBoardLayout);
        BackGroundBoardLayout.setHorizontalGroup(
            BackGroundBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackGroundBoardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BackGroundBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BlackTurn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(WhiteTurn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GameOverInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(90, 90, 90)
                .addComponent(ControlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        BackGroundBoardLayout.setVerticalGroup(
            BackGroundBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackGroundBoardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BackGroundBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BackGroundBoardLayout.createSequentialGroup()
                        .addComponent(ControlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(BackGroundBoardLayout.createSequentialGroup()
                        .addComponent(BlackTurn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(208, 208, 208)
                        .addComponent(GameOverInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 242, Short.MAX_VALUE)
                        .addComponent(WhiteTurn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jDesktopPane1.setLayer(BackGroundBoard, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(chessContainer1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap(97, Short.MAX_VALUE)
                .addComponent(chessContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(BackGroundBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap(116, Short.MAX_VALUE)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(chessContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(BackGroundBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50))))
        );

        getContentPane().add(jDesktopPane1);
        jDesktopPane1.setBounds(0, 0, 1520, 950);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Computer2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Computer2ActionPerformed
        // TODO add your handling code here:
        setUpAIPlayer(2);
        Controler.AI[2] = new BlackBrain(this);
        Controler.AI[2].start();
    }//GEN-LAST:event_Computer2ActionPerformed

    private void Computer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Computer1ActionPerformed
        // TODO add your handling code here:
        setUpAIPlayer(1);
        Controler.AI[1] = new WhiteBrain(this);
        Controler.AI[1].start();
    }//GEN-LAST:event_Computer1ActionPerformed

    private void ClosAIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClosAIActionPerformed
        // TODO add your handling code here:
        if(Controler.AI[1]!=null){
            Controler.AI[1].interrupt();
        }
        if(Controler.AI[2]!=null){
            Controler.AI[2].interrupt();
        }
    }//GEN-LAST:event_ClosAIActionPerformed

    private void AIPKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AIPKActionPerformed
        // TODO add your handling code here:
        /*System.out.println("refrf");
        Controler.AI[1] = new WhiteBrain(this);
        Controler.AI[2] = new BlackBrain(this);
        AIPK(this.WhoseTurn);*/
    }//GEN-LAST:event_AIPKActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Controler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Controler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Controler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Controler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Controler controler = new Controler();
                controler.chessContainer1.setControler(controler);
                controler.chessContainer1.InitContainer();
                controler.setVisible(true);
            }
        });
    }
    public MyCounter creatDelayEvent(int seconds ,GameTask task,boolean gui) {
        MyCounter frame = new MyCounter(seconds,task);
        frame.setVisible(gui); //necessary as of 1.3
        jDesktopPane1.add(frame);
        this.timer = frame;
        frame.start();
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
        return frame;
    }
    public void CancelTimer(){
        if(this.timer==null)
            return;
        this.timer.cancel();
        timer.setVisible(false);
        this.jDesktopPane1.remove(timer);
    }
    public void setUpControler(){
        this.chessContainer1.setControler(this);
        this.chessContainer1.InitContainer();
        
        this.setVisible(true);
    }
    public void setUpAIPlayer(int Color){
        this.AIChessType = Color;
    }
    public void unVisibleImage(){
        this.BlackTurn.setVisible(false);
        this.WhiteTurn.setVisible(false);
    }
    public void WhiteTurn(){
        this.WhiteTurn.setVisible(true);
    }
    public void BlackTurn(){
        this.BlackTurn.setVisible(true);
    }
                
    public ChessContainer getChessContainer(){
        return this.chessContainer1;
    }
    public void FocusChess(Chess chess){
        if(chess.getType()!=this.WhoseTurn||this.AIChessType==this.WhoseTurn)
            return;
        this.WaitingChess = chess;
        this.WaitingChess.Focus();
        HintLegalMove(chess.getIndex(),chess.getType());
    }
    
    public void unFocusChess(){
        if(this.WaitingChess==null)
            return;
        unHintLegalMove(this.HintMoveList,this.HintCaptureList);
        this.WaitingChess.unFocus();
        this.WaitingChess=null;
    }
    public void BoardMove(int Index , int toIndex){
        this.chessContainer1.BoardMove(Index, toIndex);
    }
    public void AIMove(Node node){
        stepCount++;
        BoardMove(node.getSrc(),node.getTarget());
        //signalAI(node.getSrc(),node.getTarget());
        CancelTimer();
        if(GameState[this.WhoseTurn].Terminnate(this.chessContainer1.getChessBitBoard())){
            GameOver(this.WhoseTurn);
        }
        record(stepCount,this.WhoseTurn,node.getSrc(),node.getTarget());
        this.WhoseTurn = ChessBitBoard.getOppisiteColor(this.WhoseTurn);
        System.out.println("who " + this.WhoseTurn);
        this.TurnTable[WhoseTurn].perform();
        this.repaint();
    }
    
    public void PlayerMove(int toIndex){
        if(this.WaitingChess==null)
            return ;
        stepCount++;
        int from = this.WaitingChess.getIndex();
        int to = toIndex;
        BoardMove(from,to);
        CancelTimer();
        if(GameState[this.WhoseTurn].Terminnate(this.chessContainer1.getChessBitBoard())){
            GameOver(this.WhoseTurn);
        }
        record(stepCount,this.WhoseTurn,from,to);
        this.WhoseTurn = ChessBitBoard.getOppisiteColor(this.WhoseTurn);
        System.out.println("who " + this.WhoseTurn);
        this.TurnTable[WhoseTurn].perform();
        this.repaint();
    }
    private void GameOver(int Who_Win){
        this.GameState[Who_Win].Win();
    }
    public void TurnLock(){
        this.TurnLock.lock();
    }
    public void TurnUnlock(){
        this.TurnLock.unlock();
    }
    public boolean tryTurn(){
        return this.TurnLock.tryLock();
    }
    
    
    public void HintLegalMove( int index , int color ){
        LinkedList<Integer> toHintMove = this.chessContainer1.getChessBitBoard().getLegalMove(index, color);
        LinkedList<Integer> toHintCapture = this.chessContainer1.getChessBitBoard().getLegalCapture(index, color);
        this.HintMoveList = toHintMove;
        this.HintCaptureList = toHintCapture;
        for(int num : toHintMove){
            this.chessContainer1.getBackGround()[num].hint();
        }
        for(int num : toHintCapture){
            this.chessContainer1.getChessTable()[num].hint();
        }
    }
    
    public void unHintLegalMove(LinkedList<Integer> HintMoveList,LinkedList<Integer> HintCaptureList){
        for(int index : HintMoveList)
            this.chessContainer1.getBackGround()[index].unhint();
        for(int index : HintCaptureList){
            this.chessContainer1.getChessTable()[index].unhint();
        }
    }
    private void setUpGame(){
        this.WhoseTurn=1;
        this.TurnTable[this.WhoseTurn].perform();
        //setAI();
    }
    abstract class Turn {
        private int From;
        private Point FromPoint;
        private Point ToPoint;
        private int To;
        private int stepCount;
        public Turn(int stepCount,int From,int To){
            this.stepCount = stepCount;
            this.From = From;
            this.To   = To;
            this.FromPoint = Transform.getRowColumn(From);
            this.ToPoint   = Transform.getRowColumn(To);
        }
        public abstract void perform();
        public abstract Turn newStep(int stepCount,int From,int To);
        public abstract String getColor();
        public final int getFrom(){
            return this.From;
        }
        public final int getTo(){
            return this.To;
        }
        public final String getMove(){
            String record =  "\n"+this.stepCount+ " " + this.getColor() + " : " + 
                    Transform.ColumnIndexTable[this.FromPoint.x] + Transform.RowIndexTable[this.FromPoint.y] + " => " + 
                    Transform.ColumnIndexTable[this.ToPoint.x] + Transform.RowIndexTable[this.ToPoint.y];
            return record;
        }     
    }
    class BlackTurn extends Turn{
        public BlackTurn(int stepCount,int From,int To){
            super(stepCount,From,To);
        }
        public Turn newStep(int stepCount,int From,int To){
            return new BlackTurn(stepCount,From,To);
        }
        public String getColor(){
            return "Black";
        }
        public void perform() {
            unVisibleImage();
            BlackTurn();
            creatDelayEvent(ThinkingTime,()->{
                GameOver(WhitePawn);
            },true);
            timer.setLocation(1000,100);
        }
    }
    class WhiteTurn extends Turn{
        public WhiteTurn(int stepCount,int From,int To){
            super(stepCount,From,To);
        }
        public Turn newStep(int stepCount,int From,int To){
            return new WhiteTurn(stepCount,From,To);
        }
        public void perform() {
            unVisibleImage();
            WhiteTurn();
            creatDelayEvent(ThinkingTime,()->{
                GameOver(BlackPawn);
            },true);
            timer.setLocation(1000,100);
        }
        public String getColor(){
            return "White";
        }
    }
    public void record(int step ,int Color , int from ,int to){
        Point fro = Transform.getRowColumn(from);
        Point t  = Transform.getRowColumn(to);
        this.TurnList.add(this.TurnTable[Color].newStep(this.stepCount,from,to));
        String record = this.TurnList.getLast().getMove();
        append(this.Recorder,record);
    }
    private void append(JEditorPane Recorder ,String s) {
        try {
            Document doc = Recorder.getDocument();
            doc.insertString(doc.getLength(), s, null);
        } catch (BadLocationException ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    abstract class FinalGame{
        public abstract boolean Terminnate(ChessBitBoard board);
        protected abstract void Win(); 
    }
    final class BlackTerminate extends FinalGame{
        @Override
        public boolean Terminnate(ChessBitBoard board) {
            long black = board.getBitBoard(2);
            return (black&AIBrain.WallTable.getRowBitBoard(0)) !=0;
        }
        protected void Win(){
            WinTextr.setText("black win!!");
            GameOverInfo.setVisible(true);
        }
        
    }
    final class WhiteTerminate extends FinalGame{
        public boolean Terminnate(ChessBitBoard board) {
            long white = board.getBitBoard(1);
            return (white&AIBrain.WallTable.getRowBitBoard(7)) !=0;
        }
        protected void Win(){
            WinTextr.setText("white win!!");
            GameOverInfo.setVisible(true);
        }
    }
    private boolean AItime(){
        return this.AIChessType!=0;
    }
    public void AIPK(int Initcolor){
        while(AItime()){
            System.out.println("AI");
            Controler.AI[Initcolor].start();
            Controler.AI[3-Initcolor].start();
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AIPK;
    private javax.swing.JPanel BackGroundBoard;
    private javax.swing.JPanel BlackTurn;
    private javax.swing.JButton ClosAI;
    private javax.swing.JButton Computer1;
    private javax.swing.JButton Computer2;
    private javax.swing.JPanel ControlPanel;
    private javax.swing.JPanel GameOverInfo;
    private javax.swing.JEditorPane Recorder;
    private javax.swing.JPanel WhiteTurn;
    private javax.swing.JLabel WinTextr;
    private aibreakthroughchessgame.ChessContainer chessContainer1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
