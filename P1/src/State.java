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
}
