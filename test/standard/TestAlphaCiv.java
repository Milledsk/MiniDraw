package standard;

import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.standard.UnitImpl;
import hotciv.standard.WorldImpl;
import hotciv.standard.factories.AlphaCivFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/** Skeleton class for AlphaCiv test cases

    Updated Oct 2015 for using Hamcrest matchers

   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Department of Computer Science
     Aarhus University
   
   Please visit http://www.baerbak.com/ for further information.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/
public class TestAlphaCiv {
  Game game;



  @Before
  public void setup(){
    game = new GameImpl(new AlphaCivFactory());
  }

  @Test
  public void playerInTurnIsRedAtGameStart(){
    assertThat(game.getPlayerInTurn(), is(Player.RED));
  }

  @Test
  public void afterRedHasBeenInTurnBlueIsInTurn(){
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
  }


  @Test
  public void afterBlueHasBeenInTurnRedIsInTurn(){
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(), is(Player.RED));
  }


  @Test
  public void worldAgeIs4000AtStart(){
    assertThat(game.getAge(),is(-4000));
  }

  @Test
  public void worldAgeIs4000AfterOneTurn() {
    game.endOfTurn();
    assertThat(game.getAge(), is(-4000));
  }

  @Test
  public void worldAgeIs3900AfterOneRound() {
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getAge(), is(-3900));
  }

  @Test
  public void blueCannotMoveRedUnit(){
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    assertThat(game.moveUnit(new Position(4,3), new Position(4,4)), is(false));
  }

  @Test
  public void oceanAtTile1x0(){
    assertThat(game.getTileAt(new Position(1,0)).getTypeString(), is(equalTo(GameConstants.OCEANS)));
  }

  @Test
  public void hillAtTile0x1(){
    assertThat(game.getTileAt(new Position(0,1)).getTypeString(), is(equalTo(GameConstants.HILLS)));
  }

  @Test
  public void mountainAtTile2x2(){
    assertThat(game.getTileAt(new Position(2,2)).getTypeString(), is(equalTo(GameConstants.MOUNTAINS)));
  }

  @Test
  public void plainesAtTile5x5(){
    assertThat(game.getTileAt(new Position(5,5)).getTypeString(), is(equalTo(GameConstants.PLAINS)));
  }

  @Test
  public void redWinsInYear3000BC(){
    roundsPassed(10);
    assertThat(game.getWinner(), is(Player.RED));
  }

  @Test
  public void nullWinsInYear3900BC(){
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getWinner(), is(nullValue()));
  }

  @Test
  public void shouldHaveRedCityAt1x1() {
    assertThat(game.getCityAt(new Position(1,1)).getOwner(), is(Player.RED));
  }

  @Test
  public void shouldHaveBlueCityAt4x1() {
    assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.BLUE));
  }

  @Test
  public void shouldHaveRedArcherAt2x0(){
    Unit unit = game.getUnitAt(new Position(2,0));
    assertThat(unit.getTypeString(), is(GameConstants.ARCHER));
    assertThat(unit.getOwner(), is(Player.RED));
  }

  @Test
  public void shouldHaveBlueLegionAt3x2(){
    Unit unit = game.getUnitAt(new Position(3,2));
    assertThat(unit.getTypeString(), is(GameConstants.LEGION));
    assertThat(unit.getOwner(), is(Player.BLUE));
  }

  @Test
  public void shouldHaveRedSettlerAt4x3(){
    Unit unit = game.getUnitAt(new Position(4,3));
    assertThat(unit.getTypeString(), is(GameConstants.SETTLER));
    assertThat(unit.getOwner(), is(Player.RED));
  }

  @Test
  public void blueShouldDefeatRedIfBlueMovesTo3x3AfterRed(){
    //(4,3) red settler, (3,2) blue legion.

    Position to = new Position(3,3);
    assertThat(game.moveUnit(new Position(4,3), to), is(true));
    game.endOfTurn();
    assertThat(game.moveUnit(new Position(3,2), to), is(true));
    Unit unit = game.getUnitAt(to);
    assertThat(unit.getTypeString(), is(GameConstants.LEGION));
    assertThat(unit.getOwner(), is(Player.BLUE));
  }


  @Test
  public void shouldMoveRedArcherFrom2x0To2x1(){
    Position from = new Position(2,0);
    Position to = new Position(2,1);
    game.moveUnit(from, to);
    Unit u = game.getUnitAt(from);
    assertThat(u, is(nullValue()));
    u = game.getUnitAt(to);
    assertThat(u, is(notNullValue()));
  }

  @Test
  public void shouldNotMoveRedArcherFrom2x0To1x0(){
    Position from = new Position(2,0);
    Position to = new Position(1,0);
    game.moveUnit(from, to);
    Unit u = game.getUnitAt(from);
    assertThat(u, is(notNullValue()));
    u = game.getUnitAt(to);
    assertThat(u, is(nullValue()));
  }



  @Test
  public void unitCannotMoveFrom2x0to2x3(){
    assertThat(game.moveUnit(new Position(2,0), new Position(2,3)), is(false));
  }


  @Test
  public void blueCityHasOnePopulation(){
    City city = new CityImpl(Player.BLUE);
    assertThat(city.getSize(), is(1));
  }

  @Test
  public void BlueCityShouldHaveAProductionOf6AfterOneRound(){
    City blueCity = game.getCityAt(new Position(4,1));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(blueCity.getProductionTreasury(), is(6));
  }

  @Test
  public void RedCityShouldHaveAProductionOf6AfterOneRound(){
    City redCity = game.getCityAt(new Position(1,1));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(redCity.getProductionTreasury(), is(6));
  }

  @Test
  public void BlueCityShouldHaveAProductionOf0AtGameStart(){
    City blueCity = game.getCityAt(new Position(4,1));
    assertThat(blueCity.getProductionTreasury(), is(0));
  }

  @Test
  public void redShouldProduceANotNullValue(){
    City redCity = game.getCityAt(new Position(1,1));
    assertThat(redCity.getProduction(), is(notNullValue()));
  }

  @Test
  public void anArcherShouldCost10() {
    Unit archer = new UnitImpl(Player.RED, GameConstants.ARCHER, ((GameImpl)game).getUnitConstantsStrategy());
    assertThat(archer.getCost(), is(10));
  }

  @Test
  public void aLegionShouldCost15(){
    Unit legion = new UnitImpl(Player.RED, GameConstants.LEGION, ((GameImpl)game).getUnitConstantsStrategy());
    assertThat(legion.getCost(), is(15));
  }

  @Test
  public void aSettlerShouldCost30(){
    Unit settler = new UnitImpl(Player.RED, GameConstants.SETTLER, ((GameImpl)game).getUnitConstantsStrategy());
    assertThat(settler.getCost(), is(30));
  }

  @Test
  public void redCannotMoveBlueUnits(){
    assertThat(game.moveUnit(new Position(3,2), new Position(2,2)), is(false));
  }

  @Test
  public void aUnitCanOnlyBeMovedOneTimeInARound(){
    assertThat(game.moveUnit(new Position(2,0), new Position(2,1)), is(true));
    assertThat(game.moveUnit(new Position(2,1), new Position(2,0)), is(false));
  }

  @Test
  public void redUnitToBlueCityMakesCityRed(){
    assertThat(game.moveUnit(new Position(4,3), new Position(4,2)), is(true));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.moveUnit(new Position(4,2), new Position(4,1)), is(true));
    assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.RED));
  }

  @Test
  public void redArcherShouldHaveAttackStrengthof2(){
    Unit archer = new UnitImpl(Player.RED, GameConstants.ARCHER, ((GameImpl)game).getUnitConstantsStrategy());
    assertThat(archer.getAttackingStrength(), is(2));
  }

  @Test
  public void redLegionShouldHaveAttackStrengthof4(){
    Unit legion = new UnitImpl(Player.RED, GameConstants.LEGION, ((GameImpl)game).getUnitConstantsStrategy());
    assertThat(legion.getAttackingStrength(), is(4));
  }

  @Test
  public void redSettlerShouldHaveAttackStrengthof0(){
    Unit settler = new UnitImpl(Player.RED, GameConstants.SETTLER, ((GameImpl)game).getUnitConstantsStrategy());
    assertThat(settler.getAttackingStrength(), is(0));
  }

  @Test
  public void redArcherShouldHaveDefensiveStrengthof3(){
    Unit archer = new UnitImpl(Player.RED, GameConstants.ARCHER, ((GameImpl)game).getUnitConstantsStrategy());
    assertThat(archer.getDefensiveStrength(), is(3));
  }

  @Test
  public void redLegionShouldHaveDefensiveStrengthof2(){
    Unit legion = new UnitImpl(Player.RED, GameConstants.LEGION, ((GameImpl)game).getUnitConstantsStrategy());
    assertThat(legion.getDefensiveStrength(), is(2));
  }

  @Test
  public void redSettlerShouldHaveDefensiveStrengthof3(){
    Unit settler = new UnitImpl(Player.RED, GameConstants.SETTLER, ((GameImpl)game).getUnitConstantsStrategy());
    assertThat(settler.getDefensiveStrength(), is(3));
  }

  @Test
  public void produceUnitInRedCity(){
    City redCity = new CityImpl(Player.RED);
    ((WorldImpl)game.getWorld()).setCityAt(new Position(5,5), redCity);
    roundsPassed(2);
    assertThat(game.getUnitAt(new Position(5,5)), is(notNullValue()));
  }

  @Test
  public void produceUnitAndPlaceItAtTheTileAboveTheCity(){

    City redCity = new CityImpl(Player.RED);
    ((WorldImpl)game.getWorld()).setCityAt(new Position(5,5), redCity);
    Unit unit = new UnitImpl(Player.RED, GameConstants.ARCHER, ((GameImpl)game).getUnitConstantsStrategy());
    game.getWorld().setUnitAt(new Position(5,5), unit);
    roundsPassed(2);
    assertThat(game.getUnitAt(new Position(4,4)), is(notNullValue()));
  }

  //Help methods
  private void roundsPassed(int rounds) {
    for(int i = 0; i < rounds; i++) {
      game.endOfTurn();
      game.endOfTurn();
    }
  }

  /*
  dSoftArk - IT - IT GROUP 1
  Mille 20115922
  Christian 201304213
  Mark 201309289
  */

}
