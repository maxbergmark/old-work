clf
incirc = 0;
points = [];
x = linspace(0, 2*pi);
figure(1)
plot(sin(x), cos(x))
hold on
axis equal
axis([-1 1 -1 1])
for k = 1:20000
    point = 2.*(rand(2, 1)-[.5; .5]);
    
    %drawnow
    if sqrt(point'*point) <= 1
        plot(point(1), point(2), '*r')
        incirc = incirc+1;
    else
        plot(point(1), point(2), '*b')
    end
    
    if mod(k, 100) == 0
        %disp(k/10000)
        drawnow
    end
    disp(incirc/k*4)
    points = [points incirc/k*4];
end
%loglog(abs(points-pi))
%axis([0, length(points), 0, 4])
