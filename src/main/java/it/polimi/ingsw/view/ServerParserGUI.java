package it.polimi.ingsw.view;


import static it.polimi.ingsw.helpers.MessageSecondary.ASK_GAME_PARAMS;
import static it.polimi.ingsw.helpers.MessageSecondary.INIT_GAME;
import static it.polimi.ingsw.helpers.MessageSecondary.LOBBY;

import com.google.gson.Gson;
import it.polimi.ingsw.guicontrollers.CharacterController;
import it.polimi.ingsw.guicontrollers.GameController;
import it.polimi.ingsw.guicontrollers.PlayerBoardController;
import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.helpers.StudentsPlayerId;
import it.polimi.ingsw.messages.BeginTurnMessage;
import it.polimi.ingsw.messages.ClientResponse;
import it.polimi.ingsw.messages.LoginMessageResponse;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MoveMessageResponse;
import it.polimi.ingsw.messages.PlayMessageResponse;
import it.polimi.ingsw.messages.WinnerMessage;
import it.polimi.ingsw.model.player.Player;
import java.util.Arrays;
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
  private int playerId;


  public ServerParserGUI(Stage stage, Scene login, Scene params, Scene lobby, Scene game,
      FXMLLoader gameFxmlLoader) {

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
        case MOVE: {
          elaborateMoveMessage((MoveMessageResponse) serverMessage);
        }
        break;
        case PLAY: {
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
        case END: {
          elaborateEndMessage((WinnerMessage) serverMessage);
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
    if (jsonString.contains("WIN")) {
      return (gson.fromJson(jsonString, WinnerMessage.class));
    }
    return null;
  }

  private void elaborateLoginMessage(LoginMessageResponse loginMessage) {
    if (loginMessage.getMessageSecondary() == ASK_GAME_PARAMS) {
      Platform.runLater(() -> mainStage.setScene(loginParamsScene));
    } else if (loginMessage.getMessageSecondary() == LOBBY) {
      playerId = loginMessage.getPlayerId();
      mainController.setNumberOfPlayers(loginMessage.getNumberOfPlayers());
      mainController.setPlayerId(playerId);
      Platform.runLater(() -> mainStage.setScene(loginLobbyScene));
    }
  }

  private void elaborateMoveMessage(MoveMessageResponse moveMessage) {

  }

  private void elaboratePlayMessage(PlayMessageResponse playMessage) {
    if (playMessage.getMessageSecondary() == MessageSecondary.ASK_ASSISTANT) {
      if (playMessage.getPreviousPlayerId() == playerId) {
        Platform.runLater(() -> mainController.hideAssistant(playMessage.getAssistantId()));
      }
    }
  }

  private void elaborateInfoMessage(ClientResponse infoMessage) {
    if (infoMessage.getResponse() != null) {
      Platform.runLater(() -> mainController.setTextArea(infoMessage.getResponse()));
    }

    if (infoMessage.getMessageSecondary() == MessageSecondary.CLIENT_DISCONNECT) {
      System.exit(0);
    }
  }

  private void elaborateEndMessage(WinnerMessage winnerMessage) {
    if (winnerMessage.getMessageSecondary() == MessageSecondary.WINNER) {
      String winText = "";
      if (winnerMessage.getNumberOfPlayers() == 4)
        winText = winnerMessage.getPlayersTeam().elementAt(0) + " and " +
            winnerMessage.getPlayersTeam().elementAt(1) +
                  " are the winners! Congratulations!";
      else
       winText = winnerMessage.getPlayersTeam().elementAt(0) + " is the winner! Congratulations!";

      String finalWinText = winText;
      Platform.runLater(() -> mainController.setTextArea(finalWinText));
    }
  }

  private void elaboratePhaseMessage(BeginTurnMessage phaseMessage) {

    if (phaseMessage.getMessageSecondary() == INIT_GAME) {
      Platform.runLater(() -> mainStage.setScene(gameScene));
    }

    // SET ISLANDS
    Platform.runLater(() -> {
      if (phaseMessage.getStudentsIsland() != null) {
        for (int islandNumber = 0; islandNumber < 12; islandNumber++) {

          if (Arrays.asList(phaseMessage.getIslandsIds()).contains(islandNumber)) {

            // STUDENTS
            int index = Arrays.asList(phaseMessage.getIslandsIds()).indexOf(islandNumber);
            int[] students = phaseMessage.getStudentsIsland().elementAt(index);
            mainController.islandsControllers.elementAt(islandNumber)
                .setRedStudents(students[Color.RED.ordinal()]);
            mainController.islandsControllers.elementAt(islandNumber)
                .setBlueStudents(students[Color.BLUE.ordinal()]);
            mainController.islandsControllers.elementAt(islandNumber)
                .setPinkStudents(students[Color.PURPLE.ordinal()]);
            mainController.islandsControllers.elementAt(islandNumber)
                .setGreenStudents(students[Color.GREEN.ordinal()]);
            mainController.islandsControllers.elementAt(islandNumber)
                .setYellowStudents(students[Color.YELLOW.ordinal()]);

            // TOWERS
            mainController.islandsControllers.elementAt(islandNumber)
                .setNumberOfTowers(phaseMessage.getTowersIsland().elementAt(index));

            // TOWERS COLOR
            mainController.islandsControllers.elementAt(islandNumber)
                .setTeamTower(phaseMessage.getTowersColor()[index]);

            // MOTHER NATURE
            mainController.islandsControllers.elementAt(islandNumber).setMotherNature(
                islandNumber == phaseMessage.getMotherNaturePosition());

            // NO ENTRY
            mainController.islandsControllers.elementAt(islandNumber).noEntry.
                setVisible(phaseMessage.getIslandsNoEntry()[index]);

          } else {
            mainController.deleteIsland(islandNumber);
          }

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

    // TOWERS
    Platform.runLater(() -> {
      for (int teamId = 0; teamId < phaseMessage.getTowersNumber().length; teamId++) {
        PlayerBoardController board = mainController.BoardsControllers
            .elementAt(teamId);
        board.showTowers(phaseMessage.getTowersNumber()[teamId]);

        if (phaseMessage.getStudentDiningRoom().size() == 4)
          mainController.BoardsControllers
              .elementAt(teamId + 2).showTowers(phaseMessage.getTowersNumber()[teamId]);
      }
    });

    // DINING ROOMS
    Platform.runLater(() -> {
      for (StudentsPlayerId playerDiningRoom : phaseMessage.getStudentDiningRoom()) {
        PlayerBoardController board = mainController.BoardsControllers
            .elementAt(playerDiningRoom.getPlayerId());
        board.showStudents(playerDiningRoom.getStudents()[Color.RED.ordinal()], board.redStudents);
        board
            .showStudents(playerDiningRoom.getStudents()[Color.BLUE.ordinal()], board.blueStudents);
        board.showStudents(playerDiningRoom.getStudents()[Color.PURPLE.ordinal()],
            board.pinkStudents);
        board.showStudents(playerDiningRoom.getStudents()[Color.GREEN.ordinal()],
            board.greenStudents);
        board.showStudents(playerDiningRoom.getStudents()[Color.YELLOW.ordinal()],
            board.yellowStudents);
      }
    });

    // ENTRANCES
    Platform.runLater(() -> {
      for (StudentsPlayerId playerEntrance : phaseMessage.getStudentEntrance()) {
        PlayerBoardController board = mainController.BoardsControllers
            .elementAt(playerEntrance.getPlayerId());
        board.setEntranceStudents(playerEntrance.getStudents());
      }
    });

    // PROFESSORS
    Platform.runLater(() -> {
      for (int indexPlayer = 0; indexPlayer < phaseMessage.getProfessors().size(); indexPlayer++) {

        PlayerBoardController board = mainController.BoardsControllers.elementAt(indexPlayer);
        int[] professors = phaseMessage.getProfessors().elementAt(indexPlayer);
        int professorsFlag = professors[0] << 2 |
            professors[1] << 4 |
            professors[2] |
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

          CharacterController character = mainController.characterControllers
              .elementAt(characterIndex);
          int[] students = phaseMessage.getCharactersStudents().elementAt(characterIndex);
          character.setStudents(students);
          character.setCost(phaseMessage.getCharactersCosts()[characterIndex]);
          character.setCharacter(charactersIds.elementAt(characterIndex));
        }
      } else {
        mainController.hideCharacters();
      }
    });
  }
}
