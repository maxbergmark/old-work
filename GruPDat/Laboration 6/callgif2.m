x = 0:0.01:1;
figure(1)
filename = 'cherp9.gif';
for n = 1:9
tic
x = [0 1 2];
y = [0 2 0];

clf
whitebg('white')

cherp(x, y, n)
text(23.5, 1, num2str(n), 'Color', [0 0 1], 'FontSize', 20)
set(gca,'XTick',[]);
set(gca,'YTick',[]);

drawnow
frame = getframe(1);
im = frame2im(frame);
[imind,cm] = rgb2ind(im,256);
if n == 1;
imwrite(imind,cm,filename,'gif', 'Loopcount',inf);
else
imwrite(imind,cm,filename,'gif','WriteMode','append');
disp([num2str(int8(n)) '   ' num2str(toc) '   ' datestr(now)])
end
end