/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame.DataSructure.neuronNetwork;

import aibreakthroughchessgame.DataSructure.Lambda_activate;
import aibreakthroughchessgame.DataSructure.neuronNetwork.layer;

/**
 *
 * @author user
 */
public class Neuron {
    public static Lambda_activate ReLU = (sum)->{
        return (sum>0)?sum:0;
    };
    private int[] weight;
    private int[] bias;
    private int output;
    private int input = 0;
    private layer nextlayer;
    
    private layer superlayer;
    private Lambda_activate activate;
    public Neuron(){
        this.activate = ReLU;
    }
    public void setActivate(Lambda_activate activate){
        this.activate = activate;
    }
    public void setWeight(int[] weight){
        this.weight = weight;
    }
    public void setBias(int[] bias){
        this.bias = bias ;
    }
    public void input(int input){
        this.input += input;
    }
    public void output(){
        for(int i=0;i<nextlayer.neuronArray.length;i++){
            forwardpropagation( i , ReLU.activate(input*weight[i]+bias[i]) );
        }
    }
    private void forwardpropagation(int index, int value ){
        this.nextlayer.neuronArray[index].input(value);
    }
    public void setNextlayer(layer nextlayer){
        this.nextlayer = nextlayer;
    }
    
}
