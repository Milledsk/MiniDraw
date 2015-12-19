package hotciv.standard;

import hotciv.framework.ActionStrategy;
import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.standard.strategies.GammaCivActionStrategy;

/**
 * Created by ChristianNordstrøm on 08-12-2015.
 */
public class ThetaCivActionDecorator implements ActionStrategy {

    private ActionStrategy actionStrategy = new GammaCivActionStrategy();

    @Override
    public void performUnitAction(Game g, Position p) {
        Unit unit = g.getUnitAt(p);

        if (g.getUnitAt(p).getTypeString().equals(ThetaCivGameConstants.CHARIOT)){
            if (((UnitImpl) unit).getIsFortified()) {
                ((UnitImpl) unit).setIsFortified(false);
                ((UnitImpl) unit).setMovalbe(true);
                ((UnitImpl)unit).multipliedDefensiveStrength(0.5);

            } else {
                ((UnitImpl) unit).setIsFortified(true);
                ((UnitImpl) unit).setMovalbe(false);
                ((UnitImpl) unit).multipliedDefensiveStrength(2);
            }
        } else {
            actionStrategy.performUnitAction(g,p);
        }
    }
}
