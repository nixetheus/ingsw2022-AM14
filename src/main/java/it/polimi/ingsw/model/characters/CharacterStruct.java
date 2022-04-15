package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.board.MainBoard;
import it.polimi.ingsw.model.board.StudentsBag;
import it.polimi.ingsw.model.player.Player;
import java.util.Vector;

public class CharacterStruct {

  public int numIsland;
  public Game currentGame;
  public Vector<Team> teams;
  public int[] studentsOpTo;
  public MainBoard mainBoard;
  public int[] studentsOpFrom;
  public Player currentPlayer;
  public int motherNatureMoves;
  public Color color;
  public StudentsBag studentsBag;
}
