package is211.connect4.strategy;

import java.awt.Color;
import java.util.ArrayList;



/**
 * This class is a model of the connect-four game
 *
 * @author evenal
 */
public class BasicBoard
{

    // board size
    public static final int COLS = 7;
    public static final int ROWS = 6;
    public static final int CONN_LEN = 4;
    public static final int NO_SELECTED_MOVE = -1;

    // current situation, player are -1/1, empty is 0
    protected BasicPlayer[][] board = new BasicPlayer[][]{
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null}};

    // stack height in each column
    protected int height[];

    protected BasicPlayer[] player;

    // history
    protected GameLog log;

    /**
     * Simple representation of the connectFour board.
     * It id mainly intended as a common super class
     * for more specialized board classes.
     */
    public BasicBoard() {
        reset();
        player = new BasicPlayer[2];
    }

    public void setPlayers(BasicPlayer[] player) {
        this.player = player;
        player[0].setColour(Colour.RED);
        player[1].setColour(Colour.BLUE);
        reset();
    }


    protected final void reset() {
        board = new BasicPlayer[ROWS][COLS];
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                board[r][c] = null;
            }
        }
        height = new int[COLS];
        for (int c = 0; c < COLS; c++) {
            height[c] = 0;
        }
        log = new GameLog();
    }

    public GameLog getLog() {
        return log.copy();
    }


    /**
     * This method is called when a player has decided on
     * move to update the game board, and to transfer the
     * right to decide the next move to the other player.
     *
     * @param col The index of the column to insert the new disk in
     */
    public void commitMove(int col) {
        assert validMove(col);

        BasicPlayer p = player[log.getCommittedMoves() % 2];
        int row = height[col];
        board[row][col] = p;
        height[col]++;
        log.commitMove(col);
    }


    /**
     * Checks that col is a valid move in the current
     * state of the game. Col is valid if it is a
     * valid column index, and the column is not full
     *
     * @param col
     * @return
     */
    public boolean validMove(int col) {
        if (col < 0 || col >= COLS) return false;
        int row = height[col];
        if (row >= ROWS) return false;
        if (get(row, col) != null) return false;
        return true;
    }


    // game log info
    /**
     * Get the last move made
     *
     * @return the column of the last move,
     * or NO_SELECTED_MOVE
     * if no move has been made yet.
     */
    public int getLastMove() {
        return log.getLastMove();
    }

    /**
     * Get the height of the stack in colum col
     *
     * @param col
     * @return
     */
    public int getHeight(int col) {
        return height[col];
    }


    /**
     * Get the moves a player can choose from in the
     * current situation. The possible moves are the
     * valid column indices (in the interval [0,COLS>),
     * where the column is not full.
     *
     * @return a list of indices to the columns that are not full
     */
    public ArrayList<Integer> getPossibleMoves() {
        ArrayList<Integer> validMoves = new ArrayList<>();
        for (int c = 0; c < COLS; c++) {
            if (height[c] < ROWS) {
                validMoves.add(c);
            }
        }
        return validMoves;
    }


    /**
     * Get the log of game
     *
     * @param col column to check
     * @param row row to check
     *
     * @return the p1ayer who occupies the specified
     * cell, or null if the cell is vacant
     */
    public BasicPlayer get(int row, int col) {
        assert row >= 0 && row < ROWS;
        assert col >= 0 && col < COLS;
        return board[row][col];
    }

    public boolean validPos(int row, int col) {
        if (row < 0 || row >= ROWS) return false;
        if (col < 0 || col >= COLS) return false;
        return true;
    }

    public boolean availablePos(int row, int col) {
        return validPos(row, col) && height[col] == row;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();

        buf.append("Move:").append(log.getCommittedMoves());
        buf.append("\n");
        for (int i = 0; i < log.getCommittedMoves(); i++)
            buf.append(log.getMove(i));
        buf.append("\n+-+-+-+-+-+-+-+\n");
        for (int r = ROWS - 1; r >= 0; r--) {
            buf.append("|");
            for (int c = 0; c < COLS; c++) {
                BasicPlayer p = get(r, c);
                if (null == p)
                    buf.append(" |");
                else if (p.colour == Colour.BLUE)
                    buf.append("x|");
                else
                    buf.append("0|");
            }
            buf.append("\n+-+-+-+-+-+-+-+\n");
        }
        buf.append("Valid moves:");
        for (int i : getPossibleMoves())
            buf.append(" ").append(i);
        return buf.toString();


    }


// sub-class myboard

    public static enum Dir
    {
        LEFT_DN(-1, -1), LEFT(-1, 0),
        LEFT_UP(-1, 1), UP(0, 1), RIGHT_UP(1, 1),
        RIGHT(1, 0), RIGHT_DN(1, -1);

        public final int dc;
        public final int dr;

        Dir(int dc, int dr) {
            this.dc = dc;
            this.dr = dr;
        }
    }



    public static class BasicPlayer
    {

        Colour colour;

        public BasicPlayer(Colour colour) {
            this.colour = colour;
        }


        public Colour getColour() {
            return colour;
        }

        public void setColour(Colour colour) {
            this.colour = colour;
        }
    }



    public static enum Colour
    {
        RED(Color.RED), BLUE(Color.BLUE);

        public final Color awtColour;

        Colour(Color colour) {
            {
                this.awtColour = colour;
            }
        }
    }
}
