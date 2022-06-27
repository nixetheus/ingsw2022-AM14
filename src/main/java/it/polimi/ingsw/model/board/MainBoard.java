package it.polimi.ingsw.model.board;

import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.player.Player;
import java.util.Arrays;
import java.util.Vector;

/**
 * MainBoard class used to model everything shared between players
 */
public class MainBoard {

  // Attributes
  private final Vector<Island> islands;
  private final MotherNature motherNature;
  private int influencePlus = 0;
  private Color forbiddenColor = null;
  private boolean areTowersCounted = true;

  /**
   * Constructor method: it creates Islands array, it places the mother nature on a random island,
   * and it places on the other islands one student with pickRandomStudent method, except the island
   * in front of the mother nature
   *
   * @param gameStudentsBag The studentBag where take the students
   */
  public MainBoard(StudentsBag gameStudentsBag) {

    islands = new Vector<>();
    motherNature = new MotherNature(MainBoard.pickStartPlaceMotherNature());

    for (int islandIndex = 0; islandIndex < Constants.getInitialNumIslands(); islandIndex++) {

      Island newIsland = new Island();
      newIsland.setIslandId(islandIndex);
      int[] randomStudent = gameStudentsBag.pickRandomStudents(1);
      int color = Arrays.stream(new int[]{0, 1, 2, 3, 4})
          .filter(c -> randomStudent[c] > 0).toArray()[0];

      if (islandIndex != motherNature.getPosition() &&
          islandIndex != ((motherNature.getPosition()
              + Constants.getInitialNumIslands() / 2)
              % Constants.getInitialNumIslands())) {
        newIsland.addStudent(color);
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
   * Method to calculate the team with the greatest influence It creates an array that represents
   * the influence of each team of the passed island For each color it calculates the influences of
   * the team Add extra influences points for the towers Return the index of the team with the
   * greatest influence
   *
   * @param professors array representing the players that control the respective professors
   * @param teams      vector of the teams
   * @param island     island on which to calculate the influence
   * @return the integer that represents the team, if it is equal to -1 it is not possible to
   * conquer or control the island, because two or more team have the same influence
   */
  public int calculateInfluence(Player[] professors, Vector<Team> teams, Island island,
      Player currentPlayer) {

    int[] influences = new int[teams.size()];
    for (Color color : Color.values()) {
      Player playerHasProfessor = professors[color.ordinal()];

      Team teamHasProfessor = null;
      for (Team team : teams) {
        if (team.getPlayers().contains(playerHasProfessor)) {
          teamHasProfessor = team;
        }

        if (team.getPlayers().contains(currentPlayer)) {
          influences[team.getId()] += influencePlus;
        }
      }

      if (teamHasProfessor != null && color != forbiddenColor) {
        influences[teamHasProfessor.getId()] += island.getStudents()[color.ordinal()];
      }
    }

    // Add the influences points of the tower
    if (island.getOwnerId() != -1 && areTowersCounted) {
      influences[island.getOwnerId()] += island.getNumberOfTowers();
    }

    // Return the index of the team with the max influences
    int indexTeamMaxInfluence = -1;
    int maxInfluence = -1;

    for (int indexTeam = 0; indexTeam < influences.length; indexTeam++) {
      if (influences[indexTeam] > maxInfluence) {
        maxInfluence = influences[indexTeam];
        indexTeamMaxInfluence = indexTeam;
      }
    }

    Arrays.sort(influences);

    boolean equalInfluence = influences[influences.length - 1] == influences[influences.length - 2];
    if (equalInfluence && island.getOwnerId() == -1) {
      return -1;
    } else if (equalInfluence && island.getOwnerId() != -1) {
      return island.getOwnerId();
    }

    return indexTeamMaxInfluence;
  }

  /**
   * addToIsland: method to add a student on an island
   *
   * @param color       the color of the student
   * @param numIslandId the id  of island to place the student
   */
  public void addToIsland(int color, int numIslandId) {
    for (Island island : islands) {
      if (island.getIslandId() == numIslandId) {
        island.addStudent(color);
      }
    }
  }

  /**
   * This method checks that the owners of the islands adjacent to the one in position
   * "numIslandConquered" are equal and if they are, it joins them into a single island, and removes
   * the other from the islands vector
   *
   * @param numIslandConquered number that identifies the islandId
   */
  public void joinIsland(int numIslandConquered) {

    int islandConqueredIndex = -1;

    for (Island island : islands) {
      if (island.getIslandId() == numIslandConquered) {
        islandConqueredIndex = islands.indexOf(island);
      }
    }
    // Check next island
    int nextIsland = (islandConqueredIndex + 1) % islands.size();
    if (islands.get(islandConqueredIndex).getOwnerId() == islands.get(nextIsland).getOwnerId() &&
        //checking if not the default one
        islands.get(islandConqueredIndex).getOwnerId() != -1) {
      islands.get(islandConqueredIndex).addIsland(islands.get(nextIsland));
      islands.remove(islands.get(nextIsland));
    }

    // Check previous island
    int previousIsland =
        (islandConqueredIndex - 1) < 0 ? islands.size() - 1 : islandConqueredIndex - 1;
    if (islands.get(islandConqueredIndex).getOwnerId() == islands.get(previousIsland).getOwnerId()
        &&
        //checking if not the default one
        islands.get(islandConqueredIndex).getOwnerId() != -1) {
      islands.get(islandConqueredIndex).addIsland(islands.get(previousIsland));
      islands.remove(islands.get(previousIsland));
    }
  }

  /**
   * Method to move mother nature
   *
   * @param numMoves number of jumps of mother nature
   */
  public void moveMotherNature(int numMoves) {
     int newMotherNaturePosition = -1;
    for (Island island : islands) {
      if (island.getIslandId() == motherNature.getPosition()) {
        if (numMoves < 0) {
          numMoves = islands.size() + numMoves;
        } else {
          newMotherNaturePosition = islands
              .get((islands.indexOf(island) + numMoves) % islands.size())
              .getIslandId();
        }
      }
    }
    motherNature.move(newMotherNaturePosition);
  }

  /**
   * This method sets an island as NoEntry meaning Mother Nature can't move here
   *
   * @param numIsland The index of the island to set as NoEntry
   */
  public void setIslandsNoEntry(int numIsland) {
    for(Island island :islands){
      if(island.getIslandId()==numIsland){
        islands.get(numIsland).setNoEntry(true);
      }
    }
  }

  /**
   * This method resets an island as NoEntry meaning Mother Nature can enter again
   *
   * @param numIsland The index of the island to reset
   */
  public void resetIslandsNoEntry(int numIsland) {
    for(Island island :islands){
      if(island.getIslandId()==numIsland){
        islands.get(numIsland).setNoEntry(false);
      }
    }
  }

  public Vector<Island> getIslands() {
    return islands;
  }

  public MotherNature getMotherNature() {
    return motherNature;
  }

  public int getInfluencePlus() {
    return influencePlus;
  }

  public void setInfluencePlus(int influencePlus) {
    this.influencePlus = influencePlus;
  }

  public Color getForbiddenColor() {
    return forbiddenColor;
  }

  public void setForbiddenColor(Color forbiddenColor) {
    this.forbiddenColor = forbiddenColor;
  }

  public boolean areTowersCounted() {
    return areTowersCounted;
  }

  public void setAreTowersCounted(boolean areTowersCounted) {
    this.areTowersCounted = areTowersCounted;
  }

}
