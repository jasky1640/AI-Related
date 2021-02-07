import java.util.ArrayList;

/**
 * EPMisplacedHeuristic class provides the functionality of calculating the h1 heuristic value of a given 8-puzzle state
 * Author: Jiaqi Yang
 * Date: 9/23/2019
 */
public class EPMisplacedHeuristic implements Heuristic{

    //The identifier of heuristic used by A-star search by the number of misplaced tiles
    private static final String EP_Misplaced_Heuristic_Name = "h1";

    //Calculate the heuristic value of a given 8-puzzle state with function h1
    @Override
    public double calculate(State state) {
        if(!(state instanceof EPState)){
            System.out.println("Invalid state provided");
            System.exit(1);
        }

        ArrayList<Integer> stateList = ((EPState) state).getStateList();
        int count = 0;
        int sum = 0;
        //If a tile is out of position, the heuristic is increased by 1
        for(Integer integer: stateList){
            if(integer != 0 && integer != count){
                sum++;
            }
            count++;
        }
        return sum;
    }

    //Return the String identifier of current heuristic function, h1
    @Override
    public String getName() {
        return EP_Misplaced_Heuristic_Name;
    }
}
