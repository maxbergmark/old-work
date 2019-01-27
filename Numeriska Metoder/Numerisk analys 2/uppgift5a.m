xh = 1.5;
yh = 0;
d = 10;

c = @(x, y) exp(-d*((x-xh)^2 + (y-yh)^2));

int = 0;
hx = 10000;
for x = linspace(0, 2, hx)
    hy = 10000*x;
    for y = linspace(0, x/2, hy)
        int = int + x/(hx*hy)*c(x, y);
    end
end
disp(int)
