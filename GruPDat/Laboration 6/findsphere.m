function findsphere(x, y, z)
clf
hold on
plot3(x, y, z, 'bo')

A = [ones(1, length(x)); x; y; z]';
b = x'.^2+y'.^2+z'.^2;
C = A\b;
spherer(50, 0.5*C(2:4)', sqrt(C(1)+0.25*(C(2:4)'*C(2:4))))