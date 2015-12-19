package hotciv.standard.strategies;

import hotciv.framework.WorldAgeStrategy;

/**
 * Created by markflarup on 12/11/15.
 */
public class AlphaCivAgeStrategy implements WorldAgeStrategy {

    @Override
    public int worldAgingProgress(int gameStart, int rounds) {
        return gameStart + rounds * 100;
    }
}
