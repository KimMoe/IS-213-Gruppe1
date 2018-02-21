/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is211.connect4.gui;

import is211.connect4.game.Tournament.Match;
import javax.swing.table.AbstractTableModel;



/**
 *
 * @author evenal
 */
public class TournamentResults extends AbstractTableModel
{

    int numPlayers;
    String[] playerName;
    String[] rowLabel;
    int[] playerSum;
    int[] playerColourSum;
    int[][] matchPoints;

    public TournamentResults(int numPlayers, String[] playerNames) {
        this.numPlayers = numPlayers;
        this.playerName = playerNames;
        rowLabel = new String[2 * numPlayers];
        playerSum = new int[numPlayers];
        playerColourSum = new int[2 * numPlayers];
        matchPoints = new int[2 * numPlayers][numPlayers];
        for (int p = 0; p < numPlayers; p++) {
            playerSum[p] = 0;
            rowLabel[2 * p] = playerName[p] + " (Red)";
            rowLabel[2 * p + 1] = playerName[p] + " (Blue)";
        }
    }

    public void addResult(Match m) {
        int redidx = m.getRedPlayer();
        int bluidx = m.getBluePlayer();
        int redpts = m.getRedPoints();
        int bluepts = m.getBluePoints();

        matchPoints[2 * redidx][bluidx] = redpts;
        matchPoints[2 * bluidx + 1][redidx] = bluepts;
        playerColourSum[2 * redidx] += redpts;
        playerColourSum[2 * bluidx + 1] += bluepts;
        playerSum[redidx] += redpts;
        playerSum[bluidx] += bluepts;
        fireTableDataChanged();
//        if (redidx < bluidx) fireTableRowsUpdated(2 * redidx, 2 * bluidx + 1);
//        else fireTableRowsUpdated(2 * bluidx, 2 * redidx - 1);
    }

    @Override
    public int getRowCount() {
        return 2 * numPlayers;
    }

    @Override
    public int getColumnCount() {
        return numPlayers + 3;
    }

    @Override
    public Class<?> getColumnClass(int col
    ) {
        if (col == 0) return String.class;
        col--;
        if (col < numPlayers) return Integer.class;
        col -= numPlayers;
        if (col == 0) return Integer.class;
        return String.class;
    }

    @Override
    public String getColumnName(int col
    ) {
        if (col == 0) return "Player";
        col--;
        if (col < numPlayers) return playerName[col];
        col -= numPlayers;
        if (col == 0) return "Sum";
        else return "Total";
    }

    @Override
    public Object getValueAt(int row, int col
    ) {
        if (col == 0) return rowLabel[row];

        col = col - 1;
        if (col < numPlayers) return matchPoints[row][col];

        col = col - numPlayers;

        if (col == 0) return playerColourSum[row];

        if (row % 2 == 0) return "" + playerSum[row / 2];
        else return "";
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex
    ) {
        return false;
    }
}
