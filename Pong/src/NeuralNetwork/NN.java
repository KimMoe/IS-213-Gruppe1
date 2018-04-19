/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NeuralNetwork;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.Perceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TrainingSetImport;

/**
 *
 * @author vegar
 */
public class NN {
    private NeuralNetwork neuralNetwork;
    private MomentumBackpropagation learningRule;
    private DataSet trainingSet;
    private int inputCount;
    private int outputCount;
    private String fileName;

    public NN() {
        inputCount = 3;
        outputCount= 1;
        fileName = "data.txt";
        neuralNetwork = new Perceptron(inputCount,outputCount); //Input: paddle, ball x, ball y, (poeng?)
        learningRule = (MomentumBackpropagation) neuralNetwork.getLearningRule();
        trainingSet();
    }   
    
    private void learningRule(){
        learningRule.setLearningRate(0.5); //vet ikke om disse verdiene er korrekte
        learningRule.setMomentum(0.8); 
    }
 
    /**
     * Mulig løsning; skrive data fra et spill til en fil, NN lærer datasettet før neste spill
     */
    private void trainNN(){
        neuralNetwork.learn(trainingSet);
    }
    
    private void trainingSet(){
        try{
            trainingSet = TrainingSetImport.importFromFile(fileName, inputCount, outputCount, ",");
        }
        catch (FileNotFoundException ex){
            System.out.println("File " + fileName + " not found");
        }
        catch (IOException|NumberFormatException ex) {
            System.out.println("Error reading file or bad number format!");
        }
    }
}
