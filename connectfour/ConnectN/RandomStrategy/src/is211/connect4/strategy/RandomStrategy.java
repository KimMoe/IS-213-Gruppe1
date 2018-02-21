package is211.connect4.strategy;


import java.util.Random;



/**
 *
 * @author evenal
 */
public class RandomStrategy extends AbstractGameAI
{

    private Random random = new Random();

    public int selectMove() {
        int i = random.nextInt(validMoves.size());
        selectedMove = validMoves.get(i);
        return selectedMove;
    }
}
