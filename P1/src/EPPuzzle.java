import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * EPPuzzle class presents a 8-puzzle game
 * Author: Jiaqi Yang
 * Date: 9/23/2019
 */
public class EPPuzzle extends Puzzle {

    private static HashMap<String, Move> moveMap;
    private static ArrayList<Move> moveList;
    private static HashMap<Integer, Heuristic> heuristicMap;

    static{
        //Create and initialize a moveList to store all possible Moves for 8-puzzle
        moveList = new ArrayList<>();
        moveList.add(new EPMoveUp());
        moveList.add(new EPMoveDown());
        moveList.add(new EPMoveLeft());
        moveList.add(new EPMoveRight());

        //Create and initialize a moveMap to store the name of move and corresponding Move for 8-puzzle
        moveMap = new HashMap<>();
        for(Move move: moveList){
            moveMap.put(move.getMoveName(), move);
        }

        //Create and initialize a heuristicMap to store the name of heuristic and corresponding Heuristic for 8-puzzle
        heuristicMap = new HashMap<>();
        heuristicMap.put(1, new EPMisplacedHeuristic());
        heuristicMap.put(2, new EPDistanceHeuristic());
    }

    public EPPuzzle(){
        //Initialized to the goal state of 8-puzzle
        state = new EPState();
    }

    //Get the corresponding Move represented by String moveName, in 8-puzzle up down left right
    @Override
    public Move getMoveByName(String moveName) {
         Move inputMove = moveMap.get(moveName);
         if(inputMove == null){
             System.out.println("The input random move number is invalid. The default left move is made.");
             return moveMap.get("left");
         }
         else{
             return inputMove;
         }
    }

    //Get the corresponding Heuristic represented by version number, in 8-puzzle h1 and h2
    @Override
    public Heuristic getHeuristicByName(int heuristicVersion) {
        Heuristic inputHeuristic = heuristicMap.get(heuristicVersion);
        if(inputHeuristic == null){
            System.out.println("The input heuristic number is invalid. The default h2 function is used.");
            return heuristicMap.get(2);
        }
        else{
            return inputHeuristic;
        }
    }

