package hotciv.framework;

/**
 * Created by ChristianNordstrøm on 03-12-2015.
 */
public interface UnitConstantsStrategy {
    int getAttackingStrength(String unitType);
    int getDefensiveStrength(String unitType);
    int getCost(String unitType);
}
