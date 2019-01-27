N=1e8;
X = exprnd(1/4,N,1);
%exp(X)
Y = normrnd(0,1,N,1);
mean(exp(X).^cos(Y))
