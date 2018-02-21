/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is211.connect4.strategy;

import java.util.ArrayList;



/**
 * An implementation of GameAI with some useful methods
 * implemented.
 *
 * @author evenal
 */
public abstract class AbstractGameAI implements GameAI
{

    /**
     * Name of the AI
     */
    String name;
    /**
     * Game board that can be used for lookahead
     */
    protected StrategyBoard board;
    /**
     * the valid moves - you must choose one of these
     */
    protected ArrayList<Integer> validMoves;
    /**
     * this should always be set to the best move you have found
     */
    protected int selectedMove;


    public AbstractGameAI() {
        this(null);
    }

    /**
     *
     * @param name the strategy name
     */
    public AbstractGameAI(String name) {
        if (name == null)
            this.name = getClass().getSimpleName();
        else
            this.name = name;
        board = new StrategyBoard();
    }

    @Override
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPlayers(BasicBoard.BasicPlayer[] p) {
        board.setPlayers(p);
    }

    @Override
    public void update(GameLog log,
                       ArrayList<Integer> validMoves) {
        board.update(log);
        this.validMoves = validMoves;
    }

    @Override
    public int getSelectedMove() {
        return selectedMove;
    }

}
