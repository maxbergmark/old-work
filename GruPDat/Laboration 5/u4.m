l = 1*10^-8:10^-7:10^-5;
T = [600 800 1000 1100];

for j=T
    y1 = custom(l, j);
    [max1, l1] = max(custom(l, j));
    
    plot(l(l1), max1, '*')
    text(l(l1), max1+10^9, ['T=' int2str(j)])
    plot(l, y1)
    hold on
end

title('Intensitetskurvor')
xlabel('Våglängd lambda')