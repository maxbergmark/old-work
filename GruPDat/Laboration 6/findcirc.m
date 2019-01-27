function findcirc(x, y)
clf
hold on
plot(x, y, '*')

A = [ones(1, length(x)); x; y]';
b = x'.^2+y'.^2;
C = A\b;
circle(0.5*C(2), 0.5*C(3), (C(1)+0.25*(C(2)^2+C(3)^2))^0.5)
 
