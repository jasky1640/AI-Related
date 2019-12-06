### Programming Assignment 3

#### Jiaqi Yang (jxy530)

#### December 6th, Friday

------

##### Exercise 1. Linear decision boundaries

###### a) Inspect irisdata.csv which is provided with the assignment file on Canvas. Write a program that loads the iris data set and plots the 2nd and 3rd iris classes, similar to the plots shown in lecture on neural networks (i.e. using the petal width and length dimensions).

The following code fragment is from p3_q1

```matlab
% import data from cvs file before using the program
pl = irisdata.petal_length;
pw = irisdata.petal_width;
species = irisdata.species;

% analyze
figure;
hold on
for i = 1:150
   if species(i) == "virginica" 
       p1 = scatter(pl(i),pw(i),'b+');
   elseif species(i) == "versicolor"
       p2 = scatter(pl(i),pw(i),'g*');
   end
end
legend([p1,p2],'virginica', 'versicolor');
```

The output of the program gives the plot below:

![1575599867329](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1575599867329.png)

###### b) Define a function that computes the output of simple one-layer neural network using a logistic non- linearity (linear classification with logistic regression). Use an output of 0 to indicate the 2nd iris class, and 1 to indicate the 3rd.

w stands for weight vector, x and y are input data (x is pedal length, y is pedal width in this case)

```matlab
function output = Q1b(w,b,x,y)
	%0 for 2nd class, 1 for 3rd class
	h = (1-1./(1+exp(-w*(x-b))))*3;
	if h > y
		output = 0;
	else
		output = 1;
	end
end
```



###### c) Write a function that plots the decision boundary for the non-linearity above overlaid on the iris data. Choose a boundary (by setting weight parameters by hand) that roughly separates the two classes.

The weight chosen are same as in part b. The left of boundary is class 2, and the right of the boundary is class 3.



The following code fragment is from p3_q1

```matlab
% plot the boundary
w = [-4 0.5 1];
m = -w(2)/w(3); b = -w(1)/w(3); x = 2:7;
plot(x, m*x+b);
```

![1575599272867](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1575599272867.png)

###### d) Use a surface plot from a 3D plotting library (e.g. in matlab or using matplotlib in python) to plot the output of your neural network over the input space. This should be similar to fig 18.7c in the textbook.

The following code fragment is from p3_q1

```matlab
% surface plot
x = 0:0.1:9;
y = 0:0.1:9;
f = zeros(91);
for i = 1:91
    for j = 1:91
        f(i,j) = Q1b(w,x(i),y(j));
    end
end
 
figure(2)
surf(x,y,f)
```

The output of the program gives the plot below:

![1575600297667](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1575600297667.png)

###### e) Show the output of your simple classifier using examples from the 2nd and 3rd Iris classes. Choose examples that are unambiguous as well as those that are near the decision boundary.

The following code fragment is from p3_q1

```matlab
function output = Q1b(w,x,y)
    %0 for 2nd class, 1 for 3rd class
    a = [1 x y];
    output = 1/(1+exp(-dot(w,a)));
end
```

The sample runs with w = [-4 0.5 1]

**Run #1 (Class 2):**

```matlab
>> Q1b(w,3.9,1.2)
ans =
    0.2994
```

In this case x = 3.9 and y = 1.2, the plant is most likely to be class 2.

**Run #2 (Ambiguous)：**

```
>> Q1b(w,5.1,1.8)
ans =
    0.5866
```

In this case x = 5.1 and y = 1.8, the plant is 59% likely to be class 3.

**Run #3 (Class 3):**

```
>> Q1b(w, 6, 2.5)
ans =
	0.8176
```

In this case x = 6 and y = 2.5, the plant is most likely to be class 3.

------

##### Exercise 2. Neural networks

###### a) Write a program that calculates the mean-squared error of the iris data for the neural network defined  above. The function should take three arguments: the data vectors, the parameters defining the neural network, and the pattern classes.

In the logistic neural network defined previously in Exercise 1, the probability that a certain point is of class 2 (output 0) or class 3 (output 1) is outputted. The error is the difference between the outputted probability and the known data.

