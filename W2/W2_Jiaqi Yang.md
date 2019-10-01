### Written Homework 2

#### Jiaqi Yang (jxy530)

#### Due October 2rd, Tuesday

------

> 1. Contrast A* search with simulated annealing. Consider the algorithms, choice of
>    next actions, memory complexity, and optimality.

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
> problem. Could the gradient be considered a heuristic? Explain.

###### Gradient Ascent Search

Gradient ascent search algorithm moves in the direction of the gradient evaluated at each point. Starting with point p<sub>0</sub>, the gradient is evaluated and the function moves to the next point. Then the process is repeated until a stopping condition is met, which is reaching the local maximum. Gradient is a derivative of a function at a certain point; basically, it's the slope of the line. So in simple words, gradient ascent search algorithm takes small steps in the locally steepest direction and reach local (hopefully global) maximum. So like hill-climbing algorithm, gradient ascent search is not guaranteed to reach global maximum.

######  Adaptation of Simulated Annealing

To reduce the problem of gradient ascent search discussed above that the algorithm is prone to local maximum, we could adapt the philosophy of simulated annealing to reduce this problem. Instead of choosing the steepest direction, the new algorithm should randomly choose a gradient (by randomly choosing an immediately neighbor and calculate the gradient) and then decide whether go or not: if the gradient is positive, then we will take the step for sure; if the gradient is negative, we move to the state with a possibility of P = e<sup>ΔE/T</sup>, similar to the function of simulated annealing dependent on the temperature. 

###### Gradient as Heuristic

By definition, a heuristic function, or simply a heuristic, is a function that ranks alternatives in search algorithms at each branching step based on available information to decide which branch to follow. For a state in gradient ascent search algorithm, the state could read all the immediate neighbors and calculate the gradients of the path leading to all those neighbors. The gradient would be an indicator of steepest direction, which leads to local optimum. Therefore, the gradient could be considered as a heuristic. 

------

> 3. Prove the following assertion: For every game tree, the utility obtained by MAX using minimax decision against a suboptimal MIN will never be lower than the utility obtained playing against an optimal MIN. Can you come up with a game tree in which MAX can do still better using a suboptimal strategy against a suboptimal MIN? 

![1569857723466](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1569857723466.png)

​								Figure 1: Minimax Tree from Lecture 6 Slide, EECS 391 CWRU

###### Proof of Assertion

If the opponent is optimal and perfectly rational, then Minimax algorithm is able to choose move to position (or state) with highest minimax value. The underlying assertion in the selection of moves for Minimax algorithm is that the opponent (MIN) will always try to minimize the utility for us (MAX). Therefore, if we play Minimax algorithm against a suboptimal opponent (MIN), which will choose the utility value greater or equal to the predicted optimal opponent, we (MAX) would consequently always obtain the utility value greater or equal to our predicted utility value. In this figure as an example, if we play Minimax algorithm against a suboptimal opponent, we will choose a1 leading to state B for MIN. If MIN behaves optimally, it will take b1 that yields utility value of 3. However, if MIN behaves suboptimally, it might take b1, b2, or b3 that will possibly yields utility value of 3, 8, or 12, which will be never lower than the utility obtained playing against an optimal MIN.

###### Improved Suboptimal Strategy

However, if MIN behaves suboptimally, MAX, who plays optimally, uses Minimax algorithm might not take full advantage of MIN's suboptimality. In this figure as an example, if we play Minimax algorithm against a suboptimal opponent, we will choose a1 leading to state B for MIN. However, if we know that MIN has a strong preference to d1 path, then we miss out the opportunity to gain utility value of 14, instead of at most 12. Therefore, we could play suboptimal strategy against the suboptimal opponent. The underlying assumption for the suboptimal strategy is that we could predict the preference of the suboptimal opponent. If so, the MIN values in the figure will be labeled as the values of predicted move, instead of minimum utility values. In this way, we could set up "traps" to gain higher utility value than Minimax algorithm does.

------

> 4. Consider the two-player game described in Figure 5.17. But modified to use 5 spaces with the starting position as follows:

![1569860196187](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1569860196187.png)

> **a**. Draw the complete game tree, using the following conventions:
>
> -  Write each state as (sA, sB), where sA and sB denote the token locations.
> - Put each terminal state in a square box and write its game value in a circle.
> - Put *loop states* (states that already appear on the path to the root) in double square boxes. Since their value is unclear, annotate each with a “?” in a circle.

![1569877615900](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1569877615900.png)

> **b**. Now mark each node with its backed-up minimax value (also in a circle). Explain how you handled the “?” values and why.

Each node with its back-up minimax value is marked in the figure of part a.

To handle the "?" value, we assume that an agent is always optimal and perfectly rational, therefore it chooses the state that will lead to the victory. Thus, when B (MIN) chooses from -1 (win) and ?, B will always choose -1 to win; when B(MIN) chooses from +1 (lost) and ?, B will always choose ? to enter the ? state instead of failure. Same theory behind A(MAX)'s attitude toward "?" state: when A chooses from +1 (win) and ?, A will always choose +1 to win; when A chooses from -1 (lost) and ?, A will always choose ? to enter the ? state instead of failure. For both A and B, they will always (and only be able to) choose ? from ? and ?.

