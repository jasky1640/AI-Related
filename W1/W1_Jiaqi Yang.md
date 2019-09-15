### Written Homework 1 
#### Jiaqi Yang (jxy530)
#### Due September 17th, Tuesday

---
##### Problem 1
Alpha-Go, a computer program that plays the board game Go, was developed by Google DeepMind. Standard Go board has 19x19 grids of lines, containing 361 points, significantly more complicated than Chess. The naïve algorithm such as table-driven algorithm could not predict more than several steps due to the large amount of possible move. A proficient Go player will normally predict more than 10 steps with a macro perspective of the board. At 2017, Alpha-Go beat Jie Ke, the best Go player in the world by the time. 

Alpha-Go's algorithm combines machine learning and tree search techniques, with extensive training from both human and computer play. [1] It uses Monte Carlo tree search, guided by a "value network" and a "policy network," both implemented using deep neural network technology. [2] A few special cases, nakada pattern (well-studied moves in some special situations) will be placed. Like reflex agents, once nakada pattern is detected, the move will be made without sending to neural network. [2]

The system was initially trained to mimic human play by attempting to match the moves of expert players from recorded historical games, using a database of around 30 million moves. [3] Once it had reached a certain degree of proficiency, it was trained further by being set to play large numbers of games against other instances of itself, using reinforcement learning to improve its play. [1] 

> ###### Reference:
> ###### 1. "Research Blog: AlphaGo: Mastering the ancient game of Go with Machine Learning". Google Research Blog. 27 January 2016.
> ###### 2. Silver, David; Huang, Aja; Maddison, Chris J.; Et al. (28 January 2016). "Mastering the game of Go with deep neural networks and tree search". Nature. 529 (7587): 484–489.
> ###### 3. Metz, Cade (27 January 2016). "In Major AI Breakthrough, Google System Secretly Beats Top Player at the Ancient Game of Go". WIRED. Retrieved 1 February 2016.

---
##### Problem 2

| Agent Type | Performance Measure | Environment | Actuators | Sensors |
| :-: | :-: | :-: | :-: | :-: |
| Shopping for used AI books on the Internet | Price, Book Quality (Customer Rate, Book Condition) | Websites for Book Rental | Programs that place the book rental order in the websites | Programs that collect the price, customer rates, and other relevant information from the book rental websites |
| Bidding on an item at an auction | Purchased Price and Revenue (Compared to market value) if win a bid, Potential Gain/Loss (Compared to market value) if lose a bid, Hit/Miss Rate (if a bid is profitable in the final bidding price, do we get the item?) | Websites for Auction, Other Online Bidders if the auction is online; Auctioneers, Other Bidders, Auction Hall if the auction happens in the real life | Speaker/Display/Jointed Arm and Hand to announce the bidding price, Programs that enter bidding price in the website | Perception of Voice or Display of bidding price by other bidders (Voice/Image Sensor or Programs that monitor bids) |

For the scenario of shopping for used AI books on the Internet, the environment (Websites for Book Rental) is represented as a HashMap, whose keys are names of the websites in the form of String and corresponding values are HashMaps (Whose keys are names of the books in the form of String, and corresponding values are Books. Book is a class that contains all the information about the book, including price, customer rate, and book condition.) The performance could be evaluated, based on all the stored information, by modeling all the factors of rental book quality into a score. The score will provide insight for the performance evaluation of program output.

For the scenario of bidding on an item at an auction, the environment is represented as a HashMap, whose keys are the names/ids of the bidders and the corresponding values are Bid. Bid is a class that contains the current highest bidding price, a list of bidding history, and the bids' time. The performance could be evaluated, based on the final bidding price and market price/profitable price of the item, that 1) if a bid is won, the AI's price is higher or lower compared to the market price/profitable price, 2) if a bid is not won, if the final price is higher or lower compared to the market price/profitable price, 3) if the AI participates in multiple auditions, what is the rate of hit (bid won when the final price is profitable) or the rate of miss (bid not won when the final price is profitable).

---
##### Problem 3

Since there are S different states, and the simple reflex agents has A actions, one possible simple reflex agent can be represented by condition-action rule.

> if State 1, then Action 1/2/.../A
>
> ......
>
> if State S, then Action 1/2/.../A

Therefore, since two agents are distinct if there exists some world state where they take different actions, there are **A<sup>S</sup>** distinct agents.

---
##### Problem 4

Given the facts that there are S different states, that the simple reflex agent has A actions, and that the simple reflex agent is equipped with a memory to remember the past k states, one possible simple reflex agent can be represented by condition-action rule.

