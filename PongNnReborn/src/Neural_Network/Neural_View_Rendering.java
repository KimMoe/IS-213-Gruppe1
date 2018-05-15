/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Neural_Network;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * This class repaints or renders the neural network view frame. 
 * 
 * @author Group 1
 */
public class Neural_View_Rendering extends JPanel {        
    @Override
    protected void paintComponent(Graphics g){        
        
        super.paintComponent(g);

        Neural_View.render((Graphics2D) g);
    }
}
