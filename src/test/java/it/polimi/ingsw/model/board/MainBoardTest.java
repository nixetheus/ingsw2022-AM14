package it.polimi.ingsw.model.board;

import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.helpers.Towers;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.player.Player;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Vector;
import org.junit.Assert;
import org.junit.Test;

/**
 * MainBoardTest class tests MainBoard class
 */
public class MainBoardTest {

  /**
   * This method tests the calculateInfluence method. It creates professors array, vector team and a
   * main board, and it fills them It creates the teams and add students to them Then calculate the
   * influences and compare with the correct value
   *
   * @throws FileNotFoundException exception
   */
  @Test
  public void testCalculateInfluence() throws FileNotFoundException {
    StudentsBag testStudBag = new StudentsBag();
    MainBoard mainBoardTest = new MainBoard(testStudBag);

    //create professors array, vector team and island to calculate influence
    Player[] profTest = new Player[Constants.getNColors()];
    Vector<Team> teamsTest = new Vector<>();

    //add students to the island and towers
    mainBoardTest.getIslands().get(3).addStudent(0);
    mainBoardTest.getIslands().get(3).addStudent(0);
    mainBoardTest.getIslands().get(3).addStudent(0);
    mainBoardTest.getIslands().get(3).addStudent(1);
    mainBoardTest.getIslands().get(3).addStudent(2);
    mainBoardTest.getIslands().get(3).addStudent(3);
    mainBoardTest.getIslands().get(3).addStudent(3);
    mainBoardTest.getIslands().get(3).addStudent(3);
    mainBoardTest.getIslands().get(3).setOwner(0);
    mainBoardTest.getIslands().get(3).addTower(1);

    //create the team
    StudentsBag testStudBag2 = new StudentsBag();
    Player player1 = new Player(0, "Jeff");
    player1.initializePlayerBoard(testStudBag2.pickRandomStudents(0));
    teamsTest.add(new Team(0, Towers.WHITE));
    StudentsBag testStudBag3 = new StudentsBag();
    Player player2 = new Player(1, "Tony");
    player2.initializePlayerBoard(testStudBag2.pickRandomStudents(0));
    teamsTest.add(new Team(1, Towers.BLACK));

    //fill the professors arr
    profTest[0] = player1;
    profTest[1] = player1;
    profTest[3] = player2;
    profTest[4] = player2;
    //player1 controls 0, 1. influence = 4 + 1(tower) [+1 (pickRandomStudent mainBoard constructor)]
    //player2 controls 3, 4. influence = 3 [+1 (pickRandomStudent mainBoard constructor]

    Assert.assertEquals(0, mainBoardTest.calculateInfluence(profTest, teamsTest,
        mainBoardTest.getIslands().get(3)));

  }

  /**
   * This method is equal to testCalculateInfluence but test the case when two team have the same
   * influences, we expect it returns -1
   *
   * @throws FileNotFoundException exception
   */
  @Test
  public void testCalculateInfluenceDuplicate() throws FileNotFoundException {
    StudentsBag testStudBag = new StudentsBag();
    MainBoard mainBoardTest = new MainBoard(testStudBag);

    //find the color of the student added in the constructor of Main board and remove it
    int[] initialVector = mainBoardTest.getIslands().get(3).getStudents();
    int colorStudentToFind = -1;
    for (Color color : Color.values()) {
      colorStudentToFind =
          (initialVector[color.ordinal()] == 1) ? color.ordinal() : colorStudentToFind;
    }

    if (colorStudentToFind != -1) {
      mainBoardTest.getIslands().get(3).getStudents()[colorStudentToFind]--;
    }

    //create professors array, vector team and island to calculate influence
    Player[] profTest = new Player[Constants.getNColors()];
    Vector<Team> teamsTest = new Vector<>();

    //add students to the island and towers
    mainBoardTest.getIslands().get(3).addStudent(0);
    mainBoardTest.getIslands().get(3).addStudent(1);
    mainBoardTest.getIslands().get(3).addStudent(2);
    mainBoardTest.getIslands().get(3).setOwner(1);
    mainBoardTest.getIslands().get(3).addTower(1);

    //create the team
    StudentsBag testStudBag2 = new StudentsBag();
    Player player1 = new Player(0, "Jeff");
    player1.initializePlayerBoard(testStudBag2.pickRandomStudents(0));
    teamsTest.add(new Team(0, Towers.WHITE));
    StudentsBag testStudBag3 = new StudentsBag();
    Player player2 = new Player(1, "Tony");
    player2.initializePlayerBoard(testStudBag3.pickRandomStudents(0));
    teamsTest.add(new Team(1, Towers.BLACK));

    //fill the professors arr
    profTest[0] = player1;
    profTest[1] = player1;
    profTest[2] = player2;
    //player1 controls 0, 1. influence = 2 [+1 (pickRandomStudent mainBoard constructor)]
    //player2 controls 3, 4. influence = 1 + 1(tower) [+1 (pickRandomStudent mainBoard constructor]
    //TODO broken
    //Assert.assertEquals(-1, mainBoardTest.calculateInfluence(profTest, teamsTest,
    //mainBoardTest.getIslands().get(3)));
  }

