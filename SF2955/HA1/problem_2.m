load('HA1-data/stations.mat');
load('HA1-data/RSSI-measurements.mat');
figure(1);
clf;
plot(Y');
axis([0 500 -30 0]);
figure(2);
clf;
hold on
plot(pos_vec(1,:), pos_vec(2,:), '*');

path = [linspace(-4000, 4000, 500)' linspace(-4000, 4000, 500)'];

v = 90;
eta = 3;
sigma = 1.5;
% Y = v - 10*eta*log10|x-pi| + noise;
y1 = v - 10*eta*log10(sqrt( sum((path-pos_vec(:,1)').^2, 2) )) + sigma*randn(500,1);
y2 = v - 10*eta*log10(sqrt( sum((path-pos_vec(:,2)').^2, 2) )) + sigma*randn(500,1);
y3 = v - 10*eta*log10(sqrt( sum((path-pos_vec(:,3)').^2, 2) )) + sigma*randn(500,1);
y4 = v - 10*eta*log10(sqrt( sum((path-pos_vec(:,4)').^2, 2) )) + sigma*randn(500,1);
y5 = v - 10*eta*log10(sqrt( sum((path-pos_vec(:,5)').^2, 2) )) + sigma*randn(500,1);
y6 = v - 10*eta*log10(sqrt( sum((path-pos_vec(:,6)').^2, 2) )) + sigma*randn(500,1);

y = [y1';y2';y3';y4';y5';y6'];
plot(path(:,1), path(:,2), '.')
figure(3);
clf;
plot(y');

A = zeros(400);

meas = y(:,100);
pos_true = path(100,:);

for i=1:400
    for j=1:400
        xt = [(i-200)*20 (j-200)*20];
        
        y1t = v - 10*eta*log10(sqrt( sum((xt-pos_vec').^2, 2) ));
        
        ds = y1t-meas;
        prob = prod(1/sqrt(2*pi*sigma^2)*exp(-ds.^2./(2*sigma^2)));
        A(i,j) = prob;
        
    end
end

%%
figure(4);
clf
imagesc(A)
title('Probability $p( y_n | \tilde{x}_n)$','Interpreter','latex');
set(gca, 'XTickLabel', [-3000 -2000 -1000 0 1000 2000 3000 4000])
set(gca, 'YTickLabel', [-3000 -2000 -1000 0 1000 2000 3000 4000])
xlabel('X')
ylabel('Y')
hold on
pix = pos_true/20+200;
plot(pix(1), pix(2), 'r*')
legend('Actual position')