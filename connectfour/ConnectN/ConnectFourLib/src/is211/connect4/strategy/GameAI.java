/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is211.connect4.strategy;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;



/**
 *
 * @author evenal
 */
public interface GameAI
{

    /**
     * the time available to decide on a move
     * The strategies will be interrupted if they
     * use more time than the limit without returning
     * a move
     */
    public static final int TIME_LIMIT = 3000;
    public static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;


    void setPlayers(BasicBoard.BasicPlayer[] p);


    /**
     * The game manager will call this method
     * immediately before call() is called,
     * to remind the strategy which moves have
     * been made before,and which moves this
     * strategy can choose from
     *
     * @param moveHistory a list of all moves made up to
     * this point. The last item is the opponents last move
     * and the second last is your move before that.
     * @param validMoves the moves to choose from
     */
    void update(GameLog state,
                ArrayList<Integer> validMoves);


    /**
     * This is the method that selects a move. It will be interrupted if it
     * spends too much time. After selectMove() has returned, or has been interrupted
     * the game manager will call getSelectedMove() to get the chosen move
     */
    int selectMove();

    /**
     * This method will be called to retrieve the chosen move regardless of whether selectMove() terminated
     * normally or was interrupted. You should always store the best move you have found so far in the field that
     * backs this method. If getSelectedMove() returns an invalid move, the game manager will select a random move.
     *
     * @return
     */
    int getSelectedMove();

    /**
     * Get the name of the strategy. To avoid confusion
     * the strategy name should at least closely resemble
     * the class name.
     *
     * @return
     */
    String getName();
}
