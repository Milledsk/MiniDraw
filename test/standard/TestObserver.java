package standard;

import hotciv.framework.*;
import hotciv.standard.*;
import hotciv.standard.factories.AlphaCivFactory;
import hotciv.standard.strategies.AlphaCivUnitConstantsStrategy;
import org.junit.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by Milledsk on 18-12-2015.
 */
public class TestObserver {
    Game game;
    World world;
    GameObserver gameObserver = new GameObserverImpl();

    @Before
    public void setup(){
        game = new GameImpl(new AlphaCivFactory());
        world = game.getWorld();

    }

    //WorldChangedAt tests
    @Test
    public void aUnitIsPlacedAt5x5(){
        Unit unit = new UnitImpl(Player.RED, GameConstants.ARCHER, new AlphaCivUnitConstantsStrategy());
        Position from = new Position(5,5);
        Position to = new Position(5,6);

        game.addObserver(new GameObserver() {
            int worldTest = 0;
            int turnTest = 0;

            @Override
            public void worldChangedAt(Position pos) {
                if(worldTest == 0){
                    worldTest++;
                    assertThat("world has changed at 5x5)", pos, is(from));
                }else if(worldTest == 1){
                    worldTest++;
                    assertThat("world has changed at 5x5)", pos, is(from));
                } else{
                    assertThat("world has changed at 5x6)", pos, is(to));
                }

            }

            @Override
            public void turnEnds(Player nextPlayer, int age) {
                if(turnTest == 0){
                    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
                    turnTest++;
                } else if(turnTest == 1){
                    assertThat(game.getPlayerInTurn(), is(Player.RED));
                    turnTest = 0;
                }

            }

            @Override
            public void tileFocusChangedAt(Position position) {

            }
        });
        world.setUnitAt(from, unit);
        assertThat(world.getUnitAt(from), is(notNullValue()));
        assertThat(game.moveUnit(from, to), is(true));
        assertThat(world.getUnitAt(to), is(notNullValue()));

        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
    }

}
