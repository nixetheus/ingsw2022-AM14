package it.polimi.ingsw.controller;

import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.model.Game;
import java.io.FileNotFoundException;

/**
 * Class GameController model the a game initializer
 */
public class GameController {

  private final LoginController loginController;

  /**
   * Constructor method for the GameController class
   *
   * @param loginController To know the names of the player
   */
  public GameController(LoginController loginController) {
    this.loginController = loginController;
  }

  /**
   * @param gameMode How many players are allowed into the game
   * @param isExpert If the created match is expert or not
   * @return Returns the created match
   * @throws FileNotFoundException If the json file are not found
   */
  public Game setUpGame(int gameMode, boolean isExpert) throws FileNotFoundException {

    Game currentGame = new Game(gameMode, isExpert);
    if (gameMode == Constants.getFourPlayerMode()) {

      currentGame.addPlayerToTeam(currentGame.addTeam(), 0,
          loginController.getPlayerIdNicknameMap().get(0));
      currentGame.addPlayerToTeam(currentGame.getTeams().get(0), 1,
          loginController.getPlayerIdNicknameMap().get(1));
      currentGame.addPlayerToTeam(currentGame.addTeam(), 2,
          loginController.getPlayerIdNicknameMap().get(2));
      currentGame.addPlayerToTeam(currentGame.getTeams().get(1), 3,
          loginController.getPlayerIdNicknameMap().get(3));


    } else {

      for (int id : loginController.getPlayerIdNicknameMap().keySet()) {

        currentGame.addPlayerToTeam(currentGame.addTeam(), id,
            loginController.getPlayerIdNicknameMap().get(id));
      }
    }
    return currentGame;
  }

}
