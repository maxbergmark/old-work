% Clear workspace and load data
clear all;
load('atlantic.mat')
y = atlantic;

% Function definitions and other constants
T = 3*14*100;

% Estimate parameters [beta_hat, mu_hat] from atlantic data
[beta_hat, mu_hat] = est_gumbel(y);

% Bootstrap k new samples using estimated parameters.
% Store estimated parameters [beta, mu] for each sample.
k = 10000;
bins = 100;
bstar = zeros(582,2);
delete(gcp('nocreate'))
parpool(8);
disp('Parallel pool started')
disp(['Estimated time: ' num2str(k/1e7*4129) ' seconds'])
tic
parfor i=1:k
    U = rand(582,1);
    ystar = inv_gumbel(U, beta_hat, mu_hat);
    [betastar, mustar] = est_gumbel(ystar);
    bstar(i,:) = [betastar, mustar];
end
toc

delete(gcp('nocreate'))

%%

% b) Provide a parametric bootstrapped 95% confidence intervals for the
% parameters.
alpha = 0.05;
sorted_beta = sort(bstar(:,1));

figure(1)
clf
[ndat, xdat] = hist(sorted_beta, bins);
L_beta = sorted_beta(ceil((alpha/2)*k));
U_beta = sorted_beta(ceil((1 - alpha/2)*k));
hold on
bar(xdat(xdat<=U_beta & xdat>=L_beta), ndat(xdat<=U_beta & xdat >= L_beta), 'b', 'BarWidth', 1);
bar(xdat(xdat>U_beta | xdat<L_beta), ndat(xdat>U_beta | xdat < L_beta), 'r', 'BarWidth', 1);
x_lim = get(gca, 'Xlim');
y_lim = get(gca, 'Ylim');
title('Parametric bootstrap estimation of $\beta$ error', 'Interpreter', 'latex');
xlabel('$\beta$ estimate', 'Interpreter', 'latex');
ylabel('Counts', 'Interpreter', 'latex');
text(x_lim(1)*.98+x_lim(2)*.02, y_lim(2)*.3820, ['$\beta \in ( ' num2str(L_beta) ', ' num2str(U_beta) ')$'], 'Interpreter', 'latex');

%%

figure(2)
clf
sorted_mu = sort(bstar(:,2));
[ndat, xdat] = hist(sorted_mu, bins);
L_mu = sorted_mu(ceil((alpha/2)*k));
U_mu = sorted_mu(ceil((1 - alpha/2)*k));
hold on
bar(xdat(xdat<=U_mu & xdat>=L_mu), ndat(xdat<=U_mu & xdat >= L_mu), 'b', 'BarWidth', 1);
bar(xdat(xdat>U_mu | xdat<L_mu), ndat(xdat>U_mu | xdat < L_mu), 'r', 'BarWidth', 1);
x_lim = get(gca, 'Xlim');
y_lim = get(gca, 'Ylim');
title('Parametric bootstrap estimation of $\mu$ error', 'Interpreter', 'latex');
xlabel('$\mu$ estimate', 'Interpreter', 'latex');
ylabel('Counts', 'Interpreter', 'latex');
text(x_lim(1)*.98+x_lim(2)*.02, y_lim(2)*.3820, ['$\mu \in ( ' num2str(L_mu) ', ' num2str(U_mu) ')$'], 'Interpreter', 'latex');

% Hit ser allt bra ut. Dubbelkolla gärna så du också tycker det
% ser bra ut!


%%

% c) Provide a one sided parametric bootstrapped 95% confidence interval for
% the 100-year return value

% Compute the T:th return value of the significant wave-height given
% estimated [beta, mu] pairs.
tstar = inv_gumbel(1-1/T, bstar(:,1), bstar(:,2));
t_hat = mean(tstar);
sorted_t = sort(tstar);
figure(3)
clf
[ndat, xdat] = hist(sorted_t, bins);
hold on
U_t = sorted_t(ceil(k*(1-alpha)));
bar(xdat(xdat<=U_t), ndat(xdat<=U_t), 'b', 'BarWidth', 1);
bar(xdat(xdat>U_t), ndat(xdat>U_t), 'r', 'BarWidth', 1);
y_lim = get(gca, 'Ylim');
% plot((U_t)*[1 1], y_lim, 'r');
title('Parametric bootstrap estimation of 100 year wave', 'Interpreter', 'latex');
xlabel('Wave height (m)', 'Interpreter', 'latex');
ylabel('Counts', 'Interpreter', 'latex');
text(U_t, y_lim(2)*.3820, ['$\tau < ' num2str(U_t) '$'], 'Interpreter', 'latex');
