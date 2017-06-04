/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame;

/**
 *
 * @author user
 */
public class ChessBoard {
    private int[] Board;
    public static final int Nothing   = 0;
    public static final int WhitePawn = 1;
    public static final int BlackPawn = 2;
    public ChessBoard(){
        Board = new int[64];
    }
}
