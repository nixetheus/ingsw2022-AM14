package it.polimi.ingsw.model.board;

import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;
import it.polimi.ingsw.helpers.Constants;
import java.util.ArrayList;

public class MainBoard {
  //Attributes
  private ArrayList<Island> islands = new ArrayList<>(Constants.getInitialNumIslands());
  private MotherNature motherNature;
  /**
   *Constructor method: it creates Islands array, and it places on each of them one student
   */
  public MainBoard() {
    /*int randomPosMotherNature = (int)(Math.random() * Constants.getInitialNumIslands());
    MotherNature motherNature = new MotherNature(randomPosMotherNature);
    for(int i = 0; i < Constants.getInitialNumIslands(); i++) {
      if(i != randomPosMotherNature)
        islands.add(new Island().addStudent());
    }
     */
  }
}
