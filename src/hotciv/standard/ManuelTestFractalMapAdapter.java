package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.World;
import hotciv.framework.WorldLayoutStrategy;
import hotciv.standard.factories.DeltaCivFactory;

/**
 * Created by Milledsk on 17-12-2015.
 */
public class ManuelTestFractalMapAdapter {

    public static void main(String[] args) {
        Game game = new GameImpl(new DeltaCivFactory());

        System.out.println("Test the fractalMapAdapter");

        WorldLayoutStrategy worldLayoutStrategy = new FractalMapAdapter();
        worldLayoutStrategy.createWorld(game);

        World world = game.getWorld();
        System.out.print("Tile (0,0)'s typeString is: ");
        System.out.println(world.getTileAt(new Position(0,0)).getTypeString());
    }
}
