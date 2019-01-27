figure(1)
clf
hold on
axis off
axis vis3d
set(gca, 'CameraViewAngle', 3)
axis([-20 20 -20 20 -20 20])
y = @(x) sqrt(1-x.^2);
points = 40;
x = linspace(-1, 1, points);
circ = linspace(0, 2*pi, points);
maxrad = 3;
circs = 10;
h = 0;
xdat = [];
ydat = [];
zdat = [];
vel = [];
for k = 1:circs
    rad = rand+1;
    X = maxrad*rad*y(x)'*sin(circ)+20*rand-10;
    disp(size(X))
    Y = maxrad*rad*y(x)'*cos(circ)+20*rand-10;
    Z = maxrad*rad*linspace(-1, 1, points)'*ones(1, points)+10*rand-5;
    h(k) = surf(X, Y, Z, X, 'EdgeColor', 'none');
    xdat = [xdat; X];
    ydat = [ydat; Y];
    zdat = [zdat; Z];
    vel = [vel; rand rand rand];
    

end

for z = 0:2:10800
    for k = 1:circs
        set(h(k), 'CData',cos(z*pi/180)*Y-sin(z*pi/180)*X)
        set(h(k), 'XData', xdat((k-1)*points+1:k*points, :)+10*sin(vel(k, 1)*z*pi/180))
        set(h(k), 'YData', ydat((k-1)*points+1:k*points, :)+10*cos(vel(k, 2)*z*pi/180))
        set(h(k), 'ZData', zdat((k-1)*points+1:k*points, :)+10*sin(vel(k, 3)*z*pi/180))
    end
    whitebg([rand 0 rand])
    %colormap([rand(128, 1) zeros(128, 1) rand(128, 1)])
    colormap(jet(128))
    %view(z, 90*sin(z*pi/180))
    view(z, 30)
    
    drawnow
end