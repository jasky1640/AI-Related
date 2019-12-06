% 0 stands for cherry, 1 stands for lime

% 100 candies following h4. 75% lime, 25% cherry

candies = ones(500,100);
for i = 1:500
    cherry = randperm(100,25);
    candies(i,cherry) = 0;
end

% Calculate P(d|hi)
Pdh1 = ones(500,101); Pdh2 = ones(500,101); Pdh3 = ones(500,101); Pdh4 = ones(500,101); Pdh5 = ones(500,101);
for rows = 1:500
    for i = 1:100
       Pdh3(rows,i+1) = Pdh3(rows,i) * 0.5;
       if candies(rows,i) == 1
           Pdh1(rows,i+1) = Pdh1(rows,i) * 0;
           Pdh2(rows,i+1) = Pdh2(rows,i) * 0.25;
           Pdh4(rows,i+1) = Pdh4(rows,i) * 0.75;
           Pdh5(rows,i+1) = Pdh5(rows,i);
       else
           Pdh1(rows,i+1) = Pdh1(rows,i);
           Pdh2(rows,i+1) = Pdh2(rows,i) * 0.75;
           Pdh4(rows,i+1) = Pdh4(rows,i) * 0.25;
           Pdh5(rows,i+1) = Pdh5(rows,i) * 0;
       end
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

% Average out the curves
Ph1d = mean(Ph1d);
Ph2d = mean(Ph2d);
Ph3d = mean(Ph3d);
Ph4d = mean(Ph4d);
Ph5d = mean(Ph5d);

% Plot P(hi|d)
figure;
title('h4 posterior')
x = 0:100;
plot (x,Ph1d,x,Ph2d,x,Ph3d,x,Ph4d,x,Ph5d)
legend('h1','h2','h3','h4','h5');


% 100 candies following h3. 50% lime, 50% cherry

candies = ones(500,100);
for i = 1:500
    cherry = randperm(100,50);
    candies(i,cherry) = 0;
end

% Calculate P(d|hi)
Pdh1 = ones(500,101); Pdh2 = ones(500,101); Pdh3 = ones(500,101); Pdh4 = ones(500,101); Pdh5 = ones(500,101);
for rows = 1:500
    for i = 1:100
       Pdh3(rows,i+1) = Pdh3(rows,i) * 0.5;
       if candies(rows,i) == 1
           Pdh1(rows,i+1) = Pdh1(rows,i) * 0;
           Pdh2(rows,i+1) = Pdh2(rows,i) * 0.25;
           Pdh4(rows,i+1) = Pdh4(rows,i) * 0.75;
           Pdh5(rows,i+1) = Pdh5(rows,i);
       else
           Pdh1(rows,i+1) = Pdh1(rows,i);
           Pdh2(rows,i+1) = Pdh2(rows,i) * 0.75;
           Pdh4(rows,i+1) = Pdh4(rows,i) * 0.25;
           Pdh5(rows,i+1) = Pdh5(rows,i) * 0;
       end
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

% Average out the curves
Ph1d = mean(Ph1d);
Ph2d = mean(Ph2d);
Ph3d = mean(Ph3d);
Ph4d = mean(Ph4d);
Ph5d = mean(Ph5d);

% Plot P(hi|d)
figure;
title('h3 posterior')
x = 0:100;
plot (x,Ph1d,x,Ph2d,x,Ph3d,x,Ph4d,x,Ph5d)
legend('h1','h2','h3','h4','h5');


% 100 candies following h2. 25% lime, 75% cherry

candies = ones(500,100);
for i = 1:500
    cherry = randperm(100,75);
    candies(i,cherry) = 0;
end

% Calculate P(d|hi)
Pdh1 = ones(500,101); Pdh2 = ones(500,101); Pdh3 = ones(500,101); Pdh4 = ones(500,101); Pdh5 = ones(500,101);
for rows = 1:500
    for i = 1:100
       Pdh3(rows,i+1) = Pdh3(rows,i) * 0.5;
       if candies(rows,i) == 1
           Pdh1(rows,i+1) = Pdh1(rows,i) * 0;
           Pdh2(rows,i+1) = Pdh2(rows,i) * 0.25;
           Pdh4(rows,i+1) = Pdh4(rows,i) * 0.75;
           Pdh5(rows,i+1) = Pdh5(rows,i);
       else
           Pdh1(rows,i+1) = Pdh1(rows,i);
           Pdh2(rows,i+1) = Pdh2(rows,i) * 0.75;
           Pdh4(rows,i+1) = Pdh4(rows,i) * 0.25;
           Pdh5(rows,i+1) = Pdh5(rows,i) * 0;
       end
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

% Average out the curves
Ph1d = mean(Ph1d);
Ph2d = mean(Ph2d);
Ph3d = mean(Ph3d);
Ph4d = mean(Ph4d);
Ph5d = mean(Ph5d);

% Plot P(hi|d)
figure;
title('h4 posterior')
x = 0:100;
plot (x,Ph1d,x,Ph2d,x,Ph3d,x,Ph4d,x,Ph5d)
legend('h1','h2','h3','h4','h5');


% 100 candies following h1. 100% cherry

candies = zeros(500,100);

% Calculate P(d|hi)
Pdh1 = ones(500,101); Pdh2 = ones(500,101); Pdh3 = ones(500,101); Pdh4 = ones(500,101); Pdh5 = ones(500,101);
for rows = 1:500
    for i = 1:100
       Pdh3(rows,i+1) = Pdh3(rows,i) * 0.5;
       if candies(rows,i) == 1
           Pdh1(rows,i+1) = Pdh1(rows,i) * 0;
           Pdh2(rows,i+1) = Pdh2(rows,i) * 0.25;
           Pdh4(rows,i+1) = Pdh4(rows,i) * 0.75;
           Pdh5(rows,i+1) = Pdh5(rows,i);
       else
           Pdh1(rows,i+1) = Pdh1(rows,i);
           Pdh2(rows,i+1) = Pdh2(rows,i) * 0.75;
           Pdh4(rows,i+1) = Pdh4(rows,i) * 0.25;
           Pdh5(rows,i+1) = Pdh5(rows,i) * 0;
       end
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

% Average out the curves
Ph1d = mean(Ph1d);
Ph2d = mean(Ph2d);
Ph3d = mean(Ph3d);
Ph4d = mean(Ph4d);
Ph5d = mean(Ph5d);

% Plot P(hi|d)
figure;
title('h4 posterior')
x = 0:100;
plot (x,Ph1d,x,Ph2d,x,Ph3d,x,Ph4d,x,Ph5d)
legend('h1','h2','h3','h4','h5');
