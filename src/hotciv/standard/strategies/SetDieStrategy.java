package hotciv.standard.strategies;

import hotciv.framework.DieStrategy;

/**
 * Created by Milledsk on 23-11-2015.
 */
public class SetDieStrategy implements DieStrategy {
    private int dieValue = 1;

    @Override
    public int roleDie() {
        return dieValue;
    }

    public void setDieValue(int value){
        dieValue = value;
    }

}

