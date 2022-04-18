package it.polimi.ingsw.controller;

import java.util.HashMap;

/**
 * LoginController control any kind of error incurring in the log in phase
 */
public class LoginController {


  private final HashMap<Integer, String> playerIdNicknameMap;

  /**
   * Constructor method for the LoginController class
   */
  public LoginController() {
    this.playerIdNicknameMap = new HashMap<>();
  }

  /**
   * Method addPlayer adds a player into the LoginController and gives him/her a unique id
   *
   * @param playerNickname The name of the player that will be checked
   */
  public void addPlayer(String playerNickname) {
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
  }

  public HashMap<Integer, String> getPlayerIdNicknameMap() {
    return playerIdNicknameMap;
  }
}
