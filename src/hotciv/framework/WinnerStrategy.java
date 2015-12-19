package hotciv.framework;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by markflarup on 17/11/15.
 */
public interface WinnerStrategy {

    public Player getWinner(Game game, Map<Player, ArrayList<Integer>> battlesWon);
}
