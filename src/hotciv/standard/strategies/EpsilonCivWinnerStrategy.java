package hotciv.standard.strategies;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.WinnerStrategy;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Milledsk on 24-11-2015.
 */
public class EpsilonCivWinnerStrategy implements WinnerStrategy {

    @Override
    public Player getWinner(Game game, Map<Player, ArrayList<Integer>> battlesWon) {

        for(Player player : battlesWon.keySet()){
            if (battlesWon.get(player).size() == 3){
                return player;
            }
        }
        return null;
    }
}
