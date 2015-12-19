package standard;

import hotciv.framework.*;
import hotciv.standard.*;
import hotciv.standard.factories.EpsilonCivFactory;
import hotciv.standard.strategies.EpsilonCivBattleOutcomeStrategy;
import hotciv.standard.strategies.SetDieStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by markflarup on 19/11/15.
 */
public class TestEpsilonCiv {

    private Game game;
    private DieStrategy dieStrategy = new SetDieStrategy();

    @Before
    public void setup() {
        game = new GameImpl(new EpsilonCivFactory(new SetDieStrategy()));
    }

    @Test
    public void redLegionAttacksAndConquersBlueSettler() {

        World world = game.getWorld();

        Unit redLegion = new UnitImpl(Player.RED, GameConstants.LEGION, ((GameImpl) game).getUnitConstantsStrategy());
        Unit blueSettler = new UnitImpl(Player.BLUE, GameConstants.SETTLER, ((GameImpl) game).getUnitConstantsStrategy());

        Position legionPosition = new Position(5, 1);
        Position settlerPosition = new Position(5, 0);

        world.setUnitAt(settlerPosition, blueSettler);
        world.setUnitAt(legionPosition, redLegion);

        ((SetDieStrategy) dieStrategy).setDieValue(5);

        assertThat(game.moveUnit(legionPosition, settlerPosition), is(true));
    }

    @Test
    public void redArcherAttacksAndConquersBlueLegion() {

        World world = game.getWorld();

        Unit redArcher = new UnitImpl(Player.RED, GameConstants.ARCHER, ((GameImpl) game).getUnitConstantsStrategy());
        Unit blueLegion = new UnitImpl(Player.BLUE, GameConstants.LEGION, ((GameImpl) game).getUnitConstantsStrategy());

        Position archerPosition = new Position(5, 1);
        Position legionPosition = new Position(5, 0);

        ((WorldImpl) world).setCityAt(archerPosition, new CityImpl(Player.RED));

        world.setUnitAt(archerPosition, redArcher);
        world.setUnitAt(legionPosition, blueLegion);

        ((SetDieStrategy) dieStrategy).setDieValue(1);

        assertThat(game.moveUnit(archerPosition, legionPosition), is(true));
    }

    @Test
    public void blueLegionWithTwoFriendlyUnitsAttacksAndCConquersRedArcher() {
        World world = game.getWorld();

        Unit blueLegion = new UnitImpl(Player.BLUE, GameConstants.LEGION, ((GameImpl) game).getUnitConstantsStrategy());
        Unit blueFriend1 = new UnitImpl(Player.BLUE, GameConstants.LEGION, ((GameImpl) game).getUnitConstantsStrategy());
        Unit blueFriend2 = new UnitImpl(Player.BLUE, GameConstants.LEGION, ((GameImpl) game).getUnitConstantsStrategy());
        Unit redArcher = new UnitImpl(Player.RED, GameConstants.ARCHER, ((GameImpl) game).getUnitConstantsStrategy());

        Position attackerPosition = new Position(5, 5);
        Position defenderPosition = new Position(4, 5);

        world.setUnitAt(attackerPosition, blueLegion);
        world.setUnitAt(new Position(5, 4), blueFriend1);
        world.setUnitAt(new Position(5, 6), blueFriend2);
        world.setUnitAt(defenderPosition, redArcher);

        ((SetDieStrategy) dieStrategy).setDieValue(1);

        //So blue can move
        game.endOfTurn();

        assertThat(game.moveUnit(attackerPosition, defenderPosition), is(true));
    }

    @Test
    public void redWinsAfterThreeSuccessfulAttacks() {
        World world = game.getWorld();

        Unit redLegion = new UnitImpl(Player.RED, GameConstants.LEGION, ((GameImpl) game).getUnitConstantsStrategy());
        Unit blueSettler1 = new UnitImpl(Player.BLUE, GameConstants.SETTLER, ((GameImpl) game).getUnitConstantsStrategy());
        Unit blueSettler2 = new UnitImpl(Player.BLUE, GameConstants.SETTLER, ((GameImpl) game).getUnitConstantsStrategy());
        Unit blueSettler3 = new UnitImpl(Player.BLUE, GameConstants.SETTLER, ((GameImpl) game).getUnitConstantsStrategy());

        Position legionPosition = new Position(5, 0);
        Position settler1Position = new Position(5, 1);
        Position settler2Position = new Position(5, 2);
        Position settler3Position = new Position(5, 3);

        world.setUnitAt(legionPosition, redLegion);
        world.setUnitAt(settler1Position, blueSettler1);

        ((SetDieStrategy) dieStrategy).setDieValue(5);

        //Attack1:
        assertThat(game.moveUnit(legionPosition, settler1Position), is(true));
        assertThat(game.getUnitAt(settler1Position).getTypeString(), is(GameConstants.LEGION));

        world.setUnitAt(settler2Position, blueSettler2);

        endOfRounds(1);

        //Attack 2:
        assertThat(game.moveUnit(settler1Position, settler2Position), is(true));

        world.setUnitAt(settler3Position, blueSettler3);

        endOfRounds(1);

        //Attack 3:
        assertThat(game.moveUnit(settler2Position, settler3Position), is(true));

        //Red wins:
        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void redSettlerAttacksBlueLegionAndLoses() {
        World world = game.getWorld();

        Unit redSettler = new UnitImpl(Player.RED, GameConstants.SETTLER, ((GameImpl) game).getUnitConstantsStrategy());
        Unit blueLegion = new UnitImpl(Player.BLUE, GameConstants.LEGION, ((GameImpl) game).getUnitConstantsStrategy());

        Position settlerPostion = new Position(5, 0);
        Position legionPosition = new Position(5, 1);

        world.setUnitAt(settlerPostion, redSettler);
        world.setUnitAt(legionPosition, blueLegion);

        ((SetDieStrategy) dieStrategy).setDieValue(5);

        assertThat(game.moveUnit(settlerPostion, legionPosition), is(false));
    }

    @Test
    public void hillHasTileTypeStrengthOf2() {
        World world = game.getWorld();
        Tile hill = new TileImpl(GameConstants.HILLS);
        ((WorldImpl) world).setTileAt(new Position(5, 5), hill);
        EpsilonCivBattleOutcomeStrategy battleOutcomeStrategy = new EpsilonCivBattleOutcomeStrategy(new SetDieStrategy());

        assertThat(battleOutcomeStrategy.tileTypeStrength(new Position(5, 5), world), is(2));


    }

    public void endOfRounds(int rounds) {
        for (int i = 0; i < rounds; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
    }

}
