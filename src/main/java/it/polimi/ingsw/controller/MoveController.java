package it.polimi.ingsw.controller;

import it.polimi.ingsw.helpers.Places;
import it.polimi.ingsw.messages.MoveMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import java.util.Arrays;
import java.util.Optional;

public class MoveController {

  /**
   * TODO
   * @param msg the message to elaborate
   * @param currentGame
   */
  public boolean elaborateMessage(MoveMessage msg, Game currentGame) {
    //can create a method for each control
    switch (msg.getMessageSecondary()) {
      case MOTHER_NATURE:
        //control if no entry tile
        currentGame.moveNature(
            msg.getIslandNumber() - currentGame.getMainBoard().getMotherNature().getPosition());
        break;
      case CLOUD_TILE:
        //control if the number is valid
        if (Arrays.stream(currentGame.getCloudTiles().get(msg.getCloudTileNumber()).getStudents())
            .sum() != 0 && currentGame.getCloudTiles().size() >= msg.getCloudTileNumber()) {
          currentGame.takeCloud(currentGame.getCurrentPlayer(), msg.getCloudTileNumber());
          break;
        }
      case ENTRANCE:
        //control if the student is present
        if (currentGame.getCurrentPlayer().getPlayerBoard().getEntrance().getStudents()[msg.getStudentColor()] >= 1) {
          if (msg.getPlace() == 0) {
            currentGame.moveStudent(currentGame.getCurrentPlayer(), Places.ENTRANCE, Places.DINING_ROOM,
                msg.getStudentColor(),
                Optional.empty());
          } else {
            currentGame
                .moveStudent(currentGame.getCurrentPlayer(), Places.ENTRANCE, Places.ISLAND, msg.getStudentColor(),
                    Optional.of(msg.getIslandNumber()));
          }
        }
      default:
        break;
    }

    return false;
  }
}
