/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame;

import aibreakthroughchessgame.DataSructure.Node;
import aibreakthroughchessgame.DataSructure.LeaveChess;
import static aibreakthroughchessgame.DataSructure.Node.GenerateCapture;
import static aibreakthroughchessgame.DataSructure.Node.GenerateMove;
import aibreakthroughchessgame.DataSructure.StructBoard;
import aibreakthroughchessgame.DataSructure.StructList;
import java.util.LinkedList;

/**
 *
 * @author user
 */
public class BlackBrain extends AIBrain{
    
    public BlackBrain(Controler controler){
        super(controler);
        this.ColorSelet(ChessBoard.BlackPawn);
    }
    
    public void getNewNode(long[] TwoBitBoardClone,StructBoard structBoard,Node root){
        this.TwoBitBoard = TwoBitBoardClone;
        this.structBoard = structBoard;
        this.root = root;
        this.currentSearchNode = root;
    }
    
    public static void main(String[] arg ){
        Controler controler = new Controler();
        controler.setUpControler();
        AIBrain ai = new BlackBrain(controler);
        System.out.println("score is :" + ai.Nega_Max(-5000, 5000, ai.root,5));
    }

    @Override
    public void MoveSignal(Node node) {
        
    }


    
}
