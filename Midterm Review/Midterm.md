#### General Concepts in Search

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

