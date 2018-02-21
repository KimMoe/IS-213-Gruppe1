/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.net.URL;
import javax.swing.ImageIcon;


/**
 *
 * @author evenal
 */
public enum Player {
    NONE(" "), BLACK("X"), WHITE("O");

    String symbol;
    ImageIcon icon;


    Player(String symbol) {
        this.symbol = symbol;
        URL imageUrl = getClass().getResource(name() + ".png");
        icon = new ImageIcon(imageUrl);
    }

}
