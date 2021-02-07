import time
import random


class Agent:
    def __init__(self, game):
        """
        dict: Nested Dictionary: Position(string rep) -> Action(Offensive) -> Q-value
        game: Model passed from test driver
        """
        self.dict = {}
        self.game = game

    def best_action(self, pos):
        """Find the best action with highest Q-value"""
        'Dictionary for this current superstate'
        action_dict = self.dict[self.find_superstate(pos)]

        best_action = None
        best_q = None
        for x in range(0, self.game.offensive_playbook_size()):
            'Replace if this is first element or this has a better Q-value'
            if (best_action is None and best_q is None) or (action_dict[x] > best_q):
                best_action = x
                best_q = action_dict[x]
        return best_action, best_q

    def initialize_pos(self, curr_pos):
        """Initialize the position in superstate dictionary"""
        curr_superstate = self.find_superstate(curr_pos)
        if curr_superstate not in self.dict:
            self.dict[curr_superstate] = {}
            for x in range(0, self.game.offensive_playbook_size()):
                res_pos, _ = self.game.result(curr_pos, x)
                if self.game.game_over(res_pos) and self.game.win(res_pos):
                    self.dict[curr_superstate][x] = 1
                else:
                    self.dict[curr_superstate][x] = 0

    def weight_choose(self, curr_pos, epsilon):
        """Choose the next action using epsilon-greedy"""
        'List of actions available for current superstate'
        action_list = list(self.dict[self.find_superstate(curr_pos)].keys())

        'Best action for current superstate'
        best_action, _ = self.best_action(curr_pos)

        'Generate probability list using epsilon-greedy'
        prob_list = [epsilon / self.game.offensive_playbook_size() for i in range(self.game.offensive_playbook_size())]
        prob_list[best_action] += 1 - epsilon

        'Weight-choose the result'
        return random.choices(action_list, weights=prob_list, k=1)[0]

    def find_superstate(self, curr_pos):
        """Find the superstate representation of current state"""
        if self.game.game_over(curr_pos):
            return str([3, 3])
        else:
            res_list = []
            yard_per_down = curr_pos[2] / curr_pos[1]
            yard_per_tick = curr_pos[0] / curr_pos[3]
            if yard_per_down <= 2.2:
                res_list.append(0)
            elif 2.2 < yard_per_down < 3.6:
                res_list.append(1)
            else:
                res_list.append(2)

            if yard_per_tick <= 2.0:
                res_list.append(0)
            elif 2.0 < yard_per_tick < 5.8:
                res_list.append(1)
            else:
                res_list.append(2)
            return str(res_list)


def qfl_train(game, t_limit, discount_factor=0.99, alpha=0.2, epsilon=1):
    """QFL algorithm using the equation: Q(s,a) <- Q(s,a) + alpha (r + discount_factor * max(Q(s',a)) - Q(s,a))"""
    alpha_discount = 0.999985
    epsilon_rapid_discount = 0.99
    end_time = time.time() + t_limit - 0.1
    res = Agent(game)

    while time.time() < end_time:
        's <-- s0'
        curr_pos = game.initial_position()
        'Each game till game_over is an episode'
        while not game.game_over(curr_pos):
            'If the current superstate has child in the dictionary'
            curr_superstate = res.find_superstate(curr_pos)
            if curr_superstate not in res.dict:
                res.initialize_pos(curr_pos)

            'Choose action using epsilon-greedy'
            action = res.weight_choose(curr_pos, epsilon)

            'Get the resulting position from the action'
            res_pos, _ = game.result(curr_pos, action)

            'If the result superstate has child in the dictionary'
            res_superstate = res.find_superstate(res_pos)
            if res_superstate not in res.dict:
                res.initialize_pos(res_pos)

            "Calculate the Q-value using the equation: "
            "Q(s,a) <- Q(s,a) + alpha (r + discount_factor * max(Q(s',a)) - Q(s,a)) "
            'reward: The value of r'
            reward = 0
            if game.game_over(res_pos) and game.win(res_pos):
                reward = 1

            'best_res_q: The value of max(Q(s\',a))'
            _, best_res_q = res.best_action(res_pos)

            'curr_q_val: The value of Q(s,a)'
            curr_q_val = res.dict[curr_superstate][action]

            'Update the Q(s,a)'
            res.dict[curr_superstate][action] += alpha * (reward + discount_factor * best_res_q - curr_q_val)

            'Update curr_position'
            curr_pos = res_pos

        'Update Alpha after each episode'
        alpha *= alpha_discount
        if epsilon > 0.05:
            epsilon *= epsilon_rapid_discount

    return res


def q_learn(game, t_limit):
    res = qfl_train(game, t_limit)

    def fxn(pos):
        best_action, _ = res.best_action(pos)
        return best_action

    return fxn
