package hotciv.standard.strategies;

import hotciv.framework.*;

/**
 * Created by ChristianNordstrøm on 19-11-2015.
 */

public class EpsilonCivBattleOutcomeStrategy implements BattleOutcomeStrategy {
    private DieStrategy dieStrategy;


    public EpsilonCivBattleOutcomeStrategy(DieStrategy dieStrategy){
        this.dieStrategy = dieStrategy;
    }

    @Override
    public boolean battleOutcome(Position attackingPosition, Position defencePosition, Game game) {
        World world = game.getWorld();

        Unit attackingUnit = world.getUnitAt(attackingPosition);
        Unit defendingUnit = world.getUnitAt(defencePosition);
        int attackStrength = attackingUnit.getAttackingStrength();
        int defenceStrength = defendingUnit.getDefensiveStrength();
        int attackDieValue = dieStrategy.roleDie();
        int defenceDieValue = dieStrategy.roleDie();

        attackStrength += friendlyUnitValue(attackingPosition, world);
        defenceStrength += friendlyUnitValue(defencePosition, world);

        attackStrength = attackStrength*tileTypeStrength(attackingPosition, world);
        defenceStrength = defenceStrength*tileTypeStrength(defencePosition, world);

        if(attackDieValue*attackStrength > defenceDieValue * defenceStrength){
            return true;
        } else {
            return false;}
    }

    public int friendlyUnitValue(Position unitPosition, World world){
        int value = 0;
        for(int i = -1; i<2; i++ ){
            for (int j = -1; j<2; j++ ){
                Position position = new Position(unitPosition.getColumn()+i,unitPosition.getRow()+j);
                if(world.getUnitAt(position)==null){

                } else if(world.getUnitAt(position).getOwner().equals(world.getUnitAt(unitPosition).getOwner())){
                    value++;
                }
            }
        }
        return value - 1;
    }

    public int tileTypeStrength(Position unitPosition, World world){

        if(world.getTileAt(unitPosition).getTypeString().equals(GameConstants.HILLS) ||
                world.getTileAt(unitPosition).getTypeString().equals(GameConstants.FOREST)){
            return  2;
        } else if (!(world.getCityAt(unitPosition) == null)){
            return  3;
        } else {
            return 1;
        }
    }
}
