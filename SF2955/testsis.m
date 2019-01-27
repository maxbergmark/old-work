R = 1;
A = .1;
Y = ones(61,1);
S = 2;
N = 1000;
n = 60;
tau = zeros(1,n); % vector of estimates
p = @(x,y) normpdf(y,x,S); % observation density, for weights
part = R*sqrt(1/(1 - A^2))*randn(N,1); % initialization
w = p(part,Y(1));
tau(1) = sum(part.*w)/sum(w);
for k = 1:n % main loop
part = A*part + R*randn(N,1); % mutation
w = w.*p(part,Y(k + 1)); % weighting
tau(k + 1) = sum(part.*w)/sum(w); % estimation
end
plot(tau)