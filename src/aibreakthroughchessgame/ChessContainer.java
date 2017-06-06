/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame;

import aibreakthroughchessgame.DataSructure.CaptureNode;
import aibreakthroughchessgame.DataSructure.MoveNode;
import aibreakthroughchessgame.DataSructure.Node;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class ChessContainer extends javax.swing.JPanel implements Runnable {
    private final BackGroundBoard[] BackGround = new BackGroundBoard[64];
    private Thread thread;
    private Chess[] ChessIndexBoard = new Chess[64];
    private ChessBitBoard Board = new ChessBitBoard();
    private Controler control;
    /**
     * Creates new form ChessContainer
     */
    public ChessContainer() {
        initComponents();
        this.setSize(800,800);
        new Thread(this).start();
    }
    public Node BoardMove(int color , int index , int toIndex){
        Node node;
        Chess srcChess = ChessIndexBoard[index];
        ChessIndexBoard[index] = null;
        if(ChessIndexBoard[toIndex]!=null){
            ChessIndexBoard[toIndex].setVisible(false);
            ChessIndexBoard[toIndex] = null;
            node = new CaptureNode(null,index,toIndex,color);
        }else{
            node = new MoveNode(null,index,toIndex,color);
        }
        srcChess.setBoardIndex(toIndex);
        ChessIndexBoard[toIndex] = srcChess;
        srcChess.setLocation(BackGround[toIndex].getLocation().x, BackGround[toIndex].getLocation().y);
        this.Board.move(index, toIndex);
        this.repaint();
        this.control.unFocusChess();
        return node;
    }
    public Chess[] getChessTable(){
        return this.ChessIndexBoard;
    }
    private void PaintBackGround(){
        boolean Switch   = false;
        for(int i=0;i<BackGround.length;i++){
            BackGround[i] = new BackGroundBoard(this.control,i);
            BackGround[i].setBounds(0,0,100,100);
            BackGround[i].setLocation((i%8)*100, (i/8)*100);
            if(Switch)
                BackGround[i].turnBlack();
            else
                BackGround[i].turnWhite();
            this.add(BackGround[i]);
            BackGround[i].setVisible(true);
            Switch = !Switch;
            if(i%8==7)
               Switch = !Switch;
        }
    }
    
    public void InitContainer(){
        PaintBackGround();
        InitChess();
    }
    public BackGroundBoard[] getBackGround(){
        return this.BackGround;
    }
    public void setControler(Controler control){
        this.control = control;
    }
    public ChessBitBoard getChessBitBoard(){
        return this.Board;
    }
    private void InitChess(){
        for(int i=0;i<64;i++){
            if(Board.getIndexArray()[i]!=0){
                ChessIndexBoard[i] = new Chess(this.control,Board.getIndexArray()[i]);
                ChessIndexBoard[i].setBounds(0,0,100,100);
                ChessIndexBoard[i].setLocation((i%8)*100, (i/8)*100);
                ChessIndexBoard[i].setBoardIndex(i);
                this.add(ChessIndexBoard[i]);
                this.setComponentZOrder(ChessIndexBoard[i], 0);
                ChessIndexBoard[i].setVisible(true);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 10));
        setPreferredSize(new java.awt.Dimension(800, 800));
        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void run() {
        while(true)
           this.repaint();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
