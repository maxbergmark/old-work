clf

axis vis3d
whitebg('k')


t = linspace(0, 2*pi, 200);
x = [2*cos(t)-sin(t./4);cos(t)+sin(t./4)-sin(t./2)];
y = [sin(t)+.8*sin(t./1);2*sin(t)];
z = [.5*sin(t); 0*t];

surf(x, y, z, 'EdgeColor', 'none')%, 'BackFaceLighting', 'unlit')
light('Position',[0 0 5],'Style','infinite');
xlabel('x')
ylabel('y')
zlabel('z')
axis([-3 3 -2 2 -1 1])
axis off