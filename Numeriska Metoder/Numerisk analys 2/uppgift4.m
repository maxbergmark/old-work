function uppgift4(r, l)
if nargin == 0
    r = 7;
    l = 15;
end
x = r*cos(linspace(0,2*pi));
y = r*sin(linspace(0,2*pi));
figure(1)
clf
fill(x,y, 'b')
axis equal
hold on


%l = 15;
dt = 50;
t = linspace(0,l/r, dt);
pc = r*[cos(t); sin(t)]';
xg = r*cos(t)-l*sin(t)+r*t.*sin(t);
yg = r*sin(t)+l*cos(t)-r*t.*cos(t);
pg = [xg; yg]';
l1 = abs(l-7*t);
A = .5*l1(1:end-1)*(diff(xg).^2+diff(yg).^2)'.^.5;
dist = 2*sum((diff(xg).^2+diff(yg).^2)'.^.5)+l*pi;
axis([min(xg)-5 r+l+5 -max(yg)-5 max(yg)+5])

%Här börjar plotten

for k = 1:length(t)
    plot(pg(k,1), pg(k,2), '*', 'Color', 'r')
    plot(pg(k,1),-pg(k,2), '*', 'Color', 'r')
    drawnow
end


dk = 100;
for k = linspace(0,pi,dk)
    plot(r+l*sin(k), l*cos(k), '*', 'Color', 'r')
    drawnow
end
for k = 1:length(t)-1
    fill([pc(k,1) xg(k+1) xg(k)], [pc(k,2) yg(k+1) yg(k)], 'g')
    fill([pc(k,1) xg(k+1) xg(k)], -[pc(k,2) yg(k+1) yg(k)], 'g')
    drawnow
    
end
for k = linspace(0,pi-pi/dk, dk)
    fill([r r+l*sin(k) r+l*sin(k+pi/dk)], [0 l*cos(k) l*cos(k+pi/dk)], 'g')
    drawnow
end

%Här slutar plotten

A = 2*A+.5*pi*l^2;
disp(['    Area:  ' num2str(A)])
disp(['    Circumference:  ' num2str(dist)])