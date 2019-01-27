x = -10:.01:10;
p = -2;
f = 100;
figure(1)
clf

while abs(f) > 10^-10
f = p - 4*sin(2*p) - 3;
fp = 1-8*cos(2*p);
fb = 16*sin(2*p);


y = x - 4*sin(2*x) - 3;
yt = f + fp*(x-p) + .5*fb*(x-p).^2;
plot(x, y)
hold on
plot(x, yt)
plot(x, 0)
axis([-pi 2*pi -10 10])
pause(.5)

xm = p - fp/fb + sqrt((fp/fb)^2-2*f/fb);
xp = p - fp/fb - sqrt((fp/fb)^2-2*f/fb);
if abs(xm-p) < abs(xp-p)
    p = xm;
else
    p = xp;
end

disp([f;fp;fb])
disp(p)

end