f = @(x) x.^3;
g = @(x) 8;
n = 100000;
u1 = 2*rand(n,1);
u2 = 8*rand(n,1);
clf
plot(u1(f(u1) >= u2),u2(f(u1) >= u2), 'r.')
hold on
plot(u1(f(u1) < u2),u2(f(u1) < u2), 'b.')
t = sum(f(u1) >= u2);
t/n*16