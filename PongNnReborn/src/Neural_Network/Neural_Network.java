/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Neural_Network;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TrainingSetImport;
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
    DataSetRow dataRow;
    
    BufferedWriter writer;
    
    public static int generation = 0;   
    private final int batchSize = 50;
    private final int epochSize = 0;
    
    private int counter = 0;
    
    private final String file = "data.txt";
    
    public static boolean fromFileEnabled = false; //Files does not work atm.
    
    //Layers
    private final int inputs = 2;
    private final int outputs = 1;
    private final int hiddens = 5;
    
    //Inputs
    static int input_1;
    static int input_2;
    static double desOutput;
    static double output;
    
    private double[] outputArray;
      
    private Pong pongNN; 
    private Ball ballNN;
    
    /**
     * Change so that the neural network saves more data, before learning it. Slowing the learn process, lessening the data power needed. And making it a bit more managable.
 https://stackoverflow.com/questions/4752626/batchSize-vs-iteration-when-training-neural-networks?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
     * 
     * 
     * 
     * @param pong
     * @param ball 
     */
    public Neural_Network(Pong pong, Ball ball) {
        //Creating the neural network, and setting constraints and rules.
        nnet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, inputs, hiddens, outputs);
        learningRule = (MomentumBackpropagation) nnet.getLearningRule();
        
        learningRule.setLearningRate(0.2); 
        learningRule.setMomentum(0.9);
        learningRule.setMaxError(0.01);            
        
        dataSet = new DataSet(inputs, outputs);
        
        this.pongNN = pong;
        this.ballNN = ball;
    }
    
    public double getdesOutput() {
        return desOutput = Ball.desiredOutput;
    }
    
    public void startNN() {
        //writeToFile();
        
        if (fromFileEnabled) {
            loadDataFromFile(file);
        } else {
            createDataSet();
        }
    }
    
    public void clearDataSet() {
        dataSet = new DataSet(inputs, outputs);
    }
    
    public void createDataSet() {   
        System.out.print("Creating DataSet...     ");
        dataSet.addRow(new DataSetRow(new double[]{getInput_1(), getInput_2()}, new double[]{getdesOutput()}));  
            
        //System.out.println(learningRule.getCurrentIteration());
        System.out.print(dataSet);
        //System.out.println(dataSet.size());
        System.out.println("Data Createad!");      
        teachNeuralNetwork();
    }
    
    public void teachNeuralNetwork() {
        System.out.print("Learning data...       ");
        
        //nnet.randomizeWeights(random);
        nnet.learn(dataSet); 
        
        System.out.println("Data Learnt!");
        
        generation++; 
        
        calculateOutput(); //-> playPong(output);
    }
    
    public void calculateOutput() {      
        System.out.print("Output getting calculated...      ");

        System.out.print(dataSet);
        
        for (int i = 0; i < dataSet.size(); i++) { 
            dataRow = dataSet.getRowAt(i);
            nnet.setInput(dataRow.getInput());
            nnet.calculate();
            
            outputArray = nnet.getOutput();     
            output = outputArray[i];            
                      
            System.out.println(i);
            playPong(output);   
            if (!fromFileEnabled) {
                clearDataSet();
            }
        }
        System.out.println("Ouput calculated!");
        
        if (fromFileEnabled) {
            clearDataSet();
            fromFileEnabled = false;
        } 
        
        //OLD MAYBE 
//        for(DataSetRow dataRow : dataSet.getRows()) {                       
//            nnet.setInput(dataRow.getInput());           
//            nnet.calculate();
//            
//            outputArray = nnet.getOutput();     
//            output = outputArray[0];
//            
//            
//            //System.out.println("NN Output: " + output);
//            //System.out.println(desOutput);
//            //System.out.println(learningRule.getCurrentIteration());
//            //System.out.println(Arrays.toString(nnet.getWeights()));
//            
//            
//            //System.out.println(counter++);
//            playPong(output);
//            clearDataSet();
//            
//            
//        }        
    }
    
    public void playPong(double output) {
        if (output < 0.5) {
            pongNN.nnRelease('s');
            pongNN.nnPress('w');
        } else {
            pongNN.nnRelease('w');
            pongNN.nnPress('s');
        }
    }
    
    //Not in use.
    public void loadDataFromFile(String file) {
        fromFileEnabled = true;
        System.out.print("Loading dataSet from file...       ");
        try {   
            dataSet = TrainingSetImport.importFromFile(file, inputs, outputs, ",");
        } catch (NumberFormatException | IOException ex) {
            System.out.println(ex);
        }
        System.out.println("Finished loading dataSet from file!");
        teachNeuralNetwork();
    } 

    //Not in use.
    public void writeToFile() {       
        try {
            writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(getInput_1() + "," + getInput_2() + "," + getdesOutput());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
        //System.out.println("Written one line!");
    }  
    
    public double getNeuralNetwork_Output() {
        return output;
    }   
       
    public double getInput_1() {
        input_1 = Ball.ballY;
        
        if (input_1 < 0) {
            input_1 = 0;
        }   
        
        return input_1;
    }    
    
    public double getInput_2() {
        input_2 = Ball.paddleY_NN;
        
        if (input_2 < 0) {
            input_2 = 0;
        }
        
        return input_2;
    }
    
    public int getGeneration() {
        return generation;
    }    
}
