package hotciv.framework;

/**
 * Created by ChristianNordstrøm on 19-11-2015.
 */
public interface BattleOutcomeStrategy {
    public boolean battleOutcome(Position attackingPosition, Position battlePosition, Game game);
}
