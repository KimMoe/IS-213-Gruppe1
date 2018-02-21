/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Tonnes
 */
public class Paddle {

    public int paddlenumb;
    
    public int x, y, width = 25, height = 250; 
    
    public int score;
    
    public Paddle(Pong pong, int paddlenumb)   {
        
        this.paddlenumb = paddlenumb;
        
        if(paddlenumb == 1) {
            this.x = 0;         
        }
        if(paddlenumb == 2) {
            this.x = pong.width - width;           
        }
        
        this.y = pong.height / 2 - this.height / 2;  
    }

    public void render(Graphics g) {
        
        g.setColor(Color.white);
        g.fillRect(x, y, width, height);
    }

    public void move(boolean up) {
        int speed = 15;
        
        if(up) {
            if (y - speed > 0) {
                y -= speed;
            }
            else {
                y = 0;
            }
        }
        else {
            if (y + height + speed < Pong.pong.height) {
                y += speed;
            }
            else {
                y = Pong.pong.height - height;
            }
        }
    }  
}
