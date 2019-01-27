figure(1)
clf
hold on
axis off
axis vis3d
set(gca, 'CameraViewAngle', 3)

y = @(x) sqrt(1-x.^2);
points = 100;
x = linspace(-1, 1, points);
circ = linspace(0, 2*pi, points);
maxrad = 3;
circs = 10;
h = 0;
for k = 1:circs
    rad = rand+1;
    X = maxrad*rad*y(x)'*sin(circ)+20*rand-10;
    Y = maxrad*rad*y(x)'*cos(circ)+20*rand-10;
    Z = maxrad*rad*linspace(-1, 1, points)'*ones(1, points)+10*rand-5;
    h(k) = surf(X, Y, Z, X, 'EdgeColor', 'none');
    

end

for z = 0:2:10800
    for k = 1:circs
        set(h(k), 'CData',cos(z*pi/180)*Y-sin(z*pi/180)*X)
    end
    whitebg([rand 0 rand])
    %colormap([rand(128, 1) zeros(128, 1) rand(128, 1)])
    %view(z, 90*sin(z*pi/180))
    view(z, 30)
    
    drawnow
end