import java.util.ArrayList;
import java.util.HashMap;

/**
 * EPPuzzle class presents a 8-puzzle game
 * Author: Jiaqi Yang
 * Date: 9/23/2019
 */
public class EPPuzzle extends Puzzle {

    private static HashMap<String, Move> moveMap;
    private static ArrayList<Move> moveList;
    private static HashMap<String, Heuristic> heuristicMap;

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
        heuristicMap.put("h1", new EPMisplacedHeuristic());
        heuristicMap.put("h2", new EPDistanceHeuristic());
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

    //Shortcut to rapidly test the normal case of the methods
    public static void main(String[] args) {
        EPPuzzle puzzle = new EPPuzzle();
        puzzle.executeCommand("setState 345 b21 688");
        puzzle.executeCommand("printState");
        puzzle.executeCommand("setState 345 b21 678");
        puzzle.executeCommand("printState");
        puzzle.executeCommand("move up");
        puzzle.executeCommand("printState");
        puzzle.executeCommand("move left");
        puzzle.executeCommand("move boom");
        puzzle.executeCommand("printState");
        puzzle.executeCommand("randomizeState 100");
        puzzle.executeCommand("solve beam 100");
        puzzle.executeCommand("solve A-star h1");
        puzzle.executeCommand("solve A-star h9");
        puzzle.executeCommand("maxNodes 10000");
    }
}
