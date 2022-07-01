package it.polimi.ingsw.controller;

import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.helpers.StudentsPlayerId;
import it.polimi.ingsw.helpers.Towers;
import it.polimi.ingsw.messages.BeginTurnMessage;
import it.polimi.ingsw.messages.ClientResponse;
import it.polimi.ingsw.messages.InfoRequestMessage;
import it.polimi.ingsw.messages.LoginMessage;
import it.polimi.ingsw.messages.LoginMessageResponse;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MoveMessage;
import it.polimi.ingsw.messages.PlayMessage;
import it.polimi.ingsw.messages.WinnerMessage;
import it.polimi.ingsw.model.CloudTile;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.characters.CharacterStruct;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Player;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.Semaphore;

/**
 * Main controller class that manage the other controller and do the correct phase and correct
 * player check
 */
public class MainController {

  // Controllers
  private final TurnManager turnManager;
  private final InfoController infoController;
  private final PlayController playController;
  private final MoveController moveController;
  private final LoginController loginController;
  private final Vector<Team> teams;
  //private Vector<Message> messages;
  // Attributes
  private Game game;

  // Game parameters
  private int numberOfPlayers;
  private boolean isGameExpert;

  // Server
  private Semaphore serverSemaphore;

  /**
   * Constructor for the MainController Class
   */
  public MainController() {

    teams = new Vector<>();

    turnManager = new TurnManager();
    infoController = new InfoController();
    playController = new PlayController();
    moveController = new MoveController();
    loginController = new LoginController();


  }

  /**
   * This method assigns the message received from a player to the respective sub-controller
   *
   * @param msg The message received from the player
   */
  public Vector<Message> elaborateMessage(Message msg) throws IOException {

    if (msg.getMessageMain() == MessageMain.INFO) {
      return infoController.elaborateMessage((InfoRequestMessage) msg, game);
    } else if (msg.getMessageMain() == MessageMain.LOGIN) {
      return elaborateLoginMessage((LoginMessage) msg);
    } else {
      return elaborateGameMessage(msg);
    }

  }

