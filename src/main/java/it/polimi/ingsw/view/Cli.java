package it.polimi.ingsw.view;

import it.polimi.ingsw.model.CloudTile;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.MotherNature;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.PlayerBoard;
import java.util.HashMap;
import java.util.Vector;

public class Cli implements View {

  int viewId;

  public void loginRequest() {
  }

  public void setViewId(int id) {
    this.viewId = id;
  }

  public void setNickname() {
    System.out.println("Please choose a nickname");
  }

  public void selectNumberOfPlayerAndMode() {
    System.out.println("How many players do you want to play with? (including yourself)" + "\n"
        + "Do you want to play with the character or not ? Please digit expert mode in the first case and not expert mode in the second case");
  }


  public void showLobby(HashMap<Integer, String> lobby) {
    System.out.println("The lobby is composed by");
    for (int playerId = 0; playerId < lobby.size(); playerId++) {
      System.out.println(lobby.get(playerId) + "\n");
    }
  }

  public void printPlayableAssistants(Vector<Assistant> playableDeck) {
    System.out.println("your assistant deck is:");
    for (Assistant assistant : playableDeck) {
      System.out.println(
          "Assistant speed=" + assistant.getSpeed() + "mother nature max movement" + assistant
              .getMoves() + "\n");
    }

  }

  public void printPlayedAssistantsFromOthers(Vector<Assistant> playedAssistant) {
    System.out
        .println("The other played choose those assistant, you have to chose a different one");
    for (Assistant assistant : playedAssistant) {
      System.out.println(
          "Assistant speed=" + assistant.getSpeed() + "mother nature max movement" + assistant
              .getMoves() + "\n");
    }
  }

  public void playAssistant() {
  }

  //to print at the beginning of each player turn

  public void showActualTurn(String actualPlayerNickname) {
    System.out.println("The active player is :" + actualPlayerNickname + "\n");
  }

  public void printPurchasableCharacter(Vector<CharacterCard> purchasableCharacter) {
    for (CharacterCard character : purchasableCharacter) {

      switch (character.getCardEffect().ordinal()) {
        case 0:
          System.out.println("Character cost:" + character.getCost() + "\n"
              + "This character made mother nature move 2 further island than the indicated value on your assistant");
          break;
        case 1:
          System.out.println("Character cost:" + character.getCost() + "\n"
              + "This character allows to replace up to 3 student from this card to your entrance"
              + "available students on this card");
          printAvailableStudentsOnCard(character.getStudents());
          break;
        case 2:
          System.out.println("Character cost:" + character.getCost() + "\n"
              + "This character allows to exchange up to 2 student from your dining room and your entrance");
          break;
        case 3:
          System.out.println("Character cost:" + character.getCost() + "\n"
              + "This character allows to chose a student on this card and put it in your dining room");
          printAvailableStudentsOnCard(character.getStudents());
          break;
        case 4:
          System.out.println("Character cost:" + character.getCost() + "\n"
              + "This character allows to chose a student color and every player must return 3 student of that color from their dining room to the bag");
          break;
        case 5:
          System.out.println("Character cost:" + character.getCost() + "\n"
              + "This character allows to take a student from this card and put it on an island of your choice");
          printAvailableStudentsOnCard(character.getStudents());
          break;
        case 6:
          System.out.println("Character cost:" + character.getCost() + "\n"
              + "This character allows to chose an island and resolving that island as mother nature ends her movement there");
          break;
        case 7:
          System.out.println("Character cost:" + character.getCost() + "\n"
              + "This character allows to put a no entry tile on an island (mother nature cannot ends her movement there");
          break;
        case 8:
          System.out.println("Character cost:" + character.getCost() + "\n"
              + "This character blocks towers from adding points in the influence calculation");
          break;
        case 9:
          System.out.println("Character cost:" + character.getCost() + "\n"
              + "This character adds 2 more influence to the influence calculation");
          break;
        case 10:
          System.out.println("Character cost:" + character.getCost() + "\n"
              + "This character blocks a color from adding influence");
          break;
        case 11:
          System.out.println("Character cost:" + character.getCost() + "\n"
              + "This character allows to take a professor even if the number of students in your dining room is equal to another player ");
          break;


      }
      System.out.println("\n");
    }
  }

  private void printAvailableStudentsOnCard(int[] students) {
    System.out.println(students[0] + "yellow students" +
        students[1] + "blue students" + students[2] + "green students" + students[3]
        + "red students"
        + students[4] + "purple students" + "\n");
  }

  public void printMainBoard(Vector<Island> islands, MotherNature motherNature) {
    for (Island island : islands) {
      int indexIsland = islands.indexOf(island);
      System.out
          .println("Island number" + indexIsland + island.getStudents()[0] + "yellow students" +
              island.getStudents()[1] + "blue students" + island
              .getStudents()[2] + "green students" + island.getStudents()[3] + "red students"
              + island.getStudents()[4] + "purple students" + "\n" + " contains" + island
              .getNumberOfTowers() + "towers"
              + "belonging to" + island.getOwnerId() + "\n" + "contains mother nature"
              + containsMotherNature(
              indexIsland,
              motherNature.getPosition()) + "contain no entry tile" + island.isNoEntry());
    }
    System.out.println("\n");
  }

  private boolean containsMotherNature(int indexIsland, int motherPlace) {
    return indexIsland == motherPlace;
  }

  public void printPlayerBoard(PlayerBoard playerBoard) {
    System.out.println(
        "Dining room students:" + playerBoard.getDiningRoom().getStudents()[0] + "yellow students" +
            playerBoard.getDiningRoom().getStudents()[1] + "blue students" + playerBoard
            .getDiningRoom()
            .getStudents()[2] + "green students" + playerBoard.getDiningRoom().getStudents()[3]
            + "red students"
            + playerBoard.getDiningRoom().getStudents()[4] + "purple students" + "\n");
    System.out
        .println("Entrance:" + playerBoard.getEntrance().getStudents()[0] + "yellow students" +
            playerBoard.getEntrance().getStudents()[1] + "blue students" + playerBoard.getEntrance()
            .getStudents()[2] + "green students" + playerBoard.getEntrance().getStudents()[3]
            + "red students"
            + playerBoard.getEntrance().getStudents()[4] + "purple students" + "\n");
  }

  public void printCloudTile(Vector<CloudTile> cloudTiles) {
    for (CloudTile cloudTile : cloudTiles) {
      System.out.println(
          "cloud tile number:" + cloudTile.getId() + "\n" + cloudTile.getStudents()[0]
              + "yellow students" +
              cloudTile.getStudents()[1] + "blue students" + cloudTile
              .getStudents()[2] + "green students" + cloudTile.getStudents()[3] + "red students"
              + cloudTile.getStudents()[4] + "purple students" + "\n");
    }
  }


  public void moveStudents() {

  }

  //print player board again

  public void moveMotherNature() {

  }

  // print main board again

  public void purchaseCharacter() {

  }

  public void takeCloudTile() {

  }


  public void printError(String errorString) {
  }

}
