package hotciv.standard.strategies;

import hotciv.framework.DieStrategy;

/**
 * Created by Milledsk on 23-11-2015.
 */
public class RandomDieStrategy implements DieStrategy {
    @Override
    public int roleDie() {
        int random = (int) (6*Math.random()) + 1;
        return random;
    }
}
