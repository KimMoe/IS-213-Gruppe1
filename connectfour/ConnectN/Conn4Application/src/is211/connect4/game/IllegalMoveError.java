package is211.connect4.game;



/**
 *
 * @author evenal
 */
public class IllegalMoveError extends Error
{

    /**
     * Creates a new instance of <code>IllegalMoveException</code>
     * without detail message.
     */
    public IllegalMoveError() {
    }


    /**
     * Constructs an instance of <code>IllegalMoveException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public IllegalMoveError(String msg) {
        super(msg);
    }
}
