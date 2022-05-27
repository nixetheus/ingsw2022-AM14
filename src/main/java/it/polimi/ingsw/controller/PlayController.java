package it.polimi.ingsw.controller;

import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.PlayMessage;
import it.polimi.ingsw.messages.PlayMessageResponse;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.characters.CharacterStruct;
import it.polimi.ingsw.model.player.Player;
import java.util.Vector;

public class PlayController {

  /**
   *
   */
  public Message elaborateMessage(PlayMessage msg, Game game) {
    switch (msg.getMessageSecondary()) {
      case ASSISTANT:
        return playAssistant(msg, game);
      case CHARACTER:
        return playCharacter(msg, game);
      default:
        return null;
    }
  }

  /**
   * @param msg
   * @param game
   * @return
   */
  private Message playAssistant(PlayMessage msg, Game game) {

    PlayMessageResponse playResponse = new PlayMessageResponse(MessageSecondary.ASSISTANT);
    playResponse.setPlayerId(-1);

    if (canPlayAssistant(msg.getPlayerId(), msg.getAssistantId(), game.getTeams())) {
      for (Team team : game.getTeams()) {
        for (Player player : team.getPlayers()) {
          if (player.getPlayerId() == msg.getPlayerId()) {
            game.playAssistant(player, msg.getAssistantId());

            playResponse.setActivePlayerId(game.getCurrentPlayer().getPlayerId());
            playResponse.setAssistantId(player.getAssistant().getAssistantId());

          }

        }
      }
    }
    return playResponse;
  }

  /**
   * @param msg
   * @param game
   * @return
   */
  private Message playCharacter(PlayMessage msg, Game game) {

    CharacterStruct characterParameters = new CharacterStruct();
    CharacterCard characterCard = game.getPurchasableCharacter().elementAt(msg.getCharacterId());

    characterParameters.currentGame = game;
    characterParameters.color = msg.getColor();
    characterParameters.teams = game.getTeams();
    characterParameters.numIsland = msg.getNumIsland();
    characterParameters.mainBoard = game.getMainBoard();
    characterParameters.studentsBag = game.getStudentsBag();
    characterParameters.motherNatureMoves = msg.getMotherNatureMoves();

    characterParameters.studentsCard = msg.getStudentsCard();
    characterParameters.studentsEntrance = msg.getStudentsEntrance();
    characterParameters.studentsDiningRoom = msg.getStudentsDiningRoom();

    if (canPlayCharacter(msg.getPlayerId(), characterCard.getCost(), game.getTeams())) {
      for (Team team : game.getTeams()) {
        for (Player player : team.getPlayers()) {
          if (player.getPlayerId() == msg.getPlayerId()) {

            characterParameters.currentPlayer = player;
            game.playCharacter(player, msg.getCharacterId());
            characterCard.applyEffect(characterParameters);

            PlayMessageResponse playResponse = new PlayMessageResponse(MessageSecondary.CHARACTER);
            playResponse.setCharacterId(msg.getCharacterId());

            /*
            return "Character played correctly!\n"
                + "You now have " + player.getCoins() + " remaining coins.\n Please wait...";
            */
          }
        }
      }
    }
    return null;
  }

  /**
   * @param playerId
   * @param assistantId
   * @param teams
   * @return
   */
  private boolean canPlayAssistant(int playerId, int assistantId, Vector<Team> teams) {
    for (Team team : teams) {
      for (Player player : team.getPlayers()) {
        if (player.getPlayerId() == playerId) {
          return player.getPlayableAssistant().stream()
              .anyMatch(assistant -> assistant.getAssistantId() == assistantId);
        }
      }
    }
    return false;
  }

  /**
   * @param playerId
   * @param characterCost
   * @param teams
   * @return
   */
  private boolean canPlayCharacter(int playerId, int characterCost, Vector<Team> teams) {
    for (Team team : teams) {
      for (Player player : team.getPlayers()) {
        if (player.getPlayerId() == playerId) {
          return player.getCoins() >= characterCost;
        }
      }
    }
    return false;
  }
}
