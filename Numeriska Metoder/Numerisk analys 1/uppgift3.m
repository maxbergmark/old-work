
figure(1)
clf
x = -5:.05:10;
yp = @(x,a,b) x-a*sin(b*x)-3;
for i = 1:100
    a = 3.5 + rand;
    b = 1.9 + rand/5;
    y = x - a*sin(b*x) - 3;
    plot(x, y)
    hold on
    drawnow
    
end
plot(x, 0*x)

plot(x, yp(x, 4.0, 2.1))
plot(x, yp(x, 4.0, 2.0))
plot(x, yp(x, 4.0, 2.1))
plot(x, yp(x, 4.0, 2.0))


Eyap = @(x) abs(.5*sin(2*x));
Eybp = @(x) abs(4*sin(2.0*x)-4*sin((2.1)*x));
Eyam = @(x) abs(.5*sin(2*x));
Eybm = @(x) abs(4*sin(2*x)-4*sin((1.9)*x));

%plot(x, y(x, 4, 2)+Eya(x), 'r')
plot(x, yp(x, 4, 2)+Eybp(x)+Eyap(x), 'r', 'LineWidth', 3)
plot(x, yp(x, 4, 2)-Eybp(x)-Eyap(x), 'r', 'LineWidth', 3)
plot(x, Eybp(x)+Eyap(x), 'g', 'LineWidth', 2)
plot(x, yp(x, 4, 2)+Eybm(x)+Eyam(x), 'r', 'LineWidth', 3)
plot(x, yp(x, 4, 2)-Eybm(x)-Eyam(x), 'r', 'LineWidth', 3)
plot(x, Eybm(x)+Eyam(x), 'g', 'LineWidth', 2)
plot(x,yp(x,4,2), 'cyan', 'LineWidth', 3)