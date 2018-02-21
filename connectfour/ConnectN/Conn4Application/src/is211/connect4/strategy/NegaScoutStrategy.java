/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is211.connect4.strategy;



/**
 *
 * @author evenal
 */
public class NegaScoutStrategy extends AbstractGameAI
{

    public static final int US = 1;
    public static final int THEM = -1;
    public static final int MAX_DEPTH = 5;
    public static final int MIN_VALUE = Integer.MIN_VALUE;
    public static final int ALPHA = Integer.MIN_VALUE;
    public static final int BETA = Integer.MAX_VALUE;
    public static final int[] LOOK_AHEADS = {
        3, 5, 9, 15, 23
    };

    private int[] priCols;

    @Override
    public int selectMove() {
        return 0;
    }


}
