/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame.DataSructure;

import aibreakthroughchessgame.ChessBitBoard;
import aibreakthroughchessgame.Transform;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class StructBoard {
    public LeaveChess[] IndexList = new LeaveChess[64];
    public StructList[] structList;
    
    public StructBoard(int[] indexArray){
        structList = new StructList[3];
        structList[1] = new StructList(1);
        structList[2] = new StructList(2);
        for(int i=0;i<indexArray.length;i++){
            if(indexArray[i]!=0){
                LeaveChess chess = new LeaveChess(i,ChessBitBoard.IndexBitBoard[i]);
                structList[indexArray[i]].add(chess);
                IndexList[i] = chess;
            }
        }
    }
    
    public static StructBoard cloneBoard(StructBoard tar) {
        StructList white = tar.structList[1];
        StructList black = tar.structList[2];
        int[] indexArray = new int[64];
        for(int i=0;i<64;i++){
            indexArray[i] = 0;
        }
        for(int i=0;i<white.Size();i++){
            indexArray[white.getCurrent().getIndex()] = 1;
        }
        for(int i=0;i<black.Size();i++){
            indexArray[black.getCurrent().getIndex()] = 2;
        }
        return new StructBoard(indexArray);
    }
   
    
    public void move(int index , int toIndex ,int Color){
        LeaveChess src = IndexList[index];
        src.moveTo(toIndex);
        IndexList[toIndex] = src ;
        IndexList[index] = null ;
    }
    public LeaveChess capture(int index , int toIndex,int Color){
        LeaveChess dead = IndexList[toIndex];
        move(index,toIndex,Color);
        this.structList[Transform.getOppsiteColor(Color)].removeChess(dead);
        return dead;
    }
    private void resurrection(int color,int at,LeaveChess dead){
        this.IndexList[at] = dead;
        this.structList[color].add(dead);
    }
    public void deCapture(int index , int toIndex,int Color,LeaveChess dead){
        move(index,toIndex,Transform.getOppsiteColor(Color));
        resurrection(Color,index,dead);
    }
    
    
}
