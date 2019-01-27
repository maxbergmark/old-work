load('HA1-data/stations.mat');
% load('HA1-data/RSSI-measurements.mat');
load('HA1-data/RSSI-measurements-unknown-sigma.mat');

y = Y;

N = 10000;

estimates = [];
max_P = - 1e100;
max_path = [];
for sigma=.1:.05:3

    % sigma = 1.5;
    logP_sum = 0;

    sigmas = [500 5 5 200 5 5];

    X_p = [randn(6,N).*sigmas'; randi(5,1,N)];
    % figure(1)
    % clf
    % plot(coords(:,1),coords(:,2), '.')
    % hold on
    % plot(pos_vec(1,:), pos_vec(2,:), 'r*')
    % axis equal

%     figure(2)
%     clf
%     plot(y')
%     legend('norr', 'söder', 'nordost', 'sydost', 'sydväst', 'nordväst')

%     figure(3)
%     clf
%     plot(X_p(1,:), X_p(4,:), '.')
%     hold on
%     plot(pos_vec(1,:), pos_vec(2,:), 'r*')
%     axis equal

    y1t = zeros(6,N);
    y1guess = zeros(6,1);
    for i = 1:6
        y1t(i,:) = v - 10*eta*log10(sqrt( sum((X_p([1 4],:)-pos_vec(:,i)).^2, 1) ));
    end
    W_p = prod(normpdf(y1t, y(:,1), sigma));
    % W_p = ones(1,N);

%     figure(4)
%     clf
%     hist3(X_p([1 4],:)');
    % plot3(X_p(1,:),X_p(4,:), W_p, '*')
%     hold on
    % plot3(coords(1,1), coords(1,2), max(W_p), 'ro')
%     axis vis3d
%     xlabel('X')
%     ylabel('Y')
%     zlabel('Z')

    guesses = [];
    guesses = [guesses sum(X_p(1:6,:).*W_p, 2)/sum(W_p)];
    % plot3(guess(1),guess(4),max(W_p), 'go')

    sigma_w = .25;

    for i = 2:501
    %     disp(i)

    %     particle(:,t+1) = phi*particle(:,t) + phi_z*Z(c,:)' + phi_w*W;
        if sum(sum(W_p)) == 0
            W_p = W_p+1;
        end
        sample = randsample(N,N,true, W_p);
        W_p = W_p(sample');
        X_p(:,:) = X_p(:,sample');

        W = randn(2, N)*sigma_w^2;
        X_p(1:6,:) = phi*X_p(1:6,:) + phi_z * Z(X_p(7,:),:)' + phi_w*W;
    %     X_diff = phi_w*W;


    %     W_p = prod(normpdf(W, 0, sigma_w),1);

    %     for j2 = 1:N

    %     end

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
        guess_p = sum(log(normpdf(y1guess, y(:,i), sigma)));
        logP_sum = logP_sum + guess_p;
        guesses = [guesses guess];


    %     figure(3)
    %     clf
    %     plot(X_p(1,:), X_p(4,:), '.')
    %     hold on
    %     plot(pos_vec(1,:), pos_vec(2,:),  'r*');
    %     plot(coords(i,1), coords(i,2), 'go')
    %     plot(guesses(1,i), guesses(4,i), 'ro')
    % 
    %     text(-4900, 29500, ['Sum: ' num2str(sum(W_p))]);
    %     axis equal
    %     axis([-5000 5000 -4000 30000])
    %     drawnow

    end

    if (logP_sum > max_P)
        max_P = logP_sum;
        max_path = guesses;
    end


%     figure(5)
%     clf
    % plot(coords(:,1), coords(:,2), '.')
%     plot(guesses(1,:), guesses(4,:), 'g')
%     hold on
%     plot(pos_vec(1,:), pos_vec(2,:), 'r*')
%     axis equal

    % figure(6)
    % clf
    % diff = guesses([1 4],:)-coords';
    % diff_len = arrayfun(@(idx) norm(diff(:,idx)'), 1:size(diff,2));
    % plot(1:500, diff_len)

    disp([sigma logP_sum])
    estimates = [estimates;sigma logP_sum];


end
%%
[val, idx] = max(estimates(:,2));
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
title('SISR estimated path for $\sigma = 2.15$', 'Interpreter', 'latex')
ylabel('Y')
xlabel('X')


figure(2)
clf
plot(y')
legend('norr', 'söder', 'nordost', 'sydost', 'sydväst', 'nordväst')
axis([0 501 -40 10])

%%

coords = max_path([1 4],:)';
y1 = v - 10*eta*log10(sqrt( sum((coords-pos_vec(:,1)').^2, 2) )) + 0*sigma*randn(501,1);
y2 = v - 10*eta*log10(sqrt( sum((coords-pos_vec(:,2)').^2, 2) )) + 0*sigma*randn(501,1);
y3 = v - 10*eta*log10(sqrt( sum((coords-pos_vec(:,3)').^2, 2) )) + 0*sigma*randn(501,1);
y4 = v - 10*eta*log10(sqrt( sum((coords-pos_vec(:,4)').^2, 2) )) + 0*sigma*randn(501,1);
y5 = v - 10*eta*log10(sqrt( sum((coords-pos_vec(:,5)').^2, 2) )) + 0*sigma*randn(501,1);
y6 = v - 10*eta*log10(sqrt( sum((coords-pos_vec(:,6)').^2, 2) )) + 0*sigma*randn(501,1);

y2 = [y1';y2';y3';y4';y5';y6'];

figure(3)
clf
plot(y2');
legend('norr', 'söder', 'nordost', 'sydost', 'sydväst', 'nordväst')