  /**
   * This method elaborates login messages, differentiating between the ones send to set the details
   * of each player and those used to set the parameters of the game
   *
   * @param msg The Login message received
   * @return the vector of messages to be sent to the client
   */
  private Vector<Message> elaborateLoginMessage(LoginMessage msg) throws IOException {
    Vector<Message> messages = new Vector<>();

    LoginMessageResponse loginResponse = new LoginMessageResponse(msg.getMessageSecondary());
    messages.add(loginResponse);

    // Check if phase is correct
    if (turnManager.getMainGamePhase() == MessageMain.LOGIN &&
        msg.getMessageSecondary() == turnManager.getSecondaryPhase()) {

      switch (msg.getMessageSecondary()) {
        case GAME_PARAMS:

          // If parameters are okay set them and change game state else send error message
          if (loginController.checkGameParameters(msg)) {
            isGameExpert = msg.isGameExpert();
            numberOfPlayers = msg.getNumberOfPlayer();
            turnManager.setNumberOfUsers(numberOfPlayers);
            turnManager.updateCounters();
            turnManager.changeState();
            this.serverSemaphore.release();
            loginResponse.setPlayerId(0);
            loginResponse.setResponse("Game parameters correctly set!");
            loginResponse.setMessageSecondary(MessageSecondary.LOBBY);
            loginResponse.setNumberOfPlayers(numberOfPlayers);

          } else {
            loginResponse.setResponse("Error! Game parameters are not correct! Retry!");

          }
          break;

        case PLAYER_PARAMS:

          Player newPlayer = loginController
              .createPlayer(msg, turnManager.getCurrentNumberOfUsers());

          // If player is not null, add it to a team, change state
          if (newPlayer != null) {

            // Check name not empty and not already in use
            boolean alreadyExists = false;
            for (Team team : teams) {
              for (Player player : team.getPlayers()) {
                alreadyExists = alreadyExists ||
                    player.getPlayerNickname().equals(newPlayer.getPlayerNickname());
              }
            }

            if (newPlayer.getPlayerNickname().isEmpty() ||
                newPlayer.getPlayerNickname().replace(" ", "").equals("") ||
                alreadyExists) {
              loginResponse.setResponse("Username already exists!");
              loginResponse.setPlayerId(newPlayer.getPlayerId());
              break;
            }

            // Where a player goes is based on the number of players for this game
            if (numberOfPlayers == 4) {
              if (teams.size() < 2) {
                Team newTeam = new Team(teams.size(), Towers.values()[teams.size()]);
                newTeam.addPlayer(newPlayer);
                teams.add(newTeam);
              } else {
                long nCurrentPlayers = teams.stream().map(team -> team.getPlayers().size())
                    .reduce(0, Integer::sum);

                teams.elementAt((int) nCurrentPlayers % 2).addPlayer(newPlayer);
              }

            } else {
              Team newTeam = new Team(teams.size(), Towers.values()[teams.size()]);
              newTeam.addPlayer(newPlayer);
              teams.add(newTeam);
            }

            // If players are all in, setup game
            if (teams.stream().map(team -> team.getPlayers().size()).reduce(0, Integer::sum)
                == numberOfPlayers) {
              setupGame();

              // SEND WELCOME MESSAGE
              loginResponse.setResponse("Welcome aboard " + newPlayer.getPlayerNickname() + "!  ");
              loginResponse.setPlayerId(turnManager.getCurrentNumberOfUsers());
              loginResponse.setMessageSecondary(MessageSecondary.LOBBY);
              loginResponse.setNumberOfPlayers(numberOfPlayers);

              // SEND READY MESSAGE TODO: CHANGE SECONDARY
              LoginMessageResponse loginMessageResponse2 = new LoginMessageResponse(
                  MessageSecondary.INFO_HELP);
              loginMessageResponse2
                  .setResponse("Everyone is ready now! We shall let the game start!");
              loginMessageResponse2.setPlayerId(-1);
              messages.add(loginMessageResponse2);

              Vector<Message> changeTurnMessages = changeTurnMessage(MessageSecondary.INIT_GAME);
              messages.addAll(changeTurnMessages);

              messages.add(sendClientResponse(
                  MessageSecondary.ASK_ASSISTANT, "It's your turn to play an assistant",
                  game.getCurrentPlayer().getPlayerId()));


            } else {
              loginResponse.setResponse("Welcome aboard " + newPlayer.getPlayerNickname() + "!");
              loginResponse.setPlayerId(
                  turnManager.getCurrentNumberOfUsers());
              // FIRST PLAYER
              if (teams.stream().map(team -> team.getPlayers().size()).reduce(0, Integer::sum)
                  == 1) {
                loginResponse.setMessageSecondary(MessageSecondary.ASK_GAME_PARAMS);
              } else {
                loginResponse.setPlayerId(turnManager.getCurrentNumberOfUsers());
                loginResponse.setMessageSecondary(MessageSecondary.LOBBY);
                loginResponse.setNumberOfPlayers(numberOfPlayers);
              }
            }
          } else {
            loginResponse.setResponse("Error while creating new player, please try again!");
            break;
          }

          turnManager.updateCounters();
          turnManager.changeState();

          break;

        default:
          loginResponse.setResponse("Unexpected error! This should've not happened!");
          break;
      }
    }

    return messages;
  }

