package hotciv.standard.strategies;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.WinnerStrategy;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by markflarup on 17/11/15.
 */
public class AlphaCivWinnerStrategy implements WinnerStrategy {


    @Override
    public Player getWinner(Game game, Map<Player, ArrayList<Integer>> battlesWon) {
        if(game.getAge() < -3000){
            return null;
        } else {
            return Player.RED;
        }
    }
}
