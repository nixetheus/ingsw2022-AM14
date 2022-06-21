package it.polimi.ingsw.controller;

import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.messages.ClientResponse;
import it.polimi.ingsw.messages.InfoRequestMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.CloudTile;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerBoard;
import java.util.Vector;

/**
 * This class elaborates info messages
 */
public class InfoController {

  /**
   * This method elaborates InfoRequestMessages
   *
   * @param msg  the message to be elaborated
   * @param game the actual match
   * @return the message response
   */
  public Vector<Message> elaborateMessage(InfoRequestMessage msg, Game game) {

    String response;
    Vector<Message> messages = new Vector<>();
    switch (msg.getMessageSecondary()) {
      case INFO_MN:
        response = infoMotherNature(game);
        break;
      case INFO_HELP:
        response = infoHelp();
        break;
      case INFO_ISLAND:
        response = infoIsland(game, msg.getObjectId());
        break;
      case INFO_PLAYER:
        response = infoPlayer(game, msg.getPlayerId());
        break;
      case INFO_CHARACTER:
        response = infoCharacters(game, msg.getObjectId());
        break;
      case INFO_ASSISTANTS:
        response = infoAssistants(game, msg.getObjectId());
        break;
      case INFO_CLOUD_TILE:
        response = infoCloudTile(game, msg.getObjectId());
        break;
      default:
        response = null;
        break;
    }

    if (response != null) {
      ClientResponse responseMessage = new ClientResponse(
          MessageSecondary.INFO_RESPONSE_MESSAGE);
      responseMessage.setPlayerId(msg.getPlayerId());
      responseMessage.setResponse(response);
      messages.add(responseMessage);
      return messages;
    } else {
      return null;
    }
  }

  private String infoHelp() {
    return "TODO";  // TODO
  }

  /**
   * It builds a string with the current mother nature information
   *
   * @param game the actual match
   * @return the created string
   */
  private String infoMotherNature(Game game) {
    return "Mother Nature is on island number "
        + game.getMainBoard().getMotherNature().getPosition() + "\n";
  }

  /**
   * It builds a string with the current information about an island
   *
   * @param game     game the actual match
   * @param islandId the id of the island chosen
   * @return the created string
   */
  private String infoIsland(Game game, int islandId) {
    if (islandId < game.getMainBoard().getIslands().size()) {
      Island infoIsland = game.getMainBoard().getIslands().elementAt(islandId);
      StringBuilder returnString = new StringBuilder(
          "Island number " + (islandId + 1) + " has:\n");

      // Towers and owner
      returnString.append(infoIsland.getNumberOfTowers()).append(" of team: ");
      returnString.append((infoIsland.getOwnerId() == -1) ? "none" : infoIsland.getOwnerId());
      returnString.append("\n");

      // Students
      for (Color color : Color.values()) {
        returnString.append(infoIsland.getStudents()[color.ordinal()]).append(" ")
            .append(color).append(" students;\n");
      }

      return returnString.toString();

    } else {
      return "Error, the island does not exists";
    }
  }

  /**
   * It builds a string with the current information about a player
   *
   * @param game     game the actual match
   * @param playerId the id of the chosen player
   * @return the created string
   */
  private String infoPlayer(Game game, int playerId) {
    Player infoPlayer = null;

    for (Team team : game.getTeams()) {
      for (Player player : team.getPlayers()) {
        if (player.getPlayerId() == playerId) {
          infoPlayer = player;
        }
      }
    }

    if (infoPlayer != null) {

      StringBuilder returnString = new StringBuilder(
          "PLAYER " + infoPlayer.getPlayerNickname() + ":\n");

      returnString.append("COINS: ").append(infoPlayer.getCoins()).append("\n");

      returnString.append("CURRENT ASSISTANT: ");
      returnString.append(
          (infoPlayer.getAssistant() == null) ? "NONE" : infoPlayer.getAssistant().toString());
      returnString.append("\n");

      PlayerBoard infoPB = infoPlayer.getPlayerBoard();
      returnString.append("ENTRANCE: ");

      for (Color color : Color.values()) {
        returnString.append(infoPB.getEntrance().getStudents()[color.ordinal()]).append(" ")
            .append(color).append(" students;\n");
      }

      returnString.append("DINING ROOM: ");

      for (Color color : Color.values()) {
        returnString.append(infoPB.getDiningRoom().getStudents()[color.ordinal()]).append(" ")
            .append(color).append(" students;\n");
      }

      return returnString.toString();
    } else {
      return "Error, the player does not exists";
    }

  }

  /**
   * It builds a string with the current information about a character
   *
   * @param game        game the actual match
   * @param characterId the id of the chosen character
   * @return the created string
   */
  private String infoCharacters(Game game, int characterId) {
    if (characterId < game.getPurchasableCharacter().size()) {
      CharacterCard infoCard = game.getPurchasableCharacter().elementAt(characterId);
      return infoCard.getCardEffect().getStringEffectCard();
    } else {
      return "Error, the character does not exists";
    }
  }

  /**
   * It builds a string with the current information about an assistant
   *
   * @param game     game the actual match
   * @param playerId the id of the chosen assistant
   * @return the created string
   */
  private String infoAssistants(Game game, int playerId) {

    Player infoPlayer = null;
    StringBuilder returnString = new StringBuilder("You can play assistants n:");

    for (Team team : game.getTeams()) {
      for (Player player : team.getPlayers()) {
        if (player.getPlayerId() == playerId) {
          infoPlayer = player;
        }
      }
    }

    if (infoPlayer != null) {
      for (Assistant assistant : infoPlayer.getPlayableAssistant()) {
        returnString.append(" ").append(assistant.getAssistantId());
      }
      return returnString.toString();
    } else {
      return "Error, the player does not exists";
    }
  }

  /**
   * It builds a string with the current information about a cloud tile
   *
   * @param game           game the actual match
   * @param cloudTileIndex the id of the chosen cloud tile
   * @return the created string
   */
  private String infoCloudTile(Game game, int cloudTileIndex) {
    if (cloudTileIndex < game.getCloudTiles().size()) {
      CloudTile infoCT = game.getCloudTiles().elementAt(cloudTileIndex);
      StringBuilder returnString = new StringBuilder(
          "Cloud tile number " + (cloudTileIndex + 1) + " has:\n");

      for (Color color : Color.values()) {
        returnString.append(infoCT.getStudents()[color.ordinal()]).append(" ")
            .append(color).append(" students;\n");
      }
      return returnString.toString();
    } else {
      return "Error, the cloud tile does not exists";
    }
  }
}
