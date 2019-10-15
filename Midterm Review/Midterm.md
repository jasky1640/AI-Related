#### General Concepts in Search

##### Basic Concepts

- **State space**
  - The set of all states reachable from the initial state by any sequence of actions
- **Transition model**
  - The result of a move

##### Uninformed search

Uninformed search strategies can only distinguish goal states from non-goal states, i.e. you can’t tell when you’re getting closing. 

- **Breadth-first search (BFS)**
  - Breadth-first search is implemented using a first-in first-out (FIFO) queue.
  - Expand shallow nodes before deeper nodes.
  - Complete and optimal.
  - Time is O(b<sup>d</sup>). Space is O(b<sup>d</sup>). d = depth of least-cost solution.

- **Depth-first search (DFS)**
  - Depth-first search is implemented using a last-in first-put (LIFO) stack.
  - Expand deepest unexpanded node.
  - Not complete. Not optimal.
  - Time is O(b<sup>m</sup>). Space is O(bm). m = maximum depth of any node.

- **Depth-limited search (DLS)**
  - Incorporate depth limit in depth-first search.
  - L = depth limit, i.e. nodes at depth L are not expanded.
  - Complete if L >= d, but not optimal.
  - Time is O(b<sup>L</sup>). Space is O(bL).

![1571021509428](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1571021509428.png)

- **Iterative deepening search (ID-DFS)**
  - depth-limited search with gradually increasing depth limit
  - complete and optimal.
  - Time is O(b<sup>d</sup>). Space is O(bd).

![1571021489485](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1571021489485.png)

- **Bi-directional search (BDS)**
  - Perform two simultaneous searches: one from start, one from goal.
  - Time is O(b<sup>d/2</sup>). Space is O(b<sup>d/2</sup>).
- **Uniform-cost search (UCS)**
  - Expand node with lowest path cost, which is actual cost to reach node n from start, g(n). Equivalent to BFS if all costs are equal, except UCS does not stop when finding goal, and instead examines all the nodes on the frontier.
  - Complete and optimal.
  - Time is O(b<sup>C/ε</sup>). Space is O(b<sup>C/ε</sup>). C = cost of optimal solution, ε = minimum cost.
  - If C = d, ε = 1, then same cost as BFS.

![1571021324964](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1571021324964.png)



##### Informed search

Nodes are selected based on an evaluation function f(n), which will help us make better choices about node  expansion order. Most uses heuristic function h(n), which is the estimated cost of cheapest path to goal.

- **Greedy best-first search**
  - Expand node that is closest (by the heuristic) to the goal. f(n) = h(n)
  - Implementation same as UCS by using priority queue.
  - Not complete since it could go down infinite path like DFS. Not optimal.
  - Worst case time complexity O(b<sup>m</sup>), Best case time complexity O(m). m = max depth
- **A* search**
  - incorporate actual path cost into evaluation function. f(n) = g(n) + h(n).
  - Implementation same as UCS by using priority queue.
  - Complete and optimal only when
    - (For tree search) h(n) is admissible: never overestimates cost to goal, e.g. straight-line distance.
    - (For graph search) h(n) is consistent: doesn’t violate triangle inequality.
    - Thus ensures nodes are expanded in non-decreasing order of path cost
  - Time complexity depends on heuristic function. Good heuristics have bands that are narrowly focused and stretch toward the goal state.
    - h* = actual cost of getting from root to goal
    - Absolute error:  Δ = h* - h.
    - Relative error: ε = (h* - h)/h*
    - For single goal, reversible trees: O(b<sup>Δ</sup>)
    - For constant step costs: O(b<sup>εd</sup>)
- **Iterative deepening A* (IDA*)**
  - Like iterative deepening, but use f(n) = g(n)+h(n) as cutoff instead of depth.
  - Good for unit step cost problems (no priority queue), bad for real-valued costs.
- **Recursive best-first search (RBFS)**
  - Don’t keep whole priority queue, just use keep current path + best f-value of alternative paths of ancestors.
  - Can switch to alternative paths if f-value is better.
- **Simplified memory A* (SMA*)**
  - Keep priority queue until memory limit. Drop worst leaf node, i.e. node with highest f-value.
  - Complete if there is a reachable solution (i.e. one that fits in memory)



##### Local search

Local search problems, like 8-queens problem, do not need the path. 

