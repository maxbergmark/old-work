

figure(1)
clf
hold on
axis off
axis vis3d
axis([-30 30 -30 30 -20 20])
y = @(x) sqrt(1-x.^2);
points = 30;
x = linspace(-1, 1, points);
circ = linspace(0, 2*pi, points);
maxrad = 3;
circs = 15;
h = 0;
xdat = [];
ydat = [];
zdat = [];
vel = [];
for k = 1:circs
    rad = rand+1;
    X = maxrad/sqrt(k)*y(x)'*sin(circ);
    Y = maxrad/sqrt(k)*y(x)'*cos(circ);
    Z = maxrad/sqrt(k)*linspace(-1, 1, points)'*ones(1, points);
    h(k) = surf(X, Y, Z, X, 'EdgeColor', 'none');
    xdat = [xdat; X];
    ydat = [ydat; Y];
    zdat = [zdat; Z];
    
    

end
tx = text(0, 0, 5, '0');
for z = -500:1:1080000
    for k = 1:circs
        if k == 1
            set(h(k), 'CData', Z)
        else
            set(h(k), 'CData',-cos(k*z/(k-1)*pi/180)*(Y)-sin(k*z/(k-1)*pi/180)*(X))
        
            set(h(k), 'XData', xdat((k-1)*points+1:k*points, :)+5*sqrt(2*k-1)*sin(z/(k)*pi/180))
            set(h(k), 'YData', ydat((k-1)*points+1:k*points, :)+5*sqrt(2*k-1)*cos(z/(k)*pi/180))
        end
    end
    %whitebg([rand 0 rand])
    %colormap([rand(128, 1) zeros(128, 1) rand(128, 1)])
    %view(z, 90*sin(z*pi/180))
    %view(z, 30)
    set(tx, 'string', num2str(z/(2)/180/2))
    
    drawnow
end