package it.polimi.ingsw.view;

import it.polimi.ingsw.model.CloudTile;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.MotherNature;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.PlayerBoard;
import java.util.HashMap;
import java.util.Vector;

public interface View {

  void loginRequest();

  void setViewId(int id);

  void setNickname();

  void selectNumberOfPlayer();

  void selectExpertMode();

  void showLobby(HashMap<Integer,String> lobby);

  void printPlayableAssistants(Vector<Assistant> playableDeck);

  void printPlayedAssistantsFromOthers(Vector<Assistant> playedAssistant);

  void playAssistant();

  //to print at the beginning of each player turn

  void showActualTurn(String actualPlayerNickname);

  void printPurchasableCharacter(Vector<Character> purchasableCharacter);

  void printMainBoard(Vector<Island> islands, MotherNature motherNature);

  void printAllPlayerBoard(HashMap<Integer, PlayerBoard> allPlayersPlayerBoard);

  void printCloudTile(Vector<CloudTile> cloudTiles);



  void moveStudents();

  //print player board again

  void moveMotherNature();

  // print main board again

  void purchaseCharacter();

  void takeCloudTile();


  void printError(String errorString);




}
