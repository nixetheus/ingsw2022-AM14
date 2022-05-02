package it.polimi.ingsw.controller;

import it.polimi.ingsw.helpers.Places;
import it.polimi.ingsw.messages.MoveMessage;
import it.polimi.ingsw.model.Game;
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
   * TODO
   *
   * @param msg         The message sent
   * @param currentGame The started match
   */
  public boolean elaborateMessage(MoveMessage msg, Game currentGame) {
    //can create a method for each control
    switch (msg.getMessageSecondary()) {
      case MOTHER_NATURE:
        //control if no entry tile
        int motherNatureMoves =
            msg.getIslandNumber() - currentGame.getMainBoard().getMotherNature().getPosition();
        currentGame.moveNature(
            motherNatureMoves);
        return true;
      case CLOUD_TILE:
        //control if the number is valid
        if (checkCloudTileValid(msg, currentGame) && !currentGame.getMainBoard().getIslands()
            .get(msg.getIslandNumber()).isNoEntry()) {
          currentGame.takeCloud(currentGame.getCurrentPlayer(), msg.getCloudTileNumber());
          return true;
        }
        break;
      case ENTRANCE:
        //control if the student is present
        if (checkStudentIfPresent(msg, currentGame)) {
          if (msg.getPlace() == 0) {
            currentGame
                .moveStudent(currentGame.getCurrentPlayer(), Places.ENTRANCE, Places.DINING_ROOM,
                    msg.getStudentColor(),
                    Optional.empty());
          } else {
            currentGame
                .moveStudent(currentGame.getCurrentPlayer(), Places.ENTRANCE, Places.ISLAND,
                    msg.getStudentColor(),
                    Optional.of(msg.getIslandNumber()));
          }
          return true;
        }
      default:
        break;
    }

    return false;
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
        && Arrays.stream(currentGame.getCloudTiles().get(msg.getCloudTileNumber()).getStudents())
        .sum() != 0;
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
