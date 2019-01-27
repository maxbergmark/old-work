load('atlantic.mat');
% close all

[beta, mu] = est_gumbel(atlantic);

F    = @(x) exp(-exp(-(x-mu)/beta));
F_inv = @(u) mu - beta*log(-log(u));
f    = @(x) 1/beta * exp(-((x-mu)/beta + exp(-(x-mu)/beta)));

x_inv = linspace(0,1, 1000);
x = linspace(0, 40);
y = f(x);
Y = F(x);
Y_inv = F_inv(x_inv);

T = 3*14*100;
max_wave = F_inv(1-1/T);
max_prob = f(max_wave);

% Bootstrap data from gumbel dist.

% bstar = 
for i = 1:1000
    U = rand(582,1);
    samples = F_inv(U);
    
end

figure(1)
clf
plot(x,y)
hold on
plot(x,Y)
plot(max_wave, max_prob, 'r*')
text(max_wave-5, max_prob+.1, ['100-year wave: ' num2str(max_wave)]);
text(max_wave-5, max_prob+.05, ['100-year prob: ' num2str(max_prob)]);

figure(2)
clf
hist(atlantic)

figure(3)
clf
plot(atlantic, '*')

figure(4)
clf
plot(x_inv, Y_inv)

