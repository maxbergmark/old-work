clear all
clc

load('HA1-data/stations.mat');
% load('HA1-data/RSSI-measurements.mat');
load('HA1-data/RSSI-measurements-unknown-sigma.mat');


% Transition probability matrix
P = (ones(5) + diag(ones(1,5))*15)/20;

% Driving commands (bivariate Markov chain)
Z = [0 0; 3.5 0; 0 3.5; -3.5 0; 0 -3.5];
dt = 0.5;
alpha = 0.6;

% Phi matrices
phi_dash = [1 dt dt^2/2;0 1 dt; 0 0 alpha];
phi_dash_z = [dt^2/2;dt;0];
phi_dash_w = [dt^2/2;dt;1];

phi = [phi_dash zeros(3);zeros(3) phi_dash];
phi_w = [phi_dash_w zeros(3,1);zeros(3,1) phi_dash_w];
phi_z = [phi_dash_z zeros(3,1);zeros(3,1) phi_dash_z];

v = 90;
eta = 3;

y = Y;

N = 10000;

estimates = [];
max_P = - 1e100;
max_paths = {};

delete(gcp('nocreate'))
parpool(8);
format longg

parfor sig = 1:120
    sigma = sig/40;

    logP_sum = 0;

    sigmas = [500 5 5 200 5 5];

    X_p = [randn(6,N).*sigmas'; randi(5,1,N)];


    y1t = zeros(6,N);
    y1guess = zeros(6,1);
    for i = 1:6
        y1t(i,:) = v - 10*eta*log10(sqrt( sum((X_p([1 4],:)-pos_vec(:,i)).^2, 1) ));
    end
    W_p = prod(normpdf(y1t, y(:,1), sigma));

    guesses = [];
    guesses = [guesses sum(X_p(1:6,:).*W_p, 2)/sum(W_p)];

    sigma_w = .25;

    for i = 2:501
       if sum(sum(W_p)) == 0
            W_p = W_p+1;
        end
        sample = randsample(N,N,true, W_p);
        W_p = W_p(sample');
        X_p(:,:) = X_p(:,sample');

        W = randn(2, N)*sigma_w^2;
        X_p(1:6,:) = phi*X_p(1:6,:) + phi_z * Z(X_p(7,:),:)' + phi_w*W;


        change = rand(N,1);
        num = sum(change<.25);
        new = zeros(1,N);
        new(change<.25) = randi(5, 1,num);
        changed = X_p(7,:) ~= new & (change<.25)';
        X_p(7, change<.25) = new(new>0);

        for j = 1:6
            y1t(j,:) = v - 10*eta*log10(sqrt( sum((X_p([1 4],:)-pos_vec(:,j)).^2, 1) ));
        end

        W_p = prod(normpdf(y1t, y(:,i), sigma));
        guess = sum(X_p(1:6,:).*W_p, 2)/sum(W_p);
        for j = 1:6
            y1guess(j,:) = v - 10*eta*log10(sqrt( sum((guess([1 4])-pos_vec(:,j)).^2, 1) ));
        end
%         guess_p = sum(log(normpdf(y1guess, y(:,i), sigma)));
        guess_p = log(mean(W_p));
        logP_sum = logP_sum + guess_p;
        guesses = [guesses guess];


    end




    disp([sigma logP_sum])
    estimates(sig,:) = [sigma logP_sum];
    max_paths{sig} = guesses;


end

delete(gcp('nocreate'))


%%
[val, idx] = max(estimates(:,2));
max_path = max_paths{idx};
figure(7)
clf
semilogy(estimates(:,1), estimates(:,2));
hold on
semilogy(estimates(idx,1), estimates(idx,2), 'r*')
text(estimates(idx,1)-.4,estimates(idx,2)+1000, ['Best sigma: ' num2str(estimates(idx,1))])
legend('Logarithmic probability', 'Highest probability')
title('Logarithmic probability for unknown $\sigma$', 'Interpreter', 'latex')
xlabel('Standard deviation')
ylabel('log(P)')

%%

figure(8)
clf
plot(max_path(1,:), max_path(4,:))
hold on
plot(pos_vec(1,:), pos_vec(2,:),  'r*');
axis equal
legend('Estimated path', 'Base stations')
title(['SISR estimated path for $\sigma = ' num2str(estimates(idx,1)) '$'], 'Interpreter', 'latex')
ylabel('Y')
xlabel('X')


figure(2)
clf
plot(y')
legend('norr', 'söder', 'nordost', 'sydost', 'sydväst', 'nordväst')
axis([0 501 -40 10])

