package it.polimi.ingsw.controller;

import it.polimi.ingsw.helpers.CardTypes;
import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.helpers.Places;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.player.Player;
import java.util.Optional;

/**
 * Player Controller class that controls what a player can do in his/her turn
 */
public class PlayerController {

  private Game currentGame;


  /**
   * Constructor for the PlayerController class
   */
  public PlayerController() {

  }

  /**
   * The method move student allows and check if a player can move a student from everywhere to
   * every place
   *
   * @param activePlayer The  id of the player that want to do the action
   * @param color        The color of the student moved
   * @param whereToTake  The place where the student is picked up
   * @param whereToPut   The place where the student will be put
   * @param islandNumber If the student will be put into an island and this represent the number of
   *                     the island
   */
  public void moveStudent(Player activePlayer, int color, Places whereToTake, Places whereToPut,
      Optional<Integer> islandNumber) {

    if (islandNumber.isPresent()) {
      currentGame.moveStudent(activePlayer, whereToTake, whereToPut, color, islandNumber);
      //no entry tile check
    } else {
      if (activePlayer.getPlayerBoard().getDiningRoom().getStudents()[color] != Constants
          .getMaxStudentsDiningRoom()) {
        currentGame.moveStudent(activePlayer, whereToTake, whereToPut, color, Optional.empty());
      } else {
        //new message
      }

    }
  }

  /**
   * moveMotherNature moves mother nature
   *
   * @param jumps MotherNature movement that a player want to do
   */
  public void moveMotherNature(int jumps) {
    currentGame.moveNature(jumps);
  }

  /**
   * This method allows and control if a player can play a card
   *
   * @param activePlayer   Id of the player that is doing the action
   * @param idCard         The number of the card played
   * @param cardTypePlayed If it is an assistant card or a character one
   */
  public void playCard(Player activePlayer, int idCard, CardTypes cardTypePlayed) {
    //internal method call to take the active player from the game
    if (cardTypePlayed == CardTypes.ASSISTANT) {

      //check if already played
      for (Team team : currentGame.getTeams()) {
        for (Player player : team.getPlayers()) {
          if (player.getAssistant().getAssistantId() == idCard) {
            //error message
            return;
          }
        }
      }
      activePlayer.playAssistant(idCard);
    } else {
      //if(activePlayer.getCoins()>=character cost)
      currentGame.playCharacter(activePlayer, idCard);
      //enough coin check
    }
  }

  /**
   * This method select and take a certain cloud tile for a certain player
   *
   * @param activePlayer The player who wants to take the cloud tile
   * @param idCloudTile  The id of the cloud taken
   */
  public void takeCloudTile(Player activePlayer, int idCloudTile) {
    currentGame.takeCloud(activePlayer, idCloudTile);
  }


  public void setCurrentGame(Game currentGame) {
    this.currentGame = currentGame;
  }


}
