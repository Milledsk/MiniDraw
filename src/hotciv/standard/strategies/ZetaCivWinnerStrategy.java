package hotciv.standard.strategies;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.WinnerStrategy;
import hotciv.standard.GameImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChristianNordstrøm on 24-11-2015.
 */
public class ZetaCivWinnerStrategy implements WinnerStrategy {

    private final WinnerStrategy before20Rounds;
    private final WinnerStrategy after20Rounds;
    private WinnerStrategy currentState;

    public ZetaCivWinnerStrategy(WinnerStrategy before20Rounds, WinnerStrategy after20Rounds) {

        this.before20Rounds = before20Rounds;
        this.after20Rounds = after20Rounds;
        this.currentState = null;
    }

    @Override
    public Player getWinner(Game game, Map<Player, ArrayList<Integer>> battlesWon) {
        if(((GameImpl)game).getRounds() < 20){
            currentState = before20Rounds;
        }
        else{
            currentState = after20Rounds;
        }
        return  currentState.getWinner(game, makeNewMap(battlesWon));
    }

    public Map<Player, ArrayList<Integer>> makeNewMap(Map<Player, ArrayList<Integer>> battlesWon){

        //Make empty map.
        Map<Player, ArrayList<Integer>> newBattlesWon = new HashMap<Player, ArrayList<Integer>>();

        //Search HashMap
        for(Player player : battlesWon.keySet()) {

            if(!(newBattlesWon.containsKey(player))){
                newBattlesWon.put(player, new ArrayList<Integer>());
            }

            List<Integer> rounds = battlesWon.get(player);
            //Search ArrayList
            for(int i = 0; i < rounds.size(); i++){
                int round = rounds.get(i);
                if (round >= 20) {
                    newBattlesWon.get(player).add(round);
                }
            }
        }
        return newBattlesWon;
    }
}
