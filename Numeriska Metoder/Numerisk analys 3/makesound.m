t = linspace(0,440*pi, 8192);

notes = 2.^((-19:17)./12);
y = [];
for k = notes
    y = [y sin(k*t).^11];
end
soundsc(y, 16384)