  /**
   * This method elaborates game messages, differentiating between the ones used to play a card
   * specifically, a character or an assistant card, and those used to move elements inside the game
   * board. It also checks whether an action can be performed.
   *
   * @param msg The game message to be elaborated
   */
  private Vector<Message> elaborateGameMessage(Message msg) {
    boolean everythingOkay;
    Vector<Message> messages = new Vector<>();

    boolean isCharacterPlayed = (turnManager.getMainGamePhase() == MessageMain.MOVE) &&
        msg.getMessageSecondary() == MessageSecondary.CHARACTER;

    // Set current player
    if (turnManager.getMainGamePhase() == MessageMain.MOVE) {
      this.game.setCurrentPlayerIndex(turnManager.getCurrentNumberOfUsersPlayedActionPhase());
    } else if (turnManager.getSecondaryPhase() == MessageSecondary.ASSISTANT) {
      this.game.setCurrentPlayerIndex(turnManager.getCurrentNumberOfPlayedAssistants());
    }

    // Check phase is the right one
    everythingOkay = (((msg.getMessageMain() == turnManager.getMainGamePhase()) &&
        (msg.getMessageSecondary() == turnManager.getSecondaryPhase()))
        || isCharacterPlayed);

    // Check player is current player
    everythingOkay =
        everythingOkay && (msg.getPlayerId() == this.game.getCurrentPlayer().getPlayerId());

    Message gameResponse = null;
    if (everythingOkay) {
      switch (msg.getMessageMain()) {
        case MOVE:
          gameResponse = moveController.elaborateMessage((MoveMessage) msg, game);
          break;
        case PLAY:
          gameResponse = playController.elaborateMessage((PlayMessage) msg, game);
          break;
        default:
          break;
      }
      messages.add(gameResponse);//CHANGED
    }

    if (gameResponse != null && msg.getMessageSecondary() != MessageSecondary.CHARACTER) {

      //messages.add(gameResponse);Changed
      // Update turn
      turnManager.updateCounters();

      // Check win conditions
      Team winner = checkWinConditions(msg);
      if (winner != null) {

        Vector<String> winners = new Vector<>();
        for (Player player : winner.getPlayers()) {
          winners.add(player.getPlayerNickname());
        }

        WinnerMessage winnerMessage = new WinnerMessage(MessageSecondary.WINNER);
        winnerMessage.setWinnerId(winner.getId());
        winnerMessage.setNumberOfPlayers(numberOfPlayers);
        winnerMessage.setPlayersTeam(winners);
        winnerMessage.setPlayerId(-1);
        messages.add(winnerMessage);

        turnManager.finishGame();
        return messages;

      }

      // Update turn
      // turnManager.updateCounters();

      // If appropriate change game order in game
      if (msg.getMessageMain() == MessageMain.PLAY
          && msg.getMessageSecondary() == MessageSecondary.ASSISTANT
          && turnManager.getCurrentNumberOfPlayedAssistants() == numberOfPlayers) {

        //set player order
        this.game.orderBasedOnAssistant();

        //send order message
        ClientResponse order = new ClientResponse(MessageSecondary.GAME_ORDER);
        order.setPlayerId(-1);

        Vector<Integer> playerOrderIdVector = new Vector<>();

        for (Player player : game.getGameOrder()) {
          playerOrderIdVector.add(player.getPlayerId());
        }

        order.setPlayerOrderId(playerOrderIdVector);
        messages.add(order);

      } else if (msg.getMessageMain() == MessageMain.MOVE
          && msg.getMessageSecondary() == MessageSecondary.CLOUD_TILE
          && turnManager.getCurrentNumberOfUsersPlayedActionPhase() == numberOfPlayers) {

        if (game.getPurchasableCharacter() != null) {
          CharacterStruct params = new CharacterStruct();
          params.currentGame = game;
          params.mainBoard = game.getMainBoard();
          params.currentPlayer = game.getCurrentPlayer();//??
          for (CharacterCard card : game.getPurchasableCharacter()) {
            card.removeEffect(params);
          }
        }

        this.game.fillClouds();
        this.game.reverseOrderEndTurn();
        this.game.assistantAfterTurn();
      }

      turnManager.changeState();

      // Set current player
      if (msg.getMessageSecondary() == MessageSecondary.ASSISTANT) {
        if (turnManager.getCurrentNumberOfPlayedAssistants() != numberOfPlayers) {
          this.game.setCurrentPlayerIndex(turnManager.getCurrentNumberOfPlayedAssistants());
          // New current player turn to play assistant
          messages.add(sendClientResponse(
              MessageSecondary.ASK_ASSISTANT, "It's your turn to play an assistant",
              game.getCurrentPlayer().getPlayerId()));
        } else {
          this.game.setCurrentPlayerIndex(turnManager.getCurrentNumberOfUsersPlayedActionPhase());
          messages.add(sendClientResponse(
              MessageSecondary.ASK_STUDENT_ENTRANCE, "It's your turn move your students",
              game.getCurrentPlayer().getPlayerId()));
        }

      } else if (msg.getMessageMain() == MessageMain.MOVE) {

        this.game.setCurrentPlayerIndex(turnManager.getCurrentNumberOfUsersPlayedActionPhase());

        // Message to update gui
        messages.addAll(changeTurnMessage(MessageSecondary.INFRA_TURN));

        // Message to help to understand the game from cli
        if (msg.getMessageSecondary() == MessageSecondary.ENTRANCE) {
          if (turnManager.getCurrentNumberOfStudentsFromEntrance() ==
              turnManager.getNumberStudentsFromEntrance()) {

            messages.add(sendClientResponse(
                MessageSecondary.ASK_MN, "Select an island to move mother nature",
                game.getCurrentPlayer().getPlayerId()));

          } else {
            messages.add(sendClientResponse(
                MessageSecondary.ASK_STUDENT_ENTRANCE, "Move another student",
                game.getCurrentPlayer().getPlayerId()));
          }
        } else if (msg.getMessageSecondary() == MessageSecondary.MOVE_MN) {
          messages.add(sendClientResponse(
              MessageSecondary.ASK_CLOUD, "Take one cloud",
              game.getCurrentPlayer().getPlayerId()));
        } else {
          if (turnManager.getCurrentNumberOfUsersPlayedActionPhase() != numberOfPlayers
              && turnManager.getCurrentNumberOfUsersPlayedActionPhase() != 0) {
            messages.addAll(changeTurnMessage(MessageSecondary.CHANGE_TURN));

            messages.add(sendClientResponse(
                MessageSecondary.ASK_STUDENT_ENTRANCE, "It's your turn move your students",
                game.getCurrentPlayer().getPlayerId()));
          } else {
            messages.addAll(changeTurnMessage(MessageSecondary.CHANGE_TURN));

            messages.add(sendClientResponse(
                MessageSecondary.ASK_STUDENT_ENTRANCE, "It's a new round play a new assistant",
                game.getCurrentPlayer().getPlayerId()));
          }
        }

      }

    } else if (gameResponse != null && msg.getMessageSecondary() == MessageSecondary.CHARACTER) {
      messages.addAll(changeTurnMessage(MessageSecondary.INFRA_TURN));//CHANGED
    } else {
      messages.remove(gameResponse);
      ClientResponse error = new ClientResponse(MessageSecondary.ERROR);
      error.setPlayerId(msg.getPlayerId());
      error.setResponse(
          "Error! The inserted inputs are not correct,the assistant might be already played or it is not your turn!\n");
      messages.add(error);
    }
    return messages;
  }

