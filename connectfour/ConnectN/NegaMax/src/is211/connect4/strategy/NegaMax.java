/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is211.connect4.strategy;

//import is211.connect4.game.ManagedPlayer;


/**
 *
 * @author evenal
 */
public class NegaMax extends AbstractGameAI
{

    public static final int US = 1;
    public static final int THEM = -1;
    public static final int MIN_DEPTH = 2;
    public static final int MAX_DEPTH = 5;
    public static final int MIN_VALUE = Integer.MIN_VALUE;

    private static final int[] moves = {3, 2, 4, 5, 1, 0, 6};

    public NegaMax() {
    }

    @Override
    public void setPlayers(BasicBoard.BasicPlayer[] p) {
        board = new NegaMaxBoard(p);
    }


    private NegaMaxBoard getNegaMaxBoard() {
        return (NegaMaxBoard) board;
    }

    @Override
    public int selectMove() {
        selectedMove = -1;

        for (int depth = MIN_DEPTH; depth < MAX_DEPTH; depth++) {
            float bestScore = Float.NEGATIVE_INFINITY;
            int bestMove = -1;

            for (int m : moves) {
                if (board.validMove(m)) {
                    float score = -negaMax(depth, US);
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = m;
                    }
                }
            }
            selectedMove = bestMove;
        }
        return selectedMove;
    }


    private float negaMax(int depth, int sign) {
        NegaMaxBoard board = (NegaMaxBoard) (this.board);
        if (board.isFull())
            return 0;
        else if (board.lastMoveWon())
            return Float.NEGATIVE_INFINITY;
        else if (depth == 0) {
            float score = getNegaMaxBoard().eval();
            return sign * score;
        }
        else {
            float bestScore = Float.NEGATIVE_INFINITY;
            int bestmove = -1;
            for (int m : moves) {
                if (board.validMove(m)) {
                    board.pushMove(m);
                    float mscore = negaMax(depth - 1, -sign);
                    board.popMove();
                    if (mscore > bestScore) {
                        bestScore = mscore;
                        bestmove = m;
                    }
                }
            }
            return bestScore;
        }
    }
}
