% 0 stands for cherry, 1 stands for lime

% 100 candies following h4. 75% lime, 25% cherry
candies = ones(1,100);
for i = 1:25
   candies(i*4) = 0; 
end
% Calculate P(d|hi)
Pdh1 = ones(1,101); Pdh2 = ones(1,101); Pdh3 = ones(1,101); Pdh4 = ones(1,101); Pdh5 = ones(1,101);
for i = 1:100
   Pdh3(i+1) = Pdh3(i) * 0.5;
   if candies(i) == 1
       Pdh1(i+1) = Pdh1(i) * 0;
       Pdh2(i+1) = Pdh2(i) * 0.25;
       Pdh4(i+1) = Pdh4(i) * 0.75;
       Pdh5(i+1) = Pdh5(i);
   else
       Pdh1(i+1) = Pdh1(i);
       Pdh2(i+1) = Pdh2(i) * 0.75;
       Pdh4(i+1) = Pdh4(i) * 0.25;
       Pdh5(i+1) = Pdh5(i) * 0;
   end
end

% P(hi|d) = alpha * P(d|hi)*P(hi)
Ph1d = Pdh1 * 0.1;
Ph2d = Pdh2 * 0.2;
Ph3d = Pdh3 * 0.4;
Ph4d = Pdh4 * 0.2;
Ph5d = Pdh5 * 0.1;
a = Ph1d + Ph2d + Ph3d + Ph4d + Ph5d;
Ph1d = Ph1d./a;
Ph2d = Ph2d./a;
Ph3d = Ph3d./a;
Ph4d = Ph4d./a;
Ph5d = Ph5d./a;

% Plot P(hi|d)
figure;
title('h4 posterior')
x = 0:100;
plot (x,Ph1d,x,Ph2d,x,Ph3d,x,Ph4d,x,Ph5d)
legend('h1','h2','h3','h4','h5');

% Find & Plot P(Dn+1 = lime | D)
p = Ph2d * 0.25 + Ph3d * 0.5 + Ph4d * 0.75 + Ph5d;
figure;
title('h4 prediction')
plot (x,p)

% 100 candies following h3. 50% lime, 50% cherry
candies = ones(1,100);
for i = 1:50
   candies(i*2) = 0; 
end
% Calculate P(d|hi)
Pdh1 = ones(1,101); Pdh2 = ones(1,101); Pdh3 = ones(1,101); Pdh4 = ones(1,101); Pdh5 = ones(1,101);
for i = 1:100
   Pdh3(i+1) = Pdh3(i) * 0.5;
   if candies(i) == 1
       Pdh1(i+1) = Pdh1(i) * 0;
       Pdh2(i+1) = Pdh2(i) * 0.25;
       Pdh4(i+1) = Pdh4(i) * 0.75;
       Pdh5(i+1) = Pdh5(i);
   else
       Pdh1(i+1) = Pdh1(i);
       Pdh2(i+1) = Pdh2(i) * 0.75;
       Pdh4(i+1) = Pdh4(i) * 0.25;
       Pdh5(i+1) = Pdh5(i) * 0;
   end
end

% P(hi|d) = alpha * P(d|hi)*P(hi)
Ph1d = Pdh1 * 0.1;
Ph2d = Pdh2 * 0.2;
Ph3d = Pdh3 * 0.4;
Ph4d = Pdh4 * 0.2;
Ph5d = Pdh5 * 0.1;
a = Ph1d + Ph2d + Ph3d + Ph4d + Ph5d;
Ph1d = Ph1d./a;
Ph2d = Ph2d./a;
Ph3d = Ph3d./a;
Ph4d = Ph4d./a;
Ph5d = Ph5d./a;

% Plot P(hi|d)
figure;
title('h3 posterior')
x = 0:100;
plot (x,Ph1d,x,Ph2d,x,Ph3d,x,Ph4d,x,Ph5d)
legend('h1','h2','h3','h4','h5');

% Find & Plot P(Dn+1 = lime | D)
p = Ph2d * 0.25 + Ph3d * 0.5 + Ph4d * 0.75 + Ph5d;
figure;
title('h3 prediction')
plot (x,p)


