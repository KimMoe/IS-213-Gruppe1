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
 *
 * @author Tonnes
 */
public class Neural_View_Rendering extends JPanel {        
    @Override
    protected void paintComponent(Graphics g){        
        
        super.paintComponent(g);

        Neural_View.render((Graphics2D) g);
    }
}
