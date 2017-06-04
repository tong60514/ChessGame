/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame.DataSructure;

import aibreakthroughchessgame.ChessBitBoard;
import aibreakthroughchessgame.DataSructure.LeaveChess;
import aibreakthroughchessgame.Transform;
import java.util.LinkedList;

/**
 *
 * @author user
 */
public class MoveNode extends Node{
    public MoveNode(Node superNode,int index,int toIndex,int Color){
        super(superNode,index,toIndex,Color);
    }
    @Override
    public void searchNode(StructBoard StructBoard,long[] board){
       StructBoard.move(index, toIndex, Transform.getOppsiteColor(WhosTurn));
       board[Transform.getOppsiteColor(WhosTurn)] = 
               Transform.BitBoardMove(board[Transform.getOppsiteColor(WhosTurn)], 
                       ChessBitBoard.IndexBitBoard[index], 
                       ChessBitBoard.IndexBitBoard[toIndex]);
    }

    @Override
    public void backtrack(StructBoard StructBoard, long[] board) {
        StructBoard.move(toIndex, index, Transform.getOppsiteColor(WhosTurn));
        board[Transform.getOppsiteColor(WhosTurn)] = 
               Transform.BitBoardMove(board[Transform.getOppsiteColor(WhosTurn)], 
                       ChessBitBoard.IndexBitBoard[toIndex], 
                       ChessBitBoard.IndexBitBoard[index]);
    }
    

}
