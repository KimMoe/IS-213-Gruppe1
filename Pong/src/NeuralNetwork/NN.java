/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NeuralNetwork;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.Perceptron;

/**
 *
 * @author vegar
 */
public class NN {
    private NeuralNetwork neuralNetwork;

    public NN() {
        neuralNetwork = new Perceptron(2,1);
        
        
    }
    
    
}
