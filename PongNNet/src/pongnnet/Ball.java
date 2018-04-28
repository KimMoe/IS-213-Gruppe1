/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pongnnet;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileWriter;
import java.io.IOException;
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
        int speed = 1;
        
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
            this.motionx = 1;
            this.motiony = -2 + random.nextInt(4);
           
            if (motiony == 0) {
                motiony = 1;
            } 
            paddle1.score++;
        }
        else if (checkCollision(paddle2) == 1) {
            this.motionx = -1;
            this.motiony = -2 + random.nextInt(4);
            
            if (motiony == 0) {
                motiony = 1;
            }
            paddle2.score++;         
        }
        
        if (checkCollision(paddle1) == 2) {
            //Add more points for scoring or? <--------------------
            //paddle1.score++;
            //saveFileTest(paddle1.y,pong.ball.y,paddle1.score);
            saveFile(paddle1.y, pong.ball.y, paddle1.score);
            
            paddle1.score = 0;
            paddle2.score = 0;
            
            spawn();
        }
        
        else if (checkCollision(paddle2) == 2) {
            //Add more points for scoring or? <--------------------
            //paddle2.score++;
            
            //Reset the score when the ball is lost. Maybe change a little. <------------------------
            paddle2.score = 0;
            paddle1.score = 0;
            
            spawn();
        }   
        
    }
    
    //Do neural network stuff when it spawns. 
    public void spawn() {
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
        //BUGGED. When the ball hits the top or bottom of the paddle, the score shots up. Because it's techinally behind it. <-----------------------
        //It would be interesting if the NN learn to abuse this bug...
        if (this.x < paddle.x + paddle.width && this.x + width > paddle.x && this.y < paddle.y + paddle.height && this.y + height > paddle.y) {
            //Ball hits paddle
            return 1;
        }
        
        else if ((paddle.x > x && paddle.paddlenumb == 1) || (paddle.x < x - width && paddle.paddlenumb == 2)) {
            //Loses ball behind paddle.
            return 2; 
        }
        //Nothing
        return 0;    
    }
    
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillOval(x, y, width, height);
    }
    
    public void saveFile(int paddle, int ball, int score) {
        try (FileWriter writer = new FileWriter("data.txt")) {
            writer.write(""); //Deletes content of file. Just to be sure.
            writer.write(paddle + "," + ball + "," + score); //Writes content to file.
        } catch (IOException e) {
            System.out.println(e);
        }
    }

//For getting min max data.
    
//    public void saveFileTest(int paddle, int ball, int score) {           
//        try (FileWriter writer = new FileWriter("minMax.txt", true)) {
//            writer.write(ball + ","+ score + ";\n");
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//    }
}
