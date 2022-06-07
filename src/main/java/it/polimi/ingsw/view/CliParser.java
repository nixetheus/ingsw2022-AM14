package it.polimi.ingsw.view;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.Effects;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.helpers.StudentsPlayerId;
import it.polimi.ingsw.messages.BeginTurnMessage;
import it.polimi.ingsw.messages.ClientResponse;
import it.polimi.ingsw.messages.LoginMessageResponse;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MoveMessageResponse;
import it.polimi.ingsw.messages.PlayMessageResponse;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class CliParser {

  private int playerId;

  public CliParser() {
  }

  public String fromJson(String jsonMessage) throws FileNotFoundException {
    Gson gson = new Gson();
    if (jsonMessage.contains("\"messageMain\":\"LOGIN\"")) {
      return elaborateMessage(gson.fromJson(jsonMessage, LoginMessageResponse.class));
    }
    if (jsonMessage.contains("\"messageMain\":\"MOVE\"")) {
      return elaborateMessage(gson.fromJson(jsonMessage, MoveMessageResponse.class));
    }
    if (jsonMessage.contains("\"messageMain\":\"PLAY\"")) {
      return elaborateMessage(gson.fromJson(jsonMessage, PlayMessageResponse.class));
    }
    if (jsonMessage.contains("\"messageMain\":\"INFO\"")) {
      return elaborateMessage(gson.fromJson(jsonMessage, ClientResponse.class));
    }
    if (jsonMessage.contains("\"messageMain\":\"PHASE\"")) {
      return elaborateMessage(gson.fromJson(jsonMessage, BeginTurnMessage.class));
    }
    return null;
  }

  private String elaborateMessage(Message msg) throws FileNotFoundException {
    switch (msg.getMessageMain()) {
      case LOGIN:
        return printLoginMessage((LoginMessageResponse) msg);
      case MOVE:
        return printMoveMessage((MoveMessageResponse) msg);
      case PLAY:
        return printPlayMessage((PlayMessageResponse) msg);
      case INFO:
        return printInfoMessage((ClientResponse) msg);
      case PHASE:
        return printPhaseMessage((BeginTurnMessage) msg);
    }
    return null;
  }

  private String printLoginMessage(LoginMessageResponse msg) {
    if (msg.getMessageSecondary() == MessageSecondary.LOBBY) {
      setPlayerId(msg.getPlayerId());
    }
    StringBuilder returnString = new StringBuilder();
    returnString.append(msg.getResponse());
    if (msg.getPlayerId() == 0 && msg.getMessageSecondary() == MessageSecondary.ASK_GAME_PARAMS) {
      returnString.append("Now you have to choose the game mode and number of players");
    }
    if (msg.getMessageSecondary() == MessageSecondary.LOBBY) {
      returnString.append("welcome into the Eriantys lobby");
    }
    return String.valueOf(returnString);
  }

  public int getPlayerId() {
    return playerId;
  }

  public void setPlayerId(int playerId) {
    this.playerId = playerId;
  }

  private String printPlayMessage(PlayMessageResponse msg) throws FileNotFoundException {
    StringBuilder returnString = new StringBuilder();
    switch (msg.getMessageSecondary()) {
      case ASK_ASSISTANT:
        Gson gson = new Gson();
        JsonArray list = gson
            .fromJson(new FileReader(
                    "src/main/resources/json/assistants.json"),
                JsonArray.class);
        JsonObject object = list.get(msg.getAssistantId()).getAsJsonObject();
        int moves = object.get("MOVES").getAsInt();
        int speed = object.get("SPEED").getAsInt();

        if (this.playerId == msg.getPreviousPlayerId()) {
          returnString
              .append("Assistant played correctly!\n" + "You can now move mother nature of ")
              .append(moves).append(" spaces when your turn comes.\n").append("You're speed is ")
              .append(speed).append(" ouf of 10.\n").append("Please wait...").append("\n")
              .append("\n");
        } else {
          returnString.append("another player has player an assistant now he/she can do")
              .append(" ")
              .append(moves).append(" ").append("moves with a speed of ").append(speed).append("\n")
              .append("\n");
        }
        break;
      case CHARACTER:
        returnString.append("Character ").append(msg.getCharacterId())
            .append(" purchased and correctly used\n");

    }
    return String.valueOf(returnString);
  }

  private String printMoveMessage(MoveMessageResponse msg) {

    StringBuilder printedString = new StringBuilder();

    switch (msg.getMessageSecondary()) {
      case MOVE_STUDENT_ENTRANCE:
        printedString.append("now your entrance contains").append("\n");
        for (Color color : Color.values()) {
          printedString.append(msg.getStudentsEntrance()[color.ordinal()]).append(" ")
              .append(color).append(" students;\n");
        }
        if (msg.getPlace() == 0) {
          printedString.append("now your dining room contains\n");
          for (Color color : Color.values()) {
            printedString.append(msg.getStudentsDiningRoom()[color.ordinal()]).append(" ")
                .append(color).append(" students;\n");
          }
        } else if (msg.getPlace() == 1) {
          printedString.append("now the island").append(msg.getIslandNumber()).append("contains");
          for (Color color : Color.values()) {
            printedString.append(msg.getStudentsIsland()[color.ordinal()]).append(" ")
                .append(color).append(" students;\n");
          }
        }
        break;
      case CLOUD_TILE:
        printedString.append("cloud tile number ").append(msg.getCloudTileNumber()).append("\n")
            .append("successfully taken").append("\n")
            .append("These student are added to your entrance :").append("\n");
        for (Color color : Color.values()) {
          printedString.append(msg.getStudentsCloud()[color.ordinal()])
              .append(color).append(" students;\n");
        }
        printedString.append("\n");
        break;
      case MOVE_MN:
        printedString.append("now mother nature is on the island number ")
            .append(msg.getIslandNumber()).append(" island");
        break;
    }
    return String.valueOf(printedString);
  }

  private String printInfoMessage(ClientResponse msg) {
    switch (msg.getMessageSecondary()) {
      case ASK_ASSISTANT://you have to play assistant
        return msg.getResponse();
      case GAME_ORDER:
        return "You have to play as " + (msg.getPlayerOrderId().indexOf(this.playerId) + 1)
            + " player";
      case ASK_STUDENT_ENTRANCE://you have to move students from entrance
        return msg.getResponse();
      case ASK_MN:
        return msg.getResponse();
      case ASK_CLOUD:
        return msg.getResponse();
      case CLIENT_DISCONNECT:
        return msg.getResponse();
    }

    return null;
  }

  /**
   * This method creates the string to be printed in case of a turn message
   *
   * @param msg the input message to decode and print
   * @return the string to be printed
   */
  private String printPhaseMessage(BeginTurnMessage msg) throws FileNotFoundException {
    StringBuilder returnString = new StringBuilder();
    switch (msg.getMessageSecondary()) {
      case INIT_GAME:
        return printUpdate(returnString, msg);

      case CHANGE_TURN:
        //TODO add to controller
        returnString.append(printUpdate(returnString, msg));

      case INFRA_TURN:
        return printUpdate(returnString, msg);
    }
    return null;
  }

  /**
   * This method prints the actual state of the game
   *
   * @param returnString The stringBuilder to build the output
   * @param msg          The message that contains all the information
   * @return The value of the string builder
   */
  private String printUpdate(StringBuilder returnString, BeginTurnMessage msg)
      throws FileNotFoundException {
    returnString.append("The game has now started you are the player number ")
        .append(this.playerId).append("\n");

    //islands and mother nature
    returnString.append("The island are as follows").append("\n");
    for (int[] students : msg.getStudentsIsland()) {
      returnString.append("Island number ").append(msg.getStudentsIsland().indexOf(students))
          .append(": \n");
      for (Color color : Color.values()) {
        returnString.append(students[color.ordinal()]).append(" ")
            .append(color).append(" students;\n");
      }
      if (msg.getTowersIsland().contains(msg.getStudentsIsland().indexOf(students))) {
        returnString
            .append("this island contains a tower \n");//TODO contains a tower if the array is 0
        //TODO add who controls the island
      }
      if (msg.getMotherNaturePosition() == msg.getStudentsIsland().indexOf(students)) {
        returnString.append("mother nature is here");
      } else {
        returnString.append("mother nature is NOT here");
      }
      returnString.append("\n").append("\n");
    }

    //cloudTiles
    returnString.append("the cloud tiles are as follows").append("\n");
    for (int[] studentsCloud : msg.getStudentsCloudTiles()) {
      returnString.append("Cloud tile number ")
          .append(msg.getStudentsCloudTiles().indexOf(studentsCloud))
          .append(": \n");
      for (Color color : Color.values()) {
        returnString.append(studentsCloud[color.ordinal()]).append(" ")
            .append(color).append(" students;\n");
      }
      returnString.append("\n");
    }
    returnString.append("\n").append("\n");

    //entrances
    returnString.append("your and opponents entrance are as follows").append("\n");
    for (StudentsPlayerId entranceIdPlayer : msg.getStudentEntrance()) {
      if (entranceIdPlayer.getPlayerId() == this.playerId) {
        returnString.append("your entrance is:").append("\n");
      } else {
        returnString.append("an opponent entrance is").append("\n");
      }
      for (Color color : Color.values()) {
        returnString.append(entranceIdPlayer.getStudents()[color.ordinal()]).append(" ")
            .append(color).append(" students;\n");
      }
      returnString.append("\n").append("\n");
    }

    //dining rooms
    returnString.append("your and opponents dining room are as follows").append("\n");

    for (StudentsPlayerId diningRoomIdPlayer : msg.getStudentDiningRoom()) {
      if (diningRoomIdPlayer.getPlayerId() == this.playerId) {
        returnString.append("your diningRoom is:").append("\n");
      } else {
        returnString.append("an opponent diningRoom is").append("\n");
      }
      for (Color color : Color.values()) {
        returnString.append(diningRoomIdPlayer.getStudents()[color.ordinal()]).append(" ")
            .append(color).append(" students;\n");
      }
      returnString.append("\n");
    }



    //professors
    returnString.append("You own the professor:").append("\n");
    int[] myProfessor = msg.getProfessors().get(this.playerId);
    for (Color color : Color.values()) {
      if (myProfessor[color.ordinal()] == 1) {
        returnString.append(myProfessor[color.ordinal()]).append(" ")
            .append(color).append(" professor;\n");
      }
    }
    returnString.append("\n");

    //playable assistants
    returnString.append("these are the assistant tou can play :").append("\n");
    Gson gson = new Gson();
    JsonArray list = gson
        .fromJson(new FileReader("src/main/resources/json/assistants.json"), JsonArray.class);
    for (Integer idAssistant : msg.getPlayableAssistantId()) {

      JsonObject object = list.get(idAssistant).getAsJsonObject();
      int speed = object.get("SPEED").getAsInt();
      int moves = object.get("MOVES").getAsInt();

      returnString.append("assistant speed is").append(" ").append(speed).append("\n")
          .append("max mother nature moves").append(" ").append(moves).append("\n")
          .append("\n");
    }

    //purchasable character
    returnString.append("those are the assistant you can purchase:").append("\n").append("\n");
    for(int id: msg.getPurchasableCharacterId()){
      for(Effects effect:Effects.values()){
        if(id==effect.ordinal()){
          returnString.append(effect.getStringEffectCard()).append("\n");
          returnString.append("The cost of this character is ").append(msg.getCharactersCosts()[msg.getPurchasableCharacterId().indexOf(id)]).append("\n");
          if(effect.getNOfStudents()!=0){
            returnString.append("it has the following students on it").append("\n");
            for (Color color : Color.values()) {
              returnString.append(msg.getCharactersStudents().get(msg.getPurchasableCharacterId().indexOf(id))[color.ordinal()]).append(" ")
                  .append(color).append(" students;\n");
            }
          }
        }
      }
      returnString.append("to purchase use id ").append(id).append("\n").append("\n");
    }

    //coins
    returnString.append("You have ").append(msg.getPlayerCoins()[playerId]).append(" coins")
        .append("\n").append("\n");

    return returnString.toString();
  }
}

