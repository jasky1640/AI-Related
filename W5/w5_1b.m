figure;

% graph P(y|theta,n)
% x axis: value of y
% y axis: probability

n = 4;
t = 0.75;
y = 0:4;
for i = 1:5
   p(i) = nchoosek(n,y(i)) * (t^y(i)) * ((1-t)^(n-y(i))) 
end

bar(y,p)