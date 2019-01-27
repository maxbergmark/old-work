x = [];
y = [];

while 1
    [xt, yt] = ginput;
    x = [x; xt];
    y = [y; yt];
    findcirc(x', y')
end