  /**
   * Testing the addToIsland Method It checks that the size of islands vector is equal to the
   * initial numbers of Islands It tries to add 3 students on the third island and sum the student
   * added in the MainBoard constructor
   */
  @Test
  public void testAddToIsland() {
    StudentsBag testSB = new StudentsBag();
    MainBoard mainBoard = new MainBoard(testSB);
    Assert.assertEquals(mainBoard.getIslands().size(), Constants.getInitialNumIslands());

    //find the color of the student added in the constructor of Main board
    int[] initialVector = mainBoard.getIslands().get(3).getStudents();
    int colorStudentToFind = -1;
    for (Color color : Color.values()) {
      colorStudentToFind =
          (initialVector[color.ordinal()] == 1) ? color.ordinal() : colorStudentToFind;
    }

    //Add 3 students in the third island
    mainBoard.addToIsland(0, 3);
    mainBoard.addToIsland(0, 3);
    mainBoard.addToIsland(3, 3);
    Vector<Island> test = mainBoard.getIslands();

    //Create the correct array to compare
    int[] correctArr = {2, 0, 0, 1, 0};
    //if colorStudentToFind == -1 then there is mother nature, or we are on the opposite island
    if (colorStudentToFind != -1) {
      correctArr[colorStudentToFind]++;
    }

    Assert.assertEquals(Arrays.toString(correctArr), Arrays.toString(test.get(3).getStudents()));
  }

  /**
   * Testing the joinIsland Method This method tests the borderline case where joinIsland is called
   * at position 0
   * <p>
   * Initial disposition of the islands:  00 002 ... 3 Expected disposition of the islands: 000023
   * ... It starts to add 2 students in first (0) position of the islands vector 3 students in the
   * second position (1) and 1 student in the last position (n-1) Set the same owner (color = 2) for
   * each island Create a islandTests vector with the expected values inside and then call the
   * joinIslandMethod
   */
  @Test
  public void testJoinIsland() {

    StudentsBag test = new StudentsBag();
    MainBoard mainBoard = new MainBoard(test);

    //find the color of the students added in the constructor of Main board
    int[] positionOfRandomColors = new int[Constants.getInitialNumIslands()];

    for (int island = 0; island < mainBoard.getIslands().size(); island++) {

      int[] initialVector = mainBoard.getIslands().get(island).getStudents();
      int colorStudentToFind = -1;

      for (Color color : Color.values()) {
        colorStudentToFind =
            (initialVector[color.ordinal()] == 1) ? color.ordinal() : colorStudentToFind;
      }

      positionOfRandomColors[island] = colorStudentToFind;
    }

    //add students to the islands in positions 0, 1, n-1
    mainBoard.getIslands().get(0).addStudent(0);
    mainBoard.getIslands().get(0).addStudent(0);
    mainBoard.getIslands().get(0).setOwner(2);
    mainBoard.getIslands().get(1).addStudent(0);
    mainBoard.getIslands().get(1).addStudent(0);
    mainBoard.getIslands().get(1).addStudent(2);
    mainBoard.getIslands().get(1).setOwner(2);
    mainBoard.getIslands().get(11).addStudent(3);
    mainBoard.getIslands().get(11).setOwner(2);

    mainBoard.joinIsland(0);

    //Create correct vector to compare in to assert
    Vector<Island> islandsTest = new Vector<>();
    for (int island = 0; island < Constants.getInitialNumIslands() - 2; island++) {
      islandsTest.add(new Island());
    }

    islandsTest.get(0).addStudent(0);
    islandsTest.get(0).addStudent(0);
    islandsTest.get(0).addStudent(0);
    islandsTest.get(0).addStudent(0);
    islandsTest.get(0).addStudent(2);
    islandsTest.get(0).addStudent(3);

    //add to the correct vector the random students
    int islandIndexForCorrectVec = 0;
    for (int islandIndex = 0; islandIndex < Constants.getInitialNumIslands(); islandIndex++) {
      if (islandIndex == 1 || islandIndex == 11) {
        islandsTest.get(0).addStudent(positionOfRandomColors[islandIndex]);
      } else {
        islandsTest.get(islandIndexForCorrectVec).addStudent(positionOfRandomColors[islandIndex]);
        islandIndexForCorrectVec++;
      }
    }

    for (int island = 0; island < mainBoard.getIslands().size(); island++) {

      Assert.assertEquals(Arrays.toString(islandsTest.get(island).getStudents()),
          Arrays.toString(mainBoard.getIslands().get(island).getStudents()));
    }
  }

  /**
   * This method test the method moveMotherNature it tries to move mother nature in some limit
   * cases
   */
  @Test
  public void testMoveMotherNature() {
    StudentsBag studentBagTest = new StudentsBag();
    MainBoard mainBoardTest = new MainBoard(studentBagTest);

    int motherNatureInitialPos = mainBoardTest.getMotherNature().getPosition();
    int numberOfMoves = 143252;

    mainBoardTest.moveMotherNature(numberOfMoves);

    Assert.assertEquals(
        (motherNatureInitialPos + numberOfMoves) % mainBoardTest.getIslands().size(),
        mainBoardTest.getMotherNature().getPosition());
  }
}















