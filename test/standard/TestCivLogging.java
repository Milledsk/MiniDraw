package standard;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.standard.GameImpl;
import hotciv.standard.GameTranscriptDecorator;
import hotciv.standard.factories.AlphaCivFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;


/**
 * Created by ChristianNordstrøm on 14-12-2015.
 */
public class TestCivLogging {

    private Game game;
    private Game gameImpl;
    private Game gameTranscriptDecorator;


    @Before
    public void setup(){
        gameImpl = new GameImpl(new AlphaCivFactory());
        gameTranscriptDecorator = new GameTranscriptDecorator(gameImpl);
        game = gameTranscriptDecorator;

    }

    @Test
    public void logMoveUnitFrom (){
        Position from = new Position(2,0);
        Position to = new Position(2,1);
        game.moveUnit(from, to);
        Unit u = game.getUnitAt(from);
        assertThat(u, is(nullValue()));
        u = game.getUnitAt(to);
        assertThat(u, is(notNullValue()));
    }


    @Test
    public void logChangeProduction(){
        game.changeProductionInCityAt(new Position(1,1), GameConstants.ARCHER);
    }

    @Test
    public void logEndOfTurn(){
        game.endOfTurn();
    }

    @Test
    public void logPerformUnitAction(){
        game.performUnitActionAt(new Position(2,0));
    }

    @Test
    public void logPerformUnitActionAndDoNotLogEndOfTurn(){
        game.performUnitActionAt(new Position(2,0));
        game = gameImpl;
        game.endOfTurn();
    }
    @Test
    public void switchLoggingOnOffAndOn(){
        game.performUnitActionAt(new Position(2,0));
        game = gameImpl;
        game.endOfTurn();
        game = gameTranscriptDecorator;
        game.changeProductionInCityAt(new Position(1,1), GameConstants.SETTLER);
    }



}
