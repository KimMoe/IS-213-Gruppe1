/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is211.connect4.gui;

import java.awt.Container;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.TableModel;



/**
 *
 * @author evenal
 */
public class TournamentPanel extends JFrame
{

    TableModel model;

    public TournamentPanel(TableModel model) {
        super("Tournament");
        this.model = model;
        JTable table = new JTable(model);
        FontUIResource font = new FontUIResource(Font.SANS_SERIF, Font.PLAIN, 16);
        table.setFont(font);
        int h = table.getFontMetrics(font).getHeight();
        h = h + h / 2;
        table.setRowHeight(h);
        Container contentPane = getContentPane();
        contentPane.add(new JScrollPane(table));
        pack();
        setVisible(true);
    }
}
