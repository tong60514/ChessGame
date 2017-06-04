/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame.DataSructure;

import aibreakthroughchessgame.ChessBoard;
import aibreakthroughchessgame.DataSructure.LeaveChess;
import aibreakthroughchessgame.Transform;
import java.util.LinkedList;

/**
 *
 * @author user
 */
public abstract class Node {
    protected int index;
    protected int toIndex;
    protected int WhosTurn;
    protected int NodeScore;
    protected Node superNode;
    protected LinkedList<Node> Nodes;
    public Node(Node superNode,int index,int toIndex,int Color){
        this.superNode = superNode;
        this.index = index;
        this.toIndex = toIndex;
        this.WhosTurn = Color;
        Nodes= new LinkedList();
    }
    public final int getColor(){
        return this.WhosTurn;
    }
    public final int getSrc(){
        return this.index;
    }
    public final int getTarget(){
        return this.toIndex;
    }
    public void setNodeScore(int score){
        this.NodeScore = score;
    }
    public void setBranchNode(LinkedList<Node> stepList){
        this.Nodes = stepList;
    }
    public int getNodeScore(){
        return this.NodeScore;
    }
    public static void GenerateMove(int index,int toIndex,Node SuperNode){
        SuperNode.Nodes.add(new MoveNode(SuperNode,index,toIndex,3-SuperNode.WhosTurn) );
    }
    public static void GenerateCapture(int index,int toIndex,Node SuperNode){
        SuperNode.Nodes.add(new CaptureNode(SuperNode,index,toIndex,3-SuperNode.WhosTurn) );
    }
    public final LinkedList<Node> getNodes(){
        return this.Nodes;
    }
    public final Node getSuper(){
        return this.superNode;
    }
    public abstract void searchNode (StructBoard StructBoard,long[] board);
    public abstract void backtrack (StructBoard StructBoard,long[] board);
    
}
