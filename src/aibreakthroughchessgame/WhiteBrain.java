/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame;

import aibreakthroughchessgame.DataSructure.Node;
import aibreakthroughchessgame.DataSructure.StructBoard;

/**
 *
 * @author user
 */
public class WhiteBrain extends AIBrain{
    
    public WhiteBrain(Controler controler){
        super(controler);
        this.ColorSelet(ChessBoard.WhitePawn);
    }
    public WhiteBrain(long[] TwoBitBoardClone,StructBoard structBoard,Node root){
        super(TwoBitBoardClone,structBoard);
        this.root = root;
        this.currentSearchNode = root;
    }
    public void Update(long[] TwoBitBoardClone,StructBoard structBoard,Node root){
        this.TwoBitBoard = TwoBitBoardClone;
        this.structBoard = structBoard;
        this.root = root;
        this.currentSearchNode = root;
    }

    @Override
    public void MoveSignal(Node node) {
        
    }
}
