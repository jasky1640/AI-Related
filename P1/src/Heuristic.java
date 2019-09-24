/**
 * EPDistanceHeuristic interface provides the functionality of calculating the heuristic value of a given state
 * Author: Jiaqi Yang
 * Date: 9/23/2019
 */
public interface Heuristic {

    //Calculate the heuristic value of a given state
    double calculate(State state);

    //Return the String identifier of current heuristic function
    String getName();
}
