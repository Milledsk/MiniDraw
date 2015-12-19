package hotciv.standard;

import hotciv.framework.*;
import thirdparty.ThirdPartyFractalGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChristianNordstrøm on 17-12-2015.
 */
public class FractalMapAdapter implements WorldLayoutStrategy {

    @Override
    public void createWorld(Game game) {

        World world = game.getWorld();
        ((WorldImpl) world).putAllTiles(defineWorld());
    }

    private Map<Position,Tile> defineWorld() {

        ThirdPartyFractalGenerator generator =
                new ThirdPartyFractalGenerator();
        String line;
        Map<Position,Tile> theWorld = new HashMap<Position,Tile>();


        for (int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
            line = "";
            for (int c = 0; c < GameConstants.WORLDSIZE; c++) {
                char tileChar = generator.getLandscapeAt(r, c);
                line = line + tileChar;
                String type = "error";
                if ( tileChar == '.' ) { type = GameConstants.OCEANS; }
                if ( tileChar == 'o' ) { type = GameConstants.PLAINS; }
                if ( tileChar == 'M' ) { type = GameConstants.MOUNTAINS; }
                if ( tileChar == 'f' ) { type = GameConstants.FOREST; }
                if ( tileChar == 'h' ) { type = GameConstants.HILLS; }
                Position p = new Position(r,c);
                theWorld.put( p, new TileImpl(type));
            }
            System.out.println( line );
        }

        return theWorld;
    }
}

