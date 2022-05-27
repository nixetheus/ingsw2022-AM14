package it.polimi.ingsw.view;

import com.google.gson.Gson;
import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.messages.InfoRequestMessage;
import it.polimi.ingsw.messages.LoginMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MoveMessage;
import it.polimi.ingsw.messages.PlayMessage;
import java.util.Locale;

/**
 * MessageParser class used to take a string from user and parse it into a message
 */
public class MessageParser {

  private int playerId;

  /**
   * Constructor method for message parser class
   */
  public MessageParser() {
  }

  /**
   * This methode parse a message into a json string
   *
   * @param str User input string
   * @return The json string to be sent to the server
   */
  public String parser(String str) {
    Gson gson = new Gson();

    Message message = messageCreator(str);

    if (message != null) {

      if (message.getMessageSecondary() != MessageSecondary.PLAYER_PARAMS) {
        message.setPlayerId(playerId);
      }

      return gson.toJson(message);
    } else {
      return null;
    }
  }

  /**
   * This method creates the message
   *
   * @param str User input string
   * @return The constructed message
   */
  public Message messageCreator(String str) {
    str = str.toUpperCase(Locale.ROOT);
    MessageMain messageMain = findMessageMain(str);
    MessageSecondary messageSecondary = findMessageSecondary(str);

    if (messageMain != null) {
      switch (messageMain) {
        case INFO:
          InfoRequestMessage infoRequestMessage = new InfoRequestMessage(messageSecondary);
          infoRequestMessage.setObjectId(findIndex(str));
          return infoRequestMessage;

        case PLAY:
          PlayMessage playMessage = new PlayMessage(messageSecondary);
          String firstCommand = str.split(",")[0];
          playMessage.setAssistantId(findIndex(firstCommand));
          playMessage.setCharacterId(findIndex(firstCommand));

          //some character would be purchased just like "purchase 3 character" without any other effect
          if (str.contains(",")) {
            String secondCommand = str.split(",")[1];
            messageSetterForCharacter(playMessage, secondCommand);
          }

          return playMessage;

        case MOVE:
          MoveMessage moveMessage = new MoveMessage(messageSecondary);
          moveMessage.setCloudTileNumber(findIndex(str));
          moveMessage.setIslandNumber(findIndex(str));

          if (str.contains("STUDENT")) {
            moveMessage.setStudentColor(findColor(str).ordinal());
          }

          moveMessage.setPlace(findPlace(str));
          return moveMessage;

        case LOGIN:
          LoginMessage loginMessage = new LoginMessage(messageSecondary);
          loginMessage.setNickName(str);//ignored if not message secondary==player params
          loginMessage.setNumberOfPlayer(findIndex(str));
          loginMessage.setGameExpert(findMode(str));
          return loginMessage;
      }
    }

    return null;
  }

  private void messageSetterForCharacter(PlayMessage playMessage, String str) {
    String takeFromCard;
    String replaceWith;

    if (str.contains("NO ENTRY TILE")) {
      playMessage.setNumIsland(findIndex(str));
    }

    if (str.contains("MOTHER NATURE")) {
      playMessage.setMotherNatureMoves(findIndex(str));
    }

    if (str.contains("ISLAND")) {
      playMessage.setNumIsland(findIndex(str));
    }

    if (str.contains("STUDENT") || str.contains("INFLUENCE")) {
      playMessage.setColor(findColor(str));
    }

    if (str.contains("STUDENT")) {
      takeFromCard = str.split("REPLACE")[0];

      if (takeFromCard.contains("TO ENTRANCE") || takeFromCard.contains("FROM ENTRANCE")) {
        playMessage.setStudentsEntrance(findArrays(takeFromCard));
      } else if (takeFromCard.contains("TO DINING ROOM") || takeFromCard
          .contains("FROM DINING ROOM")) {
        playMessage.setStudentsDiningRoom(findArrays(takeFromCard));
      } else if (takeFromCard.contains("CARD")) {
        playMessage.setStudentsCard(findArrays(takeFromCard));
      }

      if (str.contains("REPLACE")) {
        //the order of the action does not matter
        replaceWith = str.split("REPLACE")[1];
        if (replaceWith.contains("TO DINING ROOM") || replaceWith.contains("FROM DINING ROOM")) {
          playMessage.setStudentsDiningRoom(findArrays(replaceWith));
        } else if (replaceWith.contains("TO ENTRANCE") || replaceWith.contains("FROM ENTRANCE")) {
          playMessage.setStudentsEntrance(findArrays(replaceWith));
        } else if (replaceWith.contains("CARD")) {
          playMessage.setStudentsCard(findArrays(replaceWith));
        }
      }

    }

  }

