/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame.DataSructure;

import aibreakthroughchessgame.ChessBitBoard;
import aibreakthroughchessgame.DataSructure.Node;
import aibreakthroughchessgame.DataSructure.LeaveChess;
import aibreakthroughchessgame.Transform;
import java.util.LinkedList;

/**
 *
 * @author user
 */
public class CaptureNode extends Node{
    private LeaveChess Dead ;
    public CaptureNode(Node superNode,int index,int toIndex,int Color){
        super(superNode,index,toIndex,Color);
    }


    @Override
    public void searchNode(StructBoard StructBoard,long[] board){
        this.Dead = StructBoard.capture(index, toIndex, Transform.getOppsiteColor(WhosTurn));
        this.Dead.setNEXT(null);
        this.Dead.setSuper(null);
        Transform.BitBoardCapture(board, Transform.getOppsiteColor(WhosTurn), ChessBitBoard.IndexBitBoard[index], ChessBitBoard.IndexBitBoard[toIndex]);
    }
    @Override
    public void backtrack (StructBoard StructBoard,long[] board){
       StructBoard.deCapture(toIndex,index,WhosTurn,this.Dead);
       Transform.BitBoardDeCapture(board, WhosTurn, ChessBitBoard.IndexBitBoard[toIndex], ChessBitBoard.IndexBitBoard[index]);
    }
}
