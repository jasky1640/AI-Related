/**
 * Puzzle abstract class presents a puzzle, currently including Rubik Cube and 8-puzzle
 * Author: Jiaqi Yang
 * Date: 9/23/2019
 */
public abstract class Puzzle{

    private static final int Default_Move_Number = 100;
    private static final int Default_Maximum_Nodes_Number = 10000;
    private static final int Default_Heuristic_Version = 2;
    private static final int Default_Beam_State = 10;

    protected State state;
    protected int maxNodes = 0;

    public abstract Move getMoveByName(String moveName);

    //Execute command inputs
    public void executeCommand(String command){
        command = command.trim();
        //setState <state> command sets the puzzle state, like "setState b12 345 678"
        if(command.matches("setState ([b1-8]{3}) ([b1-8]{3}) ([b1-8]{3})")){
            System.out.println("The input state is " + command.substring("setState ".length()));
            setState(command.substring("setState".length()).replaceAll("\\s",""));
        }

        //printState command prints the current puzzle state
        else if(command.matches("printState")){
            state.printState();
        }

        //move <direction> command move the puzzle to a specific direction
        else if(command.matches("move ([a-z]+)")){
            System.out.println("The input movement is " + command.substring("move ".length()));
            makeMove(getMoveByName(command.substring("move ".length())));
        }

        //randomizeState <n> command makes n random moves from the goal state
        else if(command.matches("randomizeState (\\d+)")){
            System.out.println("The number of random moves from the goal state is " + command.substring("randomizeState ".length()));
            Integer moveNumber;
            try{
                moveNumber = Integer.parseInt(command.substring("randomizeState ".length()));
            } catch (NumberFormatException e) {
                System.out.println("The input random move number is invalid. Set to default value 100");
                moveNumber = Default_Move_Number;
            }
            //RandomizeState method
        }

        //maxNodes <n> specifies the maximum number of nodes to be considered during a search.
        else if(command.matches("maxNodes (\\d+)")){
            System.out.println("The maximum number of nodes is set to " + command.substring("maxNodes ".length()));
            Integer maxNodes;
            try{
                maxNodes = Integer.parseInt(command.substring("maxNodes ".length()));
            }
            catch (NumberFormatException e){
                System.out.println("The input maximum number of nodes is invalid. Set to default value 10000");
                maxNodes = Default_Maximum_Nodes_Number;
            }
            this.maxNodes = maxNodes;
        }

        //solve A-star <heuristic> solves the puzzle from its current state using A-star search using heuristic equal to “h1”or “h2”
        else if(command.matches("solve A-star h([12])")){
            System.out.println("Solve the puzzle with A-star and heuristic function " + command.substring("solve A-star h".length()));
            Integer heuristicVersion;
            try{
                heuristicVersion = Integer.parseInt(command.substring("solve A-star h".length()));
            }
            catch (NumberFormatException e){
                System.out.println("The input heuristic version is invalid. Set to default value h2");
                heuristicVersion = Default_Heuristic_Version;
            }
            //Solve A-star Method
        }

        //solve beam <k> solves the puzzle from its current state by adapting local beam search with k states.
        else if(command.matches("solve beam [\\d+]")){
            System.out.println("Solve the puzzle with local beam search with state number" + command.substring("solve beam ".length()));
            Integer beamNumber;
            try{
                beamNumber = Integer.parseInt(command.substring("solve beam ".length()));
            }
            catch (NumberFormatException e){
                System.out.println("The input local beam state is invalid. Set to default value 10");
                beamNumber = Default_Beam_State;
            }
            //Solve Beam Method
        }

        //Invalid Command
        else{
            System.out.println("The input command" + command + " is not valid.");
        }
    }

    //Set the state based the state representation of String input
    public void setState(String input){
        state.setStateList(input);
    }

    //Set the state based on the input state
    public void setState(State state){
        this.state = state.copyState();
    }

    //Move the current puzzle state by the passing move
    public void makeMove(Move move){
        if(!move.isLegalMovement(state)){
            System.out.println("The input move " + move.getMoveName() + " is not valid for the current state.");
        }
        else{
            state = move.move(state);
        }
    }
}
