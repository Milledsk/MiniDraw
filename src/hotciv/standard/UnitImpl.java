package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.Unit;
import hotciv.framework.UnitConstantsStrategy;

/**
 * Created by Milledsk on 09-11-2015.
 */
public class UnitImpl implements Unit {

    private Player player;
    private String typeString;
    private int attackStrength;
    private int cost;
    private boolean movable = true;
    private int defensiveStrength;
    private boolean isFortified = false;
    private UnitConstantsStrategy unitConstantsStrategy;
    private int moveCount;

    public UnitImpl(Player player, String typeString, UnitConstantsStrategy unitConstantsStrategy){
        this.player = player;
        this.typeString = typeString;
        this.unitConstantsStrategy = unitConstantsStrategy;
        attackStrength = unitConstantsStrategy.getAttackingStrength(typeString);
        defensiveStrength = unitConstantsStrategy.getDefensiveStrength(typeString);
        cost = unitConstantsStrategy.getCost(typeString);


    }

    @Override
    public String getTypeString() {
        return typeString;
    }

    @Override
    public Player getOwner() {
        return player;
    }

    @Override
    public int getMoveCount() {
        if(movable){
            return 1;
        } else return 0;
    }

    @Override
    public int getDefensiveStrength() {
        return defensiveStrength;
    }

    public void multipliedDefensiveStrength(double factor){
        defensiveStrength = (int) (defensiveStrength* factor);
    }

    @Override
    public int getAttackingStrength() {
        return attackStrength;
    }

    @Override
    public int getCost() {
        return cost;
    }

    public boolean getMovable() {
        return movable;
    }

    public boolean getIsFortified(){
        return isFortified;
    }

    public void setIsFortified(boolean isFortified){
        this.isFortified = isFortified;
    }

    public void setMovalbe(boolean movable){
        this.movable = movable;
    }

}
