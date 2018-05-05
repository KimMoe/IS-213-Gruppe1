/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Neural_Network;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;

import pongnnreborn.Pong;
import pongnnreborn.Ball;

/**
 *
 * @author Tonnes
 */
public class Neural_View implements ActionListener {
     
    static final int viewWidth = 900;
    static final int viewHeight = 700;
    
    static double viewInput_1;
    static double viewInput_2;
    static double viewDesiredOutput;
    static double viewOutput;
    static int viewGeneration;
    
    static Neural_View view;
    static Neural_View_Rendering renderingView;
    static Neural_Network neuralNetwork;
    static Pong pong;
    static Ball ball;
    
    JFrame viewFrame;
    Timer viewTimer;
    
    public Neural_View() {
        renderingView = new Neural_View_Rendering();
        neuralNetwork = new Neural_Network(pong, ball);
        
        viewTimer = new Timer(2, this);
        viewFrame = new JFrame("Neural Network View");
        
        viewFrame.setSize(viewWidth, viewHeight);
        viewFrame.add(renderingView);
        viewFrame.setResizable(false);
        
        viewFrame.setVisible(true);  
        viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        viewTimer.start();
    }
    
    protected static void render(Graphics2D g) {        
        //Get data to display.
        getLiveData();
        
        //Rendering stuff
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setStroke(new BasicStroke(1));
                
        //Background
        g.setColor(Color.white);
        g.fillRect(0, 0, viewWidth, viewHeight);
        
        //Rendering shapes
        renderShapes(g);
        
        //Title
        g.setColor(Color.black);
        g.setFont(new Font("Arial", 1, 30));
        g.drawString("Neural Network Live View", 5, 26);
        
        g.setFont(new Font("Arial", 1, 20));
        //Input node 1 text
        g.drawString("Input 1", 54, 220);
        g.drawString(""+ viewInput_1, 72, 272);
        
        //Input node 2 text
        g.drawString("Input 2", 54, 372);
        g.drawString("" + viewInput_2, 72, 424);
        
        //Hidden layer text
        g.drawString("Hidden layer", 233, 95);
                                                            //Dispaly values of hidden nodes -???
        
        //Output text
        g.drawString("Output", 635, 292);
        g.drawString("" + viewOutput, 635, 340);
        
        //Activation Function text
        g.drawString("Activation", 465, 272);
        g.drawString("Function", 465, 292);
        g.drawString("Sigmoid", 480, 340);
        
        //Generation text
        g.drawString("Generation: " + viewGeneration, 5, 70);
        
        //Desired Output text
        g.drawString("Desired Output: " + viewDesiredOutput, 635, 400);
        
        
//        g.drawString("Desired Output: " + viewDesiredOutput, 105, 60);
//        g.drawString("Output: " + viewOutput, 105, 110);     
        
    
    }
    
    private static void renderShapes(Graphics2D g) {
        g.setColor(Color.getHSBColor(16, 69, 100));
        
        //Input Node 1
        g.fillOval(50, 230, 75, 75);
        //Node 2
        g.fillOval(50, 380, 75, 75);
        
        //Hidden layer Node 1
        g.fillOval(250, 105, 75, 75);
        //Node 2
        g.fillOval(250, 200, 75, 75);
        //Node 3
        g.fillOval(250, 295, 75, 75);
        //Node 4
        g.fillOval(250, 390, 75, 75);
        //Node 5
        g.fillOval(250, 485, 75, 75);
          
        //Output node
        g.fillRect(630, 300, 220, 75);
        
        //SIGMOID Box
        g.setColor(Color.orange);
        g.fillRect(460, 300, 120, 75);        
        
        g.setColor(Color.black);
        
        //Input 1 -> Hidden Node 1
        g.drawLine(124, 269, 250, 145);
        //Node 2
        g.drawLine(124, 269, 250, 240);
        //Node 3
        g.drawLine(124, 269, 250, 335);
        //Node 4
        g.drawLine(124, 269, 250, 430);
        //Node 5
        g.drawLine(124, 269, 250, 525);
        
        //Input 2 -> Hidden Node 1 
        g.drawLine(124, 423, 250, 145);
        //Node 2
        g.drawLine(124, 423, 250, 240);
        //Node 3
        g.drawLine(124, 423, 250, 335);
        //Node 5
        g.drawLine(124, 423, 250, 430);
        //Node 5
        g.drawLine(124, 423, 250, 525);
        
        //Hidden Nodes -> Sigmoid
        //Node 1
        g.drawLine(324, 145, 460, 335);
        //Node 2
        g.drawLine(324, 240, 460, 335);
        //Node 3
        g.drawLine(324, 335, 460, 335);
        //Node 4
        g.drawLine(324, 430, 460, 335);
        //Node 5
        g.drawLine(324, 525, 460, 335);
        
        //Sigmoid -> Output
        g.drawLine(580, 335, 629, 335);
    }


    private static void getLiveData() {
        viewGeneration = neuralNetwork.getGeneration();
        viewInput_1 = neuralNetwork.getInput_1();
        viewInput_2 = neuralNetwork.getInput_2();
        viewDesiredOutput = neuralNetwork.getDesOutput();
        viewOutput = neuralNetwork.getNeuralNetwork_Output();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        renderingView.repaint();
    }
}
