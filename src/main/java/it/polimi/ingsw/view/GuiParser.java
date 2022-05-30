package it.polimi.ingsw.view;

import com.google.gson.Gson;
import it.polimi.ingsw.guicontrollers.GameController;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.messages.LoginMessage;
import it.polimi.ingsw.messages.MoveMessage;
import it.polimi.ingsw.messages.PlayMessage;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.fxml.FXMLLoader;

public class GuiParser {

  PrintWriter out;
  Gson gson = new Gson();
  GameController mainGuiController;

  public GuiParser (int portNumber, String hostName, Socket socket, GameController main) {
    mainGuiController = main;
    try {
      out = new PrintWriter(socket.getOutputStream(), true);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to " + hostName + " " + portNumber);
      System.exit(1);
    }
  }

  // LOGIN MESSAGES
  public void sendLoginMessage(String username) {
    LoginMessage loginMessage = new LoginMessage(MessageSecondary.PLAYER_PARAMS);
    loginMessage.setNickName(username);
    out.println(gson.toJson(loginMessage));
  }

  public void sendLoginParameters(boolean expertMode, String playersString) {

    int players = 2; // TODO
    LoginMessage loginParamsMessage = new LoginMessage(MessageSecondary.GAME_PARAMS);
    loginParamsMessage.setGameExpert(expertMode);
    loginParamsMessage.setNumberOfPlayer(players);
    out.println(gson.toJson(loginParamsMessage));
  }

  // PLAY MESSAGES
  public void playAssistant(String assistantId) {

    int id = Integer.parseInt(assistantId.replace("assistant", ""));

    PlayMessage playAssistantMessage = new PlayMessage(MessageSecondary.ASSISTANT);
    playAssistantMessage.setPlayerId(mainGuiController.playerId);
    playAssistantMessage.setAssistantId(id);
    out.println(gson.toJson(playAssistantMessage));
  }

  public void playCharacter(String characterId) {

    int id = Integer.parseInt(characterId.replace("character", ""));

    PlayMessage playCharacterMessage = new PlayMessage(MessageSecondary.CHARACTER);
    playCharacterMessage.setCharacterId(id);
    // TODO: some things can only be set by the controller and server
  }

  // MOVE MESSAGES
  public void moveStudentsFromCloud(String cloudId) {

    int id = Integer.parseInt(cloudId.replace("cloud", ""));

    MoveMessage moveFromCloudMessage = new MoveMessage(MessageSecondary.CLOUD_TILE);
    moveFromCloudMessage.setCloudTileNumber(id);
    out.println(gson.toJson(moveFromCloudMessage));
  }

  public void moveMotherNature(String islandId) {

    int id = Integer.parseInt(islandId.replace("island", ""));

    MoveMessage moveMNMessage = new MoveMessage(MessageSecondary.MOVE_MN);
    moveMNMessage.setIslandNumber(id);

    out.println(gson.toJson(moveMNMessage));
  }

  public void moveStudentFromEntrance(String islandId, boolean diningRoom, String studentId) {

    MoveMessage moveStudentEntranceMessage = new MoveMessage(MessageSecondary.ENTRANCE);
    int studId = Integer.parseInt(studentId.replace("student", ""));

    if (diningRoom) {
      moveStudentEntranceMessage.setStudentColor(0);  // TODO: HOW?
      moveStudentEntranceMessage.setPlace(0);
    } else {
      int islId = Integer.parseInt(islandId.replace("island", ""));
      moveStudentEntranceMessage.setStudentColor(0);  // TODO: HOW?
      moveStudentEntranceMessage.setIslandNumber(islId);
      moveStudentEntranceMessage.setPlace(1);
    }

    out.println(gson.toJson(moveStudentEntranceMessage));

  }
}
