/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame.DataSructure;

/**
 *
 * @author user
 */
 public interface Lambda_Generator {
    public int generate(int color ,long Src,int SrcIndex,long[] TwoBitBoard,ChessMove move);
}
