package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

/**
 * Created by markflarup on 09/11/15.
 */
public class CityImpl implements City {

    private Player player;
    private int productionTreasury;
    private String production = GameConstants.ARCHER;

    public CityImpl(Player player) {
        this.player = player;

    }
    @Override
    public Player getOwner() {
        return player;
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public String getProduction() {
        return production;
    }

    public void setProduction(String unitType){
        production = unitType;
    }

    @Override
    public String getWorkforceFocus() {
        return GameConstants.foodFocus;
    }

    public int getProductionTreasury(){
        return productionTreasury;
    }

    public void setProductionTreasury(int productionTreasury){
        this.productionTreasury = productionTreasury;
    }

    public void setOwner(Player player){
        this.player = player;
    }
}
