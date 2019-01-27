x0 = -1:0.01:10;
y = curv(x0);
plot(x0, 0);
hold on;
plot(x0, y);
grid on

x = fzero('curv', -1);
disp(x);
