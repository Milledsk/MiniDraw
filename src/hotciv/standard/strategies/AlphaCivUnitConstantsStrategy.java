package hotciv.standard.strategies;

import hotciv.framework.GameConstants;
import hotciv.framework.UnitConstantsStrategy;

/**
 * Created by ChristianNordstrøm on 03-12-2015.
 */
public class AlphaCivUnitConstantsStrategy implements UnitConstantsStrategy {

    @Override
    public int getAttackingStrength(String unitType) {
        if (unitType.equals(GameConstants.ARCHER)){
            return 2;
        } else if (unitType.equals(GameConstants.LEGION)){
            return 4;
        } else {
            return 0;
        }
    }

    @Override
    public int getDefensiveStrength(String unitType) {
        if (unitType.equals(GameConstants.ARCHER)){
            return 3;
        } else if (unitType.equals(GameConstants.LEGION)){
            return 2;
        } else {
            return 3;
        }
    }

    @Override
    public int getCost(String unitType) {
        if (unitType.equals(GameConstants.ARCHER)){
            return 10;
        } else if (unitType.equals(GameConstants.LEGION)){
            return 15;
        } else {
            return 30;
        }
    }
}

