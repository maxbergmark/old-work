clf
n = 1e3;
x = linspace(0,10,n);
y = sin(2*x).*x.*exp(-x)+exp(.05*x);

k = 12;
X = zeros(n,k+1);
for a = 1:k+1
    X(:,a) = ones(n,1).*(x.^(a-1))';
end


B = X\y';
%B = regress(y',X);
plot(x,y)
hold on
plot(x, X*B,'r')