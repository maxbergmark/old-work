clear all;
clc;
close all;

N = 100;

h = 1/(N+1);

x = linspace(0,1,N+2)';
x = x(2:end-1);

u0 = x-x.^2;
u = 0*x;
% u = 2*rand(N,1)-1;
% u = sin(3*pi*x)+0.0001*(2*rand(N,1)-1);
% u = 1-2*x;

r = @(x) x;
% r = @(x) heaviside(x-0.5);
f = @(x) x.^2-x.^3-2;

rd = [0 zeros(1,N-2) 0]';

A = diag(ones(1,N-1),1) + diag(ones(1,N-1),-1);

for k = 1:100000
    
    u = 1./(2-h^2*r(x)).*(A*u+rd-h^2*f(x));
    plot([0 x' 1], [rd(1) u' rd(end)], 'b', x,u0,'r')
    drawnow
end

plot([rd(1) u' rd(end)])