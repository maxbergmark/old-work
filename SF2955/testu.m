clc
n = 10000;
u1 = rand(n,1); 
u2 = rand(n,1); 
l = u1.^2+u2.^2;
disp(4*sum(l<1)/n)
clf
plot(u1(l<1), u2(l<1), 'r.')
hold on
plot(u1(l>=1), u2(l>=1), 'b.')
