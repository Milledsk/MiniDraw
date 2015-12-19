package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChristianNordstrøm on 10-11-2015.
 */
public class WorldImpl implements World {

    Map<Position, Unit> unitLocations = new HashMap<Position, Unit>();
    Map<Position, City> cityLocations = new HashMap<Position, City>();
    Map<Position, Tile> tileLocations = new HashMap<Position, Tile>();
    GameObserver observer;

    public WorldImpl(GameObserver observer){
        this.observer = observer;
        for (int i = 0; i< GameConstants.WORLDSIZE; i++){
            for (int j = 0; j< GameConstants.WORLDSIZE; j++){
                tileLocations.put(new Position(i,j), new TileImpl(GameConstants.PLAINS));
            }
        }
    }

    public void setCityAt(Position to, City city) {
        cityLocations.put(to, city);
        observer.worldChangedAt(to);
    }

    public City getCityAt(Position p) {
        return cityLocations.get(p);
    }

    public void addCityProduction(int production) {
        for (Position p : cityLocations.keySet()) {
            City city = cityLocations.get(p);
            ((CityImpl) city).setProductionTreasury(city.getProductionTreasury() + production);
        }
    }

    public Unit getUnitAt(Position p) {
        return unitLocations.get(p);
    }

    @Override
    public Unit removeUnitAt(Position from) {
        Unit unit = unitLocations.get(from);
        unitLocations.remove(from);
        observer.worldChangedAt(from);
        return unit;
    }

    @Override
    public void setUnitAt(Position to, Unit unit) {
        unitLocations.put(to, unit);
        observer.worldChangedAt(to);
    }

    public void unitsCanBeMoved() {

        for (Position p : unitLocations.keySet()) {
            Unit unit = unitLocations.get(p);

            if (((UnitImpl) unit).getIsFortified()) {
                ((UnitImpl) unit).setMovalbe(false);
            } else {
                ((UnitImpl) unit).setMovalbe(true);
            }
        }
    }

    @Override
    public Tile getTileAt(Position p) {
        return tileLocations.get(p);
    }

    public void setTileAt(Position p, Tile tile){
        observer.worldChangedAt(p);
        tileLocations.put(p, tile);
    }

    public Map<Position, City> getAllCities() {
        return cityLocations;
    }

    public void putAllTiles(Map<Position, Tile> tileLocations) {
        for(Position p : tileLocations.keySet()){
            observer.worldChangedAt(p);
        }
        this.tileLocations = tileLocations;
    }

    public void setObserver(GameObserver observer){
        this.observer = observer;
    }
}
