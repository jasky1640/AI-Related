import math
from kalah import Kalah


class Node:
    """ This class represents the node in our UCB Monte Carlo Tree
        pos -- a game position
        parent -- the parent node of current node
        children -- an array of the child node(s) of current node
        _total -- total game played at current node
        _win -- total game win at current node
        _lose -- total game lose at current node
        _untried_actions -- an array of legal moves that are not expanded yet
    """

    def __init__(self, pos, parent=None):
        self.pos = pos
        self.parent = parent
        self.children = []
        self._total = 0
        self._win = 0
        self._lose = 0
        self._untried_actions = None

    # Initialize untried actions and shuffle for randomization
    @property
    def untried_actions(self):
        if self._untried_actions is None:
            self._untried_actions = self.pos.legal_moves()
            # Sort the list so that the best option is in the front
            self._untried_actions.sort(key=lambda x: (int(self.pos.is_move_again(x)) * 2 + int(self.pos.is_capture(x)) * 1), reverse=True)
        return self._untried_actions

    # Average score at the current node
    @property
    def r_j(self):
        return (self._win * 1.0 + self._lose * - 1.0) / self._total

    # Total game played at the current node
    @property
    def t(self):
        return self._total

    # Return if the current node still has untried child(ren) to expand
    def is_expandable(self):
        return len(self.untried_actions) > 0

    # Return if the current node is terminal node
    def is_terminal(self):
        return self.pos.game_over()

    # Helper: return the index of the largest element
    def argmax(self, arr):
        return max(range(len(arr)), key=lambda x: arr[x])

    # Helper: return the index of the smallest element
    def argmin(self, arr):
        return min(range(len(arr)), key=lambda x: arr[x])

    # Return the best explored child
    def best_child(self, exploration_factor=2.0):
        # Max Player
        if self.pos.next_player() == 0:
            weight = [
                child.r_j + math.sqrt((exploration_factor * math.log(self.t) / child.t))
                for child in self.children
            ]
            return self.children[self.argmax(weight)]
        # Min Player
        else:
            weight = [
                child.r_j - math.sqrt((exploration_factor * math.log(self.t) / child.t))
                for child in self.children
            ]
            return self.children[self.argmin(weight)]

    # Expand current node with a randomly selected untried child
    def expand(self):
        # Pop the first element (the best option)
        selected_child = self.untried_actions.pop(0)
        res_pos = self.pos.result(selected_child)
        child_node = Node(res_pos, parent=self)
        self.children.append(child_node)
        return child_node

    # Describe the policy we used for simulation
    def simulate_policy(self, curr_pos, possible_moves):
        # Sort the list so that the best option is at the front
        possible_moves.sort(key=lambda x: (int(curr_pos.is_move_again(x)) * 2 + int(curr_pos.is_capture(x)) * 1), reverse=True)
        # Get the first option (the best one)
        return possible_moves[0]

    # Simulate till the end of the game
    def simulate(self):
        curr_pos = self.pos
        while not curr_pos.game_over():
            possible_moves = curr_pos.legal_moves()
            action = self.simulate_policy(curr_pos, possible_moves)
            curr_pos = curr_pos.result(action)
        return curr_pos.winner()

    # Backpropagate stats along the path from current node to root
    def backpropagate(self, result):
        if result == 1:
            self._win += 1
        if result == -1:
            self._lose += 1
        self._total += 1
        if self.parent:
            self.parent.backpropagate(result)

    def best_move(self):
        best_child = self.best_child(exploration_factor=0.0)
        legal_moves = self.pos.legal_moves()
        for legal_move in legal_moves:
            if(self.pos.result(legal_move).__eq__(best_child.pos)):
                return legal_move

def mcts_strategy(n):
    def fxn(pos):
        move = mcts(pos, n)
        return move

    return fxn


def mcts(pos, n):
    ''' Returns the UCB monte carlo tree search value of the given position, with the given iteration number
        pos -- a game position
        n -- an integer that represents the iteration number MCTS is going to run
        root -- the root node for the monte carlo tree
    '''
    root = Node(pos, parent=None)
    for _ in range(0, n):
        res_node = mcts_policy(root)
        res_reward = res_node.simulate()
        res_node.backpropagate(res_reward)
    return root.best_move()


def mcts_policy(root):
    curr_node = root
    while not curr_node.is_terminal():
        if curr_node.is_expandable():
            return curr_node.expand()
        else:
            curr_node = curr_node.best_child()
    return curr_node
