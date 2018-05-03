/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pongnnet;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Tonnes
 */
public class Rendering extends JPanel {
    
    /**
     * 
     */
    public Rendering() {
    
    };
    
    /**
     * 
     * @param g 
     */
    @Override
    protected void paintComponent(Graphics g){        
        
        super.paintComponent(g);
        
        PongNNet.pong.render((Graphics2D) g);
    }
    
}

