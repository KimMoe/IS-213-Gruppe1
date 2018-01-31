/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is211.connect4.strategy;

import is211.connect4.strategy.BasicBoard.Colour;
import java.util.HashMap;



/**
 * This class extends StrategyBoard with methods for
 * evaluation of game states.
 *
 * @author evenal
 */
public class NegaMaxBoard extends StrategyBoard
{

    private static final int FOURS = 0;
    private static final int THREES = 1;
    private static final int TWOS = 2;
    private static final float DRAW = 0;

    HashMap<BasicPlayer, Score> scores;

    public NegaMaxBoard(BasicPlayer[] p) {
        super.setPlayers(p);

        scores = new HashMap<>();
        scores.put(player[0], new Score());
        scores.put(player[1], new Score());
    }


    public boolean isFull() {
        return log.getCommittedMoves() == ROWS * COLS;
    }

    /**
     * Check if the move Player p made, ended the game.
     *
     * @param p the player who moved last
     * @param col the column he chose
     * @return true if the game is over false if not.
     */
    public boolean lastMoveWon() {
        if (log.getCommittedMoves() == 0) return false;
        int col = log.getLastMove();
        int row = height[col] - 1;
        BasicPlayer p = get(row, col);
        return (check(p, row, col, Dir.RIGHT) ||
                check(p, row, col, Dir.RIGHT_UP) ||
                check(p, row, col, Dir.UP) ||
                check(p, row, col, Dir.LEFT_UP));
    }

    private boolean check(BasicPlayer p,
                          int row, int col,
                          Dir dir) {
        int f = 0;
        int r = row;
        int c = col;
        while (validPos(r, c) && get(r, c) == p) {
            f++;
            r += dir.dr;
            c += dir.dc;
        }

        int b = 0;
        r = row;
        c = col;
        while (validPos(r, c) && get(r, c) == p) {
            b++;
            r -= dir.dr;
            c -= dir.dc;
        }
        return (f + b + 1 > CONN_LEN);
    }


    public float eval() {
        try {
            countPoints();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }

        BasicPlayer nextPlayer = player[log.getCommittedMoves() % 2];
        BasicPlayer prevPlayer = player[(log.getCommittedMoves() + 1) % 2];
        Score prev = scores.get(prevPlayer);
        Score next = scores.get(nextPlayer);
        float value = 0;

        // prev player won with his last move
        if (prev.count[FOURS] > 0) value = Float.NEGATIVE_INFINITY;
        // next player has too many opportunities to block
        else if (next.count[THREES] > 0) value = Float.POSITIVE_INFINITY;
        else if (prev.count[THREES] > 1) value = Float.NEGATIVE_INFINITY;
        else if (prev.count[THREES] > 0) value = -1000;
        else value = 10 * (next.count[TWOS] - prev.count[TWOS]);
        return value;
    }

    /**
     * This method just sets up the checking
     * of horizontal, vertical and diagonal lines
     */
    private void countPoints() {
        for (int row = 0; row < ROWS; row++) countRow(row);
        for (int col = 0; col < COLS; col++) countCol(col);
        for (int col = 0; col < COLS - CONN_LEN; col++) countDiag1(col);
        for (int col = CONN_LEN; col < COLS; col++) countDiag2(col);
    }

    private void countRow(int row) {
        int col = 0;
        do {
            BasicPlayer p = get(row, col);
            if (p == null) {
                col++;
            }
            else {
                Score score = scores.get(p);
                int count = 0;
                while (col < COLS && get(row, col) == p) {
                    col++;
                    count++;
                }
                score.addCount(count);
            }
        }
        while (col < COLS - CONN_LEN);
    }

    private void countCol(int col) {
        int row = 0;
        do {
            BasicPlayer p = get(row, col);
            Score score = scores.get(p);
            int count = 0;
            while (row < ROWS && get(row, col) == p) {
                if (null == p) return;
                row++;
                count++;
            }
            score.addCount(count);
        }
        while (row < ROWS - CONN_LEN);
    }

    private void countDiag1(int col) {
        int row = 0;
        do {
            BasicPlayer p = get(row, col);
            if (p == null) {
                col++;
                row++;
            }
            else {
                Score score = scores.get(p);
                int count = 0;
                while (p == get(row, col)) {
                    row++;
                    col++;
                    count++;
                }
                score.addCount(count);
            }
        }
        while (row < ROWS - CONN_LEN);
    }

    private void countDiag2(int col) {
        int row = 0;
        do {
            BasicPlayer p = get(row, col);
            if (p == null) {
                row++;
                col--;
            }
            else {
                Score score = scores.get(p);
                int count = 0;
                while (col >= 0 && p == get(row, col)) {
                    row++;
                    col--;
                    count++;
                }
            }
        }
        while (col > CONN_LEN);
    }



    /**
     * Score objects keep a count of how many
     * possibly game-winning segments a player has
     */
    private class Score
    {

        Colour player;
        int[] count = new int[10];

        public void addCount(int c) {
            switch (c) {
            case 4:
                this.count[FOURS]++;
                break;
            case 3:
                this.count[THREES]++;
                break;
            case 2:
                this.count[TWOS]++;
                break;
            default: //do nothing
            }
        }
    }
}
