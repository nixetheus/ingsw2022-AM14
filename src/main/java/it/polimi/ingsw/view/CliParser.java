package it.polimi.ingsw.view;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.messages.ClientResponse;
import it.polimi.ingsw.messages.LoginMessageResponse;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MoveMessageResponse;
import it.polimi.ingsw.messages.PhaseMessageResponse;
import it.polimi.ingsw.messages.PlayMessageResponse;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class CliParser {

  private int playerId;

  public CliParser() {
  }

  public String fromJson(String jsonMessage) throws FileNotFoundException {
    Gson gson = new Gson();
    if (jsonMessage.contains("LOGIN")) {
      return elaborateMessage(gson.fromJson(jsonMessage, LoginMessageResponse.class));
    }
    if (jsonMessage.contains("MOVE")) {
      return elaborateMessage(gson.fromJson(jsonMessage, MoveMessageResponse.class));
    }
    if (jsonMessage.contains("PLAY")) {
      return elaborateMessage(gson.fromJson(jsonMessage, PhaseMessageResponse.class));
    }
    if (jsonMessage.contains("INFO")) {
      return elaborateMessage(gson.fromJson(jsonMessage, ClientResponse.class));
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
        return printPhaseMessage((PhaseMessageResponse) msg);
    }
    return null;
  }

  private String printLoginMessage(LoginMessageResponse msg) {
    this.playerId = msg.getPlayerId();
    StringBuilder returnString = new StringBuilder();
    returnString.append(msg.getResponse());
    if (msg.getPlayerId() == 0 && msg.getMessageSecondary() == MessageSecondary.PLAYER_PARAMS) {
      returnString.append("Now you have to choose the game mode and number of players");
    }
    return String.valueOf(returnString);
  }

  public int getPlayerId() {
    return playerId;
  }

  private String printPlayMessage(PlayMessageResponse msg) throws FileNotFoundException {
    StringBuilder returnString = new StringBuilder();
    switch (msg.getMessageSecondary()) {
      case ASSISTANT:
        Gson gson = new Gson();
        JsonArray list = gson
            .fromJson(new FileReader(
                    "C:\\Users\\Utente\\Documents\\GitHub\\ingsw2022-AM14\\src\\main\\resources\\json\\assistants.json"),
                JsonArray.class);
        JsonObject object = list.get(msg.getAssistantId()).getAsJsonObject();
        int moves = object.get("MOVES").getAsInt();
        int speed = object.get("SPEED").getAsInt();

        if (msg.getPlayerId() == this.playerId) {
          returnString
              .append("Assistant played correctly!\n" + "You can now move mother nature of ")
              .append(moves).append(" spaces when your turn comes.\n").append("You're speed is ")
              .append(speed).append(" ouf of 10.\n").append("Please wait...");
        } else {
          returnString.append("another player has player an assistant now he/she can do")
              .append(moves).append("moves with a speed of").append(speed)
              .append("you cannot play the same assistant");
        }

      case CHARACTER:
        returnString.append("Character").append(msg.getCharacterId())
            .append("purchased now you can").append(msg.getEffectString());
        //TODO
    }
    return String.valueOf(returnString);
  }

  private String printMoveMessage(MoveMessageResponse msg) {

    StringBuilder printedString = new StringBuilder();

    switch (msg.getMessageSecondary()) {
      case ENTRANCE:
        printedString.append("now your entrance contains");
        for (Color color : Color.values()) {
          printedString.append(msg.getStudentsEntrance()[color.ordinal()]).append(" ")
              .append(color).append(" students;\n");
        }
        if (msg.getPlace() == 0) {
          printedString.append("now your dining room contains");
          for (Color color : Color.values()) {
            printedString.append(msg.getStudentsDiningRoom()[color.ordinal()]).append(" ")
                .append(color).append(" students;\n");
          }
        } else {
          printedString.append("now the island").append(msg.getIslandNumber()).append("contains");
          for (Color color : Color.values()) {
            printedString.append(msg.getStudentsIsland()[color.ordinal()]).append(" ")
                .append(color).append(" students;\n");
          }
        }
      case CLOUD_TILE:
        printedString.append("cloud tile number").append(msg.getCloudTileNumber())
            .append("successfully taken").append("now your entrance contains");
        for (Color color : Color.values()) {
          printedString.append(msg.getStudentsCloud()[color.ordinal()]).append(" ")
              .append(color).append(" students;\n");
        }
      case MOTHER_NATURE:
        printedString.append("now mother nature is on the island number")
            .append(msg.getIslandNumber()).append("island");
    }
    return String.valueOf(printedString);
  }

  private String printInfoMessage(ClientResponse msg) {
    //TODO
    return null;
  }

  private String printPhaseMessage(PhaseMessageResponse msg) {
    return msg.getWhatTodo();
  }
}

