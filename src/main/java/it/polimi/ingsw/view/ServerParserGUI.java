package it.polimi.ingsw.view;


import static it.polimi.ingsw.helpers.MessageSecondary.ASSISTANT;
import static it.polimi.ingsw.helpers.MessageSecondary.CHARACTER;
import static it.polimi.ingsw.helpers.MessageSecondary.CLOUD_TILE;
import static it.polimi.ingsw.helpers.MessageSecondary.ENTRANCE;
import static it.polimi.ingsw.helpers.MessageSecondary.MOTHER_NATURE;
import static it.polimi.ingsw.helpers.MessageSecondary.PLAYER_PARAMS;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.ingsw.guicontrollers.GameController;
import it.polimi.ingsw.guicontrollers.IslandController;
import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.messages.BeginTurnMessage;
import it.polimi.ingsw.messages.ClientResponse;
import it.polimi.ingsw.messages.LoginMessageResponse;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MoveMessageResponse;
import it.polimi.ingsw.messages.PhaseMessageResponse;
import it.polimi.ingsw.messages.PlayMessageResponse;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerParserGUI {

  private final Stage mainStage;
  private final Scene loginParamsScene;
  private final Scene loginLobbyScene;
  private final Scene gameScene;
  private final GameController mainController;

  public ServerParserGUI(Stage stage, Scene login, Scene params, Scene lobby, Scene game, FXMLLoader gameFxmlLoader) {

    gameScene = game;
    mainStage = stage;
    loginLobbyScene = lobby;
    loginParamsScene = params;
    mainStage.setScene(login);

    mainController = gameFxmlLoader.getController();
  }

  public void elaborateMessage(String jsonString) {
    Message serverMessage = jsonToMessage(jsonString);
    if (serverMessage != null) {
      switch (serverMessage.getMessageMain()) {
        case LOGIN: {
          elaborateLoginMessage((LoginMessageResponse) serverMessage);
        }
        break;
        case MOVE:  {
          elaborateMoveMessage((MoveMessageResponse) serverMessage);
        }
        break;
        case PLAY:  {
          elaboratePlayMessage((PlayMessageResponse) serverMessage);
        }
        break;
        case INFO: {
          elaborateInfoMessage((ClientResponse) serverMessage);
        }
        break;
        case PHASE: {
          elaboratePhaseMessage((BeginTurnMessage) serverMessage);
        }
        break;
        default:
          break;
      }
    }
  }

  private Message jsonToMessage(String jsonString) {
    Gson gson = new Gson();
    if (jsonString.contains("LOGIN")) {
      return gson.fromJson(jsonString, LoginMessageResponse.class);
    }
    if (jsonString.contains("MOVE")) {
      return gson.fromJson(jsonString, MoveMessageResponse.class);
    }
    if (jsonString.contains("PLAY")) {
      return gson.fromJson(jsonString, PhaseMessageResponse.class);
    }
    if (jsonString.contains("INFO")) {
      return gson.fromJson(jsonString, ClientResponse.class);
    }
    if (jsonString.contains("PHASE")) {
      return (gson.fromJson(jsonString, BeginTurnMessage.class));
    }
    return null;
  }

  private void elaborateLoginMessage(LoginMessageResponse loginMessage) {
    if (loginMessage.getMessageSecondary() == PLAYER_PARAMS) {
      Platform.runLater(() -> mainStage.setScene(loginParamsScene));
    } else {
      Platform.runLater(() -> mainStage.setScene(loginLobbyScene));
    }
  }

  private void elaborateMoveMessage(MoveMessageResponse moveMessage) {
    StringBuilder returnString = new StringBuilder();

    switch (moveMessage.getMessageSecondary()) {

      case ENTRANCE: {

        // UPDATE ISLAND
        int index = moveMessage.getIslandNumber();
        int[] students = moveMessage.getStudentsIsland();
        IslandController affectedIsland = mainController.islandsControllers.elementAt(index);
        affectedIsland.setRedStudents(students[Color.RED.ordinal()]);
        affectedIsland.setBlueStudents(students[Color.BLUE.ordinal()]);
        affectedIsland.setPinkStudents(students[Color.PURPLE.ordinal()]);
        affectedIsland.setGreenStudents(students[Color.GREEN.ordinal()]);
        affectedIsland.setYellowStudents(students[Color.YELLOW.ordinal()]);

        // UPDATE ENTRANCE
        // TODO

        // UPDATE DINING ROOM
        // TODO

        // RETURN MESSAGE
        returnString.append("Student successfully moved!");
      }
      break;

      case CLOUD_TILE: {

        // UPDATE ENTRANCE
        // TODO
        /*
        for (Color color : Color.values()) {
          returnString.append(moveMessage.getStudentsCloud()[color.ordinal()]).append(" ")
              .append(color).append(" students;\n");
        }
         */

        // RETURN MESSAGE
        returnString.append("Cloud tile ").append(moveMessage.getCloudTileNumber() + 1)
            .append(" successfully taken! ");

      }
      break;

      case MOTHER_NATURE: {

        for (IslandController islandController : mainController.islandsControllers)
          islandController.setMotherNature(false);
        mainController.islandsControllers.elementAt(moveMessage.getIslandNumber())
            .setMotherNature(true);

        // RETURN MESSAGE
        returnString.append("Mother nature is on island number: ")
            .append(moveMessage.getIslandNumber());
      }
      break;

    }
    mainController.setTextArea(returnString.toString());
  }

  private void elaboratePlayMessage(PlayMessageResponse playMessage) {
    StringBuilder returnString = new StringBuilder();
    switch (playMessage.getMessageSecondary()) {
      case ASSISTANT: {
        // TODO: HIDE ASSISTANT
        // mainController.hideAssistant();
        // TODO
        returnString.append("TODO PLAY ASSISTANT");
      }
      break;
      case CHARACTER: {
        // TODO: CHANGE COST
        // TODO
        returnString.append("TODO PLAY CHARACTER");
      }
      break;
    }
    mainController.setTextArea(returnString.toString());
  }

  private void elaborateInfoMessage(ClientResponse infoMessage) {
    mainController.setTextArea(infoMessage.getResponse());
  }

  private void elaboratePhaseMessage(BeginTurnMessage phaseMessage) {

    // SET ISLANDS STUDENTS
    if ( phaseMessage.getStudentsIsland() != null) {
      for (int index = 0; index < phaseMessage.getStudentsIsland().size(); index++) {
        int[] students = phaseMessage.getStudentsIsland().elementAt(index);
        mainController.islandsControllers.elementAt(index)
            .setRedStudents(students[Color.RED.ordinal()]);
        mainController.islandsControllers.elementAt(index)
            .setBlueStudents(students[Color.BLUE.ordinal()]);
        mainController.islandsControllers.elementAt(index)
            .setPinkStudents(students[Color.PURPLE.ordinal()]);
        mainController.islandsControllers.elementAt(index)
            .setGreenStudents(students[Color.GREEN.ordinal()]);
        mainController.islandsControllers.elementAt(index)
            .setYellowStudents(students[Color.YELLOW.ordinal()]);
      }
    }

    // SET CLOUD TILES
    if ( phaseMessage.getStudentsCloudTiles() != null) {
      for (int index = 0; index < phaseMessage.getStudentsCloudTiles().size(); index++) {
        int[] students = phaseMessage.getStudentsCloudTiles().elementAt(index);
        mainController.cloudControllers.elementAt(index).setStudents(students);
      }
    }

    // TODO: REMAINING MSG PARTS
  }
}
