load('bootdata.mat')
n = 250;
data = R(randsample(250, n, true));
[mu_est, sigma_est] = normfit(data);
k = 100000;

mu_ests = [];
sigma_ests = [];

for i = 1:k
    sample = sigma_est*randn(n, 1) + mu_est;
    [mu_boot, sigma_boot] = normfit(sample);
    mu_ests = [mu_ests mu_boot];
    sigma_ests = [sigma_ests sigma_boot];
end

[y_mu, x_mu] = hist(mu_ests, 50);
[y_sigma, x_sigma] = hist(sigma_ests, 50);
mu_ests = sort(mu_ests);
sigma_ests = sort(sigma_ests);

mu_low = mu_ests(floor(.025*k));
mu_high = mu_ests(floor(.975*k));
sigma_low = sigma_ests(floor(.025*k));
sigma_high = sigma_ests(floor(.975*k));
mu_avg = mean(mu_ests);
sigma_avg = mean(sigma_ests);
mu_median = mu_ests(floor(k/2));
sigma_median = sigma_ests(floor(k/2));
mu_bias = mu_avg - mu_est;
sigma_bias = sigma_avg - sigma_est;

figure(1)
clf
bar(x_mu(x_mu<mu_low), y_mu(x_mu<mu_low))
hold on
bar(x_mu(x_mu>mu_high), y_mu(x_mu>mu_high))
bar(x_mu(x_mu>=mu_low & x_mu<=mu_high), y_mu(x_mu>=mu_low & x_mu<=mu_high), 'r')
title('$\mu$ estimate from parametric bootstrap', 'Interpreter', 'latex')
xlabel('$\mu$ estimate', 'Interpreter', 'latex')
ylabel('Counts', 'Interpreter', 'latex')
lims = get(gca, 'Ylim');
text(mu_high, lims(2)*.95, ['$\mu = ' num2str(mu_avg) '$'], 'Interpreter', 'latex')
text(mu_high, lims(2)*.9, ['$\mu = ' num2str(mu_avg-mu_bias) '$'], 'Interpreter', 'latex')


figure(2)
clf
bar(x_sigma(x_sigma<sigma_low), y_sigma(x_sigma<sigma_low))
hold on
bar(x_sigma(x_sigma>sigma_high), y_sigma(x_sigma>sigma_high))
bar(x_sigma(x_sigma>=sigma_low & x_sigma<=sigma_high), y_sigma(x_sigma>=sigma_low & x_sigma<=sigma_high), 'r')
title('$\sigma$ estimate from parametric bootstrap', 'Interpreter', 'latex')
xlabel('$\sigma$ estimate', 'Interpreter', 'latex')
ylabel('Counts', 'Interpreter', 'latex')
lims = get(gca, 'Ylim');
text(sigma_high, lims(2)*.95, ['$\sigma = ' num2str(sigma_avg) '$'], 'Interpreter', 'latex')
text(sigma_high, lims(2)*.9, ['$\sigma = ' num2str(sigma_avg-sigma_bias) '$'], 'Interpreter', 'latex')








