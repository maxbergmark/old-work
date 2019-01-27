x = -2:0.000001:10;

y = x.^2/5+1.5*sin(pi*x)-3*x.*exp(-x/2);
plot(x,y);
