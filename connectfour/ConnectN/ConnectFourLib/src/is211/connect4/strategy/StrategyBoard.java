/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is211.connect4.strategy;

import java.util.ArrayList;



/**
 * Extends BasicBoard with methods that can be used
 * to implement lookahead, i.e. try a move and backtrack
 * when the consequences have been evaluated.
 *
 * @author evenal
 */
public class StrategyBoard extends BasicBoard
{

    /**
     * The stack contains the current path, while
     * exploring future moves.
     */
    ArrayList<Integer> stack;

    public StrategyBoard() {
        stack = new ArrayList<>();
    }

    /**
     * Check out a move to see what may happen.
     * Moves added with pushMove() can be undone
     * with popMove()
     *
     * @param col
     */
    public void pushMove(int col) {
        if (validMove(col)) {
            BasicPlayer p = player[(getCommitted() + stack.size()) % 2];
            int row = height[col];
            board[row][col] = p;
            height[col]++;
            stack.add(col);
        }
    }

    private int getCommitted() {
        return log.getCommittedMoves();
    }

    /**
     * Undo a pushed move. Returns the board to the
     * log before the last move was pushed.
     */
    public int popMove() {
        int col = stack.remove(stack.size() - 1);
        height[col]--;
        int row = height[col];
        board[row][col] = null;
        return col;
    }

    public void commitMove(int col) {
        popAllMoves();
        super.commitMove(col);
    }

    public int getTotalNumMoves() {
        return log.getCommittedMoves() + stack.size();
    }

    /**
     * Undo all pushed moves. This will return the
     * board to the state after the last committed move
     */
    public void popAllMoves() {
        while (!stack.isEmpty()) popMove();
    }

    public void update(GameLog masterLog) {
        switch (masterLog.getCommittedMoves()) {
        case 0: // we will choose the first move
            break;
        case 1: // opponent made fisrt move, we will make second
            int m = masterLog.getMove(0);
            commitMove(m);
            break;
        default:
            if (log.equalsTo(masterLog,
                             log.getCommittedMoves())) {
                int numMoves = log.getCommittedMoves();
                int move = masterLog.getMove(numMoves);
                commitMove(move);
                move = masterLog.getMove(numMoves + 1);
                commitMove(move);
            }
        }
    }
}
