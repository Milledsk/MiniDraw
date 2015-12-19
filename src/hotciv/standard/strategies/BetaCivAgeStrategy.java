package hotciv.standard.strategies;

import hotciv.framework.WorldAgeStrategy;

/**
 * Created by ChristianNordstrøm on 12-11-2015.
 */
public class BetaCivAgeStrategy implements WorldAgeStrategy {
    @Override
    public int worldAgingProgress(int gameStart, int rounds) {
        if(rounds < 40){
            return gameStart + rounds * 100;
        } else if(rounds == 40){
            return -1;
        } else if (rounds == 41){
            return 1;
        } else if (rounds == 42){
            return 50;
        } else if(rounds > 42 && rounds <= 76){
            return 50 + (rounds - 42)*50;
        } else if(rounds > 76 && rounds <= 82){
            return  1750 + (rounds - 76)*25;
        } else if(rounds > 82 && rounds <= 97){
            return 1900 + (rounds - 82)*5;
        } else {
            return 1970 + (rounds - 97);
        }
    }
}