  /**
   * It creates and sets the new game, setting the current phase and choosing the game order
   *
   * @throws FileNotFoundException If the json file is not found
   */
  public void setupGame() throws IOException {
    this.game.setTeams(teams);
    this.game.setExpert(isGameExpert);
    turnManager.setNumberStudentsFromEntrance(this.game.getStudentOnCloudTiles());
  }

  /**
   * This method compiles the begin-turn message at the beginning of the game and each turn
   *
   * @param messageSecondary the message secondary to create the message
   * @return the vector of messages to be sent to each client
   */
  public Vector<Message> changeTurnMessage(MessageSecondary messageSecondary) {
    Vector<Message> returnedVector = new Vector<>();

    for (Team team : this.game.getTeams()) {
      for (Player player : team.getPlayers()) {

        BeginTurnMessage beginTurnMessage = new BeginTurnMessage(messageSecondary);
        beginTurnMessage.setPlayerId(player.getPlayerId());
        beginTurnMessage.setActivePLayerId(game.getCurrentPlayer().getPlayerId());

        Vector<StudentsPlayerId> studentsAtEntrances = new Vector<>();
        Vector<StudentsPlayerId> studentDiningRooms = new Vector<>();
        Vector<int[]> professors = new Vector<>();
        int[] playersCoins = new int[numberOfPlayers];
        int[] towerPerTeam = new int[game.getTeams().size()];

        for (int playerIndex = 0; playerIndex < game.getPlayerNumber(); playerIndex++) {
          int[] playerProfessors = new int[5];
          professors.add(playerProfessors);
        }

        for (Team teamStudents : this.game.getTeams()) {

          towerPerTeam[teamStudents.getId()] = teamStudents.getAvailableTowers();

          for (Player playerStudents : teamStudents.getPlayers()) {

            studentsAtEntrances.add(new StudentsPlayerId(playerStudents.getPlayerId(),
                playerStudents.getPlayerBoard().getEntrance().getStudents()));
            beginTurnMessage.setStudentEntrance(studentsAtEntrances);

            studentDiningRooms.add(new StudentsPlayerId(playerStudents.getPlayerId(),
                playerStudents.getPlayerBoard().getDiningRoom().getStudents()));
            beginTurnMessage.setStudentDiningRoom(studentDiningRooms);

            // Professors
            for (int profIndex = 0; profIndex < 5; profIndex++) {
              if (game.getProfessorControlPlayer()[profIndex] != null) {
                if (game.getProfessorControlPlayer()[profIndex].getPlayerId()
                    == playerStudents.getPlayerId()) {
                  professors.elementAt(playerStudents.getPlayerId())[profIndex] = 1;
                }
              }
            }

            // Coins
            playersCoins[playerStudents.getPlayerId()] = playerStudents.getCoins();
          }
        }

        beginTurnMessage.setTowersNumber(towerPerTeam);
        beginTurnMessage.setStudentEntrance(studentsAtEntrances);
        beginTurnMessage.setStudentDiningRoom(studentDiningRooms);

        beginTurnMessage
            .setMotherNaturePosition(game.getMainBoard().getMotherNature().getPosition());
        beginTurnMessage.setProfessors(professors);

        beginTurnMessage.setPlayerCoins(playersCoins);

        int towerIndex = 0;
        Integer[] islandsIds = new Integer[game.getMainBoard().getIslands().size()];
        int[] towersColors = new int[game.getMainBoard().getIslands().size()];
        boolean[] noEntry = new boolean[game.getMainBoard().getIslands().size()];
        Vector<int[]> studentsIslands = new Vector<>();
        Vector<Integer> towersIslands = new Vector<>();
        for (Island island : game.getMainBoard().getIslands()) {
          int[] studentIsland = island.getStudents();
          studentsIslands.add(studentIsland);
          towersIslands.add(island.getNumberOfTowers());
          islandsIds[towerIndex] = island.getIslandId();
          noEntry[towerIndex] = island.isNoEntry();
          towersColors[towerIndex++] = island.getOwnerId();
        }
        beginTurnMessage.setIslandsIds(islandsIds);
        beginTurnMessage.setIslandsNoEntry(noEntry);
        beginTurnMessage.setStudentsIsland(studentsIslands);
        beginTurnMessage.setTowersIsland(towersIslands);
        beginTurnMessage.setTowersColor(towersColors);

        Vector<Integer> playableAssistantId = new Vector<>();
        for (Assistant assistant : player.getPlayableAssistant()) {
          playableAssistantId.add(assistant.getAssistantId());
        }
        beginTurnMessage.setPlayableAssistantId(playableAssistantId);

        // Clouds
        Vector<int[]> clouds = new Vector<>();
        for (CloudTile cloud : game.getCloudTiles()) {
          clouds.add(cloud.getStudents());
        }
        beginTurnMessage.setStudentsCloudTiles(clouds);

        // Characters
        if (game.getPurchasableCharacter() != null) {
          Vector<Integer> charactersIds = new Vector<>();
          Vector<int[]> studentsCharacters = new Vector<>();
          int[] charactersCosts = new int[game.getPurchasableCharacter().size()];

          int costIndex = 0;
          for (CharacterCard character : game.getPurchasableCharacter()) {
            charactersIds.add(character.getCardEffect().ordinal());
            charactersCosts[costIndex++] = character.getCost();
            studentsCharacters.add(character.getStudents());
          }

          beginTurnMessage.setPurchasableCharacterId(charactersIds);
          beginTurnMessage.setCharactersStudents(studentsCharacters);
          beginTurnMessage.setCharactersCosts(charactersCosts);
        }

        returnedVector.add(beginTurnMessage);
      }
    }
    return returnedVector;
  }

