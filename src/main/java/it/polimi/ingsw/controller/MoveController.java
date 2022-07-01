package it.polimi.ingsw.controller;

import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.helpers.Places;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MoveMessage;
import it.polimi.ingsw.messages.MoveMessageResponse;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Island;
import java.util.Arrays;
import java.util.Optional;

/**
 * Move controller class elaborates move messages and controls if they are correct
 */
public class MoveController {

  /**
   * Constructor method for MoveController class
   */
  public MoveController() {
  }

  /**
   * This method elaborates the message and return the response
   *
   * @param msg         The message sent
   * @param currentGame The started match
   */
  public Message elaborateMessage(MoveMessage msg, Game currentGame) {

    switch (msg.getMessageSecondary()) {
      case MOVE_MN:
        //control if no entry tile
        int messageIslandIndex = -1;
        for (Island island : currentGame.getMainBoard().getIslands()) {
          if (island.getIslandId() == msg.getIslandNumber()) {
            messageIslandIndex = currentGame.getMainBoard().getIslands().indexOf(island);
            break;
          }
        }
        int nOfIslands = currentGame.getMainBoard().getIslands().size();
        int islandMotherNatureIndex = -1;

        for (Island island : currentGame.getMainBoard().getIslands()) {
          if (island.getIslandId() == currentGame.getMainBoard().getMotherNature().getPosition()) {
            islandMotherNatureIndex = currentGame.getMainBoard().getIslands().indexOf(island);
            break;
          }
        }

        int motherNatureMoves = 0;
        //mosse in indici
        while (true) {
          if ((islandMotherNatureIndex + motherNatureMoves) % nOfIslands == messageIslandIndex) {
            break;
          } else {
            motherNatureMoves++;
          }
        }

        // ONLY CONSENTED N OF MOVES
        if (motherNatureMoves <= currentGame.getCurrentPlayer().getAssistant().getMoves()
            && motherNatureMoves > 0) {

          // ONLY CLOCKWISE
          if (messageIslandIndex == (islandMotherNatureIndex + motherNatureMoves) % nOfIslands) {

            currentGame.moveNature(motherNatureMoves);

            int newMotherNaturePosition = currentGame.getMainBoard().getMotherNature()
                .getPosition();

            MoveMessageResponse responseNature = new MoveMessageResponse(
                MessageSecondary.MOVE_MN);

            responseNature.setPlayerId(currentGame.getCurrentPlayer().getPlayerId());
            responseNature.setIslandNumber(newMotherNaturePosition);

            responseNature.setTowersIsland(currentGame.getMainBoard().getIslands().stream()
                .filter(island -> island.getIslandId() == newMotherNaturePosition).findFirst()
                .get()
                .getNumberOfTowers());
            responseNature.setIslandOwnerId(currentGame.getMainBoard().getIslands().stream()
                .filter(island -> island.getIslandId() == newMotherNaturePosition).findFirst()
                .get()
                .getOwnerId());
            return responseNature;


          }

        }
        break;

      case CLOUD_TILE:
        //control if the number is valid

        if (checkCloudTileValid(msg, currentGame)) {

          MoveMessageResponse responseCloud = new MoveMessageResponse(MessageSecondary.CLOUD_TILE);
          responseCloud.setPlayerId(currentGame.getCurrentPlayer().getPlayerId());

          responseCloud.setStudentsCloud(
              currentGame.getCloudTiles().get(msg.getCloudTileNumber()).getStudents().clone());

          currentGame.takeCloud(currentGame.getCurrentPlayer(), msg.getCloudTileNumber());

          responseCloud.setCloudTileNumber(msg.getCloudTileNumber());

          return responseCloud;
        }
        break;
      case ENTRANCE:

        // Get color for GUI
        if (msg.getStudentNumber() >= 0) {

          int[] playerStudents = currentGame.getCurrentPlayer().getPlayerBoard().getEntrance()
              .getStudents();

          int studentIndex = 0;
          msg.setStudentColor(-1);
          for (int color = 0; color < playerStudents.length; color++) {
            for (int nOfColor = 0; nOfColor < playerStudents[color]; nOfColor++) {
              if (msg.getStudentNumber() == studentIndex) {
                msg.setStudentColor(color);
                break;
              } else {
                studentIndex++;
              }
            }
            if (msg.getStudentColor() != -1) {
              break;
            }
          }
        }

        //control if the student is present
        if (checkStudentIfPresent(msg, currentGame)) {
          MoveMessageResponse responseEntrance = new MoveMessageResponse(
              MessageSecondary.MOVE_STUDENT_ENTRANCE);
          responseEntrance.setPlayerId(currentGame.getCurrentPlayer().getPlayerId());

          if (msg.getPlace() == 0) {
            responseEntrance.setPlace(0);

            currentGame
                .moveStudent(currentGame.getCurrentPlayer(), Places.ENTRANCE, Places.DINING_ROOM,
                    msg.getStudentColor(),
                    Optional.empty());

            responseEntrance.setStudentsDiningRoom(
                currentGame.getCurrentPlayer().getPlayerBoard().getDiningRoom()
                    .getStudents());

            //professors
            int[] professorPlayerId = new int[Constants.getNColors()];
            for (Color color : Color.values()) {
              if (currentGame.getProfessorControlPlayer()[color.ordinal()] == null) {
                professorPlayerId[color.ordinal()] = -1;
              } else {
                professorPlayerId[color.ordinal()] = currentGame.getProfessorControlPlayer()[color
                    .ordinal()].getPlayerId();
              }
            }
            responseEntrance.setProfessors(professorPlayerId);

            //coins
            responseEntrance.setPlayerCoins(currentGame.getCurrentPlayer().getCoins());
          } else {
            responseEntrance.setPlace(1);

            currentGame
                .moveStudent(currentGame.getCurrentPlayer(), Places.ENTRANCE, Places.ISLAND,
                    msg.getStudentColor(),
                    Optional.of(msg.getIslandNumber()));

            int islandIndex = -1;
            for (Island island : currentGame.getMainBoard().getIslands()) {
              if (island.getIslandId() == msg.getIslandNumber()) {
                islandIndex = currentGame.getMainBoard().getIslands().indexOf(island);
              }
            }
            responseEntrance.setStudentsIsland(
                currentGame.getMainBoard().getIslands().get(islandIndex).getStudents());

            responseEntrance.setTowersIsland(
                currentGame.getMainBoard().getIslands().get(islandIndex).getNumberOfTowers());
            responseEntrance.setIslandOwnerId(
                currentGame.getMainBoard().getIslands().get(islandIndex).getOwnerId());
          }

          responseEntrance.setIslandNumber(msg.getIslandNumber());

          responseEntrance
              .setStudentsEntrance(currentGame.getCurrentPlayer().getPlayerBoard().getEntrance()
                  .getStudents());

          return responseEntrance;
        }
        break;

      default:
        break;
    }

    return null;
  }

  /**
   * This method controls if the cloud tile number is valid
   *
   * @param msg         The message sent
   * @param currentGame The started match
   * @return true if the cloudNumber is correct and it is not already taken
   */
  private boolean checkCloudTileValid(MoveMessage msg, Game currentGame) {
    return currentGame.getCloudTiles().size() >= msg.getCloudTileNumber()
        && (Arrays.stream(currentGame.getCloudTiles().get(msg.getCloudTileNumber()).getStudents())
        .sum() != 0 || Arrays.stream(currentGame.getStudentsBag().getStudents()).sum() == 0);
  }


  /**
   * This method controls if the student is present at the entrance
   *
   * @param msg         The message sent
   * @param currentGame The started match
   * @return true if the selected student is at the entrance
   */
  private boolean checkStudentIfPresent(MoveMessage msg, Game currentGame) {
    return currentGame.getCurrentPlayer().getPlayerBoard().getEntrance().getStudents()[msg
        .getStudentColor()] >= 1;
  }
}
