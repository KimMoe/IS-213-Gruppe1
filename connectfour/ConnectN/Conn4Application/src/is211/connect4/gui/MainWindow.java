package is211.connect4.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;



/**
 *
 * @author evenal
 */
public final class MainWindow extends JFrame
{

    ExitHandler exitHandler;

    public MainWindow(String title, JComponent contentPane) {
        super(title);
        setContentPane(contentPane);
        exitOnClose();
    }

    public void center() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((screen.getWidth() - getWidth()) / 2);
        int y = (int) ((screen.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }

    public void setExitHandler(ExitHandler exitHandler) {
        this.exitHandler = exitHandler;
    }

    public void start() {
        pack();
        center();
        setVisible(true);
    }

    public void exit() {
        if (exitHandler != null) exitHandler.exit();
        for (java.awt.Frame frame : java.awt.Frame.getFrames()) {
            frame.dispose();
        }

        System.out.println("Should go away now...");
    }

    private void exitOnClose() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
    }



    public static interface ExitHandler
    {

        public void exit();
    }
}