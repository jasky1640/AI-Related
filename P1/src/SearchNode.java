import java.util.ArrayList;

public class SearchNode implements Comparable<SearchNode>{

    private State state;
    private ArrayList<Move> movesToCurrentNode;
    //g(n)
    private double heuristicToGoalNode;
    //Not used in beam search, h(n)
    private int costToCurrentNode;

    //Constructor for the starting node
    public SearchNode(State state, double heuristicToGoalNode){
        this.state = state;
        this.heuristicToGoalNode = heuristicToGoalNode;
        this.movesToCurrentNode = new ArrayList<>();
        this.costToCurrentNode = 0;
    }

    //Constructor for A-star middle-way nodes
    public SearchNode(State state, double heuristicToGoalNode, ArrayList<Move> movesToCurrentNode, int costToCurrentNode){
        this.state = state;
        this.heuristicToGoalNode = heuristicToGoalNode;
        this.movesToCurrentNode = movesToCurrentNode;
        this.costToCurrentNode = costToCurrentNode;
    }

    public double getHeuristicToGoalNode() {
        return heuristicToGoalNode;
    }

    public State getState() {
        return state;
    }

    public ArrayList<Move> getMovesToCurrentNode() {
        return new ArrayList<>(movesToCurrentNode);
    }

    public int getCostToCurrentNode() {
        return costToCurrentNode;
    }

    @Override
    //If the two nodes's f(n) = g(n)+h(n) is identical,return 0
    //If the current node's f(n) is larger, return 1
    //If the current node's f(n) is smaller, return -1
    public int compareTo(SearchNode comparedSearchNode) {
        if(this.costToCurrentNode + this.heuristicToGoalNode == comparedSearchNode.costToCurrentNode + comparedSearchNode.heuristicToGoalNode){
            return 0;
        }
        else if(this.costToCurrentNode + this.heuristicToGoalNode > comparedSearchNode.costToCurrentNode + comparedSearchNode.heuristicToGoalNode){
            return 1;
        }
        else{
            return -1;
        }
    }
}
