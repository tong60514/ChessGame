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
public interface HintItem {
    public void hint();
    public void unhint();
    public boolean tryHint();
}
