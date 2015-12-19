package hotciv.standard.strategies;

import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.standard.UnitImpl;
import hotciv.standard.WorldImpl;

/**
 * Created by ChristianNordstrøm on 17-11-2015.
 */
public class GammaCivActionStrategy implements ActionStrategy {
    @Override
    public void performUnitAction(Game g, Position p) {

        Unit unit = g.getUnitAt(p);
        World world = g.getWorld();

        if(unit.getTypeString().equals(GameConstants.SETTLER)){
            ((WorldImpl)world).setCityAt(p, new CityImpl(unit.getOwner()));
            world.removeUnitAt(p);
        } else if(unit.getTypeString().equals(GameConstants.ARCHER)){

            if (((UnitImpl) unit).getIsFortified()) {
                ((UnitImpl) unit).setIsFortified(false);
                ((UnitImpl) unit).setMovalbe(true);
                ((UnitImpl)unit).multipliedDefensiveStrength(0.5);

            } else {
                ((UnitImpl) unit).setIsFortified(true);
                ((UnitImpl) unit).setMovalbe(false);
                ((UnitImpl) unit).multipliedDefensiveStrength(2);
            }
        }
    }
}
