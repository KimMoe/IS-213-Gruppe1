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
 * This is the neural network. It deals with gathering input, create file, load file,  
 * creating datasets, learning from data sets and calculating output.
 * 
 * @author Group 1
 */
public class Neural_Network {
    
    NeuralNetwork nnet;
    MomentumBackpropagation learningRule;
    DataSet dataSet;
    DataSetRow dataRow;
    
    BufferedWriter writer;
    
    //Generation integer. Goes up by one, every time the neural network learns.
    public static int generation = 0;   
    
    //File location
    private final String file = "data.txt";
    
    //If you are going to use load from file or not.
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
    
    //The calculated output being put in an array.
    private double[] outputArray;
      
    private Pong pongNN; 
    private Ball ballNN;
    
    /**
     * Creating the neural network, and setting constraints and rules.
     * 
     * @param pong The current game
     * @param ball The current ball
     */
    public Neural_Network(Pong pong, Ball ball) {
        nnet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, inputs, hiddens, outputs);
        learningRule = (MomentumBackpropagation) nnet.getLearningRule();
        
        learningRule.setLearningRate(0.2); 
        learningRule.setMomentum(0.9);
        learningRule.setMaxError(0.01);            
        
        dataSet = new DataSet(inputs, outputs);
        
        this.pongNN = pong;
        this.ballNN = ball;
    }
    
    /**
     * Method that returns the desired output.
     * 
     * @return Returns the desired output
     */
    public double getdesOutput() {
        return desOutput = Ball.desiredOutput;
    }
    
    /**
     * Begins the processes/methods necessary to run the neural network. 
     * 
     */
    public void startNN() {
        if (fromFileEnabled) {  //Dosen't realy work that well.
            loadDataFromFile(file);
        } else {
            createDataSet();
        }
    }
    
    /**
     * If there exist a dataset, it will load or else it will create a file for the datasets.
     * 
     */
    public void clearDataSet() {
        dataSet = new DataSet(inputs, outputs);
    }
    
    /**
     * Create a dataset based in the posisoning of ball and paddle, stores it and use it for training of the neural network.
     * 
     */
    public void createDataSet() {           
        System.out.print("Creating DataSet...     ");
        
        dataSet.addRow(new DataSetRow(new double[]{getInput_1(), getInput_2()}, new double[]{getdesOutput()}));  
               
        System.out.print(dataSet);               
        System.out.println("Data Createad!");
        
        teachNeuralNetwork();      
    }
    
    /**
     * Uses the dataset for training and updates the generation of the neural network by one.
     * 
     */
    public void teachNeuralNetwork() {
        System.out.print("Learning data...       ");
        
        nnet.learn(dataSet);     //STOPS WHEN THERE IS A 1.0 IN THE TXT FILE.
        
        System.out.println("Data Learnt!");
        
        generation++; 
        
        calculateOutput(); //-> playPong(output);
    }
    
    /**
     * Uses the input and training sets to calculate what the desired output will be.
     * 
     */
    public void calculateOutput() {      
        System.out.print("Output getting calculated...      ");

        System.out.print(dataSet);
        
        for (int i = 0; i < dataSet.size(); i++) { 
            dataRow = dataSet.getRowAt(i);
            nnet.setInput(dataRow.getInput());
            nnet.calculate();
            
            outputArray = nnet.getOutput();     
            output = outputArray[0];            
            
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
    }
    
    /**
     * Decides what the outcome of the different outputs are.
     * 
     * @param output 
     */
    public void playPong(double output) {
        if (output < 0.5) {
            pongNN.nnRelease('s');
            pongNN.nnPress('w');
        } else {
            pongNN.nnRelease('w');
            pongNN.nnPress('s');
        }
    }
    
    /**
     * Loades training data from a file.
     * 
     * @param file file location
     */
    public void loadDataFromFile(String file) {
        fromFileEnabled = true;
        System.out.print("Loading dataSet from file...       ");
        try {   
            dataSet = TrainingSetImport.importFromFile(file, inputs, outputs, ",");
            System.out.println(dataSet);
        } catch (NumberFormatException | IOException ex) {
            System.out.println(ex);
        }
        System.out.println("Finished loading dataSet from file!");
        teachNeuralNetwork();
    } 

    
    /**
     * Not currently in use.
     * 
     * 
     * Writes data to file, for use later.
     * Writes: Input1,Input2,DesiredOutput to a txt file.
     * 
     * @param filename
     */
    public void writeToFile(String filename) {       
        try {
            writer = new BufferedWriter(new FileWriter(filename, true));
            writer.write(getInput_1() + "," + getInput_2() + "," + getdesOutput());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
        //System.out.println("Written one line!");
    }  
    
    /**
     * Method to return the output of the neural network
     * 
     * @return Output returns the output of the neural network, as it is in this class.
     */
    public double getNeuralNetwork_Output() {
        return output;
    }   
      
    /**
     * Method that sets and returns input 1.
     * 
     * @return Input 1 is the ball's Y-position.
     */
    public double getInput_1() {
        input_1 = Ball.ballY;
        
        if (input_1 < 0) {
            input_1 = 0;
        }   
        
        return input_1;
    }    
    
    /**
     * Method that sets and returns input 2.
     * 
     * @return Input 1 is the paddle's Y-position.
     */
    public double getInput_2() {
        input_2 = Ball.paddleY_NN;
        
        if (input_2 < 0) {
            input_2 = 0;
        }
        
        return input_2;
    }
    
    /**
     * Method that returns the neural network generation
     * 
     * @return Generation return the generation at the time of getting it.
     */
    public int getGeneration() {
        return generation;
    }    
}
