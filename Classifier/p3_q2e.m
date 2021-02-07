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