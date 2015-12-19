package hotciv.standard.factories;

import hotciv.framework.*;
import hotciv.standard.strategies.*;

/**
 * Created by ChristianNordstrøm on 08-12-2015.
 */
public class SemiCivFactory implements AbstractFactory {

    private final DieStrategy dieStrategy;

    public SemiCivFactory(DieStrategy dieStrategy){
        this.dieStrategy = dieStrategy;
    }

    @Override
    public WorldAgeStrategy createWorldAgeStrategy() {
        return new BetaCivAgeStrategy();
    }

    @Override
    public WinnerStrategy createWinnerStrategy() {
        return new EpsilonCivWinnerStrategy();
    }

    @Override
    public ActionStrategy createActionStrategy() {
        return new GammaCivActionStrategy();
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return new DeltaCivWorldLayoutStrategy();
    }

    @Override
    public BattleOutcomeStrategy createBattleOutComeStrategy() {
        return new EpsilonCivBattleOutcomeStrategy(dieStrategy);
    }

    @Override
    public UnitConstantsStrategy createUnitConstantsStrategy() {
        return new AlphaCivUnitConstantsStrategy();
    }
}
