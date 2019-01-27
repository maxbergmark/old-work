%% Clear and initialize model

clear all

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
sigma = 1.5;

%% Generate particle (path) of length T

% TODO: do this for N separate instances to generate N particles
% TODO: computation of weights

% Initial state vector X_0
X = randn(6,1) .* [500 5 5 200 5 5]';

% Index for initial driving command Z_0
c = randi(5); 

T = 501; % Number of time steps

% Drive T steps and store X vector at each time step in test_particle
particle = zeros(6,T);
W = randn(2, 1)*0.25;
particle(:,1) = phi*X + phi_z*Z(c,:)' + phi_w*W;

for t = 1:T-1
    % Driving command for next step,
    y = linspace(1,5,5);
    if (rand() > 0.8)
        c = randsample(setdiff(y,c),1);
    end
    W = randn(2, 1)*0.25;
    particle(:,t+1) = phi*particle(:,t) + phi_z*Z(c,:)' + phi_w*W;
end

figure(1)
clf
hold on

coords = particle([1 4],:)';
% coords = [particle(1,:)' particle(4,:)'];
plot(coords(:,1),coords(:,2),'*')
axis equal

%%

load('HA1-data/stations.mat');
load('HA1-data/RSSI-measurements.mat');

y = Y;

%%

sigma=1.5;

% Y = v - 10*eta*log10|x-pi| + noise;
y1 = v - 10*eta*log10(sqrt( sum((coords-pos_vec(:,1)').^2, 2) )) + sigma*randn(501,1);
y2 = v - 10*eta*log10(sqrt( sum((coords-pos_vec(:,2)').^2, 2) )) + sigma*randn(501,1);
y3 = v - 10*eta*log10(sqrt( sum((coords-pos_vec(:,3)').^2, 2) )) + sigma*randn(501,1);
y4 = v - 10*eta*log10(sqrt( sum((coords-pos_vec(:,4)').^2, 2) )) + sigma*randn(501,1);
y5 = v - 10*eta*log10(sqrt( sum((coords-pos_vec(:,5)').^2, 2) )) + sigma*randn(501,1);
y6 = v - 10*eta*log10(sqrt( sum((coords-pos_vec(:,6)').^2, 2) )) + sigma*randn(501,1);

y = [y1';y2';y3';y4';y5';y6'];

%%

N = 500;

sigmas = [500 5 5 200 5 5];

X_p = [randn(6,N).*sigmas'; randi(5,1,N)];
% W_p = prod(normpdf(X_p(1:6,:), zeros(6,1), sigmas'));
figure(1)
clf
plot(coords(:,1),coords(:,2), 'b')
hold on
plot(pos_vec(1,:), pos_vec(2,:), 'r*')
axis equal
title('Generated path')
xlabel('X')
ylabel('Y')
legend('Generated path', 'Base stations')

figure(2)
clf
plot(y')
axis([0 501 -40 20])
title('Generated signal strength for all base stations')
xlabel('Time step')
ylabel('Signal strength (dB)')
legend('norr', 'söder', 'nordost', 'sydost', 'sydväst', 'nordväst')


figure(3)
clf
plot(X_p(1,:), X_p(4,:), '.')
hold on
plot(pos_vec(1,:), pos_vec(2,:), 'r*')
axis equal

%%

y1t = zeros(6,N);
for i = 1:6
    y1t(i,:) = v - 10*eta*log10(sqrt( sum((X_p([1 4],:)-pos_vec(:,i)).^2, 1) ));
end
W_p = prod(normpdf(y1t, y(:,1), sigma));
% W_p = ones(1,N);

figure(4)
clf
plot3(X_p(1,:),X_p(4,:), W_p, '*')
hold on
plot3(coords(1,1), coords(1,2), max(W_p), 'ro')
axis vis3d
xlabel('X')
ylabel('Y')
zlabel('Z')

guesses = [];
guesses = [guesses sum(X_p(1:6,:).*W_p, 2)/sum(W_p)];
% plot3(guess(1),guess(4),max(W_p), 'go')

sigma_w = .25;

for i = 2:30
    
%     particle(:,t+1) = phi*particle(:,t) + phi_z*Z(c,:)' + phi_w*W;
    W = randn(2, N)*sigma_w^2;
    X_p(1:6,:) = phi*X_p(1:6,:) + phi_z * Z(X_p(7,:),:)' + phi_w*W;
%     X_diff = phi_w*W;
    
%     W_p = W_p .* prod(normpdf(W, 0, sigma_w),1);
    
    change = rand(N,1);
    num = sum(change<.25);
    new = zeros(1,N);
    new(change<.25) = randi(5, 1,num);
    changed = X_p(7,:) ~= new & (change<.25)';
    X_p(7, change<.25) = new(new>0);
    
    for j = 1:6
        y1t(j,:) = v - 10*eta*log10(sqrt( sum((X_p([1 4],:)-pos_vec(:,j)).^2, 1) ));
    end
    
    W_p = W_p.*prod(normpdf(y1t, y(:,1), sigma));
%     W_p(changed) = W_p(changed)*.2;
%     W_p(~changed) = W_p(~changed)*.8;
%     W_p
%     W_p
%     W_p = W_p.*prod(normpdf(y1t, y(:,1), sigma));
    guesses = [guesses sum(X_p(1:6,:).*W_p, 2)/sum(W_p)];
%     [max(W_p) prod(normpdf(y1t, y(:,1), sigma))]
    if (max(W_p) > 1e-50)
%         W_p = W_p./max(W_p);
    else
%         W_p = 1;
    end

%     figure(3)
%     clf
%     plot(X_p(1,:), X_p(4,:), '.')
%     hold on
%     plot(pos_vec(1,:), pos_vec(2,:), 'r*')
%     plot(coords(i,1), coords(i,2), 'g*')
%     plot(guesses(1,i), guesses(4,i), 'm*')
% 
%     text(-4900, 3800, ['Time step: ' num2str(i)]);
%     text(-4900, 3500, ['Sum of weights: ' num2str(sum(W_p))]);
% %     [i coords(i,1) coords(i,2)]
%     axis equal
%     axis([-5000 5000 -4000 4000])
%     title('Simulation with particles')
%     xlabel('X')
%     ylabel('Y')
%     legend('Particles','Base stations','Actual position','Estimate')
%     drawnow
    figure(6)
    clf
    hist(log10(W_p), 20)
    title(['Weight distribution for t = ' num2str(i)])
    ylabel('Count')
    xlabel('log(w)')
    drawnow

end

%%

figure(5)
clf
plot(coords(:,1), coords(:,2), 'b')
hold on
plot(guesses(1,:), guesses(4,:), 'g')
plot(pos_vec(1,:), pos_vec(2,:), 'r*')
axis equal
title('Estimated path with SIS')
xlabel('X')
ylabel('Y')
legend('Actual path', 'SIS estimate', 'Base stations')
