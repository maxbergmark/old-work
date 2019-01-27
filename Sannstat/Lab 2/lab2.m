clf
load wave_data.mat
subplot(211), plot(y(1:end))
subplot(212), hist_density(y)
hold on

n = length(y);
my_est = sqrt(y*y'/2/n);
my_est_mk = sqrt(2/pi)*sum(y)/n;


S = sqrt(1/(n-1)*sum(y-my_est_mk)^2)
%D = S/sqrt(n)
D = sqrt((8-2*pi)/(n*pi^2)*sum(y/n)^2)

upper_bound = my_est_mk + 1.96*D
lower_bound = my_est_mk - 1.96*D

plot(my_est,0,'r*')
plot(upper_bound,0,'g*')
plot(lower_bound,0,'g*')

plot(linspace(0,6), raylpdf(linspace(0,6),my_est),'r')