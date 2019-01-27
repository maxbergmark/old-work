function asd = uppgift2b()
n = 80;
k = 2.5;

L = 2;
t0 = 300;
t1 = 400;
h = L/(n+1);
clf

A = -diag(ones(n-1,1), 1)+2*diag(ones(n,1), 0)-diag(ones(n-1,1), -1);
A = A.*k/h^2;

x = linspace(0,L,n+2);
Q = 300*exp(-(x(2:end-1)-L/2).^2)'+3000*exp(-1000*(x(2:end-1)-L/4).^2)';


ends = [zeros(1,n)]';
ends(1) = t0.*(k/h^2);
ends(end) = ends(end)+t1.*(k/h^2);


tic
[L,U] = lu(sparse(A));
T = U\(L\(Q+ends));
%T = U\bt;
asd = toc;
hold on
size(x)
size(T)
size(A)
h0 = plot(x, [t0; T ;t1]);
h1 = plot(x(2:end-1), Q./max(Q)*max(T), 'r');
disp([min([t0 T' t1]) max([t0 T' t1]) (sum(T)+t0+t1)/(length(T)+2)])

%for k = 1:10000
%    set(gcf, 'Color', rand(1,3))
%    set(gca, 'Color', rand(1,3))
%    set(h0, 'Color', rand(1,3))
%    set(h1, 'Color', rand(1,3))
%    drawnow
%end