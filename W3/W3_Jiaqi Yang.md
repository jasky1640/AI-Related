### Written Homework 3

#### Jiaqi Yang (jxy530)

#### Due October 11th, Friday

------

> 1. Basic Probability: Consider the following joint probability table of p(x, y). Each entry in row i and column j is p(x = i, y = j)
>
>    ​				     y
>    ​	  0.06 0.08 0.04 0.02
>    ​	  0.12 0.16 0.08 0.04
>    x	0.09 0.12 0.06 0.03
>    ​	  0.03 0.04 0.02 0.01

###### a) Show that this is a valid probability distribution.

The basic axioms of probability theory say that every possible world has a probability between 0 and 1 and that the total probability of the set of possible worlds is 1. Therefore, in this case, the probability distribution will be valid if the sum of all possible P(x = i, y = j) for i from 1 to 4 and j from 1 to 4 is 1. By adding all the probabilities of every single possible world, we get 1 and thus this is a valid probability distribution.

0.06  + 0.08 + 0.04 + 0.02 + 0.12 + 0.16 + 0.08 + 0.04 + 0.09 + 0.12 + 0.06 + 0.03 + 0.03 + 0.04 + 0.02 + 0.01 = 1

###### b) Write the mathematical expression for the distribution p(x) and calculate the distribution from the table.

From the table, we get all the probabilities of every single possible world. From there, we could calculate by the expression: the value of p(x = i) is equal to the sum of all p(x = i, y = j) for all possible values of j. 

P(x = 1, y = 1) = 0.06, P(x = 1, y = 2) = 0.08, P(x = 1, y = 3) = 0.04, P(x = 1, y = 4) = 0.02 → P(x = 1) = 0.2

P(x = 2, y = 1) = 0.12, P(x = 2, y = 2) = 0.16, P(x = 2, y = 3) = 0.08, P(x = 2, y = 4) = 0.04 → P(x = 2) = 0.4

P(x = 3, y = 1) = 0.09, P(x = 3, y = 2) = 0.12, P(x = 3, y = 3) = 0.06, P(x = 3, y = 4) = 0.03 → P(x = 3) = 0.3

P(x = 4, y = 1) = 0.03, P(x = 4, y = 2) = 0.04, P(x = 4, y = 3) = 0.02, P(x = 4, y = 4) = 0.01 → P(x = 4) = 0.1 

​			↓									↓									↓									↓

P(y = 1) = 0.3				P(y = 2) = 0.4				P(y = 3) = 0.2				P(y = 4) = 0.1			

###### c) Are x and y independent? Justify your answer.

Independence between propositions x and by can be written as P(x ∧ y) = P(x) P(y). For every possible world with x = i and y = j, for i from 1 to 4 and j from 1 to 4, we could get the probability of P(x ∧ y), for example P(x = 1, y = 1), by multiplying P(x = 1) and P(y = 1). The phenomenon holds true for every possible world. Therefore, x and y are independent. 

------

> 2. For each of the following statements, either prove it is true or give a counter example.

###### a) If P(a|b, c) = P(b|a, c), then P(a|c) = P(b|c)

By conditional probability, we know that P(A|B) = P(A and B) / P(B). Then from P(a|b,c) we get P(a and b,c) / P(b,c), and from P(b|a,c) we get P(b and a,c) / P(a,c). Given the fact that P(a|b, c) = P(b|a, c). we could reduce them to P(b,c) = P(a,c) from the derived equations. We know P(A,B) = P(A|B) P(B), so by dividing P(c) from both P(b,c) and P(a,c), we get P(b|c) = P(a|c). Therefore we prove the statement is true.

###### b) If P(a|b, c) = P(a), then P(b|c) = P(b)

From P(a|b, c) = P(a), we learn that a is independent of b and c; however, it does not indicate that b is independent of c. From example, a is rolling a dice, b is rolling another dice. In this case, if c is rolling a third dice, then the result of a is independent of the result of b and c, and b is independent of the result of b. However, if c = b, which is also the result of rolling the second dice, then b and c are obviously not independent. Therefore we prove the statement is false.

###### c) If P(a|b) = P(a), then P(a|b, c) = P(a|c)

From P(a|b) = P(a), we learn that a is independent of b, however it does not imply that a is conditionally independent of b given c. A counterexample for this statement will be when c equals xor of a and b. Therefore we prove the statement is false.

------

