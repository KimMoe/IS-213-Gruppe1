/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pongnnet;

import NeuralNetwork.NN;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 *
 * @author Tonnes
 */
public class PongNNet implements ActionListener, KeyListener {
    
    public static PongNNet pong;
    
    public int width = 700;
    public int height = 700;
    
    Dimension pad1String = new Dimension(10, 690);
    int pad2String = width/2+230;
    
    public Rendering renderer;
    public Ball ball;   
    public Paddle player1;
    public Paddle player2;
    
    public boolean bot = false, disableBot = false;       
    public boolean isNN = false; //IMPLEMENT <---------------   
    public boolean w, s, up, down;
    
    
    public int gameStatus = 0; //0 = Stopped, 1 = Paused, 2 = Playing
    
    public Random random;

    public int botDifficulty;
    
    public static void main(String[] args) {
        pong = new PongNNet();
    }
    
    public PongNNet() {        
        //NN nn = new NN();
        
        //How often the game is updated. Basiclly fps.
        Timer timer = new Timer(15, this);
        JFrame frame = new JFrame("Pong");
        random = new Random();
        
        renderer = new Rendering();
        
        frame.setSize(width + 16, height + 39);
        frame.setVisible(true);
        frame.add(renderer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        
        timer.start();
    }
    
    public void start(){
        gameStatus = 1;
        player1 = new Paddle(this, 1);
        player2 = new Paddle(this, 2);
        ball = new Ball(this);
    }
    
    public void update() {
        if(w) {
            player1.move(true);
        }
        if(s) {
            player1.move(false);
        }
        if (!bot) {
            if(up) {
                player2.move(true);
            }
            if(down) {
                player2.move(false);
            }
        }
        else {     
            if (player2.y + player2.height / 2 < ball.y) {
                player2.move(false);                
            }
            else if (player2.y + player2.height / 2 > ball.y) {
                player2.move(true);              
            }
        }
        ball.update(player1, player2);
    }
    
    public void render(Graphics2D g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);      
        
        if (gameStatus == 0) {
            g.setColor(Color.black);
            g.setFont(new Font("Arial", 1, 30));           
            g.drawString("PONG", width / 2 -39, 50);   
            
            g.setFont(new Font("Arial", 1, 20));           
            g.drawString("(SPACE) - Play", width / 2 -139, height / 2 - 25);
            g.drawString("(SHIFT) - Disable BOT", width / 2 -139, height / 2 + 25);           
        }
        
        if (gameStatus == 1) {
            g.setColor(Color.black);
            
            g.setStroke(new BasicStroke(2));
            g.drawLine(width / 2, 0, width / 2, height);
//            g.setStroke(new BasicStroke(4));
//            g.drawOval(width / 2 - 100, height / 2 -100, 200, 200);
            
            g.setFont(new Font("Arial", 1, 30));
            g.drawString(String.valueOf(player1.score), width / 2 -50, 50);
            g.drawString(String.valueOf(player2.score), width / 2 +39, 50);
                                  
            //Paddle 1
            if (isNN) { //Develop test for Neural network. <------------------
                g.setColor(Color.blue);
                g.drawString("HUMAN", pad1String.width, pad1String.height);
            } else {
                g.setColor(Color.blue);
                g.drawString("NN", pad1String.width, pad1String.height);
            }
            //Paddle 2
            if (bot) {   
                g.setColor(Color.red);
                g.drawString("BOT", pad2String, pad1String.height);
            } else {
                g.setColor(Color.red);
                g.drawString("HUMAN", pad2String, pad1String.height);
            }
            
            //Rendering
            player1.render(g);
            player2.render(g);
            ball.render(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStatus == 1)  {
            update();
        }
        renderer.repaint();   
    }
    
    @Override
    public void keyTyped(KeyEvent e) {  
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int id = e.getKeyCode();
        
        switch (id) {
            case(KeyEvent.VK_W): w = true; break;           
            case(KeyEvent.VK_S): s = true; break;            
            case(KeyEvent.VK_UP): up = true; break;           
            case(KeyEvent.VK_DOWN): down = true; break;
            
            case(KeyEvent.VK_ESCAPE): if (gameStatus == 1) { 
                gameStatus = 0; 
            } break;
            
            case(KeyEvent.VK_SHIFT): if (gameStatus == 0) {  
                bot = false; disableBot = true; 
            } break;
            
            case(KeyEvent.VK_SPACE): if (gameStatus == 0) { 
                if (!disableBot) {
                    bot = true; 
                }
            } 
            start(); break;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int id = e.getKeyCode();
        
        switch (id) {
            case(KeyEvent.VK_W): w = false; break;
            case(KeyEvent.VK_S): s = false; break;
            case(KeyEvent.VK_UP): up = false; break;
            case(KeyEvent.VK_DOWN): down = false; break;
        }
    }    
}
