/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is211.connect4.strategy;

import javax.swing.JOptionPane;



/**
 *
 * @author evenal
 */
public class HumanStrategy extends AbstractGameAI
{

    @Override
    public int selectMove() {
        StringBuilder buf = new StringBuilder("Enter your move (valid choices are");
        for (int c : validMoves) buf.append(" ").append(c);
        buf.append("): ");
        do {
            String input = JOptionPane.showInputDialog(null, buf.toString());
            try {
                int move = Integer.decode(input);
                selectedMove = move;
                return move;
            }
            catch (NumberFormatException nfe) {
                // ignore
            }
        }
        while (true);
    }
}
