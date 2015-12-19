package standard;

import hotciv.framework.*;
import hotciv.standard.*;
import hotciv.standard.factories.ZetaCivFactory;
import hotciv.standard.strategies.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;


/**
 * Created by ChristianNordstrøm on 24-11-2015.
 */
public class TestZetaCiv {

    Game game;
    private DieStrategy dieStrategy = new SetDieStrategy();
    private WinnerStrategy winnerStrategy;
    private BattleOutcomeStrategy battleOutcomeStrategy;

    @Before
    public void setup() {
        battleOutcomeStrategy = new EpsilonCivBattleOutcomeStrategy(dieStrategy);
        winnerStrategy = new ZetaCivWinnerStrategy(new BetaCivWinnerStrategy(),
                new EpsilonCivWinnerStrategy());

        game = new GameImpl(new ZetaCivFactory());
    }

    @Test
    public void redLegionConquersAllCitiesBefore20Rounds() {
        Unit redLegion = new UnitImpl(Player.RED, GameConstants.LEGION, ((GameImpl) game).getUnitConstantsStrategy());

        Position redLegionPosition = new Position(4, 0);
        game.getWorld().setUnitAt(redLegionPosition, redLegion);

        assertThat(game.moveUnit(redLegionPosition, new Position(4, 1)), is(true));

        assertThat(game.getWinner(), is(Player.RED));


    }

    @Test
    public void after20RoundsRedConquersACityAndWinnerIsNull() {
        endOfRounds(20);

        Unit redLegion = new UnitImpl(Player.RED, GameConstants.LEGION, ((GameImpl) game).getUnitConstantsStrategy());
        Position redLegionPosition = new Position(4, 0);
        game.getWorld().setUnitAt(redLegionPosition, redLegion);

        assertThat(game.moveUnit(redLegionPosition, new Position(4, 1)), is(true));

        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void after20RoundsRedWinsAfterThreeSuccessfulAttacks() {
        endOfRounds(20);

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
    public void zetaCivWinnerStrategyMakeNewMapWorks() {
        Map<Player, ArrayList<Integer>> oldMap = new HashMap<Player, ArrayList<Integer>>();

        oldMap.put(Player.RED, new ArrayList<Integer>());
        oldMap.get(Player.RED).add(18);

        ZetaCivWinnerStrategy zetaCivWinnerStrategy = new ZetaCivWinnerStrategy(new BetaCivWinnerStrategy(), new EpsilonCivWinnerStrategy());

        Map<Player, ArrayList<Integer>> newMap = zetaCivWinnerStrategy.makeNewMap(oldMap);

        assertThat(oldMap.get(Player.RED).size(), is(1));
        assertThat(newMap.get(Player.RED).size(), is(0));
    }

    @Test
    public void ifRedWins3AttacksBeforeRound20WinnerInRound22IsNull() {
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

        endOfRounds(18);

        //Red wins:
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void ifRedWins3AttacksAfterRound20WinnerRedIsWinner() {
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

        endOfRounds(20);

        //Red wins:
        assertThat(game.getWinner(), is(nullValue()));
    }


    public void endOfRounds(int rounds) {
        for (int i = 0; i < rounds; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
    }
}
