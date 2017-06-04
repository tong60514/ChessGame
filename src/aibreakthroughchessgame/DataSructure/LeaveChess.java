/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame.DataSructure;

import aibreakthroughchessgame.ChessBitBoard;

/**
 *
 * @author user
 */
public class LeaveChess {
    private LeaveChess nextNode;
    private LeaveChess ExNode;
    private long BitBoard;
    private int index;
    public LeaveChess(int index,long BitBoard){
        this.index = index;
        this.BitBoard = BitBoard;
    }
    public void moveTo(int index){
        this.index = index;
        this.BitBoard = ChessBitBoard.IndexBitBoard[index];
    }
    public final long getBitBoard(){
        return this.BitBoard;
    }
    public static LeaveChess cloneLeaveChess(LeaveChess chess){
        return new LeaveChess(chess.getIndex(),chess.getBitBoard());
    }
    public final int getIndex(){
        return this.index;
    }
    public LeaveChess getEXref(){
        return this.ExNode;  
    }
    public LeaveChess getNEXTref(){
        return this.nextNode;
    }
    
    public void setSuper(LeaveChess Ex){
        this.ExNode = Ex;
    }
    public void setNEXT(LeaveChess Next){
        this.nextNode = Next;
    }
    public LeaveChess remove(){
        LeaveChess next = this.nextNode;
        this.nextNode.setSuper(this.ExNode);
        this.ExNode.setNEXT(this.nextNode);
        this.setNEXT(null);
        this.setSuper(null);
        return next;
    }
    
    
    
}
