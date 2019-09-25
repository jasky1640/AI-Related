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