> 3. Given the full joint distribution shown in Figure 13.3, calculate the following

![1570761231501](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1570761231501.png)

###### a) P(toothache)

We could calculate P(toothache) by summing up the probabilities for each possible value of the other variables, including catch and cavity, and thereby taking them out of the equation. 

P(toothache) = 0.108 + 0.012 + 0.016 + 0.064 = 0.2, or <0.2, 0.8> in vector notation

###### b) P(Cavity)

Similarly, we could calculate P(Cavity) by summing up the probabilities for each possible value of the other variables, including catch and toothache, and thereby taking them out of the equation. 

P(cavity) = 0.108 + 0.012 + 0.072 + 0.008 = 0.2, or <0.2, 0.8> in vector notation

###### c) P(Toothache|cavity)

By conditional probability, we know that P(A|B) = P(A and B) / P(B). Therefore, we could calculate P(Toothache|cavity), using the equation P(Toothache and cavity) / P(cavity). Similarly, we could calculate P(Toothache and cavity) by summing up the probabilities for each possible value of the other variable, including catch, and thereby taking it out of the equation. 

P(Toothache and cavity) = 0.108 + 0.012 = 0.12

P(Toothache|cavity) = P(Toothache and cavity) / P(cavity) = 0.12 / 0.2 = 0.6, or <0.6, 0.4> in vector notation

###### d) P(Cavity|toothache ∨ catch)

Similarly, by conditional probability, we know that P(A|B) = P(A and B) / P(B). Therefore, we could calculate P(Cavity|toothache ∨ catch), using the equation P(Cavity and (toothache ∨ catch)) / P(toothache ∨ catch).

P(toothache ∨ catch) = 0.108 + 0.012 + 0.016 + 0.064 + 0.072 + 0.144 = 0.416

P(Cavity and (toothache ∨ catch)) = 0.108 + 0.012 + 0.072 = 0.192

P(Cavity|toothache ∨ catch) = P(Toothache and (cavity ∨ catch)) / P(toothache ∨ catch) = 0.192 / 0.416 ≈ 0.462, or <0.462, 0.538> in vector notion

------

> 4. Consider two medical tests, A and B, for a virus. Test A is 95% effective at recognizing the virus when it is present, but has a 10% false positive rate (indicating that the virus is present, when it is not). Test B is 90% effective at recognizing the virus, but has a 5% false positive rate. The two tests use independent methods of identifying the virus. The virus is carried by 1% of all people. Say that a person is tested for the virus using only one of the tests, and that the test comes back positive for carrying the virus. Which test returning positive is more indicative of someone really carrying the virus? Justify your answer mathematically.

From the descriptions, we could get the following information:

P(virus = present) = 0.01

P(A = +|virus = present) = 0.95

P(A = +|virus = absent) = 0.1

P(B = +|virus = present) = 0.9

P(B = +|virus = absent) = 0.05

We want to know which test returning positive is more indicative of someone really carrying the virus; in other word mathematically, we want to know the values of P(virus = present|A = +) and P(virus = present|B = +).

By Bayes rule, we know P(Y|X) = P(X|Y) P(Y) / P(X). Therefore, we could calculate P(virus = present|A = +) by using the equation P(A = +|virus = present) P(virus = present) / P(A = +).

P(A = +) = P(virus = present) P(A = +|virus = present) + P(virus = present) P(A = +|virus = absent) = 0.01 * 0.95 + 0.99 * 0.1 = 0.1085

P(virus = present|A = +) = P(A = +|virus = present) P(virus = present) / P(A = +) = 0.95 * 0.01 / 0.1085 ≈ 0.088

Similarly, by Bayes rule, we know P(Y|X) = P(X|Y) P(Y) / P(X). Therefore, we could calculate P(virus = present|B = +) by using the equation P(B = +|virus = present) P(virus = present) / P(B = +).

P(B = +) = P(virus = present) P(B = +|virus = present) + P(virus = present) P(B = +|virus = absent) = 0.01 * 0.9 + 0.99 * 0.05 = 0.0585

P(virus = present|B = +) = P(B = +|virus = present) P(virus = present) / P(B = +) = 0.9 * 0.01 / 0.0585 ≈ 0.154

Given the facts that P(virus = present|A = +) = 0.088 and P(virus = present|B = +) = 0.154, test B returning positive is more indicative of someone really carrying the virus.

------

