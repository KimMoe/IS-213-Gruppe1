/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author Tonnes
 */
public class Rendering2 extends JPanel {
    public Rendering2() {
    
    }
    
    /**
    * 
    * @param g 
    */
    @Override
    protected void paintComponent(Graphics g){        
        
        super.paintComponent(g);

        graphics.NnView.render((Graphics2D) g);
    }
}
