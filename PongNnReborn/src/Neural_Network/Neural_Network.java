/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Neural_Network;

import java.util.Arrays;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

import pongnnreborn.Pong;
import pongnnreborn.Ball;

/**
 *
 * @author Tonnes
 */
public class Neural_Network {
    
    NeuralNetwork nnet;
    MomentumBackpropagation learningRule;
    DataSet dataSet;
    
    public static int generation = 0;
    
    //Layers
    private final int inputs = 2;
    private final int outpus = 1;
    private final int hiddens = 5;
    
    //Inputs
    static int input_1;
    static int input_2;
    static double desOutput;
    static double output;
    
    private double[] outputArray;
    
    private Pong pongNN; 
    private Ball ballNN;
    
    public Neural_Network(Pong pong, Ball ball) {
        //Creating the neural network, and setting constraints and rules.
        nnet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, inputs, hiddens, outpus);
        learningRule = (MomentumBackpropagation) nnet.getLearningRule();
        
        learningRule.setLearningRate(0.5); 
        learningRule.setMomentum(0.7);
        learningRule.setMaxError(0.01);            
        
        this.pongNN = pong;
        this.ballNN = ball;
    }
    
    public void getData() {
        input_1 = Ball.ballY;
        input_2 = Ball.paddleY_NN;
        
        if (input_1 < 0) {
            input_1 = 0;
        }
        if (input_2 < 0) {
            input_2 = 0;
        }
        
        desOutput = Ball.desiredOutput; 
    }
    
    public void startNN() {
        createDataSet();
    }
    
    public void createDataSet() {
        getData();
        
        dataSet = new DataSet(2, 1);
        
        System.out.print("Creating DataSet...     ");              
        dataSet.addRow(new DataSetRow(new double[]{input_1, input_2}, new double[]{desOutput}));
        System.out.println("Data Createad!");
        
        teachNeuralNetwork();
    }
    
    public void teachNeuralNetwork() {
        System.out.println("Learning data...");
        nnet.learn(dataSet);       
        System.out.println("Data Learnt!");
        
        generation++;
        
        calculateOutput();
    }
    
    public void calculateOutput() {
        
        
        for(DataSetRow dataRow : dataSet.getRows()) {                       
            nnet.setInput(dataRow.getInput());           
            nnet.calculate();
            
            outputArray = nnet.getOutput();     
            output = outputArray[0];
            
            System.out.println(output);
            //System.out.println(desOutput);
            //System.out.println(learningRule.getCurrentIteration());
            
            playPong(output);
        }
    }
    
    public void playPong(double output) {
        if (output < 0.5) {
            pongNN.nnRelease('s'); //FIX
            pongNN.nnPress('w');
        } else {
            pongNN.nnRelease('w');
            pongNN.nnPress('s');
        }    
    }
    
    public double getNeuralNetwork_Output() {
        return output;
    }
    
    public double getInput_1() {
        return input_1;
    }
   
    public double getInput_2() {
        return input_2;
    }
    
    public double getDesOutput() {
        return desOutput;
    }
    
    public int getGeneration() {
        return generation;
    }
}
