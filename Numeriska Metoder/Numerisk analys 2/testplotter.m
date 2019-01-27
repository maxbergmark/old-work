clf
axis equal

n = 13;
points = linspace(0, (2)*pi, n+1);
xnod = sin(points);
ynod = cos(points);
%xnod = rand(1, length(points));
%ynod = rand(1, length(points));
bars = [];
willdraw = randi(2, n, 1);
for k = 1:n
    for m = 1:floor(n/2)
        k2 = mod(k+m, n);
        if k2 == 0
            k2 = k2+n;
        end
        %if willdraw(m) == 2
            bars = [bars;k k2];
        %end
        

    end
end

trussplot2(xnod, ynod, bars)
axis([min(xnod) max(xnod) min(ynod) max(ynod)])
