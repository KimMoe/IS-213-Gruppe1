/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;


/**
 *
 * @author evenal
 */
class BoardButton extends JButton {

    int col;
    int row;
    Player owner;
    private final Board board;


    public BoardButton(int idx, Board board) {
        this.board = board;
        col = idx % GameState.N;
        row = (idx - col) / GameState.N;
        this.owner = Player.NONE;
        ImageIcon icon = owner.icon;
        setIcon(icon);
        Dimension size = new Dimension(icon.getIconWidth(), icon.getIconHeight());
        this.setMinimumSize(size);
        this.setPreferredSize(size);
        this.setSize(size);
        this.setEnabled(true);
        setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (board.nextPlayer == board.human) {
                    board.msg.setText(String.format("Selected(c:%d r:%d", col, row));
                    board.makeMove(col, row);
                }
            }
        });
    }


    public void setOwner(Player p) {
        owner = p;
        setIcon(p.icon);
    }

}
