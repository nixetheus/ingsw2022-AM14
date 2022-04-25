package it.polimi.ingsw.controller;

import it.polimi.ingsw.helpers.Places;
import it.polimi.ingsw.messages.MoveMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import java.util.Arrays;
import java.util.Optional;

public class MoveController {

  private Game currentGame;

  /**
   * TODO
   * @param msg the message to elaborate
   * @param activePlayer the active player
   */
  public void elaborateMessage(MoveMessage msg, Player activePlayer) {
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
          currentGame.takeCloud(activePlayer, msg.getCloudTileNumber());
          break;
        } else {
          //error message
        }
      case ENTRANCE:
        //control if the student is present
        if (activePlayer.getPlayerBoard().getEntrance().getStudents()[msg.getStudentColor()] >= 1) {
          if (msg.getPlace() == 0) {
            currentGame.moveStudent(activePlayer, Places.ENTRANCE, Places.DINING_ROOM,
                msg.getStudentColor(),
                Optional.empty());
          } else {
            currentGame
                .moveStudent(activePlayer, Places.ENTRANCE, Places.ISLAND, msg.getStudentColor(),
                    Optional.of(msg.getIslandNumber()));
          }
        } else {
          //error message
        }
      default:
        break;
    }
  }


  public Game getCurrentGame() {
    return currentGame;
  }

  public void setCurrentGame(Game currentGame) {
    this.currentGame = currentGame;
  }
}