> **c**. Explain why the standard minimax algorithm would fail on this game tree and briefly sketch how you might fix it, drawing on your answer to (b). Does your modified algorithm give optimal decisions for all games with loops?

A standard minimax algorithm is implemented in a depth-first behavior pattern, and therefore in this case with the presence of loop states, it would go into an infinite loop. 

To fix this problem, we need to modify the minimax algorithm to recognize the loop states. So just like what we do in the part a and b: if we compare the current searched state with the stack and find it repeated, we return a "?" value. When comparing and determining the values of minimax nodes, we follow the rules described in part b to handle the "?" values. If the result of the minimax algorithm is "?", then it means that the game will go to an infinite loop and therefore lead to a draw-like situation, otherwise one of the rational and optimal parties will lose.

> **d**. This 4-square game can be generalized to n squares for any n > 2. Prove that A wins if n is even and loses if n is odd. (For 5.8d, consider the only possibilities that A starts in either position 1 or 2.)

**The underlying assumption of n-square game is that n > 2, as well as A will only start at position 1, and B will only start at position n.**

###### Base Case

Since for n-square game, n is larger than 2, and obviously n is an integer, the least possible odd value of n is 3. 

- The starting position is (1,3), then A's only first move is (2,3), and B's only first move is (2,1) and leads to win.

The least possible even value of n is 4.

- If the starting position is (1,4), then A's only first move is (2,4), B's only first move is (2,3), and A will move to (4,3) to win.

###### Proof of Induction

For n > 4 and n is odd, the initiate state will be (1,n), then the next two steps will guarantee to be (2,n-1). Since the game is to reach the opposite side first, a rational agent will always move forward instead of backward, the game will be reduce to n-2 square game and eventually all way to 3-square game, where B will win (in other words, A will lose).

For n>4 and n is even, the initiate state will also be (1,n), then the next two steps will guarantee to be (2,n-1). Since the game is to reach the opposite side first, a rational agent will always move forward instead of backward, the game will be reduce to n-2 square game and eventually all way to 3-square game, where A will win.

Therefore, if the starting state is (1,n) for A and B, then A wins if n is even and loses if n is odd.

**The underlying assumption of n-square game is that n > 2, as well as A will only start at position 2, and B will only start at position n.**

###### Base Case

Since for n-square game, n is larger than 2, and obviously n is an integer, the least possible odd value of n is 3. 

- The starting position is (2,3), then A's only first move is (1,3), and B's only first move is (1,2). Then A will move to (3,2) to win.

The least possible even value of n is 4.

- If the starting position is (2,4), then A's rational first move is (3,4), B's only first move is (3,2), and A will move to (4,2) to win.

Since 3-square for starting position 2 of A is unusual, we work on 5-square, the next available odd integer, to be safe.

- If the starting position is (2,5), then A will move to (3,5), B's only first move is (3,4), and A will move to (5,4) to win.

###### Proof of Induction

For n > 4 and n is odd, the initiate state will be (2,n), then the next two steps will guarantee to be (3,n-1). Since the game is to reach the opposite side first, a rational agent will always move forward instead of backward, the game will be reduce to n-2 square game and eventually all way to 5-square game, where A will win .

For n>4 and n is even, the initiate state will also be (2,n), then the next two steps will guarantee to be (3,n-1). Since the game is to reach the opposite side first, a rational agent will always move forward instead of backward, the game will be reduce to n-2 square game and eventually all way to 4-square game, where A will win.

