package standard;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.standard.GameImpl;
import hotciv.standard.factories.GammaCivFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;


/**
 * Created by ChristianNordstrøm on 17-11-2015.
 */
public class TestGammaCiv {

    private Game game;

    @Before
    public void setup() {
        game = new GameImpl(new GammaCivFactory());
    }

    @Test
    public void redSettlerTurnsIntoRedCityWithPopulationOfOne() {
        Position settlerPosition = new Position(4,3);

        game.performUnitActionAt(settlerPosition);

        assertThat(game.getUnitAt(settlerPosition), is(nullValue()));
        assertThat(game.getCityAt(settlerPosition).getOwner(), is(Player.RED));
        assertThat(game.getCityAt(settlerPosition).getSize(), is(1));
    }

    @Test
    public void redArcherFortifiesWhenActionCalled() {
        Position archerPosition = new Position(2,0);
        Unit archer = game.getUnitAt(archerPosition);

        int strength = archer.getDefensiveStrength();

        game.performUnitActionAt(archerPosition);

        assertThat(game.getUnitAt(archerPosition).getDefensiveStrength(), is(strength*2));
        assertThat(game.moveUnit(archerPosition, new Position(2,1)), is(false));
    }

    @Test
    public void redArcherUnfortify(){
        Position archerPosition = new Position(2,0);
        Unit archer = game.getUnitAt(archerPosition);
        int strength = archer.getDefensiveStrength();

        game.performUnitActionAt(archerPosition);
        game.endOfTurn();
        game.endOfTurn();
        game.performUnitActionAt(archerPosition);

        assertThat(game.getUnitAt(archerPosition).getDefensiveStrength(), is(strength));
        assertThat(game.moveUnit(archerPosition, new Position(2,1)), is(true));

    }


}
