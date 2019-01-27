% Problem 1

clc
clear all
close all
load('coal_mine')
disp('Indata coal_mine.m loaded');

d = 2; % number of breakpoints
min_year = 1851;
max_year = 1963;
t = linspace(min_year, max_year, d+1); % vector of breakpoints
tau = coal_mine;


% number of disasters in the sub-interval...
n = @(i, t) sum(tau > t(max(1,min(d+1,i))) & tau < t(max(1,min(d+1,i+1))));

% prior on the breakpoints
f = @(t) prod(diff(t));

% intensity
lambda = @(i, t) n(i, t)./(t(max(2,min(d+1,i+1)))-t(max(1,min(d,i))));


f2 = @(t) prod(t)*prod(lambda(0:d,t).^n(0:d, t) - lambda(1:d+1,t).^n(1:d+1, t)) * exp(- sum(t .* (lambda(0:d,t) - lambda(1:d+1,t))));

f_lambda = @(i, theta, t) theta^(2*d) * prod(lambda(i,t)) * exp(-theta * sum(lambda(i,t)));

f_tau = @(i, t) prod(lambda(i,t).^n(i,t)) .* exp( - sum(lambda(i, t) .* (t(i+1) - t(i))));

f_theta = @(theta, hyp_th) hyp_th^2 * theta * exp(-theta*hyp_th);

total_prob = @(theta, hyp_th, t) f_tau(1:d,t) * f_lambda(1:d, theta, t) * f_theta(theta, hyp_th) * f(t);





% Hybrid MCMC algorithm


rho = 0.02; % rho
hyp_th = 2; % hyperprior
theta = gamrnd(2,1/hyp_th);

k = 20000; % iterations
burnin = 2000;
t_res = zeros(d+1,k+1+burnin);
t_res(:,1) = t;
for i=1:k+burnin
%     disp('LOOP')
    % Update breakpoints using a MH step
    for j = 2:d
       tprop = randomwalkproposal(t,rho);
       jprop = [t(1:j-1) tprop(j) t(j+1:end)];
       prob = min(1,total_prob(theta, hyp_th, jprop)/total_prob(theta, hyp_th, t));
       if (rand < prob)
           t(j) = tprop(j);
       end
    end
    t_res(:,i+1) = t;
    theta = gamrnd(2*d+2, 1/(hyp_th+sum(lambda(1:d, t))));
    
%     disp('LOOP OUT')
    hold off

    

    if (mod(i, 1000) == 0)
        [disasters, years] = hist(floor(tau), unique(floor(tau)));
        plot(years, disasters, '*');
        hold on
        plot([t;t], [zeros(size(t));7*ones(size(t))])
        text(t(2)+2, 6.5, ['Iteration: ' num2str(i)])
        text(t(2)+2, 6.2, ['Acceptance rate: ' num2str(prob)])
        text(t(2)+2, 5.9, ['Year: ' num2str(t(2))])
        text(t(2)+2, 5.6, ['Theta: ' num2str(theta)])
        axis([1840 1980 0 7])
        drawnow
    %     pause(0.25)
    
    end
    
end
%%
figure(4)
clf
semilogx(t_res(2,:))
title(['Convergence of breakpoint for $\rho = ' num2str(rho) '$'], 'Interpreter', 'latex')
xlabel('Iteration', 'Interpreter', 'latex')
ylabel('Year', 'Interpreter', 'latex')



%%

t_res = t_res(:,burnin+1:end);

figure(2)
clf

hold on
cols = ['b','r','g','m','y'];
hxs = [];
for i = 2:d
    [hy, hx] = hist(t_res(i,:), 50);
    bar(hx,hy, cols(i-1))
    [hm, idx] = max(hy);
    text(hx(idx)+2, hm, num2str(hx(idx)))
    hxs = [hxs hx(idx)];
end
title(['Distribution of ' num2str(d-1) ' breakpoints'], 'Interpreter', 'latex')
xlabel('Year', 'Interpreter', 'latex')
ylabel('Counts', 'Interpreter', 'latex')
legend('Breakpoint 1', 'Breakpoint 2', 'Breakpoint 3', 'Breakpoint 4')
axis([1851 2000 0 5000]);

figure(3)
clf
[disasters, years] = hist(floor(tau), unique(floor(tau)));
plot(years, disasters, '*');
hold on
plot([hxs;hxs], [zeros(size(hxs));7*ones(size(hxs))]);

axis([1840 1980 0 7])
title(['Positions of ' num2str(d-1) ' breakpoints drawn on accident graph'], 'Interpreter', 'latex')
xlabel('Year', 'Interpreter', 'latex')
ylabel('Accidents per year', 'Interpreter', 'latex')
legend('Accidents', 'Breakpoint 1', 'Breakpoint 2', 'Breakpoint 3', 'Breakpoint 4')
axis([1851 1980 0 7]);

%x = linspace(1851, 1962);
%figure(1)
%clf
%plot(x, n(x));