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
    private int motionx, motiony;
    
    public static double desiredOut;
    public static double ballY;
    public static double paddleY;    
    
    private Random random;
    private PongNNet pong;
    
    /**
     * 
     * @param pong 
     */
    public Ball(PongNNet pong) {  
        this.random = new Random();
        this.pong = pong;  
        spawn();
    }
    
    /**
     * 
     * @param paddle1
     * @param paddle2 
     */
    public void update(Paddle paddle1, Paddle paddle2) {
        double speed = 1;
        
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
        
        if (pong.isNN) {
            //Data output
            desiredOut = desiredOutput(paddle1);              
            paddleY = getNNy(paddle1.y) * 0.001;
            ballY = getNNballY(y) *0.001;

            //saveFile(paddle1.y, y, paddle1.score, output); 

            //Starting the different NN processes.
            startNN(); 
        }

        if (checkCollision(paddle1) == 2 || checkCollision(paddle2) == 2) {
            resetScore(paddle1, paddle2);
            spawn();
        }
        
    }
    
    /**
     * 
     * @param paddle1
     * @param paddle2 
     */
    public void resetScore(Paddle paddle1, Paddle paddle2) {
        paddle1.score = 0;
        paddle2.score = 0;
    }
    
    /**
     * 
     */
    public void startNN() {
        pong.nn.trainingSet();
        
        pong.nn.trainNN();       
    }
    
    /**
     * Do neural network stuff when it spawns. 
     */
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
    
    /**
     * 
     * @param paddle
     * @return 
     */
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
    
    /**
     * 
     * @param paddleY
     * @return 
     */
    public double getNNy(double paddleY) {
        return paddleY;
    }
    
    /**
     * 
     * @param ballY
     * @return 
     */
    public double getNNballY(double ballY) {
        return ballY;
    }
    
    /**
     * 
     * @param paddle
     * @return 
     */
    public int desiredOutput(Paddle paddle) {
//        if (y < paddle.y + paddle.height && y + height > paddle.y) {
//            output = 1;
//        } else {
//            output = 0;
//        }
        
        if (y < paddle.y + paddle.height && y + height > paddle.y) {
            return 1;
        }
        else {
            return 0;
        }
    }
    
    
    /**
     * 
     * @param g 
     */
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillOval(x, y, width, height);
    }
    
//    public void saveFile(int paddle, int bally, double output) {
//        if (bally < 0){
//            bally = 0;
//        }
//        
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt", false))) {                       
//            writer.write("");
//            writer.write(paddle + "," + bally + "," + output); //Writes content to file.
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//    }
}
