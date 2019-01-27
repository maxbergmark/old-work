figure(1)
whitebg([.0 .0 .0])
clf
format long
p = 1;
L = 10;
y = @(x) (1-(1/pi)*atan(p*(x-1)))./(2-cos(pi*x));


%Plot-delen av programmet

x = linspace(0, L);
axis off
axis vis3d
axis([0 L -max(y(x))*1.2 max(y(x))*1.2 -max(y(x))*1.2 max(y(x))*1.2])
hold on
view(30, 30)

plot3(x, 0*x, 0*x, '--r')

dk = .01;
light('Position',[1 0 5],'Style','infinite');
circ = linspace(0, 2*pi, 100);
for k = 0:dk:L
    X = [0*circ+k; 0*circ+k+dk];
    Y = [y(k)*sin(circ);y(k+dk)*sin(circ)];
    Z = [y(k)*cos(circ); y(k+dk)*cos(circ)];
    surf(X, Y, Z, sin(6*atan(Z./Y)-10*X), 'EdgeColor', 'none')%, 'FaceLighting', 'unlit')
    colormap(jet(256));
    drawnow
end

for z = 15:2:360+15
    view(2*z, 30*cos(1*(z-30)*pi/180))
    drawnow
end




%Här slutar plotten

n = 12;
V = zeros(1,n);
Hs = zeros(1,n);
for k = 1:n
dt = 2.6*2^(-k);
areaTrap = 0;
areaSimp = 0;
for t = 0:dt:L-dt
    %areaTrap = areaTrap + pi*dt*(y(t)^2+y(t)*(y(t+dt)-y(t)) + (y(t+dt)-y(t))^2/3);
    areaSimp = areaSimp + pi*((dt/6)*(y(t)^2+4*y(t+dt/2)^2+y(t+dt)^2));
end
points = 0:dt:L;
h = L/length(points);

compSimp = pi*y(points(1).^2) + 2*sum(pi*y(points(3:2:end-2)).^2) + 4*sum(pi*y(points(2:2:end-1)).^2) + pi*y(points(end)).^2;
compSimp = compSimp*h(1)/3;
V(k) = compSimp;
H(k) = areaSimp;
Hs(k) = dt;
end
err = zeros(1, n-2);
logs = [];
for k = 1:n-2
    
    logs(k) = log2((abs(H(k)-H(k+1))/abs(H(k+1)-H(k+2))));
    err(k) = (V(k)-V(k+1)/(V(k+1)-V(k+2)));
end
disp(logs')
figure(2)
clf
whitebg('w')
loglog(Hs(1:n), abs(V-V(end)), 'r')
hold on
loglog(Hs(1:n), abs(H-H(end)), 'g')
loglog(Hs, Hs.^4)
disp(['   Volume 1:  ' num2str(areaTrap, 17)])
disp(['   Volume 2:  ' num2str(areaSimp, 17)])

p = 1;
y = @(x) ((1-(1/pi)*atan(p*(x-1)))./(2-cos(pi*x)));
yv = @(x) pi*((1-(1/pi)*atan(p*(x-1)))./(2-cos(pi*x))).^2;
[ref, check] = quad(yv, 0, L, 10^-14);
[vol, check] = quad(yv, 0, L, 10^-6);

areaSimp = 0;
compSimp = 0;
points = linspace(0,L,check);
disp(length(points))
disp('fdsafdsaf')
disp(check)
dt = L/check;

for t = 0:dt:L-dt
    areaSimp = areaSimp + pi*((dt/6)*(y(t)^2+4*y(t+dt/2)^2+y(t+dt)^2));
end

compSimp = pi*y(points(1).^2) + 2*sum(pi*y(points(3:2:end-2)).^2) + 4*sum(pi*y(points(2:2:end-1)).^2) + pi*y(points(end)).^2;
compSimp = compSimp*h/3

errSimp = abs(areaSimp-ref);
errQuad = abs(vol-ref);

disp([errQuad errSimp errQuad/errSimp])