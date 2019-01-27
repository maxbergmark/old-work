
figure(1)
clf
whitebg('black')

x = linspace(-pi, pi, 2000);
y = 0;
n = 1000;

for i = 1:n
    y = y + sin(i*x)/i;
    col = [i/n 0 1-i/n].^.3;
    if i == n
        col = [1 1 0];
    end
    plot(x, y, 'Color', col)
    hold on
    axis equal
    axis([-pi pi -2 2])
    drawnow
end