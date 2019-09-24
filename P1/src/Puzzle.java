/**
 * Puzzle abstract class presents a puzzle, currently including Rubik Cube and 8-puzzle
 * Author: Jiaqi Yang
 * Date: 9/23/2019
 */
public class Puzzle{
    protected State state = new EPState();
    protected int maxNodes = 0;

    public void executeCommand(String command){
        //setState <state> command sets the puzzle state, like "setState b12 345 678"
        if(command.matches("setState ([b1-8]{3}) ([b1-8]{3}) ([b1-8]{3})")){
            System.out.println("The input state is " + command.substring("setState ".length()));
            setState(command.substring("setState".length()).replaceAll("\\s",""));
        }
        else if(command.matches("printState")){
            state.printState();
        }
        else if(command.matches("move ([a-z]+)")){
            System.out.println("The input movement is " + command.substring("move ".length()));
        }
        else{
            System.out.println("The input command" + command + " is not valid.");
        }
    }

    public void setState(String input){
        state.setStateList(input);
    }

    public void setState(State state){
        this.state = state.copyState();
    }

    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle();
        puzzle.executeCommand("setState 345 b21 688");
        puzzle.executeCommand("printState");
        puzzle.executeCommand("setState 345 b21 678");
        puzzle.executeCommand("printState");
        puzzle.executeCommand("move up");
    }
}
