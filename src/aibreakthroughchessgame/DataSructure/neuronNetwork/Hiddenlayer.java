/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame.DataSructure.neuronNetwork;

import aibreakthroughchessgame.DataSructure.neuronNetwork.Neuron;
import aibreakthroughchessgame.DataSructure.neuronNetwork.layer;

/**
 *
 * @author user
 */
public class Hiddenlayer extends layer{

    public Hiddenlayer(int neurons) {
        super(neurons);
    }

    @Override
    public void addNeuron(Neuron neuron) {
        int[] weight = new int[this.nextlayer.neuronArray.length];
        int[] bias   = new int[this.nextlayer.neuronArray.length];
        for(int i = 0;i<weight.length;i++){
            weight[i] = layer.random.nextInt()%100;
            bias[i] = layer.random.nextInt()%30;
        }
        neuron.setWeight(weight);
        neuron.setBias(bias);
        neuron.setNextlayer(this.nextlayer);
        this.neuronArray[pointer] = neuron;
        pointer ++;
    }
}
