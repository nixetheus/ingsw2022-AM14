package it.polimi.ingsw.model.board;

/**
 * This class is used to model the character of Mother Nature
 */

public class MotherNature {
    //Attributes
    private int position;

    /**
     * Constructor method for the MotherNature Class
     *
     * @param position  Integer chosen by players that represents the position of MotherNature in the group of island
     *
     */
    public MotherNature(int position) {
        this.position = position;
    }

    /**
     * This method moves MotherNature
     *
     * @param moves Number of movements of motherNature
     */
    public void move(int moves, int numIslands){
        position = (position + moves) % numIslands;
    }

    public int getPosition() {
        return position;
    }
}
