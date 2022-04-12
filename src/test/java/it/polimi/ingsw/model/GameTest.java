package it.polimi.ingsw.model;


import java.io.FileNotFoundException;
import org.junit.Test;

public class GameTest {


  /**
   * *Test for the createCloud and fill method, it controls if the clouds will be created and filled
   * in the right way at the beginning of the game
   *
   * @throws FileNotFoundException If a json file will not be found
   */
  @Test
  public void testCreateCloudsAndFill() throws FileNotFoundException {

    /*Vector<Team> teams = new Vector<>();
    Game game = new Game(teams, 1);
    Team activePlayer = new Team(1, 2, new Player(1, "ale", game.getStudentsBag(),
        game.getStudentAtEntrance()));
    Team nonActivePlayer = new Team(2, 1, new Player(2, "luca", game.getStudentsBag(),
        game.getStudentAtEntrance()));
    teams.add(activePlayer);
    teams.add(nonActivePlayer);
    game.setGameParameter();
    game.createCloudsAndFill();
    Assert.assertEquals(game.getCloudTiles().size(), game.getTeams().size());
    for (CloudTile cloudTile : game.getCloudTiles()) {
      Assert.assertEquals(Arrays.stream(cloudTile.getStudents()).sum(),
          game.getStudentOnCloudTiles());
    }*/

    // TODO LUCA: broken
  }

  /**
   * Testing the moveNature method and it ensures that, independently from the original position of
   * motherNature, she will be moved by the correct number of steps chosen by the player according
   * to the played assistant
   *
   * @throws FileNotFoundException If a json file will not be found
   */
  @Test
  public void testMoveNature() throws FileNotFoundException {
    /*Vector<Team> teams = new Vector<>();
    Game game = new Game(teams, 1);
    Team activePlayer = new Team(1, 2, new Player(1, "ale", game.getStudentsBag(),
        game.getStudentAtEntrance()));
    Team nonActivePlayer = new Team(2, 1, new Player(2, "luca", game.getStudentsBag(),
        game.getStudentAtEntrance()));
    teams.add(activePlayer);
    teams.add(nonActivePlayer);
    int randomPosition = game.getMainBoard().getMotherNature().getPosition();
    game.moveNature(3);
    Assert.assertEquals((randomPosition + 3) % 12,
        game.getMainBoard().getMotherNature().getPosition());*/
    // TODO LUCA: broken
  }

  /**
   * Testing the takeCloud method ensuring that the chosen cloudTile students will be correctly
   * added to the active player entrance removing them from the cloudTile
   *
   * @throws FileNotFoundException If a json file will not be found
   */
  @Test
  public void testTakeCloud() throws FileNotFoundException {
    /*Vector<Team> teams = new Vector<>();
    Game game = new Game(teams, 1);
    game.setGameParameter();
    game.getPurchasableCharacter();
    Team activeTeam = new Team(1, 2, new Player(1, "ale", game.getStudentsBag(),
        game.getStudentAtEntrance()));
    Team nonActiveTeam = new Team(2, 1, new Player(2, "luca", game.getStudentsBag(),
        game.getStudentAtEntrance()));
    teams.add(activeTeam);
    teams.add(nonActiveTeam);
    game.createCloudsAndFill();
    game.takeCloud(activeTeam.getPlayers().get(0), 0);
    Assert.assertEquals(
        Arrays.stream(activeTeam.getPlayers().get(0).getPlayerBoard().getEntrance().getStudents())
            .sum(),
        game.getStudentAtEntrance() + game
            .getStudentOnCloudTiles());*/
    // TODO LUCA: broken
  }

