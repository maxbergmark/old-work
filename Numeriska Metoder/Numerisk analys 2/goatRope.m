function dist=goatRope(l)
if nargin == 0
    l = 15
end

dt = 100000;
t = linspace(0,l/7, dt);
pc = 7*[cos(t); sin(t)]';
xg = 7*cos(t)-l*sin(t)+7*t.*sin(t);
yg = 7*sin(t)+l*cos(t)-7*t.*cos(t);
pg = [xg; yg]';
l1 = abs(l-7*t);
A = .5*l1(1:end-1)*(diff(xg).^2+diff(yg).^2)'.^.5;
dist = 2*sum((diff(xg).^2+diff(yg).^2)'.^.5)+l*pi;