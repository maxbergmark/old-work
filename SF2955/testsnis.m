n = 100000;

lambda = 1;


z = @(x) 2.^(-x);
g = @(x) exppdf(x, lambda);
w = @(x) z(x)./g(x);

draws = exprnd(lambda,n,1);

tau = 1./(1:n) .* (cumsum(draws .* w(draws))');
c = 1./(1:n) .* (cumsum(w(draws))');

figure(1)
clf
semilogy(1:n, c)
hold on
semilogy(1:n, ones(1,n)./log(2))

figure(2)
clf
semilogy(1:n, tau)
hold on
semilogy(1:n, ones(1,n)./(log(2)^2))

