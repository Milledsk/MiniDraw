package standard;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.standard.factories.DeltaCivFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Milledsk on 18-11-2015.
 */
public class TestDeltaCiv {

    Game game;

    @Before
    public void setup(){
        game = new GameImpl(new DeltaCivFactory());
    }

    @Test
    public void ThereShouldBeARedCityAt8x12(){
        assertThat(game.getCityAt(new Position(8,12)).getOwner(), is(Player.RED));
    }

    @Test
    public void ThereShouldBeABlueCityAt4x5(){
        assertThat(game.getCityAt(new Position(4,5)).getOwner(), is(Player.BLUE));
    }

    @Test
    public void ThereShouldBeAOceanAt0x0(){
        assertThat(game.getTileAt(new Position(0,0)).getTypeString(), is(GameConstants.OCEANS));
    }
}
