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