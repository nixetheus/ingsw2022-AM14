package it.polimi.ingsw.view;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.messages.ClientResponse;
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

  public void printMessage(Message msg) throws FileNotFoundException {
    switch (msg.getMessageMain()) {
      case LOGIN:
        printLoginMessage((ClientResponse) msg);
      case MOVE:
        printMoveMessage((MoveMessageResponse) msg);
      case PLAY:
        printPlayMessage((PlayMessageResponse) msg);
      case INFO:
        printInfoMessage((ClientResponse) msg);
      case PHASE:
        printPhaseMessage((PhaseMessageResponse) msg);
    }

  }

  private void printLoginMessage(ClientResponse msg) {
    this.playerId = msg.getPlayerId();
    System.out.println(msg.getResponse());
    if (msg.getPlayerId() == 0) {
      System.out.println("Now you have to choose the game mode and number of players");
    }
  }

  public int getPlayerId() {
    return playerId;
  }

  private void printPlayMessage(PlayMessageResponse msg) throws FileNotFoundException {
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
          System.out.println("Assistant played correctly!\n"
              + "You can now move mother nature of " + moves
              + " spaces when your turn comes.\n"
              + "You're speed is " + speed + " ouf of 10.\n"
              + "Please wait...");
        } else {
          System.out.println("another player has player an assistant now he/she can do" + moves
              + "moves with a speed of" + speed + "you cannot play the same assistant");
        }

      case CHARACTER:
        System.out.println(
            "Character" + msg.getCharacterId() + "purchased now you can" + msg.getEffectString());
        //TODO
    }

  }

  private void printMoveMessage(MoveMessageResponse msg) {

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
    System.out.println(printedString);
  }

  private void printInfoMessage(ClientResponse msg) {
    System.out.println(msg.getResponse());
  }

  private void printPhaseMessage(PhaseMessageResponse msg) {
    System.out.println(msg.getWhatTodo());
  }
}

