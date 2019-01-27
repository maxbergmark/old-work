t = linspace(0,20*pi, 1000);
s = -.04;
x = cos(t).*exp(s*t);
y = sin(t).*exp(s*t);
P = [x; y];
plot(P(1,:),P(2,:))
z = 0*x;
%fill(x, y, 'k')
a = .1;
figure(1)
rot = [cos(a) -sin(a);sin(a) cos(a)];
for k = 1:1000
    
    P = rot*P;
    fill(P(1,:),P(2,:), 'k')
    axis([-1 1 -1 1])
    drawnow
end