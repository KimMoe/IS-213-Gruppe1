/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import NeuralNetwork.NN;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;
import org.neuroph.core.data.DataSet;
import pongnnet.Ball;

/**
 *
 * @author Tonnes
 */
public class NnView implements ActionListener {   
    static int width = 500; //Width of frame
    static int height = 400; //Height of frame
    int xTime = 1000; //Numbers of Milliseconds the frame will update.   
    static int generation; //Neural Network data:
    
    static double[] NNoutput; 
    static double output;
    static double input1;
    static double input2;
    static double desiredOutput;

    static DataSet input;
    
    private JFrame frame; //New JFrame
    private Timer timer; //New Swing Timer
    
    Rendering2 rendery;
    NeuralNetwork.NN nn;
    pongnnet.PongNNet pong;
    
    /**
     * 
     */
    public NnView() {
        rendery = new Rendering2();
        nn = new NeuralNetwork.NN(pong);   
        
        timer = new Timer(xTime, this); //Re-renders every xTime in milliseconds;        
        frame = new JFrame("Neural Network View");
        
        frame.setSize(width, height);
        frame.add(rendery);
        frame.setResizable(false);
        
        removeMinMaxClose(frame);
        
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //Changed 04.05.2018
        
        timer.start();
    }
    
    /**
     * Remove Min/Max buttons from title bar.
     * Added 04.05.2018
     * 
     * Not working as intended.
     * 
     * SOURCE: https://coderanch.com/t/344419/java/deactivate-close-minimise-resizable-window
     * 
     * @param comp 
     */
    public void removeMinMaxClose(Component comp) {
        if(comp instanceof JButton) {
            String accName = ((JButton) comp).getAccessibleContext().getAccessibleName();
            System.out.println(accName);
            if(accName.equals("Maximize") || accName.equals("Iconify") || accName.equals("Close")) { 
                comp.getParent().remove(comp); 
            }
        }
        if (comp instanceof Container) {
            Component[] comps = ((Container)comp).getComponents();
            for(int x = 0, y = comps.length; x < y; x++) {
                removeMinMaxClose(comps[x]);
            }
        }
    }
    
    //The variables are null. 
    /**
     * 
     */
    public static void getData() {       
        generation = NN.getGeneration();
        
        input1 = Ball.ballY;
        input2 = Ball.paddleY;
        desiredOutput = Ball.desiredOut ;
        
        NNoutput = NN.getNeuralNetworkOutput();
        output = NNoutput[0];
    }
    
    /**
     * 
     * @param g 
     */
    public static void render(Graphics2D g) {     
        //Gets neural network data.
        getData();
        
        //Adding anti aliasing, so that edges looks smoother.
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);               
        
        //Setting stroke (Borders)
        g.setStroke(new BasicStroke(2));
        
        //AWT:
        //x, y, sizeX, sizeY
        
        //INPUT
        //Input circles - red
        g.setColor(Color.red);
        g.fillOval(10, 40, 75, 75); //Input 1
        g.fillOval(10, 230, 75, 75); //Input 2  
        
        //Input circles borders - black
        g.setColor(Color.black);
        g.drawOval(10, 40, 75, 75); //Input 1 border
        g.drawOval(10, 230, 75, 75); //Input 2 border
        
        
        //OUTPUT
        //Output circles - red
        g.setColor(Color.red);
        g.fillOval(400, 145, 75, 75); //Output  
        
        //Output circles borders - black
        g.setColor(Color.black);
        g.drawOval(400, 145, 75, 75); //Output border
        
        
        //HIDDEN LAYER
        //Hidden layer circles - red
        g.setColor(Color.red);
        g.fillOval(220, 20, 55, 55); //Hidden layer nodes
        g.fillOval(220, 90, 55, 55);
        g.fillOval(220, 160, 55, 55);
        g.fillOval(220, 230, 55, 55);
        g.fillOval(220, 300, 55, 55);
        
        //Hidden layer circle borders - black
        g.setColor(Color.black);
        g.drawOval(220, 20, 55, 55); //Hidden layer nodes borders
        g.drawOval(220, 90, 55, 55);
        g.drawOval(220, 160, 55, 55);
        g.drawOval(220, 230, 55, 55);
        g.drawOval(220, 300, 55, 55); 
        
        //Text for hidden layer
        g.setFont(new Font("Arial", 1, 13));        
        g.drawString("Hidden Layer", 205, 15);
        
        
        //GENERATIONS
        //Text for Generation - black
        g.setFont(new Font("Arial", 1, 20));      
        g.drawString("Generation: " + generation, 10, 350);
        
        
        //INPUT
        //Text for Inputs - black
        g.setFont(new Font("Arial", 1, 18));
        g.drawString("Input 1", 17, 30);
        g.drawString("Input 2", 17, 220); 
        
        //Text for Input values - white
        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 15));
        g.drawString("" + input1, 30, 83);     
        g.drawString("" + input2, 30, 273);
        
        
        //DESIRED OUTPUT
        //Text for Desired Output - black
        g.setColor(Color.black);
        g.drawString("Desired", 405, 118);
        g.drawString("Output", 405, 138);
        
        //Text for Desired Output values - white
        g.setColor(Color.white);
        g.setFont(new Font("Arial",1,30));
        g.drawString("" + desiredOutput, 414, 193);
        
        
        //OUTPUT
        //Text for Output - black
        g.setFont(new Font("Arial", 1, 15));
        g.setColor(Color.black);
        
        g.drawString("Output", 330, 305);
        g.drawRect(330, 310, 151, 41); //Output rect border - black      
        g.setColor(Color.red);
        g.fillRect(331, 311, 150, 40); //Output rect - red  
        
        //Value for Output
        g.setColor(Color.white);
        g.drawString("" + output, 335, 335);
        
        
        //LINES
        //Lines - black
        g.setColor(Color.black);       
        g.setStroke(new BasicStroke(1));
        
        //INPUT 1 -> HIDDEN NODES
        g.drawLine(86, 77, 220, 50); //Input 1 -> Hidden Node 1
        g.drawLine(86, 77, 220, 115); //Input 1 -> Hidden Node 2
        g.drawLine(86, 77, 220, 185); //Input 1 -> Hidden Node 3
        g.drawLine(86, 77, 220, 255); //Inout 1 -> Hidden Node 4
        g.drawLine(86, 77, 220, 320); //Input 1 -> Hidden Node 5     
        
        //INPUT 2 -> HIDDEN NODES
        g.drawLine(86, 270, 220, 50); //Input 2 -> Hidden Node 1
        g.drawLine(86, 270, 220, 115); //Input 2 -> Hidden Node 2
        g.drawLine(86, 270, 220, 185); //Input 2 -> Hidden Node 3
        g.drawLine(86, 270, 220, 255); //Inout 2 -> Hidden Node 4
        g.drawLine(86, 270, 220, 320); //Input 2 -> Hidden Node 5    
        
        //HIDDEN NODES -> OUTPUT
        g.drawLine(275, 50, 400, 185); //Hidden node 1 -> Output
        g.drawLine(275, 115, 400, 185); //Hidden node 2 -> Output
        g.drawLine(275, 185, 400, 185); //Hidden node 3 -> Output
        g.drawLine(275, 255, 400, 185); //Hidden node 4 -> Output
        g.drawLine(275, 320, 400, 185); //Hidden node 5 -> Output
    }

    /**
     * 
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        rendery.repaint();  
    }
}
