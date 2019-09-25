import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

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
    protected int maxNodes = 10000;

    //Get the corresponding Move represented by String moveName
    public abstract Move getMoveByName(String moveName);

    //Get the corresponding Heuristic represented by version number
    public abstract Heuristic getHeuristicByName(int heuristicVersion);

    public abstract ArrayList<Move> getMoveList();
    public abstract HashMap<String, Move> getMoveMap();
    public abstract HashMap<Integer, Heuristic> getHeuristicMap();

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
            state.setGoalState();
            state = State.randomizeState(state, getMoveList(), moveNumber, null);
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
            System.out.println("Solve the puzzle with A-star and heuristic function h" + command.substring("solve A-star h".length()));
            Integer heuristicVersion;
            try{
                heuristicVersion = Integer.parseInt(command.substring("solve A-star h".length()));
            }
            catch (NumberFormatException e){
                System.out.println("The input heuristic version is invalid. Set to default value h2");
                heuristicVersion = Default_Heuristic_Version;
            }
            solveAStar(getHeuristicByName(heuristicVersion));
        }

        //solve beam <k> solves the puzzle from its current state by adapting local beam search with k states.
        else if(command.matches("solve beam (\\d+)")){
            System.out.println("Solve the puzzle with local beam search with state number " + command.substring("solve beam ".length()));
            Integer beamNumber;
            try{
                beamNumber = Integer.parseInt(command.substring("solve beam ".length()));
            }
            catch (NumberFormatException e){
                System.out.println("The input local beam state is invalid. Set to default value 10");
                beamNumber = Default_Beam_State;
            }
            solveBeam(beamNumber);
        }

        //Invalid Command
        else{
            System.out.println("The input command " + command + " is not valid.");
        }
    }

    //Solve the puzzle from its current state using A-star search with indicated heuristic
    public Solution solveAStar (Heuristic heuristic){
        if(!state.isSolvable()){
            System.out.println("The current state is unsolvable. Please generate a solvable state randomizing from goal state");
            return new Solution(0, -1);
        }

        else{
            ArrayList<Integer> exploredNodesHash = new ArrayList<>();                   //The ArrayList stores all the explored State's Hash code
            boolean reachGoalState = false;                                             //The boolean indicates whether goal state is found
            SearchNode goalStateSearchNode = null;                                      //The SearchNode stored the one who reach the goal state
            PriorityQueue<SearchNode> priorityQueue = new PriorityQueue<>();            //The PriorityQueue stored all the unexplored SearchNode in the order of f(n) = g(n) + h(n)

            //Add the starting point as a SearchNode into the priorityQueue
            priorityQueue.add(new SearchNode(state, heuristic.calculate(state)));

            //Loop until the goal state is found or the number of explored SearchNode is more than the preset threshold
            while(reachGoalState == false && exploredNodesHash.size() < maxNodes){
                //Get the next unexplored node in the priority queue and mark it as explored
                SearchNode searchNode = priorityQueue.poll();
                while(exploredNodesHash.contains(searchNode.getState().getHash())){
                    searchNode = priorityQueue.poll();
                    if(searchNode == null){
                        System.out.println("The priority queue is empty. The puzzle is unable to be solved.");
                        return new Solution(exploredNodesHash.size(), -1);
                    }
                }
                exploredNodesHash.add(searchNode.getState().getHash());

                //If the searched state is the goal state, exit the loop
                if(searchNode.getState().isGoalState()){
                    goalStateSearchNode = searchNode;
                    reachGoalState = true;
                }
                //If it isn't the goal state, keep moving
                else{
                    //For all possible moves of the puzzle
                    for(Move move: getMoveList()){
                        //If the move is legal, then try to move it
                        if(move.isLegalMovement(searchNode.getState())){
                            State movedState = move.move(searchNode.getState());
                            //If the moved state is explored, drop it
                            if(!exploredNodesHash.contains(movedState.getHash())){
                                //Update the moves leaded to the movedState
                                ArrayList<Move> movesToMovedStateNode = searchNode.getMovesToCurrentNode();
                                movesToMovedStateNode.add(move);
                                priorityQueue.add(new SearchNode(movedState, heuristic.calculate(movedState), movesToMovedStateNode, searchNode.getCostToCurrentNode() + 1));
                            }
                        }
                    } //The end of for loop
                } //The end of else clause
            } //The end of while loop

            //If there is no goal state search node found, then return failure Solution
            if(goalStateSearchNode == null){
                System.out.println("The puzzle is unable to be solved within " + maxNodes + " node consideration limitation. Try again by increasing the threshold");
                return new Solution(exploredNodesHash.size(), -1);
            }

            //Backtrace the SearchNode that reaches the goal state and print the path it takes from the starting state
            else{
                ArrayList<Move> goalMoveList = goalStateSearchNode.getMovesToCurrentNode();

                //Print the starting state and set up StringBuilder
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                System.out.print("The State starting with: ");
                State nextState =state.copyState();
                nextState.printState();

                //Go through all the moves leading to the goal state from the beginning
                for(int index = 0; index < goalMoveList.size(); index++){
                    nextState = goalMoveList.get(index).move(nextState);

                    //Print the middle-way state and work on StringBuilder
                    System.out.print("The next step is to move " + goalMoveList.get(index).getMoveName() + ". ");
                    nextState.printState();
                    sb.append(goalMoveList.get(index).getMoveName());
                    if(index != goalMoveList.size() - 1){
                        sb.append(", ");
                    }
                }
                sb.append("]");
                System.out.println("We reach goal state with " + goalMoveList.size() + " steps");
                System.out.println(sb.toString());
                return new Solution(goalStateSearchNode.getMovesToCurrentNode().size(), exploredNodesHash.size());
            }
        }
    }

    //Solve the puzzle from its current state by adapting local beam search with input amount of states
    public Solution solveBeam(int numberOfState){
        if(!state.isSolvable()){
            System.out.println("The current state is unsolvable. Please generate a solvable state randomizing from goal state");
            return new Solution(0, -1);
        }

        else{
            Heuristic heuristic = getHeuristicByName(2);            //The heuristic used by local beam search is h2, the EPDistanceHeuristic
            PriorityQueue<SearchNode> priorityQueue = new PriorityQueue<>();        //The priority queue that stores the successors
            ArrayList<SearchNode> bestkNodes = new ArrayList<>();                   //The ArrayList that stores the k nodes with smallest heuristic value
            boolean reachGoalState = false;                                         //The boolean indicates whether goal state is found
            SearchNode goalStateSearchNode = null;                                  //The SearchNode stored the one who reach the goal state
            ArrayList<Integer> exploredNodesHash = new ArrayList<>();               //The ArrayList stores all the explored State's Hash code
            int generation = 0;                                                     //The generation stored which generation are the current successors
            PriorityQueue<SearchNode> exploredNodesQueue = new PriorityQueue<>();   //The list that temporarily holds the explored successors

            //Initialize the bestkNodes by generating k random states
            for(int index = 0; index < numberOfState && reachGoalState == false; index++){

                State generatedState;
                ArrayList<Move> movesToCurrentNode = new ArrayList<>();
                //The first one will be the current state itslef
                if(index == 0){
                    generatedState = state.copyState();
                }
                //The later ones will be generated by randomly moving 5 steps from the current state
                else{
                    generatedState = State.randomizeState(state, getMoveList(), 10, movesToCurrentNode);
                }

                SearchNode generatedSearchNode = new SearchNode(generatedState, heuristic.calculate(generatedState), movesToCurrentNode);
                //Count the number of explored node
                if(!exploredNodesHash.contains(generatedSearchNode.getState().getHash())){
                    exploredNodesHash.add(generatedSearchNode.getState().getHash());
                }
                //If the randomly generated node is goal state, we don't need to find anymore
                if(generatedSearchNode.getState().isGoalState()){
                    reachGoalState = true;
                    goalStateSearchNode = generatedSearchNode;
                }
                //Else we put the generated node into the bestkNodes list
                else{
                    bestkNodes.add(generatedSearchNode);
                }
            }

            //Loop through the entire bestkNodes list and find all successor
            //If none of them are goal state, take best k of them and put into bestkNodes
            while(reachGoalState == false && exploredNodesHash.size() < maxNodes){
                //Traverse through all the searchNode in the bestkNodes list
                for(SearchNode searchNode: bestkNodes){
                    //Get all the possible move of the puzzle
                    for(Move move: getMoveList()) {
                        //If the move is legal, then try to move it
                        if (move.isLegalMovement(searchNode.getState())) {
                            State movedState = move.move(searchNode.getState());

                            //Update the moves leaded to the movedState
                            ArrayList<Move> movesToMovedStateNode = searchNode.getMovesToCurrentNode();
                            movesToMovedStateNode.add(move);
                            SearchNode movedSearchNode = new SearchNode(movedState, heuristic.calculate(movedState), movesToMovedStateNode);

                            //If it is goal state, we don't need to find anymore
                            if(movedSearchNode.getState().isGoalState()){
                                reachGoalState = true;
                                goalStateSearchNode = movedSearchNode;
                            }
                            //else we put it into
                            else {
                                priorityQueue.add(movedSearchNode);
                            }
                        }//end of if clause
                    }//end of the inner for loop
                }//end of the outer for loop

                //At this point, the priorityQueue includes all the successor
                //We get the best k successors from priorityQueue and store them in the bestkNodes list
                if(!reachGoalState){
                    bestkNodes.clear();
                    int bestCount = 0;
                    //When we haven't find k best nodes
                    while(bestCount < numberOfState){
                        SearchNode currentBest = priorityQueue.poll();
                        //If the priorityQueue is empty
                        if(currentBest == null){
                            SearchNode repeatedBest = exploredNodesQueue.poll();
                            //If even repeated List is empty
                            if(repeatedBest == null) {
                                break;
                            }
                            //If there is still nodes in repeated list
                            else{
                                bestkNodes.add(repeatedBest);
                                bestCount++;
                            }
                        }

                        //If there is still something in priorityQueue
                        else {
                            //If the state is explored before, we put it into the repeated list
                            if (exploredNodesHash.contains(currentBest.getState().getHash())) {
                                exploredNodesQueue.add(currentBest);
                            }
                            //If not explored before
                            else {
                                //If the state is repeated
                                if (!bestkNodes.contains(currentBest)) {
                                    bestkNodes.add(currentBest);
                                    bestCount++;
                                }
                            }
                        }
                    }
                    //Clear the priority queues after finding best k nodes
                    priorityQueue.clear();
                    exploredNodesQueue.clear();

                    //Print out the generation information
                    generation++;
                    System.out.println("This is " + generation + "th generation of successors.");
                }
            }//end of the while loop

            //If there is no goal state search node found, then return failure Solution
            if(goalStateSearchNode == null){
                System.out.println("The puzzle is unable to be solved within " + maxNodes + " node consideration limitation. Try again by increasing the threshold");
                return new Solution(exploredNodesHash.size(), -1);
            }

            //Backtrace the SearchNode that reaches the goal state and print the path it takes from the starting state
            else{
                ArrayList<Move> goalMoveList = goalStateSearchNode.getMovesToCurrentNode();

                //Print the starting state and set up StringBuilder
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                System.out.print("The State starting with: ");
                State nextState =state.copyState();
                nextState.printState();

                //Go through all the moves leading to the goal state from the beginning
                for(int index = 0; index < goalMoveList.size(); index++){
                    nextState = goalMoveList.get(index).move(nextState);

                    //Print the middle-way state and work on StringBuilder
                    System.out.print("The next step is to move " + goalMoveList.get(index).getMoveName() + ". ");
                    nextState.printState();
                    sb.append(goalMoveList.get(index).getMoveName());
                    if(index != goalMoveList.size() - 1){
                        sb.append(", ");
                    }
                }
                sb.append("]");
                System.out.println("We reach goal state with " + goalMoveList.size() + " steps");
                System.out.println(sb.toString());
                return new Solution(goalStateSearchNode.getMovesToCurrentNode().size(), exploredNodesHash.size());
            }
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
