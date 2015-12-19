package hotciv.standard.strategies;

import hotciv.framework.UnitConstantsStrategy;
import hotciv.standard.ThetaCivGameConstants;

/**
 * Created by ChristianNordstrøm on 07-12-2015.
 */
public class ThetaCivUnitConstantStrategyDecorator implements UnitConstantsStrategy {

    private UnitConstantsStrategy unitConstantsStrategy = new AlphaCivUnitConstantsStrategy();

    @Override
    public int getAttackingStrength(String unitType) {
        if (unitType.equals(ThetaCivGameConstants.CHARIOT)) {
            return 3;
        } else {
            return unitConstantsStrategy.getAttackingStrength(unitType);
        }
    }

    @Override
    public int getDefensiveStrength(String unitType) {
        if (unitType.equals(ThetaCivGameConstants.CHARIOT)){
            return 1;
        } else {
            return unitConstantsStrategy.getDefensiveStrength(unitType);
        }
    }

    @Override
    public int getCost(String unitType) {
        if (unitType.equals(ThetaCivGameConstants.CHARIOT)){
            return 20;
        } else {
            return unitConstantsStrategy.getCost(unitType);
        }
    }

}
