package it.polimi.ingsw.controller;

import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.messages.LoginMessage;
import it.polimi.ingsw.model.Game;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * LoginController control any kind of error incurring in the log in phase
 */
public class LoginController {

  private final HashMap<Integer, String> playerIdNicknameMap;
  private boolean gameMode;
  private int numberOfPlayers;
  private Game currentGame;

  /**
   * Constructor method for the LoginController class
   */
  public LoginController() {
    this.playerIdNicknameMap = new HashMap<>();
  }

  /**
   *
   */
  //public void elaborateMessage(LoginMessage msg) throws FileNotFoundException {
  //switch (msg.getMessageSecondary()) {
  // case GAME_PARAMS:
  // setGameParameters(msg);
  // break;
  //case PLAYER_PARAMS:
  //  setPlayerParameter(msg);
  //   break;
  // default:
  //   break;
  // }
  // }
  public Boolean setGameParameters(LoginMessage msg) {
    if (msg.getNumberOfPlayer() > 1 && msg.getNumberOfPlayer() < 4) {
      this.numberOfPlayers = msg.getNumberOfPlayer();
      this.gameMode = msg.isGameMode();
      return true;
    } else {
      return false;
    }
  }

  public void setPlayerParameter(LoginMessage msg) throws FileNotFoundException {
    //TODO check if is a non duplicate name
    addPlayer(msg.getNickName());
  }

  /**
   * Method addPlayer adds a player into the LoginController and gives him/her a unique id
   *
   * @param playerNickname The name of the player that will be checked
   */
  //TODO create player here and add to game after
  public void addPlayer(String playerNickname) throws FileNotFoundException {
    int nextId;
    nextId = playerIdNicknameMap.size();
    //controls the nickname
    for (int idPlayer : playerIdNicknameMap.keySet()) {
      if (playerNickname.equals(playerIdNicknameMap.get(idPlayer))) {
        //send error message if the name is duplicated
        return;
      }
    }
    playerIdNicknameMap.put(nextId, playerNickname);
    if (playerIdNicknameMap.size() == numberOfPlayers) {
      this.currentGame = setUpGame(numberOfPlayers, gameMode);
    }
  }


  /**
   * @param gameMode How many players are allowed into the game
   * @param isExpert If the created match is expert or not
   * @return Returns the created match
   * @throws FileNotFoundException If the json file are not found
   */
  //TODO create teams and add them to the game created
  public Game setUpGame(int gameMode, boolean isExpert) throws FileNotFoundException {

    Game currentGame = new Game(gameMode - 1, isExpert);
    if (gameMode == Constants.getFourPlayerMode()) {

      currentGame.addPlayerToTeam(currentGame.addTeam(), 0,
          getPlayerIdNicknameMap().get(0));
      currentGame.addPlayerToTeam(currentGame.getTeams().get(0), 1,
          getPlayerIdNicknameMap().get(1));
      currentGame.addPlayerToTeam(currentGame.addTeam(), 2,
          getPlayerIdNicknameMap().get(2));
      currentGame.addPlayerToTeam(currentGame.getTeams().get(1), 3,
          getPlayerIdNicknameMap().get(3));


    } else {

      for (int id : getPlayerIdNicknameMap().keySet()) {

        currentGame.addPlayerToTeam(currentGame.addTeam(), id,
            getPlayerIdNicknameMap().get(id));
      }
    }
    return currentGame;
  }

  public HashMap<Integer, String> getPlayerIdNicknameMap() {
    return playerIdNicknameMap;
  }

  public Game getCurrentGame() {
    return currentGame;
  }
}
