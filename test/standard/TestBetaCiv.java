package standard;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.standard.factories.BetaCivFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Milledsk on 12-11-2015.
 */
public class TestBetaCiv {

    Game game;

    @Before
    public void setup() {
        game = new GameImpl(new BetaCivFactory());
    }

    //Age Tests

    @Test
    public void worldAgeIs3800BCAfterTwoRounds() {
        roundsPassed(2);
        assertThat(game.getAge(), is(-3800));
    }

    @Test
    public void worldAgeIs1BCAfter40Rounds() {
        roundsPassed(40);
        assertThat(game.getAge(), is(-1));
    }

    @Test
    public void worldAgeIs1ACAfter41Rounds() {
        roundsPassed(41);
        assertThat(game.getAge(), is(1));
    }

    @Test
    public void worldAgeIs50ACAfter42Rounds() {
        roundsPassed(42);
        assertThat(game.getAge(), is(50));
    }

    @Test
    public void worldAgeIs100ACAfter43Rounds() {
        roundsPassed(43);
        assertThat(game.getAge(), is(100));
    }

    @Test
    public void worldAgeIs1775ACAfter77Rounds() {
        roundsPassed(77);
        assertThat(game.getAge(), is(1775));
    }

    @Test
    public void worldAgeIs1905ACAfter83Rounds() {
        roundsPassed(83);
        assertThat(game.getAge(), is(1905));
    }

    @Test
    public void worldAgeIs1971ACAfter98Rounds() {
        roundsPassed(98);
        assertThat(game.getAge(), is(1971));
    }

    //Winner Tests
    @Test
    public void RedConquorsBlueCityAndWins() {
        assertThat(game.moveUnit(new Position(4, 3), new Position(4, 2)), is(true));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.moveUnit(new Position(4, 2), new Position(4, 1)), is(true));
        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void BlueConquorsRedCityAndWins() {
        game.endOfTurn();
        assertThat(game.moveUnit(new Position(3, 2), new Position(2, 1)), is(true));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.moveUnit(new Position(2, 1), new Position(1, 1)), is(true));
        assertThat(game.getWinner(), is(Player.BLUE));
    }

    //Help Methods

    private void roundsPassed(int rounds) {
        for (int i = 0; i < rounds; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
    }
}
