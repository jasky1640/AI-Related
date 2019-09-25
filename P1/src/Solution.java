public class Solution {

    //The number of nodes explored to find the node with goal state
    private int nodesExplored;
    //The number of steps taken to reach goal state
    private int stepsToGoal;

    public Solution(int nodesExplored, int stepsToGoal){
        this.nodesExplored = nodesExplored;
        this.stepsToGoal = stepsToGoal;
    }

    public int getNodesExplored() {
        return nodesExplored;
    }

    public int getStepsToGoal() {
        return stepsToGoal;
    }
}