```matlab
function output = Q2a(pl, pw, species, w, class1, class2)
	hold on
	% Initialize error
	e = 0;
	% For each data point
	for i = 1:150
		% Find the true data
		t = -1;
		if species(i) == class2
			t = 1;
		elseif species(i) == class1
			t = 0;
		end
		% Find the probability for relevant data
		if t ~= -1
			a = [1 pl(i) pw(i)];
			p = 1/(1+exp(-dot(w,a)));
			% Add to square error
			e = e + (t-p)^2;
		end
	end
	% Find mean square error over 100 data points of types 2 or 3
	output = e/100;
end
```

###### b) Compute the mean squared error for two different settings of the weights (i.e. two different decision boundaries). Like above, select these by hand and choose settings that give large and small errors respectively. Plot both boundaries on the dataset as above.

```matlab
>> Q2a(pl,pw,species,[-4 0.5 1],"versicolor","virginica")
ans =
	0.1280
```

![1575601772203](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1575601772203.png)

This example presents a relatively good boundary, which few data is on the other side of the boundary, and the logistic regression would have them as a large percentage error.

```matlab
>> Q2a(pl,pw,species,[3 2 3],"versicolor","virginica")
ans =
	0.5000
```

![1575601928891](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1575601928891.png)

While this example presents a bad boundary, which everything is classified as type 3. Therefore, there is a large error.

###### c) Give a mathematical derivation the gradient of the objective function above with respect to the neural network output. You will have to use the derivative of the logistic function as discussed in class. Use w0 to represent the bias term. You should show and explain each step.

![1575604507218](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1575604507218.png)

###### d) Show how the gradient can be written in both scalar and vector form.

![1575604511936](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1575604511936.png)

###### e) Write code that computes the summed gradient for an ensemble of patterns. Illustrate the gradient by showing (i.e. plotting) how the decision boundary changes for a small step.

The following code fragment is from p3_q2a, which is revised version of part a.

```matlab
function output = Q2a(pl, pw, species, w, class1, class2)
    figure;
    hold on

    % Initialize error and count
    e = 0; c = 1;
    s = zeros(100,1); p = zeros(100,1); x = zeros(100,3);

    % For each data point
    for i = 1:150
       % Find the true data
       if species(i) == class2 
           s(c) = 1;
           scatter(pl(i),pw(i),'b+');
       elseif species(i) == class1
           s(c) = 0;
           scatter(pl(i),pw(i),'g*');
       end
       % Find the probablility for relevant data
       if species(i) ~= "setosa"
           x(c,1) = 1;
           x(c,2) = pl(i);
           x(c,3) = pw(i);
           p(c) = 1/(1+exp(-dot(w,[1 pl(i) pw(i)])));
           % Add to square error
           e = e + (s(c)-p(c))^2;
           c = c + 1;
       end
    end

    % Find mean square error over 100 data points of types 2 or 3
    output(1) = e/100;

    m = -w(2)/w(3); b = -w(1)/w(3); xaxis = 2:7;
    plot(xaxis, m*xaxis+b);
    
    % Find gradient
    gradient = (x')*((p-s).*(1-p))/50;
    output(2) = gradient(1);
    output(3) = gradient(2);
    output(4) = gradient(3);
end
```

The following code fragment is from p3_q2e: it starts at an arbitrarily chosen weight and uses gradient descent 4 times, resulting in the following 5 graphs.

```matlab
% Define small step
e = .1;
% Define initial weights
w = [-5 1 1];
% Store data
pl = irisdata.petal_length;
pw = irisdata.petal_width;
species = irisdata.species;
% Plot, compute error and gradient
output = Q2a(pl,pw,species,w,"versicolor","virginica");
% Loop a few (4) times
for i = 1:4
    w(1) = w(1) - output(2)*e;
    w(2) = w(2) - output(3)*e;
    w(3) = w(3) - output(4)*e;
    output = Q2a(pl,pw,species,w,"versicolor","virginica");
end
```

![1575603877826](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1575603877826.png)

 								![1575603889144](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1575603889144.png)		

![1575603910192](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1575603910192.png)

![1575603928935](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1575603928935.png)

![1575603937503](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1575603937503.png)

From the generated plots, we could observe that each time the boundary moves to a place with a potential smaller error function.

------

##### Exercise 3. Learning a decision boundary through optimization

###### a) Using your code above, write a program that implements gradient descent to optimize the decision boundary for the iris dataset.

The code for this problem follows the same principle as Exercise 2, except for the following changes made: this program stops either when the number of iteration reaches 50 or when error is less than 0.05 rather than having a set small number of iterations.