  /**
   * This method finds the hidden messageMain into the input string
   *
   * @param str User input string
   * @return The MessageMain into the input string
   */
  private MessageMain findMessageMain(String str) {
    if ((str.contains("PLAY") && !str.contains("PLAYER")) || str.contains("PURCHASE")) {
      return MessageMain.PLAY;
    } else if ((str.contains("MOVE") || str.contains("TAKE"))) {
      return MessageMain.MOVE;
    } else if ((str.contains("INFO") || str.contains("HELP"))) {
      return MessageMain.INFO;
    } else if ((!(str.contains(" ")) || str.contains("MODE")) && str.length() != 0) {
      return MessageMain.LOGIN;
    }
    return null;
  }

  /**
   * This method finds the hidden messageSecondary into the input string
   *
   * @param str User input string
   * @return The MessageSecondary into the input string
   */
  private MessageSecondary findMessageSecondary(String str) {

    if ((str.contains("CHARACTER"))) {
      if (str.contains("INFO")) {
        return MessageSecondary.INFO_CHARACTER;
      }
      return MessageSecondary.CHARACTER;
    } else if ((str.contains("ASSISTANT"))) {
      if (str.contains("INFO")) {
        return MessageSecondary.INFO_ASSISTANTS;
      }
      return MessageSecondary.ASSISTANT;
    } else if ((str.contains("MOTHER NATURE"))) {
      if (str.contains("INFO")) {
        return MessageSecondary.INFO_MN;
      }
      return MessageSecondary.MOTHER_NATURE;
    } else if ((str.contains("CLOUD TILE"))) {
      if (str.contains("INFO")) {
        return MessageSecondary.INFO_CLOUD_TILE;
      }
      return MessageSecondary.CLOUD_TILE;
    } else if (str.contains("PLAYER")) {
      if (str.contains("INFO")) {
        return MessageSecondary.INFO_PLAYER;
      }
    } else if (str.contains("ISLAND")) {
      if (str.contains("INFO")) {
        return MessageSecondary.INFO_ISLAND;
      }
      return MessageSecondary.ENTRANCE;
    } else if (str.contains("HELP")) {
      return MessageSecondary.INFO_HELP;
    } else if (str.contains("FROM ENTRANCE")) {
      return MessageSecondary.ENTRANCE;
    } else if (!str.contains(" ")) {
      return MessageSecondary.PLAYER_PARAMS;
    }
    if (str.contains("MODE")) {
      return MessageSecondary.GAME_PARAMS;
    }
    return null;
//TODO info respond message
  }

  /**
   * This method finds the hidden color into the input string
   *
   * @param str User input string
   * @return The color into the string
   */
  private Color findColor(String str) {
    if ((str.contains("YELLOW"))) {
      return Color.YELLOW;
    } else if ((str.contains("BLUE"))) {
      return Color.BLUE;
    } else if ((str.contains("GREEN"))) {
      return Color.GREEN;
    } else if ((str.contains("RED"))) {
      return Color.RED;
    } else if ((str.contains("PURPLE"))) {
      return Color.PURPLE;
    } else {
      return null;
    }
  }

  /**
   * This method finds the hidden PLace into the input string
   *
   * @param str User input string
   * @return The correct place index
   */
  private int findPlace(String str) {
    if (str.contains("TO DINING ROOM")) {
      return 0;
    } else if (str.contains("TO ISLAND")) {
      return 1;
    } else {
      return -1;
    }
  }

  /**
   * This method finds the hidden index into the input string
   *
   * @param str User input string
   * @return The correct number written
   */
  private int findIndex(String str) {
    if (str.contains("11")) {
      return 11;
    }
    if (str.contains("10")) {
      return 10;
    }
    if (str.contains("9")) {
      return 9;
    }
    if (str.contains("8")) {
      return 8;
    }
    if (str.contains("7")) {
      return 7;
    }
    if (str.contains("6")) {
      return 6;
    }
    if (str.contains("5")) {
      return 5;
    }
    if (str.contains("4")) {
      return 4;
    }
    if (str.contains("3")) {
      return 3;
    }
    if (str.contains("2")) {
      return 2;
    }
    if (str.contains("1")) {
      return 1;
    }
    if (str.contains("0")) {
      return 0;
    } else {
      return -1;
    }

  }

  /**
   * This method finds the hidden mode into the input string
   *
   * @param str User input string
   * @return The boolean for the chosen mode
   */
  private Boolean findMode(String str) {
    if (str.contains("NOT EXPERT")) {
      return false;
    } else {
      return str.contains("EXPERT");
    }
  }

  private int[] findArrays(String str) {

    int[] returnedArray = new int[Constants.getNColors()];

    for (String studentColor : str.split("STUDENT")) {
      int quantity = findIndex(studentColor);
      if (findColor(studentColor) != null) {
        returnedArray[findColor(studentColor).ordinal()] =
            returnedArray[findColor(studentColor).ordinal()] + quantity;
      }
    }
    return returnedArray;
  }

  public int getPlayerId() {
    return playerId;
  }

  public void setPlayerId(int playerId) {
    this.playerId = playerId;
  }
}

