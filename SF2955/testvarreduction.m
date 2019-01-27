c = 1;
n = 10000;

u1 = randn(n,1);
u2 = -u1;
v1 = cumsum(u1>=c);
v2 = cumsum(u2>=c);
w = (v1+v2)./2;
clf
loglog(var(w)./(1:n)')
hold on
semilogy(var(v1)./(1:n)')
semilogy(var(v2)./(1:n)')
answer = 1-normcdf(c);
% semilogy(answer.*ones(size(w)))
