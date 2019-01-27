clf
M = 1e6;
b = 4;

x = raylrnd(b,M,1);
my_est_mk = sqrt(2/pi)*sum(x)/M
my_est_ml = sqrt(x'*x/2/M)
x = sort(x);
subplot(211)
plot(x)
subplot(212)
hist_density(x)
hold on
plot(my_est_ml,0,'r*')
plot(my_est_mk,.01,'g*')

