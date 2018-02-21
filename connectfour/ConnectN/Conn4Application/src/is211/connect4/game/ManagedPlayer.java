package is211.connect4.game;

import is211.connect4.strategy.BasicBoard;
import is211.connect4.strategy.GameAI;
import is211.connect4.strategy.HumanStrategy;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeoutException;
import javax.swing.SwingWorker;



/**
 * This is subclass of BasicPlayer extended with
 * methods to help the GameMgr do its job.
 *
 * @author evenal
 */
public final class ManagedPlayer extends BasicBoard.BasicPlayer
{

    public static final int NO_SELECTED_MOVE = -1;
    public static final boolean DEBUGGING = false;

    private GameAI strategy;
    ExecutorService exec;
    Random rand;

    public ManagedPlayer(String name, GameAI strategy) {
        super(null);
        this.strategy = strategy;
        rand = new Random();
    }

    public String getName() {
        return strategy.getName();
    }

    public Color getAwtColour() {
        return getColour().awtColour;
    }

    public final int computeMove(BasicBoard game) {
        ArrayList<Integer> validMoves = game.getPossibleMoves();
        strategy.update(game.getLog(), validMoves);

        int move = NO_SELECTED_MOVE;
        if (strategy instanceof HumanStrategy || DEBUGGING) {
            try {
                strategy.selectMove();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            SwingWorker<Integer, Integer> worker = new SwingWorker<Integer, Integer>()
            {
                @Override
                protected Integer doInBackground() throws Exception {
                    strategy.selectMove();
                    return strategy.getSelectedMove();
                }
            };

            try {
                worker.execute();
                worker.get(GameAI.TIME_LIMIT, GameAI.TIME_UNIT);
            }
            catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        }
        move = strategy.getSelectedMove();
        if (!validMoves.contains(move)) {
            int i = rand.nextInt(validMoves.size());
            move = validMoves.get(i);
        }
        return move;
    }
}
