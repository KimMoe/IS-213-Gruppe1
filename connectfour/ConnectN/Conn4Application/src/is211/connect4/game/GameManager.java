/*
 * To change this license header, choose License Headers in Project Properties.
 *
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is211.connect4.game;

import is211.connect4.strategy.BasicBoard;
import is211.connect4.strategy.GameAI;
import is211.connect4.strategy.StrategyManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;



/**
 * The game manager manages a single game.
 * It makes the player select their moves in
 * turn, and notifies the GUI, whenever
 * something changes.
 *
 * @author evenal
 */
public class GameManager implements ActionListener
{

    // The lengt of the pause between moves
    // in milliseconds
    // without this delay it's impossible to see
    // what's going on in a game between AIs.
    private static final int PAUSE = 1000;
    private static final int MAX_MOVES = BasicBoard.ROWS * BasicBoard.COLS;

    private GameMgrBoard board;
    // Players, player1 moves first
    private ManagedPlayer[] player = {null, null};

    private Timer timer;
    ArrayList<GameListener> listeners;

    /**
     * Prepare a new game.
     *
     * @param name1 - name of the strategy to use for player1
     * @param name2 - name of the strategy to use for player2
     */
    public GameManager(String name1, String name2) throws InstantiationException, IllegalAccessException {
        board = null;
        listeners = new ArrayList<>();

        // set up players
        StrategyManager stratMgr = StrategyManager.getInstance();
        GameAI strat1 = stratMgr.getStrategy(name1);
        player[0] = new ManagedPlayer(name1, strat1);
        GameAI strat2 = stratMgr.getStrategy(name2);
        player[1] = new ManagedPlayer(name2, strat2);
        board = new GameMgrBoard();
        board.setPlayers(player);
        strat1.setPlayers(player);
        strat2.setPlayers(player);
        System.err.format("Starting game between %s and %s\n",
                          strat1, strat2);
    }


    public void runGame() {
        timer = new Timer(PAUSE, this);
        fireNewGame();
        timer.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        timer.stop();

        if (board.gameOver) {
            timer.stop();
            fireGameOver();
        }
        else {
            ManagedPlayer nextPlayer = board.getNextPlayer();
            System.err.println(nextPlayer.getName() + " turn");
            int move = nextPlayer.computeMove(board);
            System.err.format("%s chose %d\n", nextPlayer.getName(), move);
            board.commitMove(move);
            fireMove();
            timer.start();
        }
    }

    public void abort() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
        if (board != null) board = null;
    }


    public ManagedPlayer getPlayer1() {
        return player[0];
    }

    public ManagedPlayer getPlayer2() {
        return player[1];
    }


    public void addGameListener(GameListener l) {
        listeners.add(l);
    }

    private void fireNewGame() {
        System.err.println("Broadcast: new game");
        for (GameListener l : listeners)
            l.gameChanged(GameListener.NEWGAME, this);
    }

    private void fireMove() {
        System.err.println("Broadcast: new move");
        for (GameListener l : listeners)
            l.gameChanged(GameListener.MOVE, this);
    }

    private void fireGameOver() {
        System.err.println("Broadcast: game over");
        for (GameListener l : listeners)
            l.gameChanged(GameListener.GAMEOVER, this);
    }

    public int lastMoveCol() {
        return board.getLastMove();
    }

    public int lastMoveRow() {
        return board.getHeight(board.getLastMove()) - 1;
    }

    public ManagedPlayer lastPlayer() {
        return (ManagedPlayer) board.getPrevPlayer();
    }

    public ManagedPlayer getWinner() {
        return (ManagedPlayer) board.winner;
    }



    /**
     * The GameMgrBoard class extends BasicBoard with methods
     * that assist the game manager.
     */
    private class GameMgrBoard extends BasicBoard
    {

        protected boolean gameOver;
        protected ManagedPlayer winner;

        public GameMgrBoard() {
            gameOver = false;
        }


        public ManagedPlayer getNextPlayer() {
            return (ManagedPlayer) player[log.getCommittedMoves() % 2];
        }

        public BasicPlayer getPrevPlayer() {
            if (log.getCommittedMoves() > 0)
                return player[(log.getCommittedMoves() - 1) % 2];
            else return null;
        }

        @Override
        public void commitMove(int col) {
            ManagedPlayer p = getNextPlayer();
            super.commitMove(col);
            checkGameOver(p, col);
        }


        /**
         * Check if the move Player p made, ended the game.
         *
         * @param p the player who moved last
         * @param col the column he chose
         * @return true if the game is over false if not.
         */
        private boolean checkGameOver(ManagedPlayer p, final int col) {
            if (gameOver) return gameOver;
            if (log.getCommittedMoves() == MAX_MOVES) {
                gameOver = true;
                winner = null;
            }
            final int row = height[col] - 1;
            if (check(p, row, col, Dir.RIGHT) ||
                    check(p, row, col, Dir.RIGHT_UP) ||
                    check(p, row, col, Dir.UP) ||
                    check(p, row, col, Dir.LEFT_UP)) {
                gameOver = true;
                winner = p;
                System.out.println(p.getName() + " won");
            }
            return gameOver;
        }

        private boolean check(ManagedPlayer p,
                              final int row, final int col,
                              Dir dir) {
            int r = row;
            int c = col;
            while (validPos(r, c) && get(r, c) == p) {
                r = r - dir.dr;
                c = c - dir.dc;
            }
            r = r + dir.dr;
            c = c + dir.dc;

            int count = 0;
            while (validPos(r, c) && get(r, c) == p) {
                r = r + dir.dr;
                c = c + dir.dc;
                count++;
            }
            if (count >= CONN_LEN) return true;
            return false;
        }
    }
}
