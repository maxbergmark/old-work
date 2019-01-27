z = 1;
Z = z;
u = 0;
a = 10;
up = -a*u-z;
zp = u;

dt = .1;

for t = 1:dt:20
    up = -a*u-z;
    zp = u;
    u = u+up*dt;
    z = z+zp*dt;
    Z(end+1) = z;
    
end
figure(1)
clf
plot(linspace(0, 20, length(Z)), Z)
   