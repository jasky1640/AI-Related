### Written Homework 4

#### Jiaqi Yang (jxy530)

#### Due November 12th, Tuesday

------

> 1. It is quite often useful to consider the effect of some specific propositions in the context of some general background evidence (or information) that remains fixed, rather than in the complete absence of information. The following questions ask you to prove more general versions of the product rule and Bayes’ rule, with respect to some background evidence e:
>

###### a) Prove the conditionalized version of the general product rule: P(X, Y|e) = P(X|Y, e) P(Y|e)

![1573600014617](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1573600014617.png)

###### b) Prove the conditionalized version of Bayes’ rule in Equation P(Y|X,e) = P(X|Y, e) P(Y|e) / P(X|e)

![1573600065855](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1573600065855.png)

------

> 2.  Let H<sub>x</sub> be a random variable denoting the handedness of an individual x, with possible values l or r. A common hypothesis is that left- or right-handedness is inherited by a simple mechanism; that is, perhaps there is a gene G<sub>x</sub>, also with values l or r, and perhaps actual handedness turns out mostly the same (with some probability s) as the gene an individual possesses. Furthermore, perhaps the gene itself is equally likely to be inherited from either of an individual’s parents, with a small nonzero probability m of a random mutation flipping the handedness. 

![1573533142718](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1573533142718.png)

###### a) Which of the three networks in Figure 14.20 claim that **P**(G<sub>father</sub>, G<sub>mother</sub>, G<sub>child</sub>) =**P**(G<sub>father</sub>) **P**(G<sub>mother</sub>) **P**(G<sub>child</sub>)?

Network (c). P(G<sub>father</sub>, G<sub>mother</sub>, G<sub>child</sub>) = P(G<sub>father</sub>) P(G<sub>mother</sub>) P(G<sub>child</sub>) means that the three variables are independent. Only in network (c) they are independent.

###### b)  Which of the three networks make independence claims that are consistent with the hypothesis about the inheritance of handedness?

Networks (a) and (b). All the G’s of a person are causes of H’s of a person (satisfied by all networks). G’s of parents are causes of G’s of children (satisfied by (a) and (b)). Therefore, networks (a) and (b) are consistent with the hypothesis.

###### c)  Which of the three networks is the best description of the hypothesis? 

Network (a). Even though (a) and (b) both have independence claims consistent with thy hypothesis, the hypothesis doesn’t state that the Handedness of parents directly influence the Handedness of children. And (a) agrees with that, while (b) assumes direct influence from H of parents to H of children.  

###### d) Write down the CPT for the Gchild node in network (a), in terms of s and m. 

| G­<sub>father</sub> | G<sub>mother</sub> | P(G<sub>child</sub>) = l | P(G<sub>child</sub>) = r |
| :-----------------: | :----------------: | :----------------------: | :----------------------: |
|          l          |         l          |           1-m            |            m             |
|          l          |         r          |           1/2            |           1/2            |
|          r          |         l          |           1/2            |           1/2            |
|          r          |         r          |            m             |           1-m            |

s does not influence G<sub>child</sub> at all, because s only accounts for the difference in G and H.

###### e) Suppose that P(G<sub>father</sub> = l) = P(G<sub>mother</sub> = l) = q. In network (a), derive an expression for P(G<sub>child</sub> =l) in terms of m and q only, by conditioning on its parent nodes. 

P(G<sub>child</sub> = l) = ∑ P(G<sub>father</sub>) P(G<sub>mother</sub>) P(G<sub>child</sub>│G<sub>father</sub>, G<sub>mother</sub>)
  =q * q * (1 - m) + q * (1 - q) * 1/2 + q * (1 - q) * 1/2 + (1 - q) * (1 - q) * m
  =q + m - 2qm 

###### f) Under conditions of genetic equilibrium, we expect the distribution of genes to be the same across generations. Use this to calculate the value of q, and, given what you know about handedness in humans, explain why the hypothesis described at the beginning of this question must be wrong. 

Since the distribution of genes across generations is constant,

q = q + m - 2qm       ∴ m = 2qm       ∴ q = 0.5

But less than half of humans are left handed, which contradicts with q = 0.5. Therefore, the hypothesis is wrong. 

------

> 3.  Consider the Bayes net shown in Figure 14.23. 

![1573533590843](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1573533590843.png)

###### a)  Which of the following are asserted by the network *structure*? 

###### i) P(B, I, M) = P(B) P(I) P(M)

(i) is not true, because I is dependent on B and M.  

###### ii) P(J|G) = P(J|G, I)

(ii) is true because J is independent of I given J.   

###### iii) P(M|G, B, I) = P(M|G, B, I, J)

(iii) is true because M is independent of J given its parents (none), children (I, J), and parents of children (B).  

