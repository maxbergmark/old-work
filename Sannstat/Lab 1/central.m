clf
M=1e6;N=100;mu=5;
X = exprnd(mu,M,N);
S = cumsum(X,2);
for k=1:N
    hist(S(:,k),30)
    axis([0 10*k 0 150000])
    xlabel(num2str(k))
    drawnow
end
drawnow