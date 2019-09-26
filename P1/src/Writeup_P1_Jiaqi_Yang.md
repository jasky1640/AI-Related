#### Writeup for EECS 391 Programming Assignment 1

###### Jiaqi Yang (jxy530)

###### 09/27/2019

------

#### 1. Code Design

###### State

State is an abstract class that represents a state for a puzzle. In the 8-puzzle game, a state is represented by class EPState, which inherits State abstract class. The core of EPState class is stateList, an ArrayList of Integer that represents 8-puzzle state, which Integer 0 is used to represent blank tile. EPstate provides eight methods as shown below.

```java
    //Set the stateList to the goal state of the puzzle
    public abstract void setGoalState();

    //Test if the current state is the goal state
    public abstract boolean isGoalState();

    //Set the stateList to represent the state of input
    public abstract void setStateList(String state);

    //Validate the input puzzle representation
    public abstract boolean isValidState(String state);

    //Print the current puzzle state
    public abstract void printState();

    //Get the hash code of the current state in order to distinguish different states
    public abstract int getHash();

    //Get a new instance of State
    public abstract State copyState();

    //Validate if the current state is solvable
    public abstract boolean isSolvable();
```

Furthermore, State abstract class provides a function to randomly move from the goal state by passing number of steps. This is the implementation of randomizeState command. The seed used for Random class is 1.

###### Move

Move is an interface that represents a move in a given state. In the 8-puzzle game, all possible moves are up, down, left, right; the corresponding classes that implements Move interface are EPMoveUp, EPMoveDown, EPMoveLeft, EPMoveRight. All these classes provide three methods as shown below.

```java
    //Allow the tile to be moved in different directions
    State move(State state);

    //Get the identifier of the move direction, which is up, down, left, right
    String getMoveName();

    //Validate if the movement is legal
    boolean isLegalMovement(State state);
```

###### Heuristic

Heuristic is an interface that provides the functionality of calculating the heuristic value of a given state. For 8-puzzle, there are 2 different heuristic function required: h1, which is the number of misplaced tiles, and h2, which is the sum of the distance of the tiles from their goal positions; the corresponding classes that implements Heuristic interface are EPMisplacedHeuristic and EPDistanceHeuristic. All these classes provide three methods as shown below.

```java
    //Calculate the heuristic value of a given state
    double calculate(State state);

    //Return the String identifier of current heuristic function
    String getName();
```

###### Puzzle

Puzzle abstract class is the core implementation of Program Assignment 1. It provides the functionality to execute commands, read input File, and include the implementation of A-star algorithm and adapted local beam search algorithm. The major functional methods are described below.

- ExecuteCommand method takes input String commands and call the corresponding methods to execute them.
- solveAstar method takes input Heuristic, specified by the command, and boolean shouldPrint.  A-star algorithm is implemented with PriorityQueue. Until the algorithm finds the goal state, it keeps polling the next unexplored State and put its successor State into the PriorityQueue. The core implementation is attached.

```java
ArrayList<Integer> exploredNodesHash = new ArrayList<>();                   
boolean reachGoalState = false;                                             
SearchNode goalStateSearchNode = null;                                      
PriorityQueue<SearchNode> priorityQueue = new PriorityQueue<>();            
            
priorityQueue.add(new SearchNode(state, heuristic.calculate(state)));
            
while(reachGoalState == false && exploredNodesHash.size() < maxNodes){              
	SearchNode searchNode = priorityQueue.poll();
    while(exploredNodesHash.contains(searchNode.getState().getHash())){
    	searchNode = priorityQueue.poll();
        	if(searchNode == null)                    
            	return new Solution(exploredNodesHash.size(), -1);
  	}
                
    exploredNodesHash.add(searchNode.getState().getHash());
                        
    if(searchNode.getState().isGoalState()){                    
        goalStateSearchNode = searchNode;            
        reachGoalState = true;
    }
                
    else{                   
        for(Move move: getMoveList()){
			if(move.isLegalMovement(searchNode.getState())){
				State movedState = move.move(searchNode.getState());                             
                if(!exploredNodesHash.contains(movedState.getHash())){
                               
					ArrayList<Move> movesToMovedStateNode = 				                                 searchNode.getMovesToCurrentNode();
                    movesToMovedStateNode.add(move);
                    priorityQueue.add(new SearchNode(movedState, 																			 heuristic.calculate(movedState), 														 movesToMovedStateNode, 		                                                        searchNode.getCostToCurrentNode()+1));                     
                }                     
            }                 
        }              
    }         
} 
```

