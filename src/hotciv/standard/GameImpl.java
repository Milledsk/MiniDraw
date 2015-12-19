package hotciv.standard;

import hotciv.framework.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Skeleton implementation of HotCiv.
 * <p/>
 * This source code is from the book
 * "Flexible, Reliable Software:
 * Using Patterns and Agile Development"
 * published 2010 by CRC Press.
 * Author:
 * Henrik B Christensen
 * Department of Computer Science
 * Aarhus University
 * <p/>
 * Please visit http://www.baerbak.com/ for further information.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class GameImpl implements Game {
    private WinnerStrategy winnerStrategy;
    private Player PlayerInTurn = Player.RED;
    private int gameStart = -4000;
    private WorldAgeStrategy ageStrategy;
    private int rounds = 0;
    private ActionStrategy actionStrategy;
    private WorldLayoutStrategy worldLayoutStrategy;
    private BattleOutcomeStrategy battleOutcomeStrategy;
    private Map<Player, ArrayList<Integer>> battlesWon = new HashMap<Player, ArrayList<Integer>>();
    private UnitConstantsStrategy unitConstantsStrategy;
    private GameImpl decoratee;
    private GameObserver observer = new GameObserverImpl();
    private World world = new WorldImpl(observer);

    public GameImpl(AbstractFactory abstractFactory){
        ageStrategy = abstractFactory.createWorldAgeStrategy();
        winnerStrategy = abstractFactory.createWinnerStrategy();
        actionStrategy = abstractFactory.createActionStrategy();
        worldLayoutStrategy = abstractFactory.createWorldLayoutStrategy();
        battleOutcomeStrategy = abstractFactory.createBattleOutComeStrategy();
        unitConstantsStrategy = abstractFactory.createUnitConstantsStrategy();
        worldLayoutStrategy.createWorld(this);
    }

    public Tile getTileAt(Position p) {
        return world.getTileAt(p);
    }

    public Unit getUnitAt(Position p) {
        return world.getUnitAt(p);
    }

    public City getCityAt(Position p) {
        return world.getCityAt(p);
    }

    public Player getPlayerInTurn() {

        return PlayerInTurn;
    }

    public UnitConstantsStrategy getUnitConstantsStrategy(){
        return  unitConstantsStrategy;
    }

    public Player getWinner() {
        return winnerStrategy.getWinner(this, battlesWon);
    }

    public int getAge() {
        return ageStrategy.worldAgingProgress(gameStart, rounds);
    }

    public boolean moveUnit(Position from, Position to) {

        if(Math.abs(from.getColumn() - to.getColumn()) > 1 || Math.abs(from.getRow() - to.getRow()) > 1 ) {
            return false;
        }

        if(getTileAt(to).getTypeString().equals(GameConstants.MOUNTAINS) ||
                getTileAt(to).getTypeString().equals(GameConstants.OCEANS)){
            return false;
        }

        if(!getPlayerInTurn().equals(world.getUnitAt(from).getOwner())){
            return false;
        }

        if(!getUnitAt(from).getMovable()){
            return false;
        }

        if(!(world.getUnitAt(to) == null)){
            return attack(from, to);
        }

        else{

            Unit unit = world.removeUnitAt(from);
            world.setUnitAt(to, unit);
            ((UnitImpl) unit).setMovalbe(false);
            if(!(world.getCityAt(to) == null)){
                ((CityImpl)getCityAt(to)).setOwner(unit.getOwner());
            }
            return true;
        }
    }

    private boolean attack(Position from, Position to) {
        if(battleOutcomeStrategy.battleOutcome(from, to, this)) { //if attacking unit has won

            Unit unit = world.removeUnitAt(from);
            world.setUnitAt(to, unit);
            Unit winnerUnit = world.getUnitAt(to);

           if(battlesWon.containsKey(winnerUnit.getOwner())){
                battlesWon.get(winnerUnit.getOwner()).add(rounds);
           } else {
               battlesWon.put(winnerUnit.getOwner(),new ArrayList<Integer>());
               battlesWon.get(winnerUnit.getOwner()).add(rounds);
           }

            ((UnitImpl) unit).setMovalbe(false);
            return true;

        } else {
            world.removeUnitAt(to);
            return false;
        }
    }

    public Map<Player, ArrayList<Integer>> getBattlesWon(){
        return battlesWon;
    }

    public void endOfTurn() {
        if (PlayerInTurn == Player.RED) {
            PlayerInTurn = Player.BLUE;
            observer.turnEnds(PlayerInTurn, getAge());
        } else {
            PlayerInTurn = Player.RED;
            rounds++;
            world.addCityProduction(6);
            observer.turnEnds(PlayerInTurn, getAge());

        }
        world.unitsCanBeMoved();
        produceUnit();

    }

    public void changeWorkForceFocusInCityAt(Position p, String balance) {
    }

    public void changeProductionInCityAt(Position p, String unitType) {
        City city = world.getCityAt(p);
        ((CityImpl)city).setProduction(unitType);
    }

    public void performUnitActionAt(Position p) {
        System.out.println("unitAction called");

        actionStrategy.performUnitAction(this, p);
    }

    @Override
    public void addObserver(GameObserver observer) {
        this.observer = observer;
        ((WorldImpl)world).setObserver(observer);
    }

    @Override
    public void setTileFocus(Position position) {
        System.out.println("setTileFocus");
        observer.tileFocusChangedAt(position);
    }

    public World getWorld(){
        return world;
    }

    public int getRounds(){
        return rounds;
    }

    public void produceUnit() {
        Map<Position, City> cityLocations = ((WorldImpl)world).getAllCities();
        for(Position p: cityLocations.keySet()) {
            City city = cityLocations.get(p);
            Unit unit = new UnitImpl(PlayerInTurn, city.getProduction(), unitConstantsStrategy);

            if(city.getOwner().equals(PlayerInTurn) && city.getProductionTreasury() >= unit.getCost()) {
                    if(world.getUnitAt(p) == null) {
                        world.setUnitAt(p, unit);
                    } else {
                        Iterator<Position> neighborLocations = Utility.get8NeighborhoodIterator(p);
                        while(neighborLocations.hasNext()) {
                            Position position = neighborLocations.next();
                            if(world.getUnitAt(position) == null){
                                world.setUnitAt(position, unit);
                                break;
                            }
                        }
                    }
                ((CityImpl) city).setProductionTreasury(city.getProductionTreasury() - unit.getCost());
            }
        }
    }
}