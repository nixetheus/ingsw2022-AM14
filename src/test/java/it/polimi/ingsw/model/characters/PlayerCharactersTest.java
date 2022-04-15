package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.Effects;
import it.polimi.ingsw.helpers.Places;
import it.polimi.ingsw.helpers.Towers;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.board.StudentsBag;
import it.polimi.ingsw.model.player.Player;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Vector;
import org.junit.Assert;
import org.junit.Test;

public class PlayerCharactersTest {

  /**
   * This method tests the Character Card that increases the possible moves of currentPlayer by 2
   */
  @Test
  public void increaseMovementTwoEffectTest() throws FileNotFoundException {

    StudentsBag studentsBagTest = new StudentsBag();
    CharacterStruct params = new CharacterStruct();
    params.currentPlayer = new Player(0, "testPlayer", studentsBagTest, 0);
    params.currentPlayer.playAssistant((int) (Math.random() * 10));

    CharacterCard increaseMovementTwo = new PlayerCharacters(Effects.INCREASE_MOVEMENT_TWO,
        Effects.INCREASE_MOVEMENT_TWO.getCost(), new int[5]);

    increaseMovementTwo.applyEffect(params);
    Assert.assertEquals(2, params.currentPlayer.getAssistant().getAdditionalMoves());
    increaseMovementTwo.removeEffect(params);
    Assert.assertEquals(0, params.currentPlayer.getAssistant().getAdditionalMoves());
  }

  /**
   * This method tests the Character Card that replaces students from the player entrance with some
   * from this card
   */
  @Test
  public void replaceStudentEntranceEffectTest() throws FileNotFoundException {

    StudentsBag studentsBagTest = new StudentsBag();
    CharacterStruct params = new CharacterStruct();
    params.currentPlayer = new Player(0, "testPlayer", studentsBagTest, 3);

    CharacterCard replaceStudentEntrance = new PlayerCharacters(Effects.REPLACE_STUDENT_ENTRANCE,
        Effects.REPLACE_STUDENT_ENTRANCE.getCost(), new int[5]);

    params.studentsFrom = studentsBagTest.pickRandomStudents(3);
    params.studentsTo = params.currentPlayer.getPlayerBoard().getEntrance().getStudents();

    replaceStudentEntrance.applyEffect(params);
    Assert.assertEquals(
        Arrays.toString(params.studentsFrom),
        Arrays.toString(params.currentPlayer.getPlayerBoard().getEntrance().getStudents()));
  }

  /**
   * This method tests the Character Card that exchanges up to two students between the player's
   * entrance and dining room
   */
  @Test
  public void exchangeTwoEntranceDiningEffectTest() throws FileNotFoundException {

    StudentsBag studentsBagTest = new StudentsBag();
    CharacterStruct params = new CharacterStruct();
    params.currentPlayer = new Player(0, "testPlayer", studentsBagTest, 2);

    CharacterCard exchangeTwoEntranceDining = new PlayerCharacters(Effects.EXCHANGE_TWO_ENTRANCE_DINING,
        Effects.EXCHANGE_TWO_ENTRANCE_DINING.getCost(), new int[5]);

    int[] diningRoomTest = studentsBagTest.pickRandomStudents(2);
    for (Color color : Color.values()) {
      for (int student = 0; student < diningRoomTest[color.ordinal()]; student++) {
        params.currentPlayer.moveToPlayerBoard(Places.DINING_ROOM, color.ordinal());
      }
    }

    params.studentsFrom = params.currentPlayer.getPlayerBoard().getEntrance().getStudents();
    params.studentsTo = params.currentPlayer.getPlayerBoard().getDiningRoom().getStudents();

    exchangeTwoEntranceDining.applyEffect(params);
    Assert.assertEquals(
        Arrays.toString(params.studentsTo),
        Arrays.toString(params.currentPlayer.getPlayerBoard().getEntrance().getStudents()));
    Assert.assertEquals(
        Arrays.toString(params.studentsFrom),
        Arrays.toString(params.currentPlayer.getPlayerBoard().getDiningRoom().getStudents()));
  }

