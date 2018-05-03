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
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TrainingSetImport;
import org.neuroph.util.TransferFunctionType;
import pongnnet.PongNNet;

/**
 *
 * @author vegar
 */
public class NN{   
    private NeuralNetwork neuralNetwork;
    private MomentumBackpropagation learningRule;
    private DataSet trainingSet;
    
    private final int inputCount;
    private final int outputCount;
    private final int hiddenLayers;
    
//    private final String fileName;   
    
    private int generation;
    
    private PongNNet pong;

    /**
     * 
     * @param pong 
     */
    public NN(PongNNet pong){
//        fileName = "data.txt";

        //Variables
        inputCount = 2; //Input: Pos y ball, Pos y paddle
        outputCount = 1; //Desired output: 
        hiddenLayers = 10; //Hiden layers
        generation = 0; //Generation
               
        //Learning type
        neuralNetwork = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, inputCount, hiddenLayers, outputCount);
        learningRule = (MomentumBackpropagation) neuralNetwork.getLearningRule();
        
        //Learning Rules/Constraints
        learningRule.setLearningRate(0.5); 
        learningRule.setMomentum(0.7);
        learningRule.setMaxError(0.01);
        
        this.pong = pong;
    }
    
    /**
     * 
     */
    public void trainNN(){
        System.out.print("Learning from DataSet...   ");
        
        neuralNetwork.learn(trainingSet); 
        
        System.out.println(" Successfully learned data.");
        
        getCalculatedOutput();
    }
    
    /**
     * 
     */
    public void trainingSet() {
//      trainingSet = TrainingSetImport.importFromFile(fileName, inputCount, outputCount, ",");   
        
        System.out.print("Loading DataSet...    ");
        trainingSet = new DataSet(2, 1);
        trainingSet.addRow(new DataSetRow(new double[]{pong.ball.ballY, pong.ball.paddleY}, new double[]{pong.ball.desiredOut}));
        System.out.println(" Successfully loaded data.");
    }

    /**
     * 
     * @return 
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * 
     * @param generation 
     */
    public void setGeneration(int generation) {
        this.generation = generation;
    }
    
    /**
     * 
     */
    public void getCalculatedOutput() {
        for(DataSetRow dataRow : trainingSet.getRows()) {
            neuralNetwork.setInput(dataRow.getInput());
            neuralNetwork.calculate();
            double[] networkOutput = neuralNetwork.getOutput();
            System.out.println("Input: " + Arrays.toString(dataRow.getInput()));
            System.out.println("Output: " + Arrays.toString(networkOutput));
            playPong();
        }
    }
       
    /**
     *
     * 
     */
    public void playPong(){
        double[] output = neuralNetwork.getOutput();
        double o = output[0];
        if (o < 0.9){
            pong.nnRelease('s');
            pong.nnTypes('w');
        } else{
            pong.nnRelease('w');
            pong.nnTypes('s');
        }        
    }   
}
