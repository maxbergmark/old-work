function tree(x, y, a0, nmax, n, a)
if nargin < 5
    n = 0;
    a = pi/2;
end

if n == nmax
    return
end

if n == 1
    axis([-25 25 0 40])
end
    
x2 = x+cos(a)*(10/(n+1));
y2 = y+sin(a)*(10/(n+1));

plot([x x2+0.00*(x2-x)], [y y2+0.00*(y2-y)], 'Color', [0.4-n/(3*nmax) min(0.2, 1-n/nmax)+n/(2*nmax) 0], 'LineWidth', nmax-n, 'LineSmoothing', 'on')
hold on
set(gca,'XTick',[])
set(gca,'YTick',[])
drawnow

%tree(x2, y2, a0, nmax, n+1, a-2*a0*pi/360)

tree(x2, y2, a0, nmax, n+1, a-1*a0*pi/360)

%tree(x2, y2, a0, nmax, n+1, a+1*a0*pi/360)

tree(x2, y2, a0, nmax, n+1, a+2*a0*pi/360)