/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pongnnreborn;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * This class is responsible for repainting or rendering the pong frame. 
 * 
 * @author Group 1
 */
public class Rendering extends JPanel {
    
    @Override
    protected void paintComponent(Graphics g){        
        
        super.paintComponent(g);

        Pong.pong.render((Graphics2D) g);
    }
}
