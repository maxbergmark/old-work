clc
% q = [.4 .4 .2; 0 .7 .3; 0 .1 .9];
% q = [.1 .1 .8; .8 .1 .1; .1 .1 .8];
q = rand(3);
q = q./sum(q,2);

pi = [0 .25 .75];
k = 1000000;
y = 1;
ys = zeros(1,k);
acc = 0;
alphas = zeros(1,k);
for i = 1:k
    y_star = randi(3);
    u = rand;
    alpha = min(1, pi(y_star) * q(y_star, y) / (pi(y) * q(y, y_star)) );
    if (u < alpha)
        acc = acc+1;
        y = y_star;
    end
    ys(i) = y;
    alphas(i) = alpha;
    
    
end
100*acc/k
figure(1)
hist(ys(1000:end), [1, 2, 3])
figure(2);
plot(alphas, '*')
