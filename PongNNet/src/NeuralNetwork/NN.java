/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package NeuralNetwork;

import java.io.IOException;
import java.util.Arrays;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.Perceptron;
import org.neuroph.nnet.learning.PerceptronLearning;
import org.neuroph.util.TrainingSetImport;
import org.neuroph.util.data.norm.Normalizer;

/**
 *
 * @author vegar
 */
public class NN implements Normalizer{   
    private NeuralNetwork neuralNetwork;
    private PerceptronLearning learningRule;
    private DataSet trainingSet;
    private final int inputCount;
    private final int outputCount;
    private final String fileName;
    private int generation;

    public NN(){
        inputCount = 4; //Position pad, ball (x,y) score
        outputCount= 1;
        fileName = "data.txt";
        neuralNetwork = new Perceptron(inputCount,outputCount); //Input: paddle, ball x, ball y, (poeng?)
        learningRule = (PerceptronLearning) neuralNetwork.getLearningRule();
        learningRule.setLearningRate(0.5); //vet ikke om disse verdiene er korrekte     
        generation = 0;
    }
 
    /**
     * Mulig løsning; skrive data fra et spill til en fil, NN lærer datasettet før neste spill
     */
    public void trainNN(){
        System.out.println("Learning");
        System.out.print(trainingSet);
        
//        System.out.println("EMUALTE TRAINING");
        neuralNetwork.learn(trainingSet);        
        System.out.println("Successfully learned data.");
        
        test();
    }
    
    public void trainingSet() {
        System.out.println("Loading training data from: " + fileName);
        try{
            trainingSet = TrainingSetImport.importFromFile(fileName, inputCount, outputCount, ",");
        }
        catch (IOException | NumberFormatException e) {
            System.out.print("Error: ");
            System.out.println(e);
        }
        System.out.println("Successfully loaded data.");
    }
    
    /**
     * 0 = ball is lower than paddle
     * 0.5 = ball is higher than paddle
     * 1 = ball hits the paddle
     * @return 
     */
    public double playPong(){
        return 0;
    }

    @Override
    public void normalize(DataSet dataSet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public void test() {
        for(DataSetRow dataRow : trainingSet.getRows()) {
            neuralNetwork.setInput(dataRow.getInput());
            neuralNetwork.calculate();
            double[ ] networkOutput = neuralNetwork.getOutput();
            System.out.println("Input: " + Arrays.toString(dataRow.getInput()) );
            System.out.println(" Output: " + Arrays.toString(networkOutput) );
        }     
    }
    
}