Therefore, if starting state is (2,n) for A and B, then A wins no matter what situation. (In this case, it is just like A is playing n-1 square while B is playing n square. Even A and B's initial distance is odd, B will not be able to win.) 

Only if starting state is (2,n-1) for A and B, and n is larger than 4, the assertion stands: A wins if n is even and loses if n is odd.

------

> 5. Consider the following procedure for choosing moves in games with chance nodes:
>    - Generate some dice-roll sequences (say, 50) down to a suitable depth (say, 8)
>    - With known dice rolls, the game tree becomes deterministic. For each dice-roll sequence, solve the resulting deterministic game tree using alpha–beta.
>    - Use the results to estimate the value of each move and to choose the best. Will this procedure work well? Why (or why not)?

The procedure only generates 50 dice-roll sequences with depth of 8; assuming that the dice used here has 6 different values (1-6), we could calculate that the amount of all possible dice-roll sequence is 6<sup>8</sup>, which is 1,679,616. The dice-roll sequences we generate here is only approximately 0.003% of all the possible sequences. Unless we are able to look at all the dice-roll sequence, the resulting game tree is not deterministic. 

The idea of alpha-beta pruning is that based on a deterministic game tree, we are able to prune branches that cannot influence final decision to speed up algorithm's running time. If we do not have access to all values of each move, we are not able to use alpha-beta pruning, given the fact that the unexamined children with no estimated value could have any value. Thus, the result and move selected by this algorithm will not be complete. As a result, this procedure will not work well for the reasons discussed above.

------

> 6. Consider the problem of constructing (not solving) crossword puzzles: fitting words into a rectangular grid. The grid, which is given as part of the problem, specifies which squares are blank and which are shaded. Assume that a list of words (i.e., a dictionary) is provided and that the task is to fill in the blank squares by using any subset of the list. Formulate this problem precisely in two ways:

> a. As a general search problem. Choose an appropriate search algorithm and specify a heuristic function. Is it better to fill in blanks one letter at a time or one word at a time?



> b. As a constraint satisfaction problem. Should the variables be words or letters? Which formulation do you think will be better? Why?



------

> 7. Solve the cryptarithmetic problem in Figure 6.2 by hand, using the strategy of backtracking with forward checking and the MRV and least-constraining-value heuristics.

![1569860642997](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1569860642997.png)



------

> 8. Explain why it is a good heuristic to choose the variable that is most constrained but the value that is least constraining in a CSP search.

###### Most Constrained Variable

First of all, it is a good heuristic to choose the variable that is most constrained in a CSP search. By following this principle, we will always choose the node with most edges in the graph. This fits the philosophy of Degree Heuristic, which choose variable that most constrains others and therefore lead to maximum reduction in tree size. Since we need to work with the most constrained node soon or later, it will be most beneficial to fail as early as possible, instead of failing when most of the variables have been assigned with a values, a situation that will lead to massive backtracks.

###### Least Constraining Value

Then, it is a good heuristic to choose the value that is least constraining in a CSP search. By following this principle, we will always choose the one that rules out the fewest values in the remaining variables and therefore leave maximal flexibility for a solution. Since we have a pool of available values for each variable, by choosing the least constraining value, we will leave the most available values for other neighbor variables and most likely avoid the future assignments from being assigned with conflicting values against the constraints.

Therefore, a good heuristic is to choose the variable that is most constrained but the value that is least constraining in a CSP search.

------

> 9. Use the AC-3 algorithm to show that arc consistency can detect the inconsistency of the partial assignment {WA=green, V =red} for the problem shown in Figure 6.1.

![1569860783465](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1569860783465.png)

AC-3 algorithm aims to make every variable arc-consistent, which means that every variable is arc consistent with every other variable, and maintain a queue of arcs to consider. Initially, AC-3 has all the arcs in the CSP. Then AC-3 pops off an arc and make it X<sub>i</sub> arc-consistent with respect to X<sub>j</sub>. If this leaves D<sub>i</sub> unchanged,then AC-3 moves on to next arc; if this narrows D<sub>i</sub>, then AC-3 add X<sub>i</sub>'s neighbors into the queue. If a domain is revised down to nothing, then AC-3 returns failure and indicates an arc inconsistency.

Since the choice of arc is arbitrary, we will pick the arc in a manner that will prioritize the arcs that will yield changes in this case.

1. Starting with arc SA-WA. Since we know that WA's value is Green, then we revise the domain of SA and remove Green. This leaves SA only Red and Blue. Put all SA's neighbors except for WA into the queue.
2. Consider arc SA-V.  Since we know that V's value is Red, then we revise the domain of SA and remove Red. This leaves SA only Blue. Put all SA's neighbors except for V into the queue.
3. Consider arc NSW-V. Since we know that V's value is Red, then we revise the domain of NSW and remove Red. This leaves NSW only Blue and Green. Put all NSW's neighbors except for V into the queue.
4. Consider arc NT-WA. Since we know that WA's value is Green, then we revise the domain of NT and remove Green. This leaves NT only Red and Blue. Put all NT's neighbors except for WA into the queue.
5. Consider arc NT-SA. Since we know that SA's value is Blue, then we revise the domain of NT and remove Blue. This leaves NT only Red. Put all NT's neighbors except for SA into the queue.
6. Consider arc Q-SA. Since we know that SA's value is Blue, then we revise the domain of Q and remove Blue. This leaves Q only Red and Green. Put all Q's neighbors except for SA into the queue.
7. Consider arc NSW-SA. Since we know that SA's value is Blue, then we revise the domain of NSW and remove Blue. This leaves NSW only Green. Put all NSW's neighbors except for SA into the queue.
8. Consider arc Q-NT. Since we know that NT's value is Red, then we revise the domain of Q and remove Red. This leaves Q only Green. Put all Q's neighbors except for NT into the queue.
9. Consider arc Q-NSW. Since we know that NSW's value is Green, then we revise the domain of Q and remove Green. This leaves Q nothing in its domain. This shows an arc inconsistency of the partial assignment, and as a result AC-3 will detect the inconsistency of the partial assignment {WA=green, V =red} for the problem and return a failure (Boolean False).