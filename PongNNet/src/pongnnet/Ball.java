/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pongnnet;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedWriter;
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
    
    private int output;
    
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
        
        if (checkCollision(paddle1) == 1 || checkCollision(paddle1) == 2) {
            //See where the ball is in difference to the paddle.
            checkOutput(paddle1);
            
            //Save data to data.txt file
            saveFile(paddle1.y, y, x, paddle1.score, output); 
            
            //Starting the different NN processes.
            startNN(); 
               
            
            if (checkCollision(paddle1) == 2) {
                //Resets the score to 0.
                resetScore(paddle1, paddle2);

                //Respawns/Spawns the ball.
                spawn();
            }             
        }
        if (checkCollision(paddle2) == 2) {
            resetScore(paddle1, paddle2);

            spawn();
        } 
    }
    
    public void resetScore(Paddle paddle1, Paddle paddle2) {
        paddle1.score = 0;
        paddle2.score = 0;
    }
    
    public void startNN() {
        pong.nn.trainingSet();
        
        pong.nn.trainNN();
        
        pong.nn.setGeneration(pong.nn.getGeneration()+1);
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
    
    private void checkOutput(Paddle paddle){
        if((paddle.y+150) < y){ //ball lower than paddle
            output = 0;
        }
        if((paddle.y) > y){//ball higher than paddle
            output = 1;
        }
        if(checkCollision(paddle) == 1){//ball hits the paddle
            output = 2;
        }
    }
    
    public void saveFile(int paddle, int ballx, int bally, int score, int output) {
        if (ballx < 0){
            ballx = 0;
        }
        if (bally < 0){
            bally = 0;
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt", false))) {                       
            writer.write("");
            writer.write(paddle + "," + ballx + "," + bally + "," + score + "," + output); //Writes content to file.
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
