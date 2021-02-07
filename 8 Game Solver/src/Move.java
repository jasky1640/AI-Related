/**
 * Move is an interface that represent a move in the given state
 * Author: Jiaqi Yang
 * Date: 9/23/2019
 */
public interface Move {

    //Allow the tile to be moved in different directions
    State move(State state);

    //Get the identifier of the move direction
    String getMoveName();

    //Validate if the movement is legal
    boolean isLegalMovement(State state);
}