- solveBeam method takes int numberOfState, which is the number of beams, and boolean shouldPrint. The adapted local beam algorithm is designed that
  - First, Initialize all local variable. The heuristic function used in this algorithm is h2, sum of the distance of the tiles from their goal positions. 
  - Second, put the starting state and numberOfState - 1 states that randomly move 10 steps from the starting state. These states fill the bestkNodes, which is the ArrayList that stores the k nodes with smallest heuristic value.
  - Third, until we find the goal state or reach the upper limitation of node exploration, we put all the successors of those states in the bestkNodes into a temporary PriorityQueue. After finding all successors, we clear bestkNodes list and preferentially put unexplored successors with highest h2 value into bestkNodes.

EPPuzzle class extends Puzzle abstract class and includes specific variables and methods for 8-puzzle game.     It is the class where the main method for 8-puzzle game is located. It takes the file name in the same folder, read and execute the commands line by line. It contains two HashMap that stores the Heuristic and Move for 8-puzzle and multiple methods to return the corresponding Heuristic and Move based on the input String name.

###### SearchNode

SearchNode class presents a A-star or local beam search tree node, including the information of g(n), h(n), moves from the start state, and the current state. The cost from the starting state to current state is not used in local beam search, and thus set to 0 for those nodes. SearchNode class also implements Comparable interface in order to support the soring in PriorityQueue.

###### Solution

Solution class presents the information about a solution of the puzzle, including number of explored nodes and steps getting to goal state. This class is designed for the experiment part of the programming assignment in order to store the results of each run.

------

#### 2. Code Correctness

###### The only main method in the program is in the EPState class. To run the program, please run the main method and pass the file name as the only argument.

The following instructions are in the file named SimpleTestFile.txt within my submitted zip file.

> ```
> Commands:
> setState b12 345 678
> printState
> 
> Output:
> The input state is b12 345 678
> The current 8-puzzle state is
>  	1	2	
> 3	4	5	
> 6	7	8	
> ```

These two lines tests the setState command and printState command. The output meets the expectation, which is exactly same as the input String representation.

> ```
> Commands:
> maxNodes 1000
> move right
> printState
> randomizeState 2
> printState
> 
> Output:
> The maximum number of nodes is set to 1000
> The input movement is right
> The current 8-puzzle state is
> 1	 	2	
> 3	4	5	
> 6	7	8	
> The number of random moves from the goal state is 2
> The current 8-puzzle state is
> 3	1	2	
> 6	4	5	
>  	7	8	
> ```

These two lines test the maxNodes command, move command, and randomizeState command. The output meets the expectation: the blank tile moves right from the goal state, and then from the goal state, the blank tile moves down and then down again.

> ```
> Command:
> solve A-star h1
> 
> Output:
> The State starting with: The current 8-puzzle state is
> 3	1	2	
> 6	4	5	
>  	7	8	
> The next step is to move up. The current 8-puzzle state is
> 3	1	2	
>  	4	5	
> 6	7	8	
> The next step is to move up. The current 8-puzzle state is
>  	1	2	
> 3	4	5	
> 6	7	8	
> We reach goal state with 2 steps
> [up, up]
> ```

Then we test the solve A-star h1 command. The output is optimal: the shortest path to the goal state is up and up. This fits the expected behavior of A-star algorithm.

> ```
> Commands:
> randomizeState 10
> printState
> solve A-star h2
> 
> Output:
> The number of random moves from the goal state is 10
> The current 8-puzzle state is
> 1	2	 	
> 3	4	5	
> 6	7	8	
> Solve the puzzle with A-star and heuristic function h2
> The State starting with: The current 8-puzzle state is
> 1	2	 	
> 3	4	5	
> 6	7	8	
> The next step is to move left. The current 8-puzzle state is
> 1	 	2	
> 3	4	5	
> 6	7	8	
> The next step is to move left. The current 8-puzzle state is
>  	1	2	
> 3	4	5	
> 6	7	8	
> We reach goal state with 2 steps
> [left, left]
> ```

Then we test the solve A-star h2 command. The output is optimal: the shortest path to the goal state is left and left. This fits the expected behavior of A-star algorithm.

