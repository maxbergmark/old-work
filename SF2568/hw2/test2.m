y = load('data3.txt');
x = linspace(0,1,1002);
u = x-3*x.^2+2*x.^3;
plot(x,u);
title('Jacobi approximation of u(x)');
