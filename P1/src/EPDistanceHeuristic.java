import java.util.ArrayList;

/**
 * EPDistanceHeuristic class provides the functionality of calculating the h2 heuristic value of a given 8-puzzle state
 * Author: Jiaqi Yang
 * Date: 9/23/2019
 */
public class EPDistanceHeuristic implements Heuristic{

    //The identifier of heuristic used by A-star search by the sum of the distances of the tiles from their goal positions
    private static final String EP_Misplaced_Heuristic_Name = "h2";

    //Calculate the heuristic value of a given 8-puzzle state with function h2
    @Override
    public double calculate(State state){
        if(!(state instanceof EPState)){
            System.out.println("Invalid state provided");
            System.exit(1);
        }

        ArrayList<Integer> stateList = ((EPState) state).getStateList();
        int count = 0;
        int sum = 0;
        //The heuristic is the sum of Manhattan distances of each tile
        for(Integer integer: stateList){
            if(integer != 0) {
                // % 3 operation provides the horizontal relative position, / 3 operation provides the vertical relative position
                sum += Math.abs(integer % 3 - count % 3) + Math.abs(integer / 3 - count / 3);
            }
            count++;
        }
        return sum;
    }

    //Return the String identifier of current heuristic function, h2
    @Override
    public String getName(){
        return EP_Misplaced_Heuristic_Name;
    }

    //Shortcut to rapidly test the normal case of the methods
    public static void main(String[] args) {
        EPState state_goal = new EPState();
        EPState state_result_one = new EPState("102345678");
        EPState state_result_two = new EPState("210345678");
        EPState state_result_three = new EPState("512340678");
        EPState state_result_four = new EPState("812345670");
        EPState state_result_eighteen = new EPState("724506831");

        EPDistanceHeuristic epDistanceHeuristic = new EPDistanceHeuristic();
        System.out.println(epDistanceHeuristic.getName());
        System.out.println(epDistanceHeuristic.calculate(state_goal));
        System.out.println(epDistanceHeuristic.calculate(state_result_one));
        System.out.println(epDistanceHeuristic.calculate(state_result_two));
        System.out.println(epDistanceHeuristic.calculate(state_result_three));
        System.out.println(epDistanceHeuristic.calculate(state_result_four));
        System.out.println(epDistanceHeuristic.calculate(state_result_eighteen));
    }
}
