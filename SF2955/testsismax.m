l = linspace(-10, 10, 100);
[x, y] = meshgrid(l, l);
f = @(x,y) exp(-((x+5)/5).^2-((y-5)/5).^2) + 1.05*exp(-((x-5)/2).^2-((y+5)/2).^2);
n = 10000;
k = 500;

p = rand(2, n)*20-10;
w = f(p(1,:), p(2,:));

clf
surf(x, y, f(x,y))
axis vis3d
hold on
plot3(p(1,:), p(2,:), f(p(1,:), p(2,:)), 'r*')

for i = 1:k
    i
    p(1,:) = p(1,:) + 1/i*randn(1,n);
    p(2,:) = p(2,:) + 1/i*randn(1,n);
    sample = randsample(n, n, true, w);
    p(:,:) = p(:,sample);
    w = f(p(1,:), p(2,:));
    
    [max_w, idx] = max(w);
    est = sum(p, 2)/n;

    figure(1)
    clf
    surf(x, y, f(x,y),'EdgeColor','none')
    axis vis3d
    hold on
    plot3(p(1,:), p(2,:), f(p(1,:), p(2,:)), 'r*')
    plot3(p(1,idx), p(2,idx), f(p(1,idx), p(2,idx)), 'go')
    title([num2str(p(1,idx)) '   ' num2str(p(2,idx))])
    xlabel(['X: ' num2str(est(1))])
    ylabel(['Y: ' num2str(est(2))])
    axis([-10 10 -10 10 0 2])
    drawnow;
    
    figure(2)
    clf
    hist3(p', [10 10])
    xlabel('X')
    ylabel('Y')
    drawnow
    
end