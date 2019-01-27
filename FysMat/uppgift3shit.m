clf
n = 500;x = linspace(1/(n+1),n/(n+1),n)';u = x.^2.*(1-x);
A = bsxfun(@times, -(diag(ones(n-1,1),1)+diag(ones(n-1,1),-1)-2*diag(ones(n,1),0)), 1./(1+.5*cos(2*pi*(x'+.2))'));
[V,D] = eig(A);D = diag(D);
[D,i] = sort(D);Vs = zeros(size(V));
for k = 1:size(D)
    Vs(:,k) = V(:,i(k));
end
lambda = 1/sqrt(min(D));dt = lambda/5;c = Vs\u;
h = plot(linspace(0,1,n+2), [0 u' 0]);axis([0 1 -.2 .2]);
for k = 0:dt/50:600*lambda
    set(h, 'YData', [0 (bsxfun(@times, Vs', c)'*cos(pi*k*sqrt(D)))' 0])
    drawnow
end