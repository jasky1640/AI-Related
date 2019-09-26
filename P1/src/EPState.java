import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * EPState class represents a 8-puzzle state in the form of an ArrayList
 * Author: Jiaqi Yang
 * Date: 9/23/2019
 */
public class EPState extends State{

    public static final double Number_Of_Solvable_States = 181440;
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
        return new ArrayList<>(stateList);
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

    //Reference: https://www.geeksforgeeks.org/check-instance-8-puzzle-solvable/
    //It is impossible to solve an instance of 8 puzzle if number of inversions is odd in the input state
    //Where an inversion is when a tile precedes another tile with a lower number on it
    @Override
    public boolean isSolvable(){
        int sumOfInversion = 0;
        //Traverse the entire 8-puzzle list
        for(int index1 = 0; index1 < stateList.size() - 1; index1++){
            //If not blank tile
            if(stateList.get(index1) != 0){
                //Traverse the rest of the list to find the count of inversion tile
                for(int index2 = index1 + 1; index2 < stateList.size(); index2++){
                    //If it is an inversion
                    if(stateList.get(index2) != 0 && stateList.get(index1) > stateList.get(index2)){
                        sumOfInversion++;
                    }
                }
            }
        }
        return sumOfInversion % 2 == 0;
    }

    //For the experiment part, the method return all solvable state for 8-puzzle
    public static ArrayList<State> getAllSolvableStates(){
        ArrayList<Integer> stateList = new ArrayList<>();
        stateList.add(0);
        stateList.add(1);
        stateList.add(2);
        stateList.add(3);
        stateList.add(4);
        stateList.add(5);
        stateList.add(6);
        stateList.add(7);
        stateList.add(8);
        ArrayList<ArrayList<Integer>> allStates = generatePermutation(stateList);
        ArrayList<State> allSolvableStates = new ArrayList<>();

        for(ArrayList<Integer> arrayList: allStates){
            EPState epState = new EPState(arrayList);
            if(epState.isSolvable()){
                allSolvableStates.add(epState);
            }
        }

        return allSolvableStates;
    }

    //Generate permutation for input arrayList
    private static ArrayList<ArrayList<Integer>> generatePermutation(ArrayList<Integer> arrayList){
        ArrayList<ArrayList<Integer>> output = new ArrayList<>();
        getPermutation(arrayList, 0, output);
        return output;
    }

    //Permutation algorithm from https://stackoverflow.com/questions/2920315/permutation-of-array
    private static void getPermutation(ArrayList<Integer> arrayList, int k, ArrayList<ArrayList<Integer>> output){
        for(int i = k; i < arrayList.size(); i++){
            Collections.swap(arrayList, i, k);
            getPermutation(arrayList, k + 1, output);
            Collections.swap(arrayList, k, i);
            if(k == arrayList.size() - 1){
                output.add(new ArrayList<>(arrayList));
            }
        }
    }
}
