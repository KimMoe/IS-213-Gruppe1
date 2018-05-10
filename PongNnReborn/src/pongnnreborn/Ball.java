/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pongnnreborn;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

/*
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
*/

/**
 * This is the ball class and contains that dictates  how the ball will behave during pong.
 * @author Tonnes
 */
public class Ball /*implements ActionListener*/ {
    final int ballSpeed = 6;
    final int ballHeight = 25;
    final int ballWidth = 25;
    int motionY;
    int motionX;
    
    public static int ballY;
    public static int ballX;   
    
    int generation;
    public static int paddleY_NN;
    
    public static double desiredOutput;
    
    private Pong pongBall;
    
    private Random randomBall;
    //Timer timer;
    
    public Ball(Pong pong) {
        randomBall = new Random();
        //timer = new Timer(1000, this);
        
        pongBall = pong;  
        spawn();
    }
    
    public void update(Paddle paddle1, Paddle paddle2) {
        ballX += motionX * ballSpeed;
        ballY += motionY * ballSpeed;              
        
        paddleY_NN = paddle1.paddleY;
        desiredOutput = setDesiredOutput_NN(paddle1);
        
        if (Pong.neuralNetworkEnabled) {
            //timer.start();
            pongBall.nn.startNN();
        }
        
        if (ballY + ballHeight - motionY > pongBall.height || ballY + ballHeight + motionY < 0) {
            if (motionY < 0) {
                ballY = 0;
                motionY = randomBall.nextInt(4);
                
                if (motionY == 0) {
                    motionY = 1;
                }             
            }
            else { 
                motionY = -randomBall.nextInt(4);
                ballY = pongBall.height - ballHeight;
                if (motionY== 0) {
                    motionY = -1;
                }                 
            }
        }      
        
        if (checkCollision(paddle1) == 1 || checkCollision(paddle2) == 1) {
            if (checkCollision(paddle1) == 1) {
                motionX = 1;
                motionY = -2 + randomBall.nextInt(4);  
                if (motionY == 0) {
                    motionY = 1;
                }
                paddle1.score++;
            }
            if (checkCollision(paddle2) == 1) {
                motionX = -1;
                motionY = -2 + randomBall.nextInt(4);
            
                if (motionY == 0) {
                    motionY = 1;
                }
                paddle2.score++;   
            }
        }
        
        if (checkCollision(paddle1) == 2 || checkCollision(paddle2) == 2) {           
            paddle1.score = 0;
            paddle2.score = 0;
            spawn();
        }
    }        
    
    
    public void spawn() {
        ballX = pongBall.width / 2 - ballWidth /2;
        ballY = pongBall.height / 2 - ballHeight /2;
        
        motionY = -2 + randomBall.nextInt(4);
        
        if (motionY == 0) {
            motionY = 1;
        }
        
        if (randomBall.nextBoolean()) {
            motionX = 1; 
        }
        else {
            motionX = -1;
        }    
    }
   
    public int checkCollision(Paddle paddle) {
        //BUGGED. When the ball hits the top or bottom of the paddle, the score shots up. Because it's techinally behind it. <-----------------------
        //It would be interesting if the NN learn to abuse this bug...
        if (ballX < paddle.paddleX + paddle.paddleWidth && ballX + ballWidth > paddle.paddleX && ballY < paddle.paddleY + paddle.paddleHeight && ballY + ballHeight > paddle.paddleY) {
            //Ball hits paddle
            return 1;
        }
        
        else if ((paddle.paddleX > ballX && paddle.paddleNumber == 1) || (paddle.paddleX < ballX - ballWidth && paddle.paddleNumber == 2)) {
            //Loses ball behind paddle.
            return 2; 
        }
        //Nothing
        return 0;
    }
    
    /**
    * Renders the graphic of the pong interface
    */
    public void render(Graphics2D g) {
        g.setColor(Color.black);
        g.fillOval(ballX, ballY, ballWidth, ballHeight);
    } 
    
    public double setDesiredOutput_NN(Paddle paddle) {
        if (ballY < paddle.paddleY) {
            return 0;
        }
        else if (ballY > paddle.paddleY  + 150) {
            return 1;
        }
        else {
            return 0.5;
        }
    }
/*
    @Override
    public void actionPerformed(ActionEvent e) {
        pongBall.nn.startNN();
    }
*/
}
