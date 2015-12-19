package standard;

import hotciv.framework.*;
import hotciv.standard.GameImpl;
import hotciv.standard.ThetaCivGameConstants;
import hotciv.standard.UnitImpl;
import hotciv.standard.factories.ThetaCivFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by ChristianNordstrøm on 07-12-2015.
 */
public class TestThetaCiv {
    Game game;

    @Before
    public void setup(){
        game = new GameImpl(new ThetaCivFactory());

    }

    @Test
    public void createRedChariot(){
        Unit chariot = new UnitImpl(Player.RED, ThetaCivGameConstants.CHARIOT, ((GameImpl)game).getUnitConstantsStrategy());
        assertThat(chariot.getTypeString(), is(ThetaCivGameConstants.CHARIOT));
    }

    @Test
    public void redChariotHasAttackStrengthOf3(){
        Unit chariot = new UnitImpl(Player.RED, ThetaCivGameConstants.CHARIOT, ((GameImpl)game).getUnitConstantsStrategy());
        assertThat(chariot.getAttackingStrength(), is(3));
    }

    @Test
    public void redChariotHasDefensiveStrengthOf1(){
        Unit chariot = new UnitImpl(Player.RED, ThetaCivGameConstants.CHARIOT, ((GameImpl)game).getUnitConstantsStrategy());
        assertThat(chariot.getDefensiveStrength(), is(1));
    }

    @Test
    public void redChariotCost20Production(){
        Unit chariot = new UnitImpl(Player.RED, ThetaCivGameConstants.CHARIOT, ((GameImpl)game).getUnitConstantsStrategy());
        assertThat(chariot.getCost(), is(20));
    }

    @Test
    public void produceRedChariotAtCityAt1x1(){
        game.changeProductionInCityAt(new Position(1,1), ThetaCivGameConstants.CHARIOT);
        endOfRound(4);
        assertThat(game.getUnitAt(new Position(1,1)).getTypeString(), is(ThetaCivGameConstants.CHARIOT));
    }

    @Test
    public void chariotFortifiesAndGetsDefensiveStrengthOf2(){
        Unit chariot = new UnitImpl(Player.RED, ThetaCivGameConstants.CHARIOT, ((GameImpl)game).getUnitConstantsStrategy());
        Position chariotPostion = new Position(5,5);
        World world = game.getWorld();
        world.setUnitAt(chariotPostion, chariot);
        game.performUnitActionAt(chariotPostion);

        assertThat(chariot.getDefensiveStrength(), is(2));
    }

    @Test
    public void chariotFortifiesAndGetsDefensiveStrengthOf1(){
        Unit chariot = new UnitImpl(Player.RED, ThetaCivGameConstants.CHARIOT, ((GameImpl)game).getUnitConstantsStrategy());
        Position chariotPostion = new Position(5,5);
        World world = game.getWorld();
        world.setUnitAt(chariotPostion, chariot);
        game.performUnitActionAt(chariotPostion);
        endOfRound(1);
        game.performUnitActionAt(chariotPostion);

        assertThat(chariot.getDefensiveStrength(), is(1));
    }

    public void endOfRound(int rounds){
        for(int i = 0; i < rounds; i++){
            game.endOfTurn();
            game.endOfTurn();
        }
    }



}
