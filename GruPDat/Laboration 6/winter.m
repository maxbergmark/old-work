load('vinter.mat');

time = vinterdag(1:97) + vinterdag(98:194)/60;
temp = vinterdag(195:291);

plot(time', temp', '.')
hold on

f = fittype('c1 + c2*sin(2*pi*x/24) + c3*cos(2*pi*x/24)'); 
fit1 = fit(time',temp', f, 'Startpoint', [1 1 1]);

plot(fit1)

maxx = 0;
maxy = 0;

for x=6:0.01:22
    if maxy < fit1(x)
        maxy = fit1(x);
        maxx = x;
    end
end

plot(maxx, maxy, 'g*')
text(maxx, maxy+2, ['T=' int2str(maxy)])