> 5. Show that the statement of conditional independence P(X,Y|Z) = P(X|Z) P(Y|Z) is equivalent to each of the statements P(X|Y,Z) = P(X|Z) and P(Y|X,Z) = P(Y|Z)

From the statement of corollary of conditional probability P(Y|X)=P(X,Y) / P(X), we can convert P(X,Y|Z) to P(X,Y,Z) / P(Z). 

Similarly, we can convert P(Y|Z) to P(Y,Z) / P(Z). Then, the statement of conditional independence becomes 

 P(X,Y|Z) = P(X|Z) P(Y|Z) → P(X,Y,Z) / P(Z) = P(X|Z) P(Y,Z) / P(Z)

From the product rule, we can convert P(X,Y,Z) into P(X|Y,Z) P(Y,Z), and therefore the expression becomes

P(X|Y,Z) P(Y,Z) / P(Z) = P(X|Z) P(Y,Z) / P(Z)

which can be easily reduced to P(X|Y,Z) = P(X|Z).

From the given statement of conditional independence P(X,Y|Z) = P(X|Z) P(Y|Z), we could convert it to P(Y|Z) = P(X,Y|Z) / P(X|Z).

Based on the conditional probability, we get P(X,Y) = P(X|Y) P(X). Also, we know that P(X,Y) = P(Y,X). Therefore, from P(X,Y|Z) we get P(X,Y|Z) = P(Y|X|Z) P(X|Z). Therefore, the expression becomes

P(Y|Z) = P(Y|X,Z) P(X|Z) / P(X|Z)

which can be easily reduced to P(Y|X,Z) = P(Y|Z).

Therefore, the statement of conditional independence P(X,Y|Z) = P(X|Z) P(Y|Z) is equivalent to each of the statements P(X|Y,Z) = P(X|Z) and P(Y|X,Z) = P(Y|Z).

------

> 6. Suppose you are a witness to a nighttime hit-and-run accident involving a taxi in Athens. All taxis in Athens are blue or green. You swear, under oath, that the taxi was blue. Extensive testing shows that, under dim lighting conditions, discrimination between blue and green is 75% reliable.

###### a) Is it possible to calculate the most likely color for the taxi? (Hint: distinguish carefully between the proposition that the taxi is blue and the proposition that it appears blue.)

From the descriptions, we could get the following information:

P(Witness = Blue|Taxi = Blue) = 0.75

P(Witness= Green|Taxi = Blue) = 0.25

P(Witness = Blue|Taxi = Green) = 0.25

P(Witness= Green|Taxi = Green) = 0.75

We want to know the most likely color for the taxi; in other word mathematically, we want to know the value of P(Taxi = Blue|Witness = Blue). 

By Bayes rule, we know P(Y|X) = P(X|Y) P(Y) / P(X). Therefore, we could calculate P(Taxi = Blue|Witness = Blue) by using P(Witness = Blue|Taxi = Blue) P(Taxi = Blue) / P(Witness = Blue).

P(Witness = Blue) = P(Witness = Blue|Taxi = Blue) P(Taxi = Blue) + P(Witness = Blue|Taxi = Green) P(Taxi = Green)

However, we are in the lack of information about the P(Taxi = Green) and P(Taxi = Blue). Since we know that all taxis in Athens are blue or green, we only need to know one of them, such as 30% of taxis in Athens are blue. With this information, it is not possible to calculate the most likely color for the taxi.

###### b) What if you know that 9 out of 10 Athenian taxis are green?

From the descriptions, we could get the following additional information:

P(Taxi = Blue) = 0.1

P(Taxi = Green) = 0.9

------

> 7. You are designing a new system for detecting an explosive device. Your current design has a 1% false negative rate and a 5% false positive rate. The estimated frequency of actual devices through a typical security check point where the system will be employed is 1 in 5,000. Use the variables E to represent the explosive device and D (or D1 and D2) to represent the detector(s).

###### a) What is the probability that when the detector indicates the presence of an explosive device that the person is actually carrying one? Show your work?



###### b) Assume the check point security detains and inspects someone whenever the detector signals positive. On average, how many people will have to be detained and inspected for every device actually found? (Assume the security staff is always able to find an explosive if one is present.)



###### c) Now suppose you have additional equipment that gives you an independent test for the same types of devices, but it’s noisier: the false negative rate is 5% and the false positive rate is 10%. What is the probability of a device when both of them signal a detection? Show your work.

