x = [11  12  15  28  45  52  57  75  81  88  93  97];
y = [1.0  1.0  1.5  6.0  9.0  10.5  11.0  16.5  9.5  8.0  12.5  12.5];

plot(x, y, '*')
hold on

p1 = (length(x)*sum(x.*y)-sum(x)*sum(y))/(length(x)*sum(x.^2)-(sum(x))^2);
p2 = 1/length(x)*(sum(y)-p1*sum(x));

disp(p1)
disp(p2)
xp = [1 100];
yp = p1*xp+p2;

plot(xp, yp);

rms = (1/length(x)*sum((p2+p1*x-y).^2))^0.5;

disp(rms)