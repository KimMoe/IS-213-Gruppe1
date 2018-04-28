/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package NeuralNetwork;

import java.io.IOException;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.Perceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TrainingSetImport;
import org.neuroph.util.data.norm.Normalizer;

/**
 *
 * @author vegar
 */
public class NN implements Normalizer{   
    private NeuralNetwork neuralNetwork;
    private MomentumBackpropagation learningRule;
    private DataSet trainingSet;
    private int inputCount;
    private int outputCount;
    private String fileName;

    public NN(){
        inputCount = 3; //Position pad, ball and score
        outputCount= 1; //If the pad should go up or down
        fileName = "data.txt";
        neuralNetwork = new Perceptron(inputCount,outputCount); //Input: paddle, ball x, ball y, (poeng?)
        learningRule = (MomentumBackpropagation) neuralNetwork.getLearningRule();
        learningRule.setLearningRate(0.5); //vet ikke om disse verdiene er korrekte
        learningRule.setMomentum(0.8);
        trainingSet();       
    }
 
    /**
     * Mulig løsning; skrive data fra et spill til en fil, NN lærer datasettet før neste spill
     */
    private void trainNN(){
        neuralNetwork.learn(trainingSet);     
    }
    
    private void trainingSet() {
        try{
            trainingSet = TrainingSetImport.importFromFile(fileName, inputCount, outputCount, ",");
        }
        catch (IOException | NumberFormatException e) {
            System.out.print("Error: ");
            System.out.println(e);
        }
    }

    @Override
    public void normalize(DataSet dataSet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
