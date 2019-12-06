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