###### b)  Calculate the value of P(b, i, ![img](https://media.cheggcdn.com/study/94a/94aff690-4788-450f-9ce4-e9eaf324cf7e/7445-14-14EEI3.png) m, g, j). 

P(b, i, ~m, g, j)= P(b) P(~m) P(i│b,~m) P(g│b, i, ~m) P(j│g) = 0.9 * (1 - 0.1) * 0.5 * 0.8 * 0.9 = 0.2916

###### c) Calculate the probability that someone goes to jail given that they broke the law, have been indicted, and face a politically motivated prosecutor. 

b = t, i = t, m = t    ∴ P(g) = 0.9  

P(j│b, i,  m)=P(j│g) P(g) + P(j│~g) P(~g) = 0.9 * 0.9 + 0 * 0.1 = 0.81

###### d)   A **context-specific independence** (see page 542) allows a variable to be independent of some of its parents given certain values of others. In addition to the usual conditional independences given by the graph structure, what context-specific independences exist in the Bayes net in Figure 14.23? 

G(FoundGuilty) is independent of B(BrokeElectionLaw) and M(PoliticallyMotivatedProsecutor) if I(Indicted) is true.

###### e)  Suppose we want to add the variable P =PresidentialPardon to the network; draw the new network and briefly explain any links you add. 

Pardon is only needed if the person is found guilty. Therefore, P is a child of G.

Pardon is going to influence if a person goes to jail. Therefore, P is a parent of J.

![1573603224895](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1573603224895.png)

------

> 4. We have a bag of three biased coins a, b, and c with probabilities of coming up heads of 20%, 60%, and 80% respectively. One coin is drawn randomly from the bag (with equal likelihood of drawing each of the three coins), and then the coin is flipped three times to generate the outcomes X1, X2, and X3.

###### a) Draw the Bayesian network corresponding to this setup and define the necessary CPTs.

C stands for Coin picked and can have values a, b, c. X1, X2, X3 are the 3 outcomes of the coin toss.

|  C   | X<sub>n</sub> = heads |
| :--: | :-------------------: |
|  a   |          0.2          |
|  b   |          0.6          |
|  c   |          0.8          |

![1573603300291](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1573603300291.png)

###### b) Calculate which coin was most likely to have been drawn from the bag if the observed flips come out heads twice and tails once.

In order to find the coin most likely to produce the desired result, we need to find C that maximizes P(2 heads, 1 tail | C).

C=a: P = P(head) P(head) P(tail) * (3 combinations) = 0.2 * 0.2 * (1 - 0.2) * 3 = 0.096

C=b: P = 0.6 * 0.6 * (1 - 0.6) * 3 = 0.432

C=c: P = 0.8 * 0.8 * 0.2 * 3 = 0.384

P is greatest when C = b. Therefore, Coin B is most likely to have been drawn.

------

> 5. Three soccer teams A, B, and C, play each other once. Each match is between two teams, and can be won, drawn, or lost. Each team has a fixed, unknown degree of quality - an integer ranging from 0 to 3 - and the outcome of a match depends on probabilistically on the difference in quality between the two teams.

###### a) Construct a relational (or probabilistic) model to describe this domain, and suggest numerical values for all the necessary probability distributions.

There are 3 teams, which are the parents (priors) in this problem. And 3 matches, which are the children. 

The outcome of a match is represented by numbers which are then interpreted to become win, draw, or loss. The number value is calculated with (team 1’s quality) – (team 2’s quality). So that when the value is larger than 1, team 1 is more likely to win. When the value is between -1 and 1, a draw is more likely to happen. When the value is smaller than -1, team 2 is more likely to win.

Suggest numerical value of 1. So that when the value is larger than 1, team 1 wins. When the value is between -1 and 1, a draw is going to happen. When the value is smaller than -1, team 2 wins.

###### b) Construct an equivalent Bayesian network to describe this domain.

![1573603442314](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1573603442314.png)

###### c) Suppose that in the first two matches A beats B and draws with C. Using an exact inference algorithm of your choice, compute the posterior distribution for the outcome of the third.

Since A,B,C are independent, also AB,BC,AC are independent given A,B,C, AB=w, AC=d.

P(AB│A, B) = P(AB, A, B) / (P(A) P(B)), P(AC│A, C) = P(AC, A, C) / (P(A) P(C))

Therefore, given the results of the previous matches and the numerical value suggested in part a, 

A – B > 1, and -1 <= A – C <= 1   

P(BC│AB, AC) = P(BC, AB, AC) / P(AB) P(AC) = P(BC│B, C) P(B) P(C)

Since we know B < A - 1 and A – 1 <= C <= A + 1, we have B < C.

Since B and C are both integers, C >= B + 1

Therefore, it is most likely that C wins.