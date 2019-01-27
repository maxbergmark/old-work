clf
L = 4;
n = 100;
c = L/n;

P = -3000;
b = .5;
h = .03;
I = (b*h^3)/12;
E = 75e9;
M = .6*P*L;

A = diag(ones(n-2,1),2)-4*diag(ones(n-1,1),1)+6*diag(ones(n,1))-4*diag(ones(n-1,1),-1)+diag(ones(n-2,1),-2);

A(1,1) = 6;
A(1,2) = 4;
A(1,3) = 2;
A(2,1) = -4;
A(2,2) = -5;
A(end-1,end-1:end) = [5 -2];
A(end,end-2:end) = [2 -4 2]

A = A./(c^4);

x = linspace(0,L,n);
ends = zeros(n,1);
ends(end) = P;


w = A\(ends./(E*I));
w = w.*(2/(c));
w(end)
P*L^3/(3*E*I)
plot(x,w+1, 'LineWidth', 1)
axis equal
axis([0 L+1 -.5 2])
drawnow
hold on
y = -P/(E*I)*(x.^3/6-(L*x.^2/2));
plot(x,y+1,'r', 'LineWidth', 1)