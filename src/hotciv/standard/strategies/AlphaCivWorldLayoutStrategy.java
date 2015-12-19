package hotciv.standard.strategies;

import hotciv.framework.*;
import hotciv.standard.*;

/**
 * Created by Milledsk on 18-11-2015.
 */
public class AlphaCivWorldLayoutStrategy implements WorldLayoutStrategy {
    @Override
    public void createWorld(Game game) {
        World world = game.getWorld();
        world.setUnitAt(new Position(2, 0), new UnitImpl(Player.RED, GameConstants.ARCHER, ((GameImpl)game).getUnitConstantsStrategy()));
        world.setUnitAt(new Position(3, 2), new UnitImpl(Player.BLUE, GameConstants.LEGION, ((GameImpl)game).getUnitConstantsStrategy()));
        world.setUnitAt(new Position(4, 3), new UnitImpl(Player.RED, GameConstants.SETTLER, ((GameImpl)game).getUnitConstantsStrategy()));

        ((WorldImpl)world).setCityAt(new Position(1, 1), new CityImpl(Player.RED));
        ((WorldImpl)world).setCityAt(new Position(4, 1), new CityImpl(Player.BLUE));

        ((WorldImpl)world).setTileAt(new Position(0,1), new TileImpl(GameConstants.HILLS));
        ((WorldImpl)world).setTileAt(new Position(2,2), new TileImpl(GameConstants.MOUNTAINS));
        ((WorldImpl)world).setTileAt(new Position(1,0), new TileImpl(GameConstants.OCEANS));
    }
}
