package is211.connect4.gui;

import is211.connect4.game.GameListener;
import is211.connect4.game.GameManager;
import is211.connect4.game.ManagedPlayer;
import is211.connect4.strategy.BasicBoard;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;
import javax.swing.JPanel;



/**
 * This class displays the game log on the screen. .
 *
 * @author evenal
 */
public class GamePanel extends JPanel implements GameListener
{

    public static final int CELL_GAP = 0;
    public static final Color EMPTY_COLOUR = Color.DARK_GRAY;
    public static final Color PLAYER1_COLOUR = Color.RED;
    public static final Color PLAYER2_COLOUR = Color.BLUE;

    private static final int CELL_SIZE = 50; // cell size
    private static final int CELL_BORDER = 5; // distance between cells

    private final Cell[][] board;

    public GamePanel() {
        GridLayout layout = new GridLayout(BasicBoard.ROWS,
                                           BasicBoard.COLS,
                                           CELL_GAP,
                                           CELL_GAP);
        setLayout(layout);

        board = new Cell[BasicBoard.ROWS][BasicBoard.COLS];
        for (int r = 0; r < BasicBoard.ROWS; r++) {
            for (int c = 0; c < BasicBoard.COLS; c++) {
                board[r][c] = new Cell();
            }
        }
        for (int r = BasicBoard.ROWS; r > 0; r--) {
            for (int c = 0; c < BasicBoard.COLS; c++) {
                add(board[r - 1][c]);
            }
        }
        repaint();
    }

    public void reset() {
        for (int r = 0; r < BasicBoard.ROWS; r++) {
            for (int c = 0; c < BasicBoard.COLS; c++) {
                board[r][c].colour = EMPTY_COLOUR;
            }
        }
        repaint();
    }

    private void update(ManagedPlayer player, int row, int col) {
        board[row][col].colour = player.getColour().awtColour;
        System.err.println("updating ui");
        repaint();
    }

    public void gameChanged(int reason, GameManager game) {
        if (reason == GameListener.MOVE) {
            int col = game.lastMoveCol();
            int row = game.lastMoveRow();
            ManagedPlayer p = game.lastPlayer();
            update(p, row, col);
        }
        else if (reason == GameListener.NEWGAME) {
            reset();
        }

    }



    private class Cell extends JComponent
    {

        int row;
        int col;
        Color colour;

        public Cell() {
//            setBackground(Color.);
            colour = EMPTY_COLOUR;
            Dimension dim = new Dimension(CELL_SIZE, CELL_SIZE);
            setMinimumSize(dim);
            setMaximumSize(dim);
            setPreferredSize(dim);
            setSize(dim);
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            super.paintComponent(g);

            g2.setPaint(getBackground());
            g2.fill(new Rectangle2D.Double(0, 0, CELL_SIZE, CELL_SIZE));

            Ellipse2D disc = new Ellipse2D.Double();
            disc.setFrame(new Rectangle2D.Double(CELL_BORDER, CELL_BORDER,
                                                 CELL_SIZE - 2 * CELL_BORDER - 1, CELL_SIZE - 2 * CELL_BORDER - 1));
            g2.setPaint(colour);
            g2.fill(disc);

        }

    }
}
