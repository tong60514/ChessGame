/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame;

import aibreakthroughchessgame.DataSructure.Node;
import aibreakthroughchessgame.DataSructure.StructBoard;
import java.util.LinkedList;

/**
 *
 * @author user
 */
public class AIConcurrency extends AIBrain {
    private AIBrain superbrain;
    private LinkedList<Node> branches;
    public AIConcurrency(AIBrain superbrain,long[] TwoBitBoard,StructBoard structBoard ,int depthLeft){
        super(TwoBitBoard,structBoard);
        this.superbrain = superbrain;
        this.root = superbrain.getCurrentSearch();
        this.currentSearchNode = superbrain.getCurrentSearch();
        this.depth = depthLeft;
    }
    public void Excute(){
        
    }
    @Override
    public void run(){
        this.expandBranches(this.root);
        LinkedList<Node> f = IterativeAlphaBetaNegaMax(this.root.getNodes(),depth);
        this.root.setBranchNode(f);
        this.root.setNodeScore(-f.getFirst().getNodeScore());
        superbrain.addBranches(this.root);
        this.interrupt();
    }
    
}
