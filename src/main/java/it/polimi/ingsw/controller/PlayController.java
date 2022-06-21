package it.polimi.ingsw.controller;

import static it.polimi.ingsw.helpers.CharactersFlags.COLOR_FLAG;
import static it.polimi.ingsw.helpers.CharactersFlags.MOTHER_NATURE_MOVES_FLAG;
import static it.polimi.ingsw.helpers.CharactersFlags.NUM_ISLAND_FLAG;
import static it.polimi.ingsw.helpers.CharactersFlags.STUDENTS_CARD_FLAG;
import static it.polimi.ingsw.helpers.CharactersFlags.STUDENTS_DINING_ROOM_FLAG;
import static it.polimi.ingsw.helpers.CharactersFlags.STUDENTS_ENTRANCE_FLAG;

import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.messages.ClientResponse;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.PlayMessage;
import it.polimi.ingsw.messages.PlayMessageResponse;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.characters.CharacterStruct;
import it.polimi.ingsw.model.player.Player;
import java.util.Arrays;
import java.util.Vector;

/**
 * PLayController class elaborate play messages
 */
public class PlayController {

  /**
   * Constructor method for PlayController class
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
   * @param msg  The userInput message
   * @param game the current game
   * @return The response message created
   */
  private Message playAssistant(PlayMessage msg, Game game) {

    PlayMessageResponse playResponse = null;

    if (canPlayAssistant(msg.getPlayerId(), msg.getAssistantId(), game.getTeams())) {

      playResponse = new PlayMessageResponse(MessageSecondary.ASK_ASSISTANT);
      playResponse.setPlayerId(-1);

      for (Team team : game.getTeams()) {
        for (Player player : team.getPlayers()) {

          if (player.getPlayerId() == msg.getPlayerId()) {

            game.playAssistant(player, msg.getAssistantId());

            // playResponse.setActivePlayerId(msg.getPlayerId() + 1);  // TODO
            playResponse.setPreviousPlayerId(msg.getPlayerId());
            playResponse.setAssistantId(msg.getAssistantId());

          }

        }
      }
    }
    return playResponse;
  }

  /**
   * @param msg  The userInput message
   * @param game the current game
   * @return The response message created
   */
  private Message playCharacter(PlayMessage msg, Game game) {

    CharacterStruct characterParameters = new CharacterStruct();
    CharacterCard characterCard = game.getPurchasableCharacter().elementAt(msg.getCharacterId());

    // Translate GUI message
    if (msg.getStudentsCardGUI() != null) {
      msg.setStudentsCard(fromPositionsToColors(msg.getStudentsCardGUI(),
          characterCard.getStudents()));
      if (Arrays.stream(msg.getStudentsCard()).sum() == 1) {
        for (Color color : Color.values()) {
          if (msg.getStudentsCard()[color.ordinal()] == 1) {
            msg.setColor(color);
          }
        }
      }
    }

    if (msg.getStudentsEntranceGUI() != null) {
      msg.setStudentsEntrance(fromPositionsToColors(msg.getStudentsEntranceGUI(),
          game.getCurrentPlayer().getPlayerBoard().getEntrance().getStudents()));
    }

    //TODO broken
    if (msg.getMotherNatureMoves() < 0 && msg.getNumIsland() >= 0) {

      int motherNatureMoves = 0;
      int nOfIslands = game.getMainBoard().getIslands().size();
      int islandCurrentMN = -1;
      int messageIsland = -1;

      for (Island island : game.getMainBoard().getIslands()) {
        if (island.getIslandId() == game.getMainBoard().getMotherNature().getPosition()) {
          islandCurrentMN = game.getMainBoard().getIslands().indexOf(island);
        }
        if (island.getIslandId() == msg.getNumIsland()) {
          messageIsland = game.getMainBoard().getIslands().indexOf(island);
        }
      }
      while (true) {
        if ((islandCurrentMN + motherNatureMoves) % nOfIslands == messageIsland) {
          break;
        } else {
          motherNatureMoves++;
        }
      }
      msg.setMotherNatureMoves(motherNatureMoves);
    }

    // Some objects are missing for the character
    if (!hasAllCharacterObjects(characterCard, msg)) {
      ClientResponse missingParams = new ClientResponse(MessageSecondary.INFO_CHARACTER);
      missingParams.setPlayerId(msg.getPlayerId());
      missingParams.setResponse("This character is missing some objects! Try again!");
      return missingParams;
    }

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
            playResponse.setPlayerId(game.getCurrentPlayer().getPlayerId());

            return playResponse;
          }
        }
      }
    }
    return null;
  }

  /**
   * @param playerId    the player that want to play the assistant
   * @param assistantId The assistant that a player want to play
   * @param teams       The teams in witch you will search for the player
   * @return true if the player can play that assistant
   */
  private boolean canPlayAssistant(int playerId, int assistantId, Vector<Team> teams) {

    boolean canPlay = true;
    for (Team team : teams) {
      for (Player player : team.getPlayers()) {

        // IS IN PLAYER'S ASSISTANTS
        if (player.getPlayerId() == playerId) {
          canPlay = canPlay && player.getPlayableAssistant().stream()
              .anyMatch(assistant -> assistant.getAssistantId() == assistantId);
        }
        // HAS IT BEEN ALREADY PLAYED BY ANOTHER PLAYER
        else {
          if (player.getAssistant() != null) {
            canPlay = canPlay && (player.getAssistant().getAssistantId() != assistantId);
          }
        }
      }
    }
    return canPlay;
  }

  /**
   * @param playerId      The player that want to play the Character
   * @param characterCost The cost of the character
   * @param teams         The teams in witch you will search for the player
   * @return true if the player can purchase the character
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

  /**
   * This method control if the client selected everything is needed to play a certain character
   *
   * @param character The character that the player wants to play
   * @param msg       The message arrived from the client
   * @return True if all che parameters are present
   */
  private boolean hasAllCharacterObjects(CharacterCard character, PlayMessage msg) {

    boolean hasObjects = true;

    int flags = character.getCardEffect().getObjectsFlags();
    if ((flags & (1 << NUM_ISLAND_FLAG.ordinal())) > 0 && (msg.getNumIsland() == -1) ||
        (flags & (1 << MOTHER_NATURE_MOVES_FLAG.ordinal())) > 0 && (msg.getMotherNatureMoves()
            == -1) ||
        (flags & (1 << COLOR_FLAG.ordinal())) > 0 && (msg.getColor() == null) ||
        (flags & (1 << STUDENTS_CARD_FLAG.ordinal())) > 0 && (msg.getStudentsCard() == null) ||
        (flags & (1 << STUDENTS_ENTRANCE_FLAG.ordinal())) > 0 && (msg.getStudentsEntrance() == null)
        ||
        (flags & (1 << STUDENTS_DINING_ROOM_FLAG.ordinal())) > 0 && (msg.getStudentsDiningRoom()
            == null)) {
      hasObjects = false;
    }
    return hasObjects;
  }

  /**
   * This method change the position of the students into an array of colors
   *
   * @param positions The array of the position
   * @param students  The color of the students
   * @return The array with the colors filled
   */
  private int[] fromPositionsToColors(int[] positions, int[] students) {

    int[] colors = new int[5];

    for (int position : positions) {
      if (position >= 0) {

        int studentIndex = 0;
        for (int color = 0; color < students.length; color++) {
          for (int nOfColor = 0; nOfColor < students[color]; nOfColor++) {
            if (position == studentIndex) {
              colors[color]++;
            }
            studentIndex++;
          }
        }

      }
    }

    return colors;
  }
}
