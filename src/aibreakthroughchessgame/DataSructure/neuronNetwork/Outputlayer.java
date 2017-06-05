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
public class Outputlayer extends layer{

    public Outputlayer(int neurons) {
        super(neurons);
    }

    @Override
    public void addNeuron(Neuron neuron) {
        this.neuronArray[pointer] = neuron;
        pointer ++;
    }
    
    
}
