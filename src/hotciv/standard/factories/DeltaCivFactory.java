package hotciv.standard.factories;

import hotciv.framework.*;
import hotciv.standard.strategies.*;

/**
 * Created by Milledsk on 24-11-2015.
 */
public class DeltaCivFactory implements AbstractFactory {
    @Override
    public WorldAgeStrategy createWorldAgeStrategy() {
        return new AlphaCivAgeStrategy();
    }

    @Override
    public WinnerStrategy createWinnerStrategy() {
        return new AlphaCivWinnerStrategy();
    }

    @Override
    public ActionStrategy createActionStrategy() {
        return new AlphaCivActionStrategy();
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return new DeltaCivWorldLayoutStrategy();
    }

    @Override
    public BattleOutcomeStrategy createBattleOutComeStrategy() {
        return new AlphaCivBattleOutcomeStrategy();
    }

    @Override
    public UnitConstantsStrategy createUnitConstantsStrategy() {return new AlphaCivUnitConstantsStrategy();}
}
