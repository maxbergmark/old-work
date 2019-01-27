figure(3)
clf
axis off
axis vis3d
hold on
view(3)
L = 2.6;
p = 1;
points = 100;
x = linspace(0, L, points);
circ = linspace(0, 2*pi, points);
y = @(x) (1-(1/pi)*atan(p*(x-1)))./(2-cos(pi*x));
dk = L/(points-1);
for k = x
    X = [0*circ+k; 0*circ+k+dk];
    Y = [y(k)*sin(circ);y(k+dk)*sin(circ)];
    Z = [y(k)*cos(circ); y(k+dk)*cos(circ)];
    surf(Z, Y, X, sin(6*atan(Z./Y)-10*X), 'EdgeColor', 'none')
    colormap(jet(256));
    drawnow
end
col = zeros(128, 3);
for z = 15:1:100*360+15
    %view(180*sin(z/180), 30*cos(z/1800))
    %view((z/100)^3, 90*cos(z/360))
    %view(sin(8*z), 30)
    col = .05*ones(128, 3)-col;
    colormap(max(0, min(1, jet(128)+col)))
    whitebg([1/log(z+1) 1/log(z+1) 1/log(z+1)])
    drawnow
end