  /**
   * This method creates a client response with the given input
   *
   * @param messageSecondary The message secondary to be set into the new message
   * @param response         The string to be set into the message
   * @param id               The id of the player
   * @return the client response created
   */
  private Message sendClientResponse(MessageSecondary messageSecondary, String response, int id) {
    ClientResponse clientResponse = new ClientResponse(messageSecondary);
    clientResponse.setResponse(response);
    clientResponse.setPlayerId(id);
    return clientResponse;
  }

  /**
   * It gets the winner team
   *
   * @return the winner team
   */
  private Team getWinner() {

    int[] towersTeam = {0, 0, 0};
    for (Island island : game.getMainBoard().getIslands()) {
      if (island.getOwnerId() != -1) {
        towersTeam[island.getOwnerId()] += island.getNumberOfTowers();
      }
    }

    int maxTeamId = 0;
    int maxTeamTowers = 0;
    for (int teamId = 0; teamId < towersTeam.length; teamId++) {
      if (towersTeam[teamId] > maxTeamTowers) {
        maxTeamTowers = towersTeam[teamId];
        maxTeamId = teamId;
      }
    }
    Arrays.sort(towersTeam);

    if (towersTeam[towersTeam.length - 1] == towersTeam[towersTeam.length - 2]) {
      maxTeamId = checkDraw().getId();
    }

    return game.getTeams().elementAt(maxTeamId);

  }

