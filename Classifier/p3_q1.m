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

% plot the boundary
w = [-4 0.5 1];
m = -w(2)/w(3); b = -w(1)/w(3); x = 2:7;
plot(x, m*x+b);

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

Q1b(w,3.9,1.2)

function output = Q1b(w,x,y)
    %0 for 2nd class, 1 for 3rd class
    a = [1 x y];
    output = 1/(1+exp(-dot(w,a)));
end