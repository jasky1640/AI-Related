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

    //Shortcut to rapidly test the normal case of the methods
    public static void main(String[] args) {
        EPState state_goal = new EPState();
        EPState state_missing_one = new EPState("812345670");
        EPState state_missing_two = new EPState("012345687");
        EPState state_missing_three = new EPState("813245670");
        EPState state_missing_four = new EPState("012354687");
        EPState state_missing_eight = new EPState("724506831");

        EPMisplacedHeuristic epMisplacedHeuristic = new EPMisplacedHeuristic();
        System.out.println(epMisplacedHeuristic.getName());
        System.out.println(epMisplacedHeuristic.calculate(state_goal));
        System.out.println(epMisplacedHeuristic.calculate(state_missing_one));
        System.out.println(epMisplacedHeuristic.calculate(state_missing_two));
        System.out.println(epMisplacedHeuristic.calculate(state_missing_three));
        System.out.println(epMisplacedHeuristic.calculate(state_missing_four));
        System.out.println(epMisplacedHeuristic.calculate(state_missing_eight));
    }
}
