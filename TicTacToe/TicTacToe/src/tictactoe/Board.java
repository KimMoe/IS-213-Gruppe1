/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 *
 * @author evenal
 */
class Board extends JPanel {
    GameState state;
    BoardButton[] buttons;
    Player nextPlayer;
    Player human;
    JTextField msg;


    public Board(final TicTacToe game) {
        super(new GridLayout(GameState.N, GameState.N, 10, 10));
        msg = game.msg;
        buttons = new BoardButton[GameState.NxN];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new BoardButton(i, this);
            this.add(buttons[i]);
        }
        setupGame(Player.BLACK);
    }


    public void setupGame(Player human) {
        state = new GameState();
        for (int i = 0; i < GameState.NxN; i++)
            buttons[i].setOwner(state.board[i]);
        nextPlayer = Player.BLACK;
        this.human = human;
    }


    void makeMove(int col, int row) {
        int i = state.getIndex(col, row);
        try {
            state.makeMove(nextPlayer, row, col);
            buttons[i].setOwner(nextPlayer);
            if (nextPlayer == Player.BLACK)
                nextPlayer = Player.WHITE;
            else
                nextPlayer = Player.BLACK;
        }
        catch (IllegalMoveException ime) {
            msg.setText(ime.getMessage());
        }
    }

}
