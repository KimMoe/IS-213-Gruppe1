/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pongnnet;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author Tonnes
 */
public class Ball {
      
    public int x, y, width = 25, height = 25;
    public int motionx, motiony;

    public int amoutofHits;
    
    public Random random;
    
    private PongNNet pong;
    
    public Ball(PongNNet pong) {
        this.random = new Random();
        this.pong = pong;  
        spawn();
    }
    
    public void update(Paddle paddle1, Paddle paddle2) {
        int speed = 6;
        
        this.x += motionx * speed;
        this.y += motiony * speed;
        
        if (this.y + height - motiony > pong.height || this.y + height + motiony < 0) {
            if (this.motiony < 0) {
                this.y = 0;
                this.motiony = random.nextInt(4);
                
                if (motiony == 0) {
                    motiony = 1;
                }             
            }
            else { 
                this.motiony = -random.nextInt(4);
                this.y = pong.height - height;
                if (motiony == 0) {
                    motiony = -1;
                }                 
            }
        }      
        
        if (checkCollision(paddle1) == 1) {
            this.motionx = 1 + (amoutofHits / 5);
            this.motiony = -2 + random.nextInt(4);
           
            if (motiony == 0) {
                motiony = 1;
            } 
            paddle1.score++;
            amoutofHits++;
        }
        else if (checkCollision(paddle2) == 1) {
            this.motionx = -1 - (amoutofHits / 5);
            this.motiony = -2 + random.nextInt(4);
            
            if (motiony == 0) {
                motiony = 1;
            }
            paddle2.score++;
            amoutofHits++;            
        } 
        
        if (checkCollision(paddle1) == 2) {
            //Add more points for scoring or? <--------------------
            //paddle1.score++;
            
            //Reset score when the ball is lost. Swap out for real reset of game. <------------------------
            paddle1.score = 0;
            paddle2.score = 0;
            
            spawn();
        }
        
        else if (checkCollision(paddle2) == 2) {
            //Add more points for scoring or? <--------------------
            //paddle2.score++;
            
            //Reset the score when the ball is lost. Swap out for real reset of game. <------------------------
            paddle2.score = 0;
            paddle1.score = 0;
            
            spawn();
        }    
    }
    
    //Do neural network stuff when it spawns. 
    public void spawn() {
        this.amoutofHits = 0;
        this.x = pong.width / 2 - this.width /2;
        this.y = pong.height / 2 - this.height /2;
        
        this.motiony = -2 + random.nextInt(4);
        
        if (motiony == 0) {
            motiony = 1;
        }
        
        if (random.nextBoolean()) {
            motionx = 1; 
        }
        else {
            motionx = -1;
        } 
    }
    
    public int checkCollision(Paddle paddle) {
        if (this.x < paddle.x + paddle.width && this.x + width > paddle.x && this.y < paddle.y + paddle.height && this.y + height > paddle.y) {
            return 1;
        }
        
        else if ((paddle.x > x && paddle.paddlenumb == 1) || (paddle.x < x - width && paddle.paddlenumb == 2)) {
            return 2;
        }
        
        return 0;    
    }
    
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillOval(x, y, width, height);       
    }
}
