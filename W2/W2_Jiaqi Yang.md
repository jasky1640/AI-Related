### Written Homework 2

#### Jiaqi Yang (jxy530)

#### Due October 2rd, Tuesday

------

> 1. Contrast A* search with simulated annealing. Consider the algorithms, choice of
>    next actions, memory complexity, and optimality. (10 points)

###### A* search

- A* search algorithm is a guided breadth first search in some sense. Its implementation uses a priority queue. The algorithm expands node that is fewest in the value f(n), which is sum of g(n), the actual cost to reach goal node n from start, and h(n), the estimated cost to reach goal from node n. Then it puts the node's successors into the priority queue. This is repeated until the node n is reached, or failure if there is no path to node n
- As mentioned above, A* search algorithm chooses the next node from the priority queue with the smallest f(n), which is sum of g(n), the actual cost to reach goal node n from start, and h(n), the estimated cost to reach goal from node n.
- If the solution exists, A* search's worse case space complexity is O(b<sup>d</sup>), where b is the branching factor (the average number of successor per state) and d is the is the depth of the optimal solution path. If the solution doesn't exist, the space complexity could be infinite if the tree is infinitely deep. The reason that the worse case space complexity is exponential is that if all the heuristics are constant, then A* search will be degraded to breadth first search.
- A* search is complete and optimal if heuristic h(n) is admissible (never overestimates cost to reach goal for tree search) and consistent (never violates triangle inequality for graph search).

###### Simulated Annealing

- Simulated annealing algorithm, unlike hill climbing and A* search algorithm, makes downhill moves, or worse choices, toward states with lower value. It is inspired by annealing in metallurgy: the notion of slow cooling implemented in the simulated annealing algorithm is interpreted as a slow decrease in the probability of accepting worse solutions as the solution space is explored. In other words, when the algorithm just starts, the chance to take a downhill successor is higher than when it runs for a while.
- Unlike A* search algorithm, which picks the best f(n) in the priority queue, the simulated annealing takes a random selected successor of the current state. If it is an uphill successor, it will be taken in any situation; if it is a downhill successor, the possibility is dependent on the temperature, following the equation P = e<sup>ΔE/T</sup>.
- Unlike A* search algorithm, which needs to store all the successors in a priority queue under the risk of exponential increase of memory usage, stimulated annealing algorithm only stores the current state; since it only cares about the goal state, applied in problems like travelling salesman problem, it does not even need to store the path taken. Therefore, the memory complexity is constant, O(1), since all we need to keep track is the current state.
- Unlike A* search algorithm, which guarantees the optimality of the solution if the algorithm could find one, simulated annealing algorithm is guaranteed to be incomplete, because it can get stuck on a local maximum. Therefore, simulated annealing algorithm is not guaranteed to yield a optimal solution. However, if enough randomness is used in combination with very slow cooling, it will converge to its global optimality.

------

> 2. Gradient ascent search is prone to local optima just like hill climbing. Describe how
> you might adapt simulated annealing to gradient ascent search to reduce this
> problem. Could the gradient be considered a heuristic? Explain. (10 points)

Answer

------

> 3. Problem 5.7, Russell & Norvig. (10 points) (Prove the following assertion...)

Answer

------

> 4. Problem 5.8, Russell & Norvig. (20 points) (Consider the two-player game…) but
>    modified to use 5 spaces with the starting position as follows:
>    For 5.8d, consider the only possibilities that A starts in either position 1 or 2.

Answer

------

> 5. Problem 5.19, Russell & Norvig. (10 points) (Consider the following procedure...)

Answer

------

> 6. Problem 6.3, Russell & Norvig. (10 points) (Consider the problem of constructing...)

Answer

------

> 7. Problem 6.5, Russell & Norvig. (10 points) (Solve the cryptarithmetic problem…)

Answer

------

> 8. Problem 6.9, Russell & Norvig. (10 points) (Explain why it is a good heuristic...)

Answer

------

> 9. Problem 6.11, Russell & Norvig. (10 points) (Use the AC-3 algorithm...)

Answer