  /**
   * Testing the purchasableCharacter method ensuring that the purchasableCharacter attributes will
   * be correctly created and filled
   *
   * @throws FileNotFoundException If a json file will not be found
   */
  @Test
  public void testSetPurchasableCharacter() throws FileNotFoundException {
    /*Vector<Team> teams = new Vector<>();
    Game game = new Game(teams, 1);
    game.setGameParameter();
    Team activePlayer = new Team(1, 2, new Player(1, "ale", game.getStudentsBag(),
        game.getStudentAtEntrance()));
    Team nonActivePlayer = new Team(2, 1, new Player(2, "luca", game.getStudentsBag(),
        game.getStudentAtEntrance()));
    teams.add(activePlayer);
    teams.add(nonActivePlayer);
    game.setPurchasableCharacter();
    Assert.assertEquals(game.getPurchasableCharacter().length, 3);
    Assert.assertNotNull(game.getPurchasableCharacter()[0]);
    Assert.assertNotNull(game.getPurchasableCharacter()[1]);
    Assert.assertNotNull(game.getPurchasableCharacter()[2]);
    */
  }

  /**
   * testing the setGameParameter method ensuring that the parameters in the gameParameter.json file
   * will be correctly read and set
   *
   * @throws FileNotFoundException If a json file will not be found
   */
  @Test
  public void testSetGameParameter() throws FileNotFoundException {
    /*Vector<Team> teams = new Vector<>();
    Game game = new Game(teams, 2);
    Team activePlayer = new Team(1, 2, new Player(1, "ale", game.getStudentsBag(),
        game.getStudentAtEntrance()));
    Team nonActivePlayer = new Team(2, 1, new Player(2, "luca", game.getStudentsBag(),
        game.getStudentAtEntrance()));
    teams.add(activePlayer);
    teams.add(nonActivePlayer);
    game.setGameParameter();
    Assert.assertEquals(game.getPlayerNumber(), 3);
    Assert.assertEquals(game.getPlayerTowerNumber(), 6);
    Assert.assertEquals(game.getStudentAtEntrance(), 9);
    Assert.assertEquals(game.getStudentOnCloudTiles(), 4);
    */
    // TODO LUCA: broken
  }

  /**
   * testing the moveStudent method(during a diningRoom case) and it ensures that the student will
   * be removed from the entrance and added to the diningRoom
   *
   * @throws FileNotFoundException If a json file will not be found
   */
  @Test
  public void testMoveStudentDiningRoomCase() throws FileNotFoundException {
    /*Vector<Team> teams = new Vector<>();
    Game game = new Game(teams, 2);
    game.setGameParameter();
    Team activePlayer = new Team(1, 2, new Player(1, "ale", game.getStudentsBag(),
        game.getStudentAtEntrance()));
    Team nonActivePlayer = new Team(2, 1, new Player(2, "luca", game.getStudentsBag(),
        game.getStudentAtEntrance()));
    teams.add(activePlayer);
    teams.add(nonActivePlayer);
    game.setGameParameter();
    game.moveStudent(activePlayer.getPlayers().get(0), Places.ENTRANCE, Places.DINING_ROOM, 1, -1);*/
    // TODO LUCA: broken
  }

  /**
   * Testing the moveStudent method(during an island case) and it ensures that the student will be
   * removed from the entrance and added to the selected island To avoid a possible side effect due
   * to the random color of students into the entrance this test also add a student with the same
   * color of the one added to the island
   *
   * @throws FileNotFoundException If a json file will not be found
   */
  @Test
  public void testMoveStudentCloudCase() throws FileNotFoundException {

    /*Vector<Team> teams = new Vector<>();
    Game game = new Game(teams, 1);
    game.setGameParameter();

    Team activePlayer = new Team(1, 2, new Player(1, "ale", game.getStudentsBag(),
        game.getStudentAtEntrance()));
    Team nonActivePlayer = new Team(2, 1, new Player(2, "luca", game.getStudentsBag(),
        game.getStudentAtEntrance()));
    teams.add(activePlayer);
    teams.add(nonActivePlayer);

    activePlayer.getPlayers().get(0).getPlayerBoard().moveToEntrance(1);
    game.moveStudent(activePlayer.getPlayers().get(0), Places.ENTRANCE, Places.CLOUD_TILE, 1, 5);

    Assert.assertEquals(Arrays.stream(game.getMainBoard().getIslands().get(5).getStudents()).sum(),
        1);
    Assert.assertEquals(
        Arrays.stream(activePlayer.getPlayers().get(0).getPlayerBoard().getEntrance().getStudents())
            .sum(), (game.getStudentAtEntrance()) - 1);*/
    // TODO LUCA: broken
  }
}

