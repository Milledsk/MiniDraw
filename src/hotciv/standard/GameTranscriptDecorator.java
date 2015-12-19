package hotciv.standard;

import hotciv.framework.*;

/**
 * Created by ChristianNordstrøm on 14-12-2015.
 */
public class GameTranscriptDecorator implements Game {

    Game game;
    public GameTranscriptDecorator(Game game){
        this.game = game;
    }
    @Override
    public Tile getTileAt(Position p) {
        return game.getTileAt(p);
    }

    @Override
    public Unit getUnitAt(Position p) {
        return game.getUnitAt(p);
    }

    @Override
    public City getCityAt(Position p) {
        return game.getCityAt(p);
    }

    @Override
    public Player getPlayerInTurn() {
        return game.getPlayerInTurn();
    }

    @Override
    public Player getWinner() {
        return game.getWinner();
    }

    @Override
    public int getAge() {
        return game.getAge();
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        System.out.println("Player " + getPlayerInTurn().toString() + " moved unit " + getUnitAt(from).getTypeString() + " from position " + from + " to " + to + "." );
        return game.moveUnit(from, to);
    }

    @Override
    public void endOfTurn() {
        System.out.println(getPlayerInTurn().toString() + " ends turn");
        game.endOfTurn();
    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        game.changeWorkForceFocusInCityAt(p, balance);
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        System.out.println("Player " + getPlayerInTurn().toString() + " changed production in city at " + p + " to " + unitType + ".");
        game.changeProductionInCityAt(p, unitType);
    }

    @Override
    public void performUnitActionAt(Position p) {
        System.out.println("Player " + getPlayerInTurn().toString() + " called performUnitAction at " + getUnitAt(p).getTypeString() + " at position " + p );
        game.performUnitActionAt(p);
    }

    @Override
    public void addObserver(GameObserver observer) {

    }

    @Override
    public void setTileFocus(Position position) {

    }

    @Override
    public World getWorld() {
        return game.getWorld();
    }
}