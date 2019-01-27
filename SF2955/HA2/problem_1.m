clear all
close all
load('coal_mine.mat');
d = 10;

min_year = 1851;
max_year = 1963;
t = linspace(min_year, max_year, d+1);
tau = coal_mine;

hyptheta = 2;
theta = gamrnd(2, 1/hyptheta);

% H = histogram(tau,t);
% centers = (t(2:end)+t(1:end-1))/2;
% lambda_discrete = H.Values;
% bin_width = diff(H.BinEdges);


n = @(i, t) sum(tau > t(i) & tau < t(i+1));
lambda = @(i, t) n(i, t)./(t(i+1)-t(i));

x = 1:d;
figure(1)
clf
% bar(x, lambda_discrete)
hold on
plot(x, n(x, t), 'r*');
plot(x, lambda(x,t), 'g*')


% figure(2)
% clf
%%
figure(1)
clf
hist(diff(coal_mine)*365, 20)
figure(2)
clf
plot(coal_mine)
