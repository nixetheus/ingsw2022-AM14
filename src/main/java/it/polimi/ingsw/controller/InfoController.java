package it.polimi.ingsw.controller;

import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.messages.InfoRequestMessage;
import it.polimi.ingsw.messages.InfoResponseMessage;
import it.polimi.ingsw.model.CloudTile;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerBoard;

public class InfoController {

  /**
   *
   */
  public void elaborateMessage(InfoRequestMessage msg, Game game) {

    String response;
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
      InfoResponseMessage responseMessage = new InfoResponseMessage(
          MessageSecondary.INFO_RESPONSE_MESSAGE);
      responseMessage.setResponse(response);
    }
  }

  private String infoHelp() {
    return "TODO";  // TODO
  }

  private String infoMotherNature(Game game) {
    return "Mother Nature is on island number "
        + game.getMainBoard().getMotherNature().getPosition() + "\n";
  }

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

  private String infoCharacters(Game game, int characterId) {
    if (characterId < game.getPurchasableCharacter().size()) {
      CharacterCard infoCard = game.getPurchasableCharacter().elementAt(characterId);
      return "Character number " + characterId + " costs " + infoCard.getCost() + " coins";
    } else {
      return "Error, the character does not exists";
    }
  }

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
