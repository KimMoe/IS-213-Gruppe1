/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pongnnreborn;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Tonnes
 */
public class Paddle {
    
    public int score = 0;
    
    int paddleNumber;
    int paddleSpeed = 18;
    
    final int paddleHeight = 150;
    final int paddleWidth = 25;    
    
    int paddleY;
    int paddleX;
    
    public Paddle(Pong pong, int paddleNumber) {
        this.paddleNumber = paddleNumber;
        
        if(paddleNumber == 1) {
            paddleX = 0;         
        }
        if(paddleNumber == 2) {
            paddleX = pong.width - paddleWidth;           
        }
        
        paddleY = pong.height / 2 - this.paddleHeight / 2; 
    }
    
    public void render(Graphics2D g) {
        if (paddleNumber == 1) {
            g.setColor(Color.blue);
        }
        else if (paddleNumber == 2) {
            g.setColor(Color.red);
        }
        
        g.fillRect(paddleX, paddleY, paddleWidth, paddleHeight);
    }
    
    public void move(boolean up) {
        if(up) {
            if (paddleY - paddleSpeed > 0) {
                paddleY -= paddleSpeed;
            }
            else {
                paddleY = 0;
            }
        }
        else {
            if (paddleY + paddleHeight + paddleSpeed < Pong.pong.height) {
                paddleY += paddleSpeed;
            }
            else {
                paddleY = Pong.pong.height - paddleHeight;
            }
        }
    }
}