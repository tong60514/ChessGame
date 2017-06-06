/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame.DataSructure;

import aibreakthroughchessgame.AIBrain;
import aibreakthroughchessgame.Controler;

/**
 *
 * @author user
 */
public interface Predictor {
    public AIBrain predict(Controler controler , Node node);
}
