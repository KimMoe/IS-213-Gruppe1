/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is211.connect4;

import is211.connect4.gui.Connect4MainPanel;
import is211.connect4.gui.MainWindow;
import is211.connect4.strategy.StrategyManager;
import java.io.PrintStream;



/**
 *
 * @author evenal
 */
public class Main
{

    public static PrintStream logg;
    public static boolean tee = true;

    static {
//        try {
        logg = System.out;
//        catch (IOException e) {
//            logg = null;
//        }
    }

    public static void log(String m) {
        logg.println(m);
        logg.flush();
        if (tee) System.err.println(m);
    }

    public static final void main(String[] args) {
        StrategyManager mgr = StrategyManager.getInstance();

        Connect4MainPanel mainPanel = new Connect4MainPanel();
        MainWindow window = new MainWindow("JConnectN", mainPanel);
        window.setExitHandler(mainPanel);
        window.start();
    }

}
