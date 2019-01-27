clf
num = 20;

for k = 1:2:10
[x, y] = meshgrid(real(linspace(-1, 1, 10).^k));
z = real(sqrt(1-x.^2-y.^2));

surf(x, y, z, 'EdgeColor', 'none')
axis vis3d
axis([-2 2 -2 2 -2 2])
drawnow
pause(2)
end




%hold on
%surf(X, Y, -Z, 'EdgeColor', 'none', 'FaceColor', [1 0 0])
%surf(Z, X, Y, 'EdgeColor', 'none', 'FaceColor', [0 1 0])
%surf(-Z, X, Y, 'EdgeColor', 'none', 'FaceColor', [0 1 1])
%surf(X, Z, Y, 'EdgeColor', 'none', 'FaceColor', [1 0 1])
%surf(X, -Z, Y, 'EdgeColor', 'none', 'FaceColor', [1 1 0])


%colormap([1 0 0;0 0 1])