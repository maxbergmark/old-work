clf
x = -10:.01:10;
y = x- 4*sin(2*x)-3;
plot(x, y)
hold on
plot(x, 0)
axis([-2 8 -1 1])