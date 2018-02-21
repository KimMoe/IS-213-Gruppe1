package is211.connect4.game;



/**
 *
 * @author evenal
 */
public class GameException extends Exception
{

    private ManagedPlayer player;


    public GameException() {
    }


    public GameException(String msg) {
        super(msg);
    }


    public GameException(String msg, Exception cause) {
        super(msg, cause);
    }


    public GameException(String msg, Exception cause, ManagedPlayer player) {
        super(msg, cause);
        this.player = player;
    }


    public ManagedPlayer getPlayer() {
        return player;
    }
}
