
xh = 1.5;
yh = 0;
zh = .25;
d = @(z) 50*(z+zh)*exp(-z/(2*zh));


c = @(x, y, z) exp(-d(z)*((x-xh)^2 + (y-yh)^2));

int = 0;
hx = 200;
hz = 200;
for x = linspace(0, 2, hx)
    hy = 200*x;
    for y = linspace(0, x/2, hy)
        for z = linspace(0, 1, hz)
            int = int + x/(hx*hy*hz)*c(x, y, z);
        end
    end
    %disp(x)
end
disp(int)
