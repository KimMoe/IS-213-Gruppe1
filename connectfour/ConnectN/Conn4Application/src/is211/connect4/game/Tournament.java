/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is211.connect4.game;

import is211.connect4.gui.Connect4MainPanel;
import is211.connect4.gui.GamePanel;
import is211.connect4.gui.TournamentResults;
import is211.connect4.strategy.StrategyManager;
import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;



/**
 *
 * @author evenal
 */
public class Tournament implements GameListener
{

    // the names of participating strategies
    String[] names;
    // number of players
    int numPlayers;
    // list of matches;
    ArrayList<Match> matches;
    TournamentResults results;

    PrintWriter tlog;

    int currentMatch;
    GameManager game;
    GamePanel gamePanel;
    Connect4MainPanel mainPanel;


    public Tournament(Connect4MainPanel mainWin, GamePanel gamePanel) {
        try {
            tlog = new PrintWriter("tournament.log");
        }
        catch (IOException ioe) {
        }

        this.mainPanel = mainWin;
        this.gamePanel = gamePanel;
        setup();
    }

    private void setup() {
        StrategyManager stratMgr = StrategyManager.getInstance();
        names = filter(stratMgr.getStrategyNames());
        // set up match program
        matches = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++)
            for (int j = i + 1; j < numPlayers; j++) {
                matches.add(new Match(i, j));
                matches.add(new Match(j, i));
            }
        Collections.shuffle(matches);
        currentMatch = 0;
        results = new TournamentResults(numPlayers, names);
        startMatch(currentMatch);
    }

    public void abort() {
        if (game != null) game.abort();
    }

    public TournamentResults getResults() {
        return results;
    }

    private String[] filter(String[] names) {
        // remove HumanStrategy from the competitors
        numPlayers = 0;
        for (int i = 0; i < names.length; i++) {
            if (!names[i].contains("Human")) {
                names[numPlayers++] = names[i];
            }
        }
        names = Arrays.copyOf(names, numPlayers);
        Arrays.sort(names);
        return names;
    }


    private void startMatch(int i) {
        Match match = matches.get(i);
        try {
            Thread.sleep(2000);
            game = new GameManager(names[match.red], names[match.blue]);
        }
        catch (InstantiationException | IllegalAccessException | InterruptedException ie) {
            ie.printStackTrace();
        }
        game.addGameListener(this);
        game.addGameListener(mainPanel);
        game.addGameListener(gamePanel);
        game.runGame();
    }

    @Override
    public void gameChanged(int reason, GameManager game) {
        switch (reason) {
        case GameListener.NEWGAME:
        case GameListener.MOVE:
            // deliberately ignored, moves handled by the gamepanel
            break;
        case GameListener.GAMEOVER:
            handleGameOver();
            break;
        }
    }


    private void handleGameOver() {
        ManagedPlayer winner = game.getWinner();
        if (winner == null)
            assignPoints(1, 1);
        if (winner.getColour().awtColour == Color.RED) {
            assignPoints(2, 0);
        }
        else {
            assignPoints(0, 2);
        }
        currentMatch++;
        if (currentMatch < matches.size()) {
            startMatch(currentMatch);
        }
        else if (game != null) game.abort();

    }

    /**
     * assign points to the players after the match. The winner gets
     * 2 points, loser gets none, both get one point if it's a draw
     *
     * @param redPoints number of points awarded the red player
     * @param bluePoints number of points awarded the blue player
     */
    private void assignPoints(int redPoints, int bluePoints) {
        Match m = matches.get(currentMatch);
        m.setPoints(redPoints, bluePoints);
        Match cm = matches.get(currentMatch);
        tlog.format("%s - %s:  %d - %d\n",
                    names[cm.red], names[cm.blue],
                    cm.redPoints, cm.bluePoints);
        tlog.flush();
        results.addResult(m);
    }



    public static class Match
    {

        int red;
        int blue;
        int redPoints;
        int bluePoints;

        Match(int red, int blue) {
            this.red = red;
            this.blue = blue;
        }

        void setPoints(int red, int blue) {
            redPoints = red;
            bluePoints = blue;
        }


        public int getRedPlayer() {
            return red;
        }

        public int getBluePlayer() {
            return blue;
        }

        public int getRedPoints() {
            return redPoints;
        }

        public int getBluePoints() {
            return bluePoints;
        }

    }
}
