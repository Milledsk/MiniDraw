package hotciv.framework;

/**
 * Created by ChristianNordstrøm on 10-11-2015.
 */
public interface World {
    Unit removeUnitAt(Position from);

    void setUnitAt(Position to, Unit unit);

    Unit getUnitAt(Position p);

    City getCityAt(Position p);

    void addCityProduction(int i);

    void unitsCanBeMoved();

    Tile getTileAt(Position p);

}
