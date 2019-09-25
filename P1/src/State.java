import java.util.ArrayList;
import java.util.Random;

/**
 * State abstract class represents a state for a puzzle
 * Author: Jiaqi Yang
 * Date: 9/23/2019
 */
public abstract class State {
    //Set the stateList to the goal state of the puzzle
    public abstract void setGoalState();

    //Test if the current state is the goal state
    public abstract boolean isGoalState();

    //Set the stateList to represent the state of input
    public abstract void setStateList(String state);

    //Validate the input puzzle representation
    public abstract boolean isValidState(String state);

    //Print the current puzzle state
    public abstract void printState();

    //Get the hash code of the current state
    public abstract int getHash();

    //Get a new instance of State
    public abstract State copyState();

    //Validate if the current state is solvable
    public abstract boolean isSolvable();

    //Randomly move from the goal state by passing number of steps
    public static State randomizeState(State state, ArrayList<Move> move, int numberOfSteps, ArrayList<Move> movesToCurrentNode){
        if(movesToCurrentNode == null){
            movesToCurrentNode = new ArrayList<>();
        }

        State output = state.copyState();
        Random random = new Random();
        int moveDirection;
        //Randomly move from the goal state for numberOfSteps times
        for(int index = 0; index < numberOfSteps; index++){
            moveDirection = random.nextInt(move.size());
            while (!move.get(moveDirection).isLegalMovement(output)){
                moveDirection = random.nextInt(move.size());
            }
            movesToCurrentNode.add(move.get(moveDirection));
            output = move.get(moveDirection).move(output);
        }
        return output;
    }
}
