package hotciv.framework;

/**
 * Created by Milledsk on 24-11-2015.
 */
public interface AbstractFactory {

    WorldAgeStrategy createWorldAgeStrategy();
    WinnerStrategy createWinnerStrategy();
    ActionStrategy createActionStrategy();
    WorldLayoutStrategy createWorldLayoutStrategy();
    BattleOutcomeStrategy createBattleOutComeStrategy();
    UnitConstantsStrategy createUnitConstantsStrategy();


    //new GameImpl(new AlphaCivAgeStrategy(), new AlphaCivWinnerStrategy(),
    //new AlphaCivActionStrategy(), new AlphaCivWorldLayoutStrategy(), new AlphaCivBattleOutcomeStrategy());
}
