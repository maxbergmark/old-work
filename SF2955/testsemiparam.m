
n = 1000;
lambda = 0;
sigma = 2.5;
k_true = 3;
m_true = 4;

x = linspace(0, 10, n);
resid = randn(1,n)*sigma + lambda;
y = k_true*x + m_true + resid;
figure(1)
clf;
plot(x,y, '.')
hold on

x_fit = [x' x.^0'];
params = x_fit\y';
k_boot = params(1);
m_boot = params(2);
plot(x, x_fit*params)

boot_resid = x_fit*params - y';
figure(2);
clf
plot(x, boot_resid, '.')

k = 10000;
boot_params = [];

bootresids = [];
for i = 1:k
    bootresid = randsample(boot_resid, n, true);
    y_boot = k_boot * x + m_boot + bootresid';
    boot_param = x_fit\y_boot';
    boot_params = [boot_params;boot_param'];
%     bootresids = [bootresids bootresid];
end

figure(3)
clf
hist(boot_params(:,1), 100)
title(['k = ' num2str(mean(boot_params(:,1)))])
lims = get(gca, 'YLim');
axis([0 5 lims])
figure(4)
clf
hist(boot_params(:,2), 100)
title(['m = ' num2str(mean(boot_params(:,2)))])
lims = get(gca, 'YLim');
axis([0 6 lims])

