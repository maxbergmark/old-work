figure(1)
clf
for k = 1:8
z0 = 1;

u0 = 0;
a = .2;
up = -a*u0-z0;
zp = u0;


n = 8;


Z = z0;
Zp = zp;
z = z0;
u = u0;
dt = .4*2^(-k);
t1 = 20;

for t = 0:dt:t1
    up = -a*u-z;
    zp = u;
    u = u+up*dt;
    z = z+zp*dt;
    Z(end+1) = z;
    Zp(end+1) = u;
    
end
subplot(2, 1, 1)
hold on
plot(0:dt:t1, Z(1:end-1), 'Color', [k/n 0 1-k/n])


pl = subplot(2, 1, 2);
cla(pl)
dk = max(1, floor(1/(10*dt)));
for k = 1:dk:length(Z)-dk

    fill([0 Z(k) Z(k+dk)], [0 Zp(k) Zp(k+dk)], [k/(length(Z)-1) 0 1-k/(length(Z))].^.5, 'edgecolor', 'none')
    
    axis equal
    axis([min(Z) max(Z) min(Zp) max(Zp)])
    
    hold on
    drawnow
end
%comet(Z, Zp)
pause(1)
end