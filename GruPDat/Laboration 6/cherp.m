function cherp(x, y, nmax, n)

if nargin < 4
   n = 0; 
end

if n == nmax
    return
end

if n == nmax-1
    c = 'black';
else
    c = 'red';
end


fill(x, y, c)
hold on
drawnow

cherp([x(1) (x(1)+x(2))/2 (x(1)+x(3))/2], [y(1) (y(1)+y(2))/2 (y(1)+y(3))/2], nmax, n+1)
cherp([(x(1)+x(3))/2 (x(2)+x(3))/2 x(3)], [(y(1)+y(3))/2 (y(2)+y(3))/2 y(3)], nmax, n+1)
cherp([(x(1)+x(2))/2 x(2) (x(3)+x(2))/2], [(y(1)+y(2))/2 y(2) (y(3)+y(2))/2], nmax, n+1)

