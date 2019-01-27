function x = lab3(x)
sorted = false;
n = length(x);
while ~sorted
    sorted = true;
    for ii=1:2:n-1
        if x(ii) > x(ii+1)
            
            [x(ii), x(ii+1)] = deal(x(ii+1), x(ii));
            sorted = false;
        end
    end
    for ii=2:2:n-1
        if x(ii) > x(ii+1)
            [x(ii), x(ii+1)] = deal(x(ii+1), x(ii));
            sorted = false;
        end
    end
    plot(x,'*')
    drawnow
end