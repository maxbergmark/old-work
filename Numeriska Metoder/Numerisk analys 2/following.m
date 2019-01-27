figure(1)
clf
hold on
axis off
axis vis3d

y = @(x) sqrt(1-x.^2);
points = 200;
x = linspace(-1, 1, points);
circ = linspace(0, 2*pi, points);

X = y(x)'*sin(circ);
Y = y(x)'*cos(circ);
Z = linspace(-1, 1, points)'*ones(1, points);
h = surf(X, Y, Z, X, 'EdgeColor', 'none');
temp = [1:10:200 200];
surf(.1*X(temp, temp)+1.2, .1*Y(temp, temp)+1.2, .1*Z(temp, temp), 'EdgeColor', 'none')
 
while true
    [az, el] = view;
    disp(az)
    set(h, 'CData', cos(2*az*pi/180)*Y-sin(2*az*pi/180)*X)
    view(az+1, 40*cos(.5*az*pi/180))
    drawnow
end