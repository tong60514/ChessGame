/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame.DataSructure.neuronNetwork;

import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author user
 */
public abstract class layer {
    public static final Random random = new Random();
    protected Neuron[] neuronArray;
    protected layer nextlayer;
    protected layer superlayer;
    protected int pointer = 0;
    public  layer(int neurons){
        neuronArray = new Neuron[neurons];
    }
    public abstract void addNeuron(Neuron neuron);
    public Neuron[] getNeurons(){
        return this.neuronArray;
    }
}
