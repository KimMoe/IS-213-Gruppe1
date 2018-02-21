/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.awt.BasicStroke;
import java.awt.Color;
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
public class Pong implements ActionListener, KeyListener {
    
    public static Pong pong;
    
    public int width = 700;
    public int height = 700;
 
    public Rendering renderer;
    public Ball ball;   
    public Paddle player1;
    public Paddle player2;
    
    public boolean bot = false, selectingDifficulty;    
    public boolean w, s, up, down;
    
    public int gameStatus = 0; //0 = Stopped, 1 = Paused, 2 = Playing
    
    public Random random;

    public int botMoves, botCooldown = 0, botDifficulty;
    
    public static void main(String[] args) {
        pong = new Pong();
    }
    
    public Pong() {        
        Timer timer = new Timer(20, this);
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
        gameStatus = 2;
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
            if (botCooldown > 0) { 
                botCooldown--;
                if (botCooldown == 0) {
                    botMoves = 0;
                }
            }
            
            if (botMoves < 10) {           
                if (player2.y + player2.height / 2 < ball.y) {
                    player2.move(false);
                    botMoves++;
                }
                else if (player2.y + player2.height / 2 > ball.y) {
                    player2.move(true);
                    botMoves++;
                }
                if (botDifficulty == 0) { //Easy bot
                    botCooldown = 20;
                }
                else if (botDifficulty == 1) { //Medium bot
                    botCooldown = 15;
                }
                else if (botDifficulty == 2) { //Hard bot
                    botCooldown = 10;
                }                
            }
        }
        ball.update(player1, player2);
    }
    
    public void render(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);      
        
        if (gameStatus == 0) {
            g.setColor(Color.white);
            g.setFont(new Font("Arial", 1, 30));
            
            g.drawString("PONG", width / 2 -39, 50);
            
            if (!selectingDifficulty) {
                g.setFont(new Font("Arial", 1, 20));
            
                g.drawString("Press SPACE to Play", width / 2 -139, height / 2 - 25);
                g.drawString("Press Shift to Play with BOT", width / 2 -139, height / 2 + 25);
            }
        }

        if (selectingDifficulty) {
            String diffString = botDifficulty == 0 ? "Easy" : (botDifficulty == 1 ? "Medium" : "Hard");
            
            g.setFont(new Font("Arial", 1, 20));
            g.drawString("Press SPACE to Play", width / 2 -139, height / 2 + 25);
            g.drawString("BOT difficulty:" + diffString, width / 2 -139, height / 2 - 25);      
        }  
        
        if (gameStatus == 2 || gameStatus == 1) {
            g.setColor(Color.white);
            
            g.setStroke(new BasicStroke(4));
            g.drawLine(width / 2, 0, width / 2, height);
            g.setStroke(new BasicStroke(4));
            g.drawOval(width / 2 - 100, height / 2 -100, 200, 200);
            
            
            g.setFont(new Font("Arial", 1, 30));
            g.drawString(String.valueOf(player1.score), width / 2 -50, 50);
            g.drawString(String.valueOf(player2.score), width / 2 +39, 50);
            
            player1.render(g);
            player2.render(g);
            ball.render(g);
        }
        
        if (gameStatus == 1) {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("PAUSED", width / 2 - 103, height / 2 + 12);   
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStatus == 2)  {
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
        
        if(id == KeyEvent.VK_W) {
            w = true;
        }
        else if(id == KeyEvent.VK_S) {
            s = true;
        }
        else if(id == KeyEvent.VK_UP) {
            up = true;
        }
        else if(id == KeyEvent.VK_DOWN) {
            down = true;
        }
        
        else if(id == KeyEvent.VK_RIGHT && selectingDifficulty) {
            if (botDifficulty < 2) {
                botDifficulty ++;  
            }
            else {
                botDifficulty  = 0;
            }
        }   
        
        else if(id == KeyEvent.VK_ESCAPE && gameStatus == 2) {
            gameStatus = 0;
        }        
        else if(id == KeyEvent.VK_SHIFT && gameStatus == 0) {
            bot = true;
            selectingDifficulty = true;
        }
        else if(id == KeyEvent.VK_SPACE) {
            if (gameStatus == 0) {               
                if (!selectingDifficulty) {
                    bot = false;
                }
                else {
                    selectingDifficulty = false;
                }
                start();
            }
            else if (gameStatus == 1) {
                gameStatus = 2;
            } 
            else if (gameStatus == 2) {
                gameStatus = 1;
            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        //Game
        int id = e.getKeyCode();
        
        if(id == KeyEvent.VK_W) {
            w = false;
        }
        else if(id == KeyEvent.VK_S) {
            s = false;
        }
        else if(id == KeyEvent.VK_UP) {
            up = false;
        }
        else if(id == KeyEvent.VK_DOWN) {
            down = false;
        } 
    }    
}
