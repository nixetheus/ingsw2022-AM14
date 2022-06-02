package it.polimi.ingsw.view;


import static it.polimi.ingsw.helpers.MessageSecondary.ASK_GAME_PARAMS;
import static it.polimi.ingsw.helpers.MessageSecondary.INIT_GAME;
import static it.polimi.ingsw.helpers.MessageSecondary.LOBBY;
import static it.polimi.ingsw.helpers.MessageSecondary.MOVE_MN;

import com.google.gson.Gson;
import it.polimi.ingsw.guicontrollers.CharacterController;
import it.polimi.ingsw.guicontrollers.GameController;
import it.polimi.ingsw.guicontrollers.IslandController;
import it.polimi.ingsw.guicontrollers.PlayerBoardController;
import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.StudentsPlayerId;
import it.polimi.ingsw.messages.BeginTurnMessage;
import it.polimi.ingsw.messages.ClientResponse;
import it.polimi.ingsw.messages.LoginMessageResponse;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MoveMessageResponse;
import it.polimi.ingsw.messages.PlayMessageResponse;
import java.util.Vector;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerParserGUI {

  private int playerId;
  private final Stage mainStage;
  private final Scene loginParamsScene;
  private final Scene loginLobbyScene;
  private final Scene gameScene;
  private final GameController mainController;

  // Character Files
  String[] charactersFiles = {
      ""  // TODO
  };

  public ServerParserGUI(Stage stage, Scene login, Scene params, Scene lobby, Scene game, FXMLLoader gameFxmlLoader) {

    gameScene = game;
    mainStage = stage;
    loginLobbyScene = lobby;
    loginParamsScene = params;
    mainStage.setScene(login);

    System.out.println(
        Eriantys.class.getResource("src/main/resources/Graphical_Assets/Personaggi/CarteTOT_front.jpg"));
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
      return gson.fromJson(jsonString, PlayMessageResponse.class);
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
    if (loginMessage.getMessageSecondary() == ASK_GAME_PARAMS) {
      Platform.runLater(() -> mainStage.setScene(loginParamsScene));
    } else if (loginMessage.getMessageSecondary() == LOBBY) {
      playerId = loginMessage.getPlayerId();
      mainController.setPlayerId(playerId);
      Platform.runLater(() -> mainStage.setScene(loginLobbyScene));
    }
  }

  private void elaborateMoveMessage(MoveMessageResponse moveMessage) {
    if (moveMessage.getMessageSecondary() == MOVE_MN) {

      Platform.runLater(() -> {

        for (IslandController islandController : mainController.islandsControllers)
          islandController.setMotherNature(false);

        mainController.islandsControllers.elementAt(moveMessage.getIslandNumber())
            .setMotherNature(true);
      });

    }
  }

  private void elaboratePlayMessage(PlayMessageResponse playMessage) {

    StringBuilder returnString = new StringBuilder();

    switch (playMessage.getMessageSecondary()) {

      case ASK_ASSISTANT: {
        if (playMessage.getPreviousPlayerId() == playerId) {
          Platform.runLater(() -> mainController.hideAssistant(playMessage.getAssistantId()));
        }
      }
      break;

      case CHARACTER: {
        // TODO: CHANGE COST
        returnString.append("TODO PLAY CHARACTER");
      }
      break;

    }

    if (playMessage.getResponse() != null)
      Platform.runLater(() -> mainController.setTextArea(playMessage.getResponse() + " " + returnString));
  }

  private void elaborateInfoMessage(ClientResponse infoMessage) {
    if (infoMessage.getResponse() != null)
      Platform.runLater(() ->mainController.setTextArea(infoMessage.getResponse()));
  }

  private void elaboratePhaseMessage(BeginTurnMessage phaseMessage) {

    if (phaseMessage.getMessageSecondary() == INIT_GAME) {
      Platform.runLater(() -> mainStage.setScene(gameScene));
    }

    // SET ISLANDS
    Platform.runLater(() -> {
      if (phaseMessage.getStudentsIsland() != null) {
        for (int index = 0; index < phaseMessage.getStudentsIsland().size(); index++) {

          // STUDENTS
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

          // TOWERS
          mainController.islandsControllers.elementAt(index)
              .setNumberOfTowers(phaseMessage.getTowersIsland().elementAt(index));

          // TOWERS COLOR
          mainController.islandsControllers.elementAt(index)
              .setTeamTower(phaseMessage.getTowersColor()[index]);

          // MOTHER NATURE
          mainController.islandsControllers.elementAt(index).setMotherNature(
              index == phaseMessage.getMotherNaturePosition());
        }
      }
    });

    // SET CLOUD TILES
    Platform.runLater(() -> {
      if (phaseMessage.getStudentsCloudTiles() != null) {
        for (int index = 0; index < phaseMessage.getStudentsCloudTiles().size(); index++) {
          int[] students = phaseMessage.getStudentsCloudTiles().elementAt(index);
          mainController.cloudControllers.elementAt(index).setStudents(students);
        }
      }
    });

    // DINING ROOMS
    Platform.runLater(() -> {
      for (StudentsPlayerId playerDiningRoom : phaseMessage.getStudentDiningRoom()) {
        PlayerBoardController board = mainController.BoardsControllers.elementAt(playerDiningRoom.getPlayerId());
        board.showStudents(playerDiningRoom.getStudents()[Color.RED.ordinal()], board.redStudents);
        board.showStudents(playerDiningRoom.getStudents()[Color.BLUE.ordinal()], board.blueStudents);
        board.showStudents(playerDiningRoom.getStudents()[Color.PURPLE.ordinal()], board.pinkStudents);
        board.showStudents(playerDiningRoom.getStudents()[Color.GREEN.ordinal()], board.greenStudents);
        board.showStudents(playerDiningRoom.getStudents()[Color.YELLOW.ordinal()], board.yellowStudents);
      }
    });

    // ENTRANCES
    Platform.runLater(() -> {
      for (StudentsPlayerId playerEntrance : phaseMessage.getStudentEntrance()) {
        PlayerBoardController board = mainController.BoardsControllers.elementAt(playerEntrance.getPlayerId());
        board.setEntranceStudents(playerEntrance.getStudents());
      }
    });

    // PROFESSORS
    Platform.runLater(() -> {
      for (int indexPlayer = 0; indexPlayer < phaseMessage.getProfessors().size(); indexPlayer++) {

        PlayerBoardController board = mainController.BoardsControllers.elementAt(indexPlayer);
        int[] professors = phaseMessage.getProfessors().elementAt(indexPlayer);
        int professorsFlag =  professors[0] << 2 |
                              professors[1] << 4 |
                              professors[2]      |
                              professors[3] << 1 |
                              professors[4] << 3;

        board.showProfessors(professorsFlag);
      }
    });

    // PLAYER COINS
    Platform.runLater(() -> {
      mainController.playerCoins.setText("Coins: " + phaseMessage.getPlayerCoins()[playerId]);
    });

    // CHARACTERS
    Platform.runLater(() -> {
      if (phaseMessage.getPurchasableCharacterId() != null) {
        Vector<Integer> charactersIds = phaseMessage.getPurchasableCharacterId();
        for (int characterIndex = 0; characterIndex < charactersIds.size(); characterIndex++) {

          CharacterController character = mainController.characterControllers.elementAt(characterIndex);
          int[] students = phaseMessage.getCharactersStudents().elementAt(characterIndex);
          character.setStudents(students);
          character.setCost(phaseMessage.getCharactersCosts()[characterIndex]);
          // character.changeCharacterPic(charactersFiles[charactersIds.elementAt(characterIndex)]); TODO
        }
      } else {
        mainController.hideCharacters();
      }
    });
  }
}
