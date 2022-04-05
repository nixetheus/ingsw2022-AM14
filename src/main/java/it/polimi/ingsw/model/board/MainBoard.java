package it.polimi.ingsw.model.board;

import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.player.Player;
import java.util.Vector;

public class MainBoard {

  //Attributes
  private final StudentsBag studentsBag;
  private final Vector<Island> islands;
  private final MotherNature motherNature;

  /**
   * Constructor method: it creates Islands array, and it places on each of them one student
   */
  public MainBoard(StudentsBag gameStudentsBag) {

    islands = new Vector<>();
    studentsBag = gameStudentsBag;
    motherNature = new MotherNature(MainBoard.pickStartPlaceMotherNature());

    for (int islandIndex = 0; islandIndex < Constants.getInitialNumIslands(); islandIndex++) {

      Island newIsland = new Island();
      if (islandIndex != motherNature.getPosition() ||
          islandIndex != ((motherNature.getPosition()
              + Constants.getInitialNumIslands() / 2)
              % Constants.getInitialNumIslands())) {
        newIsland.addStudent(studentsBag.pickRandomStudents(1)[0]);
      }
      islands.add(newIsland);
    }
  }

  /**
   * Static method to make a random int
   *
   * @return a random integer from 0 to initial number of islands - 1
   */
  static public int pickStartPlaceMotherNature() {
    return (int) (Math.random() * Constants.getInitialNumIslands());
  }

  /**
   * Method to calculate the team with the greatest influence
   *
   * @return the integer that represents the team
   */
  public int calculateInfluence(Player[] professors, Vector<Team> teams, Island island) {
    int[] influences = new int[teams.size()];
    for (Color color : Color.values()) {
      Player playerHasProfessor = professors[color.ordinal()];

      Team teamHasProfessor = null;
      for (Team team : teams) {
        if (team.getPlayers().contains(playerHasProfessor)) {
          teamHasProfessor = team;
        }
      }

      if (teamHasProfessor != null) {
        influences[teamHasProfessor.getId()] += island.getStudents()[color.ordinal()];
      }
    }

    // TODO LUCA: return id/team con più influenza
    return 0;
  }

  /**
   * addToIsland: method to add a student on an island
   *
   * @param color     the color of the student
   * @param numIsland number of island to place the student
   */
  public void addToIsland(int color, int numIsland) {
    islands.get(numIsland).addStudent(color);
  }

  /**
   * it checks that the owners of the islands adjacent to the one in position "numIslandConquered"
   * are equal and if they are, it joins them into a single island, and removes the other
   *
   * @param numIslandConquered number that identifies the island
   */
  public void joinIsland(int numIslandConquered) {

    // NEXT
    int nextIsland = (numIslandConquered + 1) % islands.size();
    if (islands.get(numIslandConquered).getOwnerId() == islands.get(nextIsland).getOwnerId()) {
      islands.get(numIslandConquered).addIsland(islands.get(nextIsland));
      islands.remove(islands.get(nextIsland));
    }

    // PREVIOUS
    int previousIsland = (numIslandConquered - 1) < 0 ? islands.size() - 1 : numIslandConquered - 1;
    if (islands.get(numIslandConquered).getOwnerId() == islands.get(previousIsland).getOwnerId()) {
      islands.get(numIslandConquered).addIsland(islands.get(previousIsland));
      islands.remove(islands.get(previousIsland));
    }
  }

  public Vector<Island> getIslands() {
    return islands;
  }

  public MotherNature getMotherNature() {
    return motherNature;
  }
}
