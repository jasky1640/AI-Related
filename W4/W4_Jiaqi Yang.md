### Written Homework 3

#### Jiaqi Yang (jxy530)

#### Due November 12th, Tuesday

------

> 1. It is quite often useful to consider the effect of some specific propositions in the context of some general background evidence (or information) that remains fixed, rather than in the complete absence of information. The following questions ask you to prove more general versions of the product rule and Bayes’ rule, with respect to some background evidence e:
>

###### a) Prove the conditionalized version of the general product rule: P(X,Y|e) = P(X|Y,e)P(Y|e)



###### b) Prove the conditionalized version of Bayes’ rule in Equation P(Y|X,e) = P(X|Y,e) P(Y|e) / P(X|e)



------

> 2.  Let Hx be a random variable denoting the handedness of an individual x, with possible values l or r. A common hypothesis is that left- or right-handedness is inherited by a simple mechanism; that is, perhaps there is a gene Gx, also with values l or r, and perhaps actual handedness turns out mostly the same (with some probability s) as the gene an individual possesses. Furthermore, perhaps the gene itself is equally likely to be inherited from either of an individual’s parents, with a small nonzero probability m of a random mutation flipping the handedness. 

![1573533142718](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1573533142718.png)

###### a) Which of the three networks in Figure 14.20 claim that **P**(Gfather,Gmother,Gchild) =**P**(Gfather )**P**(Gmother )**P**(Gchild )?



###### b)  Which of the three networks make independence claims that are consistent with the hypothesis about the inheritance of handedness?



###### c)  Which of the three networks is the best description of the hypothesis? 



###### d) Write down the CPT for the Gchild node in network (a), in terms of s and m. 



###### e) Suppose that P(Gfather =l) = P(Gmother =l) = q. In network (a), derive an expression for P(Gchild =l) in terms of m and q only, by conditioning on its parent nodes. 



###### f) Under conditions of genetic equilibrium, we expect the distribution of genes to be the same across generations. Use this to calculate the value of q, and, given what you know about handedness in humans, explain why the hypothesis described at the beginning of this question must be wrong. 



------

> 3.  Consider the Bayes net shown in Figure 14.23. 

![1573533590843](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1573533590843.png)

###### a)  Which of the following are asserted by the network *structure*? 

###### i) P(B,I,M) = P(B) P(I) P(M)



###### ii) P(J|G) = P(J|G,I)



###### iii) P(M|G,B,I) = P(M|G,B,I,J)



###### b)  Calculate the value of P(b, i, ![img](https://media.cheggcdn.com/study/94a/94aff690-4788-450f-9ce4-e9eaf324cf7e/7445-14-14EEI3.png) m, g, j). 



###### c) Calculate the probability that someone goes to jail given that they broke the law, have been indicted, and face a politically motivated prosecutor. 



###### d)   A **context-specific independence** (see page 542) allows a variable to be independent of some of its parents given certain values of others. In addition to the usual conditional independences given by the graph structure, what context-specific independences exist in the Bayes net in Figure 14.23? 



###### e)  Suppose we want to add the variable P =PresidentialPardon to the network; draw the new network and briefly explain any links you add. 

------

> 4. We have a bag of three biased coins a, b, and c with probabilities of coming up heads of 20%, 60%, and 80% respectively. One coin is drawn randomly from the bag (with equal likelihood of drawing each of the three coins), and then the coin is flipped three times to generate the outcomes X1, X2, and X3.

###### a) Draw the Bayesian network corresponding to this setup and define the necessary CPTs.



###### b) Calculate which coin was most likely to have been drawn from the bag if the observed flips come out heads twice and tails once.



------

> 5. Three soccer teams A, B, and C, play each other once. Each match is between two teams, and can be won, drawn, or lost. Each team has a fixed, unknown degree of quality - an integer ranging from 0 to 3 - and the outcome of a match depends on probabilistically on the difference in quality between the two teams.

###### a) Construct a relational (or probabilistic) model to describe this domain, and suggest numerical values for all the necessary probability distributions.



###### b) Construct an equivalent Bayesian network to describe this domain.



###### c) Suppose that in the first two matches A beats B and draws with C. Using an exact inference algorithm of your choice, compute the posterior distribution for the outcome of the third.