> if last k state is State 1, last k-1 state is State 1, ... , current state is State 1, then Action 1/2/.../A
>
> ......
>
> if last k state is State A, last k-1 state is State A, ... , current state is State A, then Action 1/2/.../A

Therefore, since two agents are distinct if there exists some world state where they take different actions, there are **A<sup>S<sup> (k+1)</sup></sup>** distinct agents.

---
##### Problem 5

The information that needs to be stored for a single state with reasonably minimal memory requirements for 8-puzzle is a 3x3 char array, and for 15 puzzle is a 4x4 char array. The storage size of a single char value is 1 bytes, therefore the storage size of 3x3 char array for 8 puzzle is 9 bytes, and the storage size of 4x4 char array for 15 puzzle is 16 bytes.

The state space is the set of all states reachable from the initial state by any sequence of actions. For 8 puzzle, there are 9!/2 = 181440 reachable states; for 15 puzzle, there are 16!/2 ≈ 1.064x10<sup>13</sup> reachable states. 

Combined with the calculation of reasonably minimal memory requirements,  we need 1632960 bytes to store the 8-puzzle state space and approximately 1.674x10<sup>14</sup> bytes to store the 15-puzzle state space.

---
##### Problem 6

Don't know

---
##### Problem 7

> a. Suppose the state space consists of all positions (x,y) in the plane. How many states are there? How many paths are there to the goal?

Assume the number of polygonal obstacles is finite and therefore leaves free space, the number of coordinate pairs (x,y) is infinite. The state space contain all the positions (x,y) in the free space, and therefore the number of states is infinite.

Assume the goal state is reachable, there will be infinite paths toward the goal state and one optimal path toward the goal state.

> b. Explain briefly why the shortest path from one polygon vertex to any other in the scene must consist of straight-line segments joining some of the vertices of the polygons. Define a good state space now. How large is this state space?

If there is no polygonal obstacles, the shortest path will surely be a straight line between starting point and goal. Given the fact that polygonal obstacles exist, the shortest path will then become segments of straight lines. To bypass the obstacles, the shortest way is to travel to the vertex of the polygon in the way and then try to reach goal in a straight line. If not possible, then the shortest way is to travel to next vertex of the polygon in the way and try to reach goal in a straight line again. Therefore, the shortest path from one polygon vertex to any other in the scene must consist of straight-line segments joining some of the vertices of the polygons.

Given the proof that the shortest path must consist of the vertices of the polygons, we could safely ignore all the coordinate pairs (x,y) except for the coordinate pairs that represent the vertices of the polygons. In this way, we reduce the size of state space from infinite to the number of polygonal vertices in the plane as well as at least more than two paths from starting point to vertex to goal.  

------

##### Problem 8

> a. Breath-first search is a special case of uniform-cost search.

By definition, Breadth-first search expands node in First-In-First-Out order, and Uniform-cost search expands node with lowest path cost g(n).

In the special case that all step costs are equal to the same constant value, the behavior of Breadth-first search and Uniform-cost search will be the same.

> b. Depth-first search is a special case of best-first tree search.

By definition, Depth-first search expands node in Last-In-First-Out order, and Best-first tree search expands node with lowest heuristic path cost h(n).

In the special case that the heuristic path cost h(n) equals to 1/the depth of the node, the behavior of Depth-first search and Best-first tree search will be the same.

> c. Uniform-cost search is a special case of A* search.

By definition, Uniform-cost search expands node with lowest path cost g(n), and A* search expands node with lowest f(n) = g(n) + h(n).

In the special case that h(n) equals to 0, the node expansion logics for Uniform-cost search and A* search are both based on g(n), and therefore both of them behaves the same.

---
##### Problem 9

![1568503488296](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1568503488296.png)

A* search expands best f(n) = g(n) + h(n)

---
##### Problem 10

> a. Local beam search with k=1

This will simply become Hill-climbing search since when Local beam search starts with one random state, it looks at the neighboring states and searches for the local maximum.

> b. Local beam search with one initial state and no limit on the number of states retained

This will simply become Breadth-first search since this algorithm looks at all the neighbors, then their neighbors, and so on.

> c. Stimulated annealing with T = 0 at all times (and omitting the termination test)

Since we omit the termination test, in this special case, Stimulated annealing simply becomes Hill-climbing search (Specifically, First-choice Hill-climbing search) because the search will not accept any downward successor.

> d. Stimulated annealing with T = ∞ at all times

Since we omit the termination test, in this special case, Stimulated annealing will accepts any successor with probability of 1 and wander downward among the nodes. This could be counted as roughly Depth-first search, or most accurately Random walk search.

> e. Genetic algorithm with population size N = 1

If the population size is 1, crossover will not affect the result, and therefore only left random mutation. As a result, the algorithm becomes Random walk search.