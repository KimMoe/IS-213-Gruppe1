/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is211.connect4.strategy;



/**
 * This is a compact representation of the log of
 * the game, essentially a list of the moves made.
 * The board can always be recreated just by starting
 * with an empty board and redoing the moves
 *
 * @author evenal
 */
public class GameLog
{

    private int[] moves;
    private int committedMoves;


    public GameLog() {
        moves = new int[BasicBoard.COLS * BasicBoard.COLS];
        committedMoves = 0;
    }

    /**
     * Create a copy of this log
     *
     * @return the copy
     */
    public GameLog copy() {
        GameLog copy = new GameLog();
        for (int i = 0; i < committedMoves; i++)
            copy.moves[i] = moves[i];
        copy.committedMoves = committedMoves;
        return copy;
    }

    /**
     * For debugging
     *
     * @return a string representation of the log
     */
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("GameState (").append(committedMoves);
        buf.append(");");
        for (int i = 0; i < committedMoves; i++)
            buf.append(" ").append(moves[i]);
        return buf.toString();
    }

    /**
     * add a move that cannot be undone to the log
     *
     * @param m
     */
    public void commitMove(int m) {
        moves[committedMoves] = m;
        committedMoves++;
    }

    /**
     * get the number of committed moves
     *
     * @return
     */
    public int getCommittedMoves() {
        return committedMoves;
    }


    /**
     * Get the ith move from the log
     *
     * @param i
     * @return
     */
    public int getMove(int i) {
        assert (i >= 0 && i < committedMoves) : "Index " + i;
        return moves[i];
    }

    public int getLastMove() {
        return moves[committedMoves - 1];
    }

    public boolean equals(GameLog that) {
        if (this.committedMoves != that.committedMoves)
            return false;
        for (int i = 0; i < committedMoves; i++) {
            if (this.moves[i] != that.moves[i]) return false;
        }
        return true;
    }

    public boolean equalsTo(GameLog that, int moves) {
        for (int i = 0; i < moves; i++) {
            if (this.moves[i] != that.moves[i]) return false;
        }
        return true;
    }
}
