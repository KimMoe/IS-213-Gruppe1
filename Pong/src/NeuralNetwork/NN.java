/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NeuralNetwork;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.Perceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

/**
 *
 * @author vegar
 */
public class NN {
    private NeuralNetwork neuralNetwork;
    private MomentumBackpropagation learningRule;

    public NN() {
        neuralNetwork = new Perceptron(2,1);
        learningRule = (MomentumBackpropagation) neuralNetwork.getLearningRule();
        
    }   
    
    private void learningRule(){
        learningRule.setLearningRate(0.5); //vet ikke om disse verdiene er korrekte
        learningRule.setMomentum(0.8); 
        
    }
    
}