> ```
> Commands:
> randomizeState 100
> printState
> solve beam 30
> 
> Output:
> The number of random moves from the goal state is 100
> The current 8-puzzle state is
> 5	2	8	
> 6	 	1	
> 4	3	7	
> Solve the puzzle with local beam search with state number 30
> The State starting with: The current 8-puzzle state is
> 5	2	8	
> 6	 	1	
> 4	3	7	
> The next step is to move down. The current 8-puzzle state is
> 5	2	8	
> 6	3	1	
> 4	 	7	
> The next step is to move up. The current 8-puzzle state is
> 5	2	8	
> 6	 	1	
> 4	3	7	
> The next step is to move left. The current 8-puzzle state is
> 5	2	8	
>  	6	1	
> 4	3	7	
> The next step is to move down. The current 8-puzzle state is
> 5	2	8	
> 4	6	1	
>  	3	7	
> The next step is to move right. The current 8-puzzle state is
> 5	2	8	
> 4	6	1	
> 3	 	7	
> The next step is to move left. The current 8-puzzle state is
> 5	2	8	
> 4	6	1	
>  	3	7	
> The next step is to move right. The current 8-puzzle state is
> 5	2	8	
> 4	6	1	
> 3	 	7	
> The next step is to move up. The current 8-puzzle state is
> 5	2	8	
> 4	 	1	
> 3	6	7	
> The next step is to move right. The current 8-puzzle state is
> 5	2	8	
> 4	1	 	
> 3	6	7	
> The next step is to move left. The current 8-puzzle state is
> 5	2	8	
> 4	 	1	
> 3	6	7	
> The next step is to move right. The current 8-puzzle state is
> 5	2	8	
> 4	1	 	
> 3	6	7	
> The next step is to move up. The current 8-puzzle state is
> 5	2	 	
> 4	1	8	
> 3	6	7	
> The next step is to move left. The current 8-puzzle state is
> 5	 	2	
> 4	1	8	
> 3	6	7	
> The next step is to move down. The current 8-puzzle state is
> 5	1	2	
> 4	 	8	
> 3	6	7	
> The next step is to move left. The current 8-puzzle state is
> 5	1	2	
>  	4	8	
> 3	6	7	
> The next step is to move up. The current 8-puzzle state is
>  	1	2	
> 5	4	8	
> 3	6	7	
> The next step is to move right. The current 8-puzzle state is
> 1	 	2	
> 5	4	8	
> 3	6	7	
> The next step is to move down. The current 8-puzzle state is
> 1	4	2	
> 5	 	8	
> 3	6	7	
> The next step is to move left. The current 8-puzzle state is
> 1	4	2	
>  	5	8	
> 3	6	7	
> The next step is to move down. The current 8-puzzle state is
> 1	4	2	
> 3	5	8	
>  	6	7	
> The next step is to move right. The current 8-puzzle state is
> 1	4	2	
> 3	5	8	
> 6	 	7	
> The next step is to move right. The current 8-puzzle state is
> 1	4	2	
> 3	5	8	
> 6	7	 	
> The next step is to move up. The current 8-puzzle state is
> 1	4	2	
> 3	5	 	
> 6	7	8	
> The next step is to move left. The current 8-puzzle state is
> 1	4	2	
> 3	 	5	
> 6	7	8	
> The next step is to move up. The current 8-puzzle state is
> 1	 	2	
> 3	4	5	
> 6	7	8	
> The next step is to move left. The current 8-puzzle state is
>  	1	2	
> 3	4	5	
> 6	7	8	
> We reach goal state after searching 258 nodes.
> We can get to the goal state by moving blank tile 26 times.
> [down, up, left, down, right, left, right, up, right, left, right, up, left, down, left, up, right, down, left, down, right, right, up, left, up, left]
> ```

Lastly we test solve beam command. Obviously, the local beam search is not optimal, but it will provide a path to the goal state. In this case, we take 26 steps to get to the goal state, which only takes 20 steps for A-star algorithm, and thus it is not optimal. The behavior fits the expected behavior of local beam search, and therefore it is correct.

------

#### 3. Experiments

###### Control Experiments of the Code

Before diving into the experiments and discussion sections, I would like to present my output of control experiments. I implemented a method called getExperimentAndDiscussionInfo in EPState class, and the outputs will be self-explanatory. I will elaborate these outputs as answers for experiments and discussion sections.

