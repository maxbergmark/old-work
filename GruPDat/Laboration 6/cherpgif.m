function cherpgif(x, y, nmax, n, count)

figure(1)
filename = 'cherp6.gif';

if nargin < 4
   n = 0; 
   count = 0;
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
axis off
hold on

drawnow
frame = getframe(1);
im = frame2im(frame);
[imind,cm] = rgb2ind(im,256);
if count == 0;
imwrite(imind,cm,filename,'gif', 'Loopcount',inf);
count = count +1;
else
imwrite(imind,cm,filename,'gif','WriteMode','append');
count = count +1;
%disp([num2str(int8(n)) '   ' num2str(toc) '   ' datestr(now)])
end




cherpgif([x(1) (x(1)+x(2))/2 (x(1)+x(3))/2], [y(1) (y(1)+y(2))/2 (y(1)+y(3))/2], nmax, n+1, count)
cherpgif([(x(1)+x(3))/2 (x(2)+x(3))/2 x(3)], [(y(1)+y(3))/2 (y(2)+y(3))/2 y(3)], nmax, n+1, count)
cherpgif([(x(1)+x(2))/2 x(2) (x(3)+x(2))/2], [(y(1)+y(2))/2 y(2) (y(3)+y(2))/2], nmax, n+1, count)

