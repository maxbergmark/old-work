num = 100;
t = linspace(0,1, num);

dim = 10;
P = zeros(dim+1, 2);

p = 3*rand(dim-2, 2);
trail = 20;

figure(1)
clf
whitebg('k')
hold on
set(gcf,'DefaultAxesColorOrder',linspace(0, 1, trail)'*[1 0 0])

Bs = zeros(2*trail, num);


%h = quiver(Bs(1:2:end,:)', Bs(2:2:end,:)');


k = 0;
dk = .01;
while true

deriv = [4*p(1, 1)*cos(p(1, 1)*k), 4*p(1, 2)*cos(p(1, 2)*k)];

P(1,:) = [4*sin(p(1, 1)*k), 4*sin(p(1, 2)*k)];
P(end,:) = P(1,:);
P(2,:) = P(1,:) + deriv;
P(end-1,:) = P(end,:) - deriv;
P(3:end-2,:) = 4*sin(p(2:end,:)*k);

B = zeros(2, num);
for a = 0:dim
    B = B + P(a+1,:)'*((1-t).^(dim-a).*t.^a*factorial(dim)/(factorial(a)*factorial(dim-a)));
end
Bs = [Bs(3:end,:);B];
hold off

plot(Bs(1:2:end,:)', Bs(2:2:end,:)')
%hold on
%plot(Bs(1:2:end,:), Bs(2:2:end,:),'r')
%set(h, 'Xdata', Bs(1:2:end,:)', 'YData', Bs(2:2:end,:)', 'Color', [1 0 0])


axis([-5 5 -5 5])
set(0,'defaultaxesposition',[0 0 1 1])
set(gca, 'XTick', [])
set(gca, 'YTick', [])
drawnow
k = k+dk;
end