> ```
> Command:
> getExperimentAndDiscussionInfo
> 
> Output:
> Here we present all the information required for experiments and discussion section.
> -------------------------------------------------------------------------------------
> MaxNodes: 50
> h1: Solved: 728, Solvable fraction: 0.40%, Time consumed: 7556, Average path length: 26, Average node explored: 8
> h2: Solved: 4453, Solvable fraction: 2.45%, Time consumed: 8168, Average path length: 31, Average node explored: 13
> local beam search (k = 2): Solved: 13432, Solvable fraction: 7.40%, Time consumed: 9705, Average path length: 37, Average node explored: 23
> local beam search (k = 3): Solved: 6656, Solvable fraction: 3.67%, Time consumed: 9640, Average path length: 37, Average node explored: 20
> local beam search (k = 5): Solved: 2582, Solvable fraction: 1.42%, Time consumed: 10642, Average path length: 38, Average node explored: 17
> local beam search (k = 10): Solved: 694, Solvable fraction: 0.38%, Time consumed: 13190, Average path length: 35, Average node explored: 15
> local beam search (k = 20): Solved: 320, Solvable fraction: 0.18%, Time consumed: 18478, Average path length: 31, Average node explored: 13
> local beam search (k = 30): Solved: 222, Solvable fraction: 0.12%, Time consumed: 24363, Average path length: 27, Average node explored: 12
> local beam search (k = 50): Solved: 194, Solvable fraction: 0.11%, Time consumed: 32433, Average path length: 26, Average node explored: 11
> local beam search (k = 100): Solved: 183, Solvable fraction: 0.10%, Time consumed: 60845, Average path length: 29, Average node explored: 11
> -------------------------------------------------------------------------------------
> MaxNodes: 100
> h1: Solved: 1656, Solvable fraction: 0.91%, Time consumed: 16125, Average path length: 54, Average node explored: 10
> h2: Solved: 11483, Solvable fraction: 6.33%, Time consumed: 15414, Average path length: 57, Average node explored: 14
> local beam search (k = 2): Solved: 55507, Solvable fraction: 30.59%, Time consumed: 17874, Average path length: 66, Average node explored: 38
> local beam search (k = 3): Solved: 37432, Solvable fraction: 20.63%, Time consumed: 19037, Average path length: 71, Average node explored: 31
> local beam search (k = 5): Solved: 22007, Solvable fraction: 12.13%, Time consumed: 20655, Average path length: 74, Average node explored: 25
> local beam search (k = 10): Solved: 6276, Solvable fraction: 3.46%, Time consumed: 24215, Average path length: 76, Average node explored: 20
> local beam search (k = 20): Solved: 1579, Solvable fraction: 0.87%, Time consumed: 30906, Average path length: 71, Average node explored: 16
> local beam search (k = 30): Solved: 804, Solvable fraction: 0.44%, Time consumed: 37237, Average path length: 63, Average node explored: 14
> local beam search (k = 50): Solved: 560, Solvable fraction: 0.31%, Time consumed: 50729, Average path length: 59, Average node explored: 13
> local beam search (k = 100): Solved: 434, Solvable fraction: 0.24%, Time consumed: 85886, Average path length: 56, Average node explored: 12
> -------------------------------------------------------------------------------------
> MaxNodes: 500
> h1: Solved: 9679, Solvable fraction: 5.33%, Time consumed: 144279, Average path length: 276, Average node explored: 13
> h2: Solved: 70675, Solvable fraction: 38.95%, Time consumed: 114685, Average path length: 256, Average node explored: 18
> local beam search (k = 2): Solved: 181409, Solvable fraction: 99.98%, Time consumed: 41008, Average path length: 167, Average node explored: 89
> local beam search (k = 3): Solved: 179233, Solvable fraction: 98.78%, Time consumed: 47782, Average path length: 188, Average node explored: 70
> local beam search (k = 5): Solved: 170811, Solvable fraction: 94.14%, Time consumed: 60905, Average path length: 215, Average node explored: 53
> local beam search (k = 10): Solved: 166086, Solvable fraction: 91.54%, Time consumed: 80130, Average path length: 264, Average node explored: 39
> local beam search (k = 20): Solved: 135552, Solvable fraction: 74.71%, Time consumed: 123659, Average path length: 328, Average node explored: 30
> local beam search (k = 30): Solved: 98186, Solvable fraction: 54.11%, Time consumed: 160136, Average path length: 360, Average node explored: 26
> local beam search (k = 50): Solved: 45005, Solvable fraction: 24.80%, Time consumed: 212110, Average path length: 382, Average node explored: 23
> local beam search (k = 100): Solved: 10225, Solvable fraction: 5.64%, Time consumed: 293340, Average path length: 365, Average node explored: 18
> -------------------------------------------------------------------------------------
> MaxNodes: 1000
> h1: Solved: 19020, Solvable fraction: 10.48%, Time consumed: 480724, Average path length: 539, Average node explored: 15
> h2: Solved: 113043, Solvable fraction: 62.30%, Time consumed: 292001, Average path length: 442, Average node explored: 20
> local beam search (k = 2): Solved: 181440, Solvable fraction: 100.00%, Time consumed: 41277, Average path length: 167, Average node explored: 89
> local beam search (k = 3): Solved: 181440, Solvable fraction: 100.00%, Time consumed: 47926, Average path length: 192, Average node explored: 72
> local beam search (k = 5): Solved: 181409, Solvable fraction: 99.98%, Time consumed: 64176, Average path length: 237, Average node explored: 58
> local beam search (k = 10): Solved: 181359, Solvable fraction: 99.96%, Time consumed: 86139, Average path length: 293, Average node explored: 42
> local beam search (k = 20): Solved: 181112, Solvable fraction: 99.82%, Time consumed: 141551, Average path length: 401, Average node explored: 34
> local beam search (k = 30): Solved: 179536, Solvable fraction: 98.95%, Time consumed: 207191, Average path length: 493, Average node explored: 31
> local beam search (k = 50): Solved: 163654, Solvable fraction: 90.20%, Time consumed: 348913, Average path length: 623, Average node explored: 28
> local beam search (k = 100): Solved: 86819, Solvable fraction: 47.85%, Time consumed: 634820, Average path length: 742, Average node explored: 24
> -------------------------------------------------------------------------------------
> MaxNodes: 5000
> h1: Solved: 66785, Solvable fraction: 36.81%, Time consumed: 10569355, Average path length: 2200, Average node explored: 18
> h2: Solved: 177702, Solvable fraction: 97.94%, Time consumed: 1431587, Average path length: 1051, Average node explored: 21
> local beam search (k = 2): Solved: 181440, Solvable fraction: 100.00%, Time consumed: 41092, Average path length: 167, Average node explored: 89
> local beam search (k = 3): Solved: 181440, Solvable fraction: 100.00%, Time consumed: 47890, Average path length: 192, Average node explored: 72
> local beam search (k = 5): Solved: 181440, Solvable fraction: 100.00%, Time consumed: 63994, Average path length: 237, Average node explored: 58
> local beam search (k = 10): Solved: 181440, Solvable fraction: 100.00%, Time consumed: 85540, Average path length: 294, Average node explored: 42
> local beam search (k = 20): Solved: 181440, Solvable fraction: 100.00%, Time consumed: 140737, Average path length: 401, Average node explored: 34
> local beam search (k = 30): Solved: 181440, Solvable fraction: 100.00%, Time consumed: 206896, Average path length: 500, Average node explored: 31
> local beam search (k = 50): Solved: 181440, Solvable fraction: 100.00%, Time consumed: 360174, Average path length: 673, Average node explored: 29
> local beam search (k = 100): Solved: 181440, Solvable fraction: 100.00%, Time consumed: 839851, Average path length: 1034, Average node explored: 27
> -------------------------------------------------------------------------------------
> MaxNodes: 10000
> 
> ```



> (a) How does fraction of solvable puzzles from random initial states vary with the
> maxNodes limit?



> (b) For A* search, which heuristic is better?



> (c) How does the solution length vary across the three search methods?



> (d) For each of the three search methods, what fraction of your generated problems
> were solvable?

------

#### 4. Discussion

> (a) Based on your experiments, which search algorithm is better suited for this
> problem? Which finds shorter solutions? Which algorithm seems superior in terms
> of time and space?



> (b) Discuss any other observations you made, such as the difficulty of implementing
> and testing each of these algorithms.

------

#### Extra Credit

TAT no enough time to do it.