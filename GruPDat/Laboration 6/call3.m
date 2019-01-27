x = 0;
y = 0;
figure(1)
clf

whitebg('black')
axis off
for i = 0:90
clf
tree2(x, y, 90-i, 6)
drawnow
end