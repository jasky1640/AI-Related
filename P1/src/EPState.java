import java.util.ArrayList;
import java.util.Arrays;

/**
 * EPState class represents a 8-puzzle state in the form of an ArrayList
 * Author: Jiaqi Yang
 * Date: 9/23/2019
 */
public class EPState extends State{

    //The representation of a 8-puzzle state
    private ArrayList<Integer> stateList = new ArrayList<>();

    public EPState(){
        setGoalState();
    }

    public EPState(String state){
        if(isValidState(state)) {
            setStateList(state);
        }
        else{
            System.out.println("The input state is invalid. The state is set as goal state instead");
            setGoalState();
        }
    }

    public EPState(ArrayList<Integer> inputList){
        stateList = inputList;
    }

    public ArrayList<Integer> getStateList() {
        return stateList;
    }

    //Get a new instance of EPState
    public State copyState(){
        return new EPState(getStateList());
    }

    //Set the stateList to the goal state of 8-puzzle state
    public void setGoalState(){
        stateList.clear();
        stateList.add(0);
        stateList.add(1);
        stateList.add(2);
        stateList.add(3);
        stateList.add(4);
        stateList.add(5);
        stateList.add(6);
        stateList.add(7);
        stateList.add(8);
    }

    //Test if the current state is the goal state
    public boolean isGoalState(){
        int count = 0;
        for(Integer integer: getStateList()){
            if(integer != count) {
                return false;
            }
            count++;
        }
        return true;
    }

    //Set the stateList to represent the state of input 8-puzzle
    public void setStateList(String state) {
        if(!isValidState(state)){
            System.out.println("The input state is invalid.");
            return;
        }

        stateList.clear();
        //Replace b in the input String with 0 and convert it into a char array
        char[] inputStateList = state.replace('b', '0').toCharArray();
        for(char c: inputStateList){
            stateList.add(Character.getNumericValue(c));
        }
    }

    //Validate the input 8-puzzle representation
    public boolean isValidState(String state){
        if(state.length() != 9) {
            return false;
        }

        //Replace b in the input String with 0 and convert it into a char array
        char[] inputStateList = state.replace('b', '0').toCharArray();
        Arrays.sort(inputStateList);

        int count = 0;
        //Test if the input is a valid 8-puzzle representation
        for(char c: inputStateList){
            char current = (char)('0' + count);
            if(c != current) {
                return false;
            }
            count++;
        }
        return true;
    }

    //Print the current 8-puzzle state
    public void printState(){
        StringBuilder sb = new StringBuilder();
        sb.append("The current 8-puzzle state is\n");

        for(int index = 0; index < getStateList().size(); index++){
            if(index != 0 && index % 3 == 0){
               sb.append("\n");
            }
            if(getStateList().get(index) == 0) {
                sb.append(" \t");
            }
            else {
                sb.append(getStateList().get(index) + "\t");
            }
        }
        System.out.println(sb.toString());
    }

    //Get the hash code of the current state
    public int getHash(){
        return stateList.hashCode();
    }

    //Shortcut to rapidly test the normal case of the methods
    public static void main(String[] args) {
        EPState state = new EPState();

        //Test setEPGoalState method
        state.setGoalState();

        //Test printEPState method
        state.printState();

        //Test isGoalState method
        System.out.print("IsGoalState:");
        System.out.println(state.isGoalState());

        //Test isValidEPState and setStateList method
        System.out.print("0123456789 is valid:");
        System.out.println(state.isValidState("0123456789"));
        state.setStateList("0123456789");
        state.printState();

        System.out.print("blank is valid:");
        System.out.println(state.isValidState(""));
        state.setStateList("");
        state.printState();

        System.out.print("012345687 is valid:");
        System.out.println(state.isValidState("012345687"));
        state.setStateList("012345687");
        state.printState();

        //Test isGoalState method
        System.out.print("IsGoalState:");
        System.out.println(state.isGoalState());
    }
}
