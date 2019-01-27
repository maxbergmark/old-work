A = load('data.txt');
x = linspace(1,8);
f = 0.4;
y = x./(1+(x-1)*f);

plot(A(:,1), A(1,2)./A(:,2));

axis([1 8 0 2.5])
title('Speedup for parallel Odd-Even Sort')
xlabel('Processes')
ylabel('Speedup')