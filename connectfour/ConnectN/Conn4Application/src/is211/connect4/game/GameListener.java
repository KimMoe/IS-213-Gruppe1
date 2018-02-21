/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is211.connect4.game;



/**
 *
 * @author evenal
 */
public interface GameListener
{

    public static final int NEWGAME = 1;
    public static final int MOVE = 2;
    public static final int GAMEOVER = 3;

    void gameChanged(int reason, GameManager game);
}