  /**
   * This method tests the Character Card that places one student from the card to the player's
   * Dining room
   */
  @Test
  public void putOneDiningRoomEffectTest() throws FileNotFoundException {

    StudentsBag studentsBagTest = new StudentsBag();
    CharacterStruct params = new CharacterStruct();
    params.currentPlayer = new Player(0, "testPlayer", studentsBagTest, 0);

    CharacterCard putOneDiningRoom = new PlayerCharacters(Effects.PUT_ONE_DINING_ROOM,
        Effects.PUT_ONE_DINING_ROOM.getCost(), new int[5]);

    int[] diningRoomTest = studentsBagTest.pickRandomStudents(14);
    for (Color color : Color.values()) {
      for (int student = 0; student < diningRoomTest[color.ordinal()]; student++) {
        params.currentPlayer.moveToPlayerBoard(Places.DINING_ROOM, color.ordinal());
      }
    }

    params.color = Color.BLUE;

    putOneDiningRoom.applyEffect(params);
    Assert.assertEquals(diningRoomTest[Color.BLUE.ordinal()] + 1,
        params.currentPlayer.getPlayerBoard().getDiningRoom().getStudents()[Color.BLUE.ordinal()]);
  }

  /**
   * This method tests the Character Card that removes up to three students of a color from each
   * player's dining room
   */
  @Test
  public void returnThreeDiningRoomBagTest() throws FileNotFoundException {

    StudentsBag studentsBagTest = new StudentsBag();
    CharacterStruct params = new CharacterStruct();
    Player playerTest1 = new Player(0, "testPlayer1", studentsBagTest, 0);
    Player playerTest2 = new Player(0, "testPlayer2", studentsBagTest, 0);

    CharacterCard returnThreeDiningRoom = new PlayerCharacters(Effects.RETURN_THREE_DINING_ROOM_BAG,
        Effects.RETURN_THREE_DINING_ROOM_BAG.getCost(), new int[5]);

    params.color = Color.GREEN;

    int[] diningRoomTest1 = studentsBagTest.pickRandomStudents(12);
    for (Color color : Color.values()) {
      for (int student = 0; student < diningRoomTest1[color.ordinal()]; student++) {
        playerTest1.moveToPlayerBoard(Places.DINING_ROOM, color.ordinal());
      }
    }

    int[] diningRoomTest2 = studentsBagTest.pickRandomStudents(9);
    for (Color color : Color.values()) {
      for (int student = 0; student < diningRoomTest2[color.ordinal()]; student++) {
        playerTest2.moveToPlayerBoard(Places.DINING_ROOM, color.ordinal());
      }
    }

    Team team1 = new Team(0, Towers.WHITE); team1.addPlayer(playerTest1);
    Team team2 = new Team(1, Towers.BLACK); team2.addPlayer(playerTest2);
    Vector<Team> teams = new Vector<Team>(); teams.add(team1); teams.add(team2);
    int studentBagTestColorN = studentsBagTest.getStudents()[params.color.ordinal()];

    params.teams = teams;
    params.studentsBag = studentsBagTest;

    returnThreeDiningRoom.applyEffect(params);
    Assert.assertEquals(Math.max(diningRoomTest1[params.color.ordinal()] - 3, 0),
        playerTest1.getPlayerBoard().getDiningRoom().getStudents()[params.color.ordinal()]);
    Assert.assertEquals(Math.max(diningRoomTest2[params.color.ordinal()] - 3, 0),
        playerTest2.getPlayerBoard().getDiningRoom().getStudents()[params.color.ordinal()]);

    int leftovers1 = Math.min(diningRoomTest1[params.color.ordinal()], 3);
    int leftovers2 = Math.min(diningRoomTest2[params.color.ordinal()], 3);
    Assert.assertEquals(studentBagTestColorN + leftovers1 + leftovers2,
        studentsBagTest.getStudents()[params.color.ordinal()]);
  }
}