% 100 candies following h2. 25% lime, 75% cherry
candies = zeros(1,100);
for i = 1:25
   candies(i*4) = 1; 
end
% Calculate P(d|hi)
Pdh1 = ones(1,101); Pdh2 = ones(1,101); Pdh3 = ones(1,101); Pdh4 = ones(1,101); Pdh5 = ones(1,101);
for i = 1:100
   Pdh3(i+1) = Pdh3(i) * 0.5;
   if candies(i) == 1
       Pdh1(i+1) = Pdh1(i) * 0;
       Pdh2(i+1) = Pdh2(i) * 0.25;
       Pdh4(i+1) = Pdh4(i) * 0.75;
       Pdh5(i+1) = Pdh5(i);
   else
       Pdh1(i+1) = Pdh1(i);
       Pdh2(i+1) = Pdh2(i) * 0.75;
       Pdh4(i+1) = Pdh4(i) * 0.25;
       Pdh5(i+1) = Pdh5(i) * 0;
   end
end

% P(hi|d) = alpha * P(d|hi)*P(hi)
Ph1d = Pdh1 * 0.1;
Ph2d = Pdh2 * 0.2;
Ph3d = Pdh3 * 0.4;
Ph4d = Pdh4 * 0.2;
Ph5d = Pdh5 * 0.1;
a = Ph1d + Ph2d + Ph3d + Ph4d + Ph5d;
Ph1d = Ph1d./a;
Ph2d = Ph2d./a;
Ph3d = Ph3d./a;
Ph4d = Ph4d./a;
Ph5d = Ph5d./a;

% Plot P(hi|d)
figure;
title('h2 posterior')
x = 0:100;
plot (x,Ph1d,x,Ph2d,x,Ph3d,x,Ph4d,x,Ph5d)
legend('h1','h2','h3','h4','h5');

% Find & Plot P(Dn+1 = lime | D)
p = Ph2d * 0.25 + Ph3d * 0.5 + Ph4d * 0.75 + Ph5d;
figure;
title('h2 prediction')
plot (x,p)


% 100 candies following h3. 50% lime, 50% cherry
candies = zeros(1,100);
% Calculate P(d|hi)
Pdh1 = ones(1,101); Pdh2 = ones(1,101); Pdh3 = ones(1,101); Pdh4 = ones(1,101); Pdh5 = ones(1,101);
for i = 1:100
   Pdh3(i+1) = Pdh3(i) * 0.5;
   if candies(i) == 1
       Pdh1(i+1) = Pdh1(i) * 0;
       Pdh2(i+1) = Pdh2(i) * 0.25;
       Pdh4(i+1) = Pdh4(i) * 0.75;
       Pdh5(i+1) = Pdh5(i);
   else
       Pdh1(i+1) = Pdh1(i);
       Pdh2(i+1) = Pdh2(i) * 0.75;
       Pdh4(i+1) = Pdh4(i) * 0.25;
       Pdh5(i+1) = Pdh5(i) * 0;
   end
end

% P(hi|d) = alpha * P(d|hi)*P(hi)
Ph1d = Pdh1 * 0.1;
Ph2d = Pdh2 * 0.2;
Ph3d = Pdh3 * 0.4;
Ph4d = Pdh4 * 0.2;
Ph5d = Pdh5 * 0.1;
a = Ph1d + Ph2d + Ph3d + Ph4d + Ph5d;
Ph1d = Ph1d./a;
Ph2d = Ph2d./a;
Ph3d = Ph3d./a;
Ph4d = Ph4d./a;
Ph5d = Ph5d./a;

% Plot P(hi|d)
figure;
title('h1 posterior')
x = 0:100;
plot (x,Ph1d,x,Ph2d,x,Ph3d,x,Ph4d,x,Ph5d)
legend('h1','h2','h3','h4','h5');

% Find & Plot P(Dn+1 = lime | D)
p = Ph2d * 0.25 + Ph3d * 0.5 + Ph4d * 0.75 + Ph5d;
figure;
title('h1 prediction')
plot (x,p)