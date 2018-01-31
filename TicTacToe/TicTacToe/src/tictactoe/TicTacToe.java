/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.plaf.basic.BasicBorders;


/**
 *
 * @author evenal
 */
public class TicTacToe extends JFrame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TicTacToe ttt = new TicTacToe();
        ttt.setVisible(true);
    }

    private Board board;
    JTextField msg;


    public TicTacToe() {
        buildGui();
    }


    private void buildGui() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });

        BorderLayout layout = new BorderLayout(5, 5);
        JPanel contentPane = new JPanel(layout);
        setContentPane(contentPane);
        contentPane.setLayout(layout);
        contentPane.setBorder(new BasicBorders.MarginBorder());
        msg = new JTextField();
        contentPane.add(msg, BorderLayout.SOUTH);
        board = new Board(this);
        contentPane.add(board, BorderLayout.CENTER);
        JPanel taskBar = new JPanel();
        JButton start = new JButton(new AbstractAction("Start") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                board.setupGame(Player.WHITE);
            }
        });
        taskBar.add(start);
        taskBar.add(new JLabel("Play as:"));
        JRadioButton black = new JRadioButton("Black");
        black.setSelected(true);
        taskBar.add(black);
        JRadioButton white = new JRadioButton("White");
        white.setSelected(false);
        taskBar.add(white);
        contentPane.add(taskBar, BorderLayout.NORTH);
        setLocation(500, 500);
        pack();

    }


    private void exit() {
        for (java.awt.Frame frame : java.awt.Frame.getFrames()) {
            frame.dispose();
        }
    }
}