    //Get all the information for experiment part and discussion part for 8-puzzle
    @Override
    public void getExperimentAndDiscussionInfo() {
        ArrayList<Integer> maxNodesList = new ArrayList<>();
        maxNodesList.add(50);
        maxNodesList.add(100);
        maxNodesList.add(500);
        maxNodesList.add(1000);
        maxNodesList.add(5000);
        maxNodesList.add(10000);
        maxNodesList.add(50000);
        maxNodesList.add(100000);
        maxNodesList.add(500000);

        ArrayList<State> solvables = EPState.getAllSolvableStates();
        long startTime;
        long timeConsumed;
        int solved = 0;
        int sumOfLength = 0;
        int sumOfNodeExplored = 0;
        int averageLength;
        int averageNodeExplored;
        ArrayList<Solution> currentSolution = new ArrayList<>();
        Heuristic h1 = getHeuristicByName(1);
        Heuristic h2 = getHeuristicByName(2);
        System.out.println("Here we present all the information required for experiments and discussion section.");

        for(Integer currentMaxNodes: maxNodesList) {
            maxNodes = currentMaxNodes;
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("MaxNodes: " + currentMaxNodes);

            //A-star h1 part
            currentSolution.clear();
            solved = 0;
            sumOfLength = 0;
            sumOfNodeExplored = 0;
            StringBuilder sb_h1 = new StringBuilder();
            sb_h1.append("h1: ");

            startTime = System.currentTimeMillis();
            for (State currentState : solvables) {
                setState(currentState);
                currentSolution.add(solveAStar(h1, false));
            }
            timeConsumed = System.currentTimeMillis() - startTime;

            for (Solution solution : currentSolution) {
                if (solution.getStepsToGoal() != -1) {
                    solved++;
                    sumOfLength += solution.getStepsToGoal();
                    sumOfNodeExplored += solution.getNodesExplored();
                    if(solved % 10 == 0){
                        System.out.println("Solve 5000");
                    }
                }
            }
            if (solved != 0) {
                averageLength = sumOfLength / solved;
                averageNodeExplored = sumOfNodeExplored / solved;
            } else {
                averageLength = -1;
                averageNodeExplored = -1;
            }
            sb_h1.append("Solved: " + solved + ", Solvable fraction: " + String.format("%.2f", (solved / EPState.Number_Of_Solvable_States) * 100) + "%, Time consumed: " + timeConsumed + ", Average path length: " + averageLength + ", Average node explored: " + averageNodeExplored);
            System.out.println(sb_h1.toString());

            //A-star h2 part
            currentSolution.clear();
            solved = 0;
            sumOfLength = 0;
            sumOfNodeExplored = 0;
            StringBuilder sb_h2 = new StringBuilder();
            sb_h2.append("h2: ");
            startTime = System.currentTimeMillis();

            for (State currentState : solvables) {
                setState(currentState);
                currentSolution.add(solveAStar(h2, false));
            }
            timeConsumed = System.currentTimeMillis() - startTime;

            for (Solution solution : currentSolution) {
                if (solution.getStepsToGoal() != -1) {
                    solved++;
                    sumOfLength += solution.getStepsToGoal();
                    sumOfNodeExplored += solution.getNodesExplored();
                }
            }
            if (solved != 0) {
                averageLength = sumOfLength / solved;
                averageNodeExplored = sumOfNodeExplored / solved;
            } else {
                averageLength = -1;
                averageNodeExplored = -1;
            }
            sb_h2.append("Solved: " + solved + ", Solvable fraction: " + String.format("%.2f", (solved / EPState.Number_Of_Solvable_States) * 100) + "%, Time consumed: " + timeConsumed + ", Average path length: " + averageLength + ", Average node explored: " + averageNodeExplored);
            System.out.println(sb_h2.toString());

            //Local beam search k = 2 part
            currentSolution.clear();
            solved = 0;
            sumOfLength = 0;
            sumOfNodeExplored = 0;
            StringBuilder sb_lb9 = new StringBuilder();
            sb_lb9.append("local beam search (k = 2): ");
            startTime = System.currentTimeMillis();
            for (State currentState : solvables) {
                setState(currentState);
                currentSolution.add(solveBeam(2, false));
            }
            timeConsumed = System.currentTimeMillis() - startTime;
            for (Solution solution : currentSolution) {
                if (solution.getStepsToGoal() != -1) {
                    solved++;
                    sumOfLength += solution.getStepsToGoal();
                    sumOfNodeExplored += solution.getNodesExplored();
                }
            }
            if (solved != 0) {
                averageLength = sumOfLength / solved;
                averageNodeExplored = sumOfNodeExplored / solved;
            } else {
                averageLength = -1;
                averageNodeExplored = -1;
            }
            sb_lb9.append("Solved: " + solved + ", Solvable fraction: " + String.format("%.2f", (solved / EPState.Number_Of_Solvable_States) * 100) + "%, Time consumed: " + timeConsumed + ", Average path length: " + averageLength + ", Average node explored: " + averageNodeExplored);
            System.out.println(sb_lb9.toString());

            //Local beam search k = 3 part
            currentSolution.clear();
            solved = 0;
            sumOfLength = 0;
            sumOfNodeExplored = 0;
            StringBuilder sb_lb8 = new StringBuilder();
            sb_lb8.append("local beam search (k = 3): ");
            startTime = System.currentTimeMillis();
            for (State currentState : solvables) {
                setState(currentState);
                currentSolution.add(solveBeam(3, false));
            }
            timeConsumed = System.currentTimeMillis() - startTime;
            for (Solution solution : currentSolution) {
                if (solution.getStepsToGoal() != -1) {
                    solved++;
                    sumOfLength += solution.getStepsToGoal();
                    sumOfNodeExplored += solution.getNodesExplored();
                }
            }
            if (solved != 0) {
                averageLength = sumOfLength / solved;
                averageNodeExplored = sumOfNodeExplored / solved;
            } else {
                averageLength = -1;
                averageNodeExplored = -1;
            }
            sb_lb8.append("Solved: " + solved + ", Solvable fraction: " + String.format("%.2f", (solved / EPState.Number_Of_Solvable_States) * 100) + "%, Time consumed: " + timeConsumed + ", Average path length: " + averageLength + ", Average node explored: " + averageNodeExplored);
            System.out.println(sb_lb8.toString());

            //Local beam search k = 5 part
            currentSolution.clear();
            solved = 0;
            sumOfLength = 0;
            sumOfNodeExplored = 0;
            StringBuilder sb_lb7 = new StringBuilder();
            sb_lb7.append("local beam search (k = 5): ");
            startTime = System.currentTimeMillis();
            for (State currentState : solvables) {
                setState(currentState);
                currentSolution.add(solveBeam(5, false));
            }
            timeConsumed = System.currentTimeMillis() - startTime;
            for (Solution solution : currentSolution) {
                if (solution.getStepsToGoal() != -1) {
                    solved++;
                    sumOfLength += solution.getStepsToGoal();
                    sumOfNodeExplored += solution.getNodesExplored();
                }
            }
            if (solved != 0) {
                averageLength = sumOfLength / solved;
                averageNodeExplored = sumOfNodeExplored / solved;
            } else {
                averageLength = -1;
                averageNodeExplored = -1;
            }
            sb_lb7.append("Solved: " + solved + ", Solvable fraction: " + String.format("%.2f", (solved / EPState.Number_Of_Solvable_States) * 100) + "%, Time consumed: " + timeConsumed + ", Average path length: " + averageLength + ", Average node explored: " + averageNodeExplored);
            System.out.println(sb_lb7.toString());

            //Local beam search k = 10 part
            currentSolution.clear();
            solved = 0;
            sumOfLength = 0;
            sumOfNodeExplored = 0;
            StringBuilder sb_lb1 = new StringBuilder();
            sb_lb1.append("local beam search (k = 10): ");
            startTime = System.currentTimeMillis();
            for (State currentState : solvables) {
                setState(currentState);
                currentSolution.add(solveBeam(10, false));
            }
            timeConsumed = System.currentTimeMillis() - startTime;
            for (Solution solution : currentSolution) {
                if (solution.getStepsToGoal() != -1) {
                    solved++;
                    sumOfLength += solution.getStepsToGoal();
                    sumOfNodeExplored += solution.getNodesExplored();
                }
            }
            if (solved != 0) {
                averageLength = sumOfLength / solved;
                averageNodeExplored = sumOfNodeExplored / solved;
            } else {
                averageLength = -1;
                averageNodeExplored = -1;
            }
            sb_lb1.append("Solved: " + solved + ", Solvable fraction: " + String.format("%.2f", (solved / EPState.Number_Of_Solvable_States) * 100) + "%, Time consumed: " + timeConsumed + ", Average path length: " + averageLength + ", Average node explored: " + averageNodeExplored);
            System.out.println(sb_lb1.toString());

            //Local beam search k = 20 part
            currentSolution.clear();
            solved = 0;
            sumOfLength = 0;
            sumOfNodeExplored = 0;
            StringBuilder sb_lb2 = new StringBuilder();
            sb_lb2.append("local beam search (k = 20): ");
            startTime = System.currentTimeMillis();
            for (State currentState : solvables) {
                setState(currentState);
                currentSolution.add(solveBeam(20, false));
            }
            timeConsumed = System.currentTimeMillis() - startTime;
            for (Solution solution : currentSolution) {
                if (solution.getStepsToGoal() != -1) {
                    solved++;
                    sumOfLength += solution.getStepsToGoal();
                    sumOfNodeExplored += solution.getNodesExplored();
                }
            }
            if (solved != 0) {
                averageLength = sumOfLength / solved;
                averageNodeExplored = sumOfNodeExplored / solved;
            } else {
                averageLength = -1;
                averageNodeExplored = -1;
            }
            sb_lb2.append("Solved: " + solved + ", Solvable fraction: " + String.format("%.2f", (solved / EPState.Number_Of_Solvable_States) * 100) + "%, Time consumed: " + timeConsumed + ", Average path length: " + averageLength + ", Average node explored: " + averageNodeExplored);
            System.out.println(sb_lb2.toString());

            //Local beam search k = 30 part
            currentSolution.clear();
            solved = 0;
            sumOfLength = 0;
            sumOfNodeExplored = 0;
            StringBuilder sb_lb3 = new StringBuilder();
            sb_lb3.append("local beam search (k = 30): ");
            startTime = System.currentTimeMillis();
            for (State currentState : solvables) {
                setState(currentState);
                currentSolution.add(solveBeam(30, false));
            }
            timeConsumed = System.currentTimeMillis() - startTime;
            for (Solution solution : currentSolution) {
                if (solution.getStepsToGoal() != -1) {
                    solved++;
                    sumOfLength += solution.getStepsToGoal();
                    sumOfNodeExplored += solution.getNodesExplored();
                }
            }
            if (solved != 0) {
                averageLength = sumOfLength / solved;
                averageNodeExplored = sumOfNodeExplored / solved;
            } else {
                averageLength = -1;
                averageNodeExplored = -1;
            }
            sb_lb3.append("Solved: " + solved + ", Solvable fraction: " + String.format("%.2f", (solved / EPState.Number_Of_Solvable_States) * 100) + "%, Time consumed: " + timeConsumed + ", Average path length: " + averageLength + ", Average node explored: " + averageNodeExplored);
            System.out.println(sb_lb3.toString());

            //Local beam search k = 50 part
            currentSolution.clear();
            solved = 0;
            sumOfLength = 0;
            sumOfNodeExplored = 0;
            StringBuilder sb_lb4 = new StringBuilder();
            sb_lb4.append("local beam search (k = 50): ");
            startTime = System.currentTimeMillis();
            for (State currentState : solvables) {
                setState(currentState);
                currentSolution.add(solveBeam(50, false));
            }
            timeConsumed = System.currentTimeMillis() - startTime;
            for (Solution solution : currentSolution) {
                if (solution.getStepsToGoal() != -1) {
                    solved++;
                    sumOfLength += solution.getStepsToGoal();
                    sumOfNodeExplored += solution.getNodesExplored();
                }
            }
            if (solved != 0) {
                averageLength = sumOfLength / solved;
                averageNodeExplored = sumOfNodeExplored / solved;
            } else {
                averageLength = -1;
                averageNodeExplored = -1;
            }
            sb_lb4.append("Solved: " + solved + ", Solvable fraction: " + String.format("%.2f", (solved / EPState.Number_Of_Solvable_States) * 100) + "%, Time consumed: " + timeConsumed + ", Average path length: " + averageLength + ", Average node explored: " + averageNodeExplored);
            System.out.println(sb_lb4.toString());

            //Local beam search k = 100 part
            currentSolution.clear();
            solved = 0;
            sumOfLength = 0;
            sumOfNodeExplored = 0;
            StringBuilder sb_lb5 = new StringBuilder();
            sb_lb5.append("local beam search (k = 100): ");
            startTime = System.currentTimeMillis();
            for (State currentState : solvables) {
                setState(currentState);
                currentSolution.add(solveBeam(100, false));
            }
            timeConsumed = System.currentTimeMillis() - startTime;
            for (Solution solution : currentSolution) {
                if (solution.getStepsToGoal() != -1) {
                    solved++;
                    sumOfLength += solution.getStepsToGoal();
                    sumOfNodeExplored += solution.getNodesExplored();
                }
            }
            if (solved != 0) {
                averageLength = sumOfLength / solved;
                averageNodeExplored = sumOfNodeExplored / solved;
            } else {
                averageLength = -1;
                averageNodeExplored = -1;
            }
            sb_lb5.append("Solved: " + solved + ", Solvable fraction: " + String.format("%.2f", (solved / EPState.Number_Of_Solvable_States) * 100) + "%, Time consumed: " + timeConsumed + ", Average path length: " + averageLength + ", Average node explored: " + averageNodeExplored);
            System.out.println(sb_lb5.toString());
        }
    }

    @Override
    public ArrayList<Move> getMoveList() {
        return moveList;
    }

    @Override
    public HashMap<String, Move> getMoveMap() {
        return moveMap;
    }

    @Override
    public HashMap<Integer, Heuristic> getHeuristicMap() {
        return heuristicMap;
    }

    //This is the main method to input the file name and process the commands for 8-puzzle
    public static void main(String[] args) {
        EPPuzzle epPuzzle = new EPPuzzle();

        if(args == null || args.length == 0 || args[0] == null){
            System.out.println("The file name is not found");
            System.exit(1);
        }
        else{
            //Read the input file line by line
            Path path = Paths.get(args[0]);
            try{
                BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset());
                String line = br.readLine();
                while (line != null){
                    epPuzzle.executeCommand(line);
                    line = br.readLine();
                }
                br.close();
            }
            catch (IOException e) {
                System.out.println("The file is not found.");
                System.exit(1);
            }
        }
        System.out.println("Finishing process the input file " + args[0] + ". If you want to manually enter any more commands, please do so!");

        Scanner sc = new Scanner(System.in);
        while(true){
            String command = sc.nextLine();
            epPuzzle.executeCommand(command);
        }
    }
}