- **Hill climbing search**
  - Using a heuristic function to move toward a successor, which is closer to the goal state.
  - Not optimal. Could stuck in local maximum.
- **Local beam search**
  - Randomly generate k states. Generate successor for all k states and select k best to keep memory size bounded. Repeat until reaching global maximum.
  - Not necessarily optimal. Could still stuck in local maximum.
- **Simulated annealing**
  - Simulated annealing algorithm, unlike hill climbing and A* search algorithm, makes downhill
    moves, or worse choices, toward states with lower value.
  - The simulated annealing takes a random selected successor of the current state. If it is an uphill successor, it will be taken in any situation; if it is a downhill successor, the possibility is dependent on the temperature, following the equation P = e<sup>ΔE/T</sup>.
  - Not necessarily optimal. Could still stuck in local maximum.

![1571024816763](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1571024816763.png)



#### Optimal Gameplay

##### Basic Concepts

- **Optimal gameplay**

  - Each player tries to maximize their utility (or win the game)

- **Game Tree**

  - Top node is initial state
  - Each level lists the available moves (or results) from each game state

- **Zero-sum game: game**

  - One player’s loss is another’s gain.
  - Technically, total payoff of all players is  constant.

- **Utility function**

  - Numerical value of terminal states
  - +1, -1, or 0 for won, loose or draw]

- **Deterministic vs. Stochastic, Perfect vs. Imperfect Information**

  - Deterministic, Perfect Information: chess, go
  - Deterministic, Imperfect Information: battleship
  - Stochastic, Perfect Information: monopoly
  - Stochastic, Imperfect Information: poker

  

##### Minimax

- Idea: Choose move to position (or state) with highest minimax value. Best achievable payoff against optimal player.
- minimax-val(n) =
  - Utility(n) if terminal state
  - max s ∈ result(n) if n is max node
  - min s ∈ result(n) if n is min node
- Complete and optimal if opponent is optimal.
- Time complexity O(b<sup>m</sup>). b = branching factor = number of legal moves. m = depth of tree
- Space complexity O(bm) when using depth-first exploration, O(m) if generating one successor at a time.



##### Alpha-beta pruning

- Idea: Prune branches that can't influence final decision
- Alpha-beta pruning keeps track of the range of possible values:
  - α = max’s best choice (highest val.) so far at any point along path
  - β = min’s best choice (lowest val.) so far at any point along path

![1571096474304](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1571096474304.png)



##### Evaluation function

- Idea: Estimate expected utility based on game position evaluation function
- Instead of using Utility of Terminal State, use heuristics at maximum cutoff depth.
- H-Minimax(s,d) =
  - Eval(s) if cutoff-test(s,d)
  - max a in Actions(s) H-Minimax(Result(s,a), d+1) if Player(s) = Max
  - min a in Actions(s) H-Minimax(Result(s,a), d+1) if Player(s) = Min



#### Constraint satisfaction

CSP problems are defined by the constraints:

- Set d variables x<sub>i</sub> (Variables)
- Each variable can be assigned value v<sub>j</sub> (Domain of possible values)
- Constraint



##### Backtracking Search

- Choose values for one variable at a time.

- Backtrack when variable has no legal values left to assign.

- Equivalent to DFS but only one successor at a time.

- Time complexity O(b<sup>m</sup>). Space complexity O(m).

  ![1571108597849](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1571108597849.png)

  - We had choices of
    - What variable should be assigned next
    - What order to consider the values
  - We utilize heuristics to make these choices
    - **Minimum Remaining Values (MRV)**: choose variable with fewest legal values
    - **Degree** **Heuristic**: choose variable which most constrains others
    - **Least** **Constraining** **Value**: choose value that rules out fewest choices for neighbors 
  - **Constraint Propagation**
    - use constraints to reduce the number of legal values of one var, which in turn (i.e. propagate to) reduce the legal values of other vars.
    - a var is **node** **consistent** if all the values in the var’s domain satisfy the unary constraint.
    - a var is arc consistent with regard to another var if their domains satisfy their constraint.

  ![1571111363462](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1571111363462.png)

  - **Forward Checking**
    - Idea: Whenever a variable X is assigned, do forward checking from X
      - look at each unassigned variable Y that is connected to X
      - delete values from Y’s domain that are inconsistent with X

  

#### Probabilistic Reasoning and Bayes’ Rule

![1571100698099](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1571100698099.png)

