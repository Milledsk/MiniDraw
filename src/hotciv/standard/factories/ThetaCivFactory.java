package hotciv.standard.factories;

import hotciv.framework.*;
import hotciv.standard.*;
import hotciv.standard.strategies.*;

/**
 * Created by ChristianNordstrøm on 07-12-2015.
 */
public class ThetaCivFactory implements AbstractFactory {
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
        return new ThetaCivActionDecorator();
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return new AlphaCivWorldLayoutStrategy();
    }

    @Override
    public BattleOutcomeStrategy createBattleOutComeStrategy() {
        return new AlphaCivBattleOutcomeStrategy();
    }

    @Override
    public UnitConstantsStrategy createUnitConstantsStrategy() {return new ThetaCivUnitConstantStrategyDecorator();}
}
