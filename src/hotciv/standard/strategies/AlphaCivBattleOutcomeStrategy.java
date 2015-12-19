package hotciv.standard.strategies;

import hotciv.framework.BattleOutcomeStrategy;
import hotciv.framework.Game;
import hotciv.framework.Position;

/**
 * Created by ChristianNordstrøm on 19-11-2015.
 */
public class AlphaCivBattleOutcomeStrategy implements BattleOutcomeStrategy {

    @Override
    public boolean battleOutcome(Position attackingPosition, Position battlePosition, Game game) {
        return true;
    }
}