The following modified code fragment is from p3_q3.

```matlab
% Define small step
e = .1;
% Define initial weights
w = [-5 1 1];
% Store data
pl = irisdata.petal_length;
pw = irisdata.petal_width;
species = irisdata.species;
% Plot, compute error and gradient
output = Q2a(pl,pw,species,w,"versicolor","virginica");
mse(1) = output(1);
% Loop until reach stopping condition
mse = zeros(1,50);
c = 1;
while output(0) > 0.05 && c < 50
    c = c + 1;
    w(1) = w(1) - output(2)*e;
    w(2) = w(2) - output(3)*e;
    w(3) = w(3) - output(4)*e;
    output = Q2a(pl,pw,species,w,"versicolor","virginica");
    mse(c) = output(1);
end

figure();
plot(mse);
```

This is the final output with MSE of 0.1178 and gradient of [0.0345, 0.0057, -0.0357]. At this point, the program stops.

###### b) In your program, include code that shows the progress in two plots: the first should show the current decision boundary location overlaid on the data; the second should show the learning curve, i.e. a plot of the objective function as a function of the iteration (see figure 18.18 of the textbook).

The following modified code fragment is from p3_q3, including the boundary plotting functionality. The array mse stores all the mean squares errors, and the program plots.

```matlab
% Define small step
e = .1;
% Define initial weights
w = [-5 1 1];
% Store data
pl = irisdata.petal_length;
pw = irisdata.petal_width;
species = irisdata.species;
% Plot, compute error and gradient
output = Q2a(pl,pw,species,w,"versicolor","virginica");
mse(1) = output(1);
% Loop until reach stopping condition
mse = zeros(1,50);
c = 1;
while output(0) > 0.05 && c < 50
    c = c + 1;
    w(1) = w(1) - output(2)*e;
    w(2) = w(2) - output(3)*e;
    w(3) = w(3) - output(4)*e;
    output = Q2a(pl,pw,species,w,"versicolor","virginica");
    mse(c) = output(1);
end

figure();
plot(mse);
```

![1575605751390](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1575605751390.png)

![1575605759148](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1575605759148.png)

###### c) Run your code on the iris data set starting from a random setting of the weights. Note: you might need to restrict the degree of randomness so that the initial decision boundary is visible somewhere in the plot. In your writeup, show the two output plots at the initial, middle, and final locations of the decision boundary.

The majority of the code for this part is same as part a and b, except the definition of initial weight is now randomly selected within a region. The generated plots are in the order of initial, middle, and final plot.

```matlab
% Define small step
e = .1;
% Define initial weights
w(1) = rand() * 2 - 5;
w(2) = rand();
w(3) = rand() + 0.5;
% Store data
pl = irisdata.petal_length;
pw = irisdata.petal_width;
species = irisdata.species;
% Plot, compute error and gradient
output = Q2a(pl,pw,species,w,"versicolor","virginica");
mse(1) = output(1);
% Loop until reach stopping condition
mse = zeros(1,50);
c = 1;
while output(0) > 0.05 && c < 50
    c = c + 1;
    w(1) = w(1) - output(2)*e;
    w(2) = w(2) - output(3)*e;
    w(3) = w(3) - output(4)*e;
    output = Q2a(pl,pw,species,w,"versicolor","virginica");
    mse(c) = output(1);
end

figure();
plot(mse);
```

![1575606021657](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1575606021657.png)

![1575606028388](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1575606028388.png)

![1575606035913](C:\Users\jasky\AppData\Roaming\Typora\typora-user-images\1575606035913.png)

###### d) Explain how you chose the gradient step size.

In my program, the gradient step size I chose is 0.1. The reason for this is that it is relatively small and therefore not likely to skip over the global minimum; in the meanwhile, it is also large enough that the convergence would be relatively fast. By observing the learning curve, there is a dip in the early phase, but it bounced up later. This means that the algorithm probably skipped the global minimum; in this case, a smaller step size might help.

###### e) Explain how you chose a stopping criterion.

As I described in part a, this program stops either when the number of iteration reaches 50 or when error is less than 0.05 rather than having a set small number of iterations. The mean square error reaching 0.05 is an obvious one, but for some datasets it could be hard or even impossible to achieve this. Therefore, the number of iterations (50 iterations) is added also, which takes approximately two mintues.

