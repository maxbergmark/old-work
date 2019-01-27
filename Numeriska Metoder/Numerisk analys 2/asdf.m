summor = [];
for a = 0:100
    summa = 0;
    for b = 0:floor(a/2)
        summa = summa + factorial(a-b)/(factorial(b)*factorial(a-2*b));
    end
    summor(end+1) = summa;
end
disp(summor')
hold off
semilogy(summor)
hold on
plot(1.618.^(0:100), 'r')