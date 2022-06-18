package it.polimi.ingsw.controller;

import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.WinnerMessage;
import it.polimi.ingsw.model.CloudTile;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.player.Player;
import java.util.Arrays;

public class WinnerController {

  public WinnerController() {
  }

  public Message checkWinner(Game game) {
    int sumOfStudents = 0;
    WinnerMessage winnerMessage = null;

    //first winner condition
    for (Team team : game.getTeams()) {
      if (team.getAvailableTowers() == 0) {
        winnerMessage = new WinnerMessage(MessageSecondary.WINNER);
        winnerMessage.setWinnerId(team.getId());
      }
    }

    //second winner condition
    if (game.getMainBoard().getIslands().size() == 3) {
      winnerMessage = findWinner(game);

    }

    //third winner condition
    for (Island island : game.getMainBoard().getIslands()) {
      sumOfStudents += Arrays.stream(island.getStudents()).sum();
    }
    for (Team team : game.getTeams()) {
      for (Player player : team.getPlayers()) {
        sumOfStudents += Arrays.stream(player.getPlayerBoard().getEntrance().getStudents()).sum();
        sumOfStudents += Arrays.stream(player.getPlayerBoard().getDiningRoom().getStudents()).sum();
      }
    }
    for (CloudTile cloudTile : game.getCloudTiles()) {
      sumOfStudents += Arrays.stream(cloudTile.getStudents()).sum();
    }
    if (sumOfStudents >= 130) {
      winnerMessage = findWinner(game);
    }
    return winnerMessage;
  }

  private WinnerMessage findWinner(Game game) {
    int idTeamBuildMostTowers = -1;
    int minTowerAvailable = 8;
    for (Team team : game.getTeams()) {
      if (team.getAvailableTowers() < minTowerAvailable) {
        minTowerAvailable = team.getAvailableTowers();
        idTeamBuildMostTowers = team.getId();
      }
    }
    WinnerMessage winnerMessage = new WinnerMessage(MessageSecondary.WINNER);
   winnerMessage.setWinnerId(idTeamBuildMostTowers);
   winnerMessage.setPlayerId(-1);
    return winnerMessage;
  }
}
