package it.polimi.ingsw.controller;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.Towers;
import it.polimi.ingsw.messages.InfoMessage;
import it.polimi.ingsw.messages.LoginMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.PlayMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.player.Player;
import java.io.FileNotFoundException;
import java.util.Vector;

/**
 * Main controller class that manage the other controller and do the correct phase and correct
 * player check
 */
public class MainController {

  // Controllers
  private final TurnManager turnManager;
  private final InfoController infoController;
  private final PlayController playController;
  // Attributes
  private Game game;
  private final Vector<Team> teams;
  private Player currentPlayer;
  // Game parameters
  private int numberOfPlayers;
  private boolean isGameExpert;

  /**
   * Constructor for the MainController Class
   */
  public MainController() {

    teams = new Vector<>();

    turnManager = new TurnManager();
    infoController = new InfoController();
    playController = new PlayController();
  }

  /**
   * TODO
   */
  public void elaborateMessage(Message msg) throws FileNotFoundException {

    if (msg.getMessageMain() == MessageMain.INFO) {
      infoController.elaborateMessage((InfoMessage) msg, game);
    } else if (msg.getMessageMain() == MessageMain.LOGIN) {
      elaborateLoginMessage((LoginMessage) msg);
    } else {
      elaborateGameMessage(msg);
    }
  }

  /**
   * @param msg
   */
  private void elaborateLoginMessage(LoginMessage msg) throws FileNotFoundException {

    // Check if phase is correct
    if (turnManager.getMainGamePhase() == MessageMain.LOGIN &&
        msg.getMessageSecondary() == turnManager.getSecondaryPhase()) {

      switch (msg.getMessageSecondary()) {
        case GAME_PARAMS:

          // TODO

          // If parameters are okay set them and change game state
          if (true) {
            numberOfPlayers = 0; // TODO
            isGameExpert = true; // TODO
            turnManager.setNumberOfUsers(numberOfPlayers);
            turnManager.setNumberStudentsFromEntrance(100000); // TODO
            turnManager.updateCounters();
            turnManager.changeState();
          }

          break;

        case PLAYER_PARAMS:

          Player newPlayer = null;  // TODO

          // If player is not null, add it to a team, change state
          if (newPlayer != null) {

            // Where a player goes is based on the number of players for this game
            if (numberOfPlayers == 4) {  // TODO: don't like magic numbers
              if (teams.size() < 2) {
                Team newTeam = new Team(teams.size(), Towers.values()[teams.size()]);
                newTeam.addPlayer(newPlayer);
                teams.add(newTeam);
              } else {
                long nCurrentPlayers = teams.stream().map(team -> team.getPlayers().size()).count();
                teams.elementAt((int) nCurrentPlayers % 2).addPlayer(newPlayer);
              }

            } else {
              Team newTeam = new Team(teams.size(), Towers.values()[teams.size()]);
              newTeam.addPlayer(newPlayer);
              teams.add(newTeam);
            }

            // Change state
            turnManager.updateCounters();
            turnManager.changeState();

            // If players are all in, setup game
            if (teams.stream().map(team -> team.getPlayers().size()).count() == numberOfPlayers) {
              setupGame();
            }
          }

          break;

        default:
          break;
      }
    }
  }

  /**
   * @param msg
   */
  private void elaborateGameMessage(Message msg) {

    boolean everythingOkay = true;

    // Check player is current player OR phase is login
    everythingOkay = !(msg.getPlayerId() == 0);  // ;

    // Check phase is the right one
    everythingOkay = everythingOkay &&
        !(msg.getMessageMain() == turnManager.getMainGamePhase()) &&
        !(msg.getMessageSecondary() == turnManager.getSecondaryPhase());

    // Character todo

    if (everythingOkay) {
      switch (msg.getMessageMain()) {
        case MOVE:
          // TODO: everythingOkay = moveController.elaborateMessage(msg);
          break;
        case PLAY:
          everythingOkay = playController.elaborateMessage((PlayMessage) msg, game);
          break;
        default:
          break;
      }
    }

    if (everythingOkay) {
      // Update turn
      turnManager.updateCounters();
      turnManager.changeState();
    } else {
      // Send error message
      // TODO
    }
  }

  /**
   * It creates and set the new game, setting the current phase and chose the game order
   *
   * @throws FileNotFoundException If the json file are not found
   */
  public void setupGame() throws FileNotFoundException {

    this.game = new Game(0, isGameExpert);

  }

}
