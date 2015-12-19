package hotciv.standard.factories;

import hotciv.framework.*;
import hotciv.standard.strategies.*;

/**
 * Created by Milledsk on 24-11-2015.
 */
public class EpsilonCivFactory implements AbstractFactory {
    private DieStrategy dieStrategy;

    public EpsilonCivFactory(DieStrategy dieStrategy){
        this.dieStrategy = dieStrategy;
    }

    @Override
    public WorldAgeStrategy createWorldAgeStrategy() {
        return new AlphaCivAgeStrategy();
    }

    @Override
    public WinnerStrategy createWinnerStrategy() {
        return new EpsilonCivWinnerStrategy();
    }

    @Override
    public ActionStrategy createActionStrategy() {
        return new AlphaCivActionStrategy();
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return new AlphaCivWorldLayoutStrategy();
    }

    @Override
    public BattleOutcomeStrategy createBattleOutComeStrategy() {
        return new EpsilonCivBattleOutcomeStrategy(dieStrategy);
    }

    @Override
    public UnitConstantsStrategy createUnitConstantsStrategy() {return new AlphaCivUnitConstantsStrategy();}
}
