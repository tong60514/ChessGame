/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame.DataSructure.neuronNetwork;

/**
 *
 * @author user
 */
public class network {
    private layer[] Layers;
    private int pointer = 1;
    private Inputlayer inputlayer;
    private Outputlayer outputlayer;
    public network(int inputSize , int outputSize){
        inputlayer = new Inputlayer(inputSize);
        outputlayer = new Outputlayer(outputSize);
    }
    public void addLayer(Hiddenlayer hiddenlayer){
        this.Layers[pointer-1].nextlayer =  hiddenlayer;
        this.Layers[pointer] = hiddenlayer;
        pointer++;
        
    }
}
