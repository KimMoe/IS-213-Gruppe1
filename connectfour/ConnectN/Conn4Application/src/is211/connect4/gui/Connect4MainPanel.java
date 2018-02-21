package is211.connect4.gui;

import is211.connect4.game.GameListener;
import is211.connect4.game.GameManager;
import is211.connect4.game.ManagedPlayer;
import is211.connect4.game.Tournament;
import is211.connect4.strategy.StrategyManager;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



/**
 * This is the main window of the GUI. It contains the board,
 * the names of the players and buttons to start new
 * single games or tournaments
 *
 * @author evenal
 */
public final class Connect4MainPanel extends JPanel implements GameListener, MainWindow.ExitHandler
{

    JTextField status;
    JTextField colField, rowField, timeoutField, connLenField;
    JTextField p1, p2;

    GamePanel board;
    ManagedPlayer winner = null;

    int delay = 1000; //millisec
    StrategyManager stratMgr;
    GameManager game;

    Tournament tournament;
    TournamentPanel tournamentPanel;


    public Connect4MainPanel() {
        setLayout(new BorderLayout());
        JPanel menu = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
                                                                0, new Insets(3, 3, 3, 3), 5, 5);
        addChild(menu, new JButton(new PlayGameAction()), constraints);
        addChild(menu, new JButton(new PlayTournamentAction()), constraints);
        addChild(menu, new JLabel(" "), constraints);
        JLabel redLabel = new JLabel("The Red Player is:");
        addChild(menu, redLabel, constraints);
        p1 = new JTextField(20);
        p1.setEditable(false);
        addChild(menu, p1, constraints);
        JLabel blueLabel = new JLabel("TheBlue Player is:");
        addChild(menu, blueLabel, constraints);
        p2 = new JTextField(20);
        addChild(menu, p2, constraints);
        p2.setEditable(false);
        constraints.weighty = 1;
        addChild(menu, new JLabel(""), constraints);
        add(menu, BorderLayout.EAST);

        status = new JTextField();
        add(status, BorderLayout.SOUTH);
        status.setEditable(false);
        board = new GamePanel();
        add(board);

        stratMgr = StrategyManager.getInstance();
        game = null;
    }

    private void addChild(JPanel parent, JComponent child, GridBagConstraints constraints) {
        parent.add(child, constraints);
        constraints.gridy++;
    }

    @Override
    public void gameChanged(int reason, GameManager game) {
        if (reason == GameListener.NEWGAME) {
            p1.setText(game.getPlayer1().getName());
            p2.setText(game.getPlayer2().getName());
            board.reset();
            status.setText("");
        }
        else if (reason == GameListener.GAMEOVER) {
            ManagedPlayer winner = game.getWinner();
            if (null == winner) {
                status.setText("Draw");
            }
            else {
                status.setText(winner.getName() + " won");
            }
        }
    }


    public void exit() {
        if (game != null) game.abort();
        if (tournament != null) tournament.abort();
    }



    private class PlayTournamentAction extends AbstractAction
    {

        public PlayTournamentAction() {
            super("Tournament");
        }

        /**
         * Start the tournament "pseudo-sequence" is::
         * we create a new tournament here, and feed it pointers to the
         * mainPanel and the board,
         * The tournament sets up, and starts the tournament,
         * - When the first game is ready a newgame event is to the
         * mainpanel - to update board,a and player names in the gui
         * gamePanel - t
         *
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
            tournament = new Tournament(Connect4MainPanel.this, board);
            tournamentPanel = new TournamentPanel(tournament.getResults());
            tournamentPanel.setVisible(true);
        }
    }



    private class PlayGameAction extends AbstractAction
    {

        public PlayGameAction() {
            super("Single game...");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String[] stratName = selectStrategyNames();
            startNewGame(stratName[0], stratName[1]);
        }
    }

    private void startNewGame(String strat1, String strat2) {
        try {
            game = new GameManager(strat1, strat2);
            game.addGameListener(board);
            game.addGameListener(this);
            game.runGame();
        }
        catch (IllegalAccessException | InstantiationException ie) {
            System.err.println("Starting game faild, because:");
            ie.printStackTrace();
        }

    }

    private String[] selectStrategyNames() {
        SimpleForm form = new SimpleForm();
        StrategyManager stratMgr = StrategyManager.getInstance();
        String[] strategies = stratMgr.getStrategyNames();
        JComboBox<String> stratSelector1 = new JComboBox<>(strategies);
        JComboBox<String> stratSelector2 = new JComboBox<>(strategies);
        form.addComboBox("First player strategy:", stratSelector1);
        form.addComboBox("Second player strategy:", stratSelector2);
        JOptionPane.showConfirmDialog(this,
                                      form, "Select player strategies:",
                                      JOptionPane.OK_CANCEL_OPTION);
        String[] strategyNames = new String[]{
            strategies[stratSelector1.getSelectedIndex()],
            strategies[stratSelector2.getSelectedIndex()]
        };

        return strategyNames;
    }
}
