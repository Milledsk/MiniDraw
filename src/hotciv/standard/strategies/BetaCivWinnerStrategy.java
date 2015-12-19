package hotciv.standard.strategies;

import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.standard.WorldImpl;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by markflarup on 17/11/15.
 */
public class BetaCivWinnerStrategy implements WinnerStrategy {


    @Override
    public Player getWinner(Game game, Map<Player, ArrayList<Integer>> battlesWon) {
        World world = game.getWorld();
        Map<Position, City> cityLocations = ((WorldImpl)world).getAllCities();
        Player owner = null;
        Player firstOwner = null;

        for(Position p: cityLocations.keySet()) {
            if(firstOwner == null) {
                firstOwner = ((CityImpl)cityLocations.get(p)).getOwner();
            }

            owner = ((CityImpl)cityLocations.get(p)).getOwner();

            if(!(firstOwner.equals(owner))) {
                return null;
            }
        }
        return owner;
    }
}
