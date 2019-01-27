L = 2.6;
p = 1;

y = @(x) pi*((1-(1/pi)*atan(p*(x-1)))./(2-cos(pi*x))).^2;
[vol, check] = quad(y, 0, L, 10^-14);