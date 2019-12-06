% graph P(theta|y,n)
% x axis: value of theta
% y axis: probability

% trial 1: n = 1, y = 1
figure;
n = 1;
y = 1;
t = 0:0.01:1;
p = (n+1).*nchoosek(n,y).*(t.^y).*((1-t).^(n-y));
plot(t,p)
% trial 2: n = 2, y = 2
figure;
n = 2;
y = 2;
t = 0:0.01:1;
p = (n+1).*nchoosek(n,y).*(t.^y).*((1-t).^(n-y));
plot(t,p)
% trial 3: n = 3, y = 2
figure;
n = 3;
y = 2;
t = 0:0.01:1;
p = (n+1).*nchoosek(n,y).*(t.^y).*((1-t).^(n-y));
plot(t,p)
% trial 4: n = 4, y = 3
figure;
n = 4;
y = 3;
t = 0:0.01:1;
p = (n+1).*nchoosek(n,y).*(t.^y).*((1-t).^(n-y));
plot(t,p)