  /**
   * This method checks if the winning condition are verified
   *
   * @param msg the message arrived from the client
   * @return the winner team
   */
  private Team checkWinConditions(Message msg) {

    Team winner = null;

    if (msg.getMessageSecondary() == MessageSecondary.CLOUD_TILE
        && game.getCurrentPlayer().getPlayableAssistant().size() == 0
        && turnManager.getCurrentNumberOfUsersPlayedActionPhase() == numberOfPlayers) {
      winner = getWinner();
    } else if (msg.getMessageSecondary() == MessageSecondary.MOVE_MN) {

      for (Team team : game.getTeams()) {
        for (Player player : team.getPlayers()) {
          if (player.getPlayerId() == game.getCurrentPlayer().getPlayerId()) {
            if (team.getAvailableTowers() == 0) {
              winner = team;
            }
          }
        }
      }

      if (game.getMainBoard().getIslands().size() <= 3) {
        winner = getWinner();
      }

    } else if (Arrays.stream(game.getStudentsBag().getStudents()).sum() == 0
        && msg.getMessageSecondary() == MessageSecondary.CLOUD_TILE
        && turnManager.getCurrentNumberOfUsersPlayedActionPhase() == numberOfPlayers) {
      winner = getWinner();
    }

    return winner;
  }

  /**
   * This method checks who will win in case one or more team has the same number of built towers
   *
   * @return The winner team
   */
  public Team checkDraw() {
    int maxProfessorTeam = 0;
    Team winnerTeam = null;

    for (Team team : game.getTeams()) {
      int teamProfessorNumber = 0;
      for (Player player : team.getPlayers()) {
        for (Color color : Color.values()) {
          if (game.getProfessorControlPlayer()[color.ordinal()] != null) {
            if (game.getProfessorControlPlayer()[color.ordinal()].getPlayerId() == player
                .getPlayerId()) {
              teamProfessorNumber++;
            }
          }
        }
      }
      if (teamProfessorNumber > maxProfessorTeam) {
        winnerTeam = team;
        maxProfessorTeam = teamProfessorNumber;
      }
    }
    return winnerTeam;
  }

  public Vector<Team> getTeams() {
    return teams;
  }

  public Game getGame() {
    return game;
  }

  /**
   * Sets the game as given by the server
   *
   * @param newGame the game created by the Server
   */
  public void setGame(Game newGame) {
    this.game = newGame;
  }

  public int getNumberOfPlayers() {
    return numberOfPlayers;
  }

  public void setNumberOfPlayers(int numberOfPlayers) {
    this.numberOfPlayers = numberOfPlayers;
  }

  public boolean isGameExpert() {
    return isGameExpert;
  }

  public void setGameExpert(boolean gameExpert) {
    isGameExpert = gameExpert;
  }

  public Semaphore getServerSemaphore() {
    return serverSemaphore;
  }

  /**
   * Gets the server's semaphore for controlling logic
   *
   * @param serverSemaphore The server's Semaphore
   */
  public void setServerSemaphore(Semaphore serverSemaphore) throws InterruptedException {
    this.serverSemaphore = serverSemaphore;
    this.serverSemaphore.acquire();
  }
}
