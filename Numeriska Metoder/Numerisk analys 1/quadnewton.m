function P = quadnewton(p, error)
if nargin < 2
    error = 10^-10;
end
x = -10:.01:10;
f = vpa(100);
p = vpa(p);
error = vpa(error);
digits(200)
P = p;


draw = false;
if draw
    figure(1)
    clf
end

while abs(f) > error
f = p - 4*sin(2*p) - 3;
fp = 1-8*cos(2*p);
fb = 16*sin(2*p);



y = x - 4*sin(2*x) - 3;
yt = f + fp*(x-p) + .5*fb*(x-p).^2;
if draw
    plot(x, y)
    hold on
    plot(x, yt)
    plot(x, 0)
    axis([-pi 2*pi -10 10])
    drawnow
end

xm = p - fp/fb + sqrt((fp/fb)^2-2*f/fb);
xp = p - fp/fb - sqrt((fp/fb)^2-2*f/fb);
if abs(xm-p) < abs(xp-p)
    p = xm;
else
    p = xp;
end
P = [P; p];
%disp([f;fp;fb])
%disp(p)
if abs(f) > 10^10
    P = inf;
    break
end

end