/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pongnnet;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Tonnes
 */
public class Paddle {

    public int paddlenumb;
    
    public int x, y, width = 25, height = 150; 
    
    public int score;
    
    public Paddle(PongNNet pong, int paddlenumb)   {
        
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
        if (paddlenumb == 1) {
            g.setColor(Color.blue);
        }
        else if (paddlenumb == 2) {
            g.setColor(Color.red);
        }
        
        g.fillRect(x, y, width, height);
    }

    public void move(boolean up) {
        int speed = 4;
        
        if(up) {
            if (y - speed > 0) {
                y -= speed;
            }
            else {
                y = 0;
            }
        }
        else {
            if (y + height + speed < PongNNet.pong.height) {
                y += speed;
            }
            else {
                y = PongNNet.pong.height - height;
            }
        }
    }  
}
