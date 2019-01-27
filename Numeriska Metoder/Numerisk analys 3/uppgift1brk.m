
%mz'' + az'+kz = 0, z(0) = 1, z'(0) = 0

m = 1;
k = 1;
a = .2;

clf

fb = @(t, Z) [-a/m*Z(1)-k/m*Z(2) Z(1)];

for b = 1:10

h = 4*2^(-b);

z = 1;
v = 0;
t = 0;
Z = [0 1];

zs = z;
vs = v;
ts = t;

for c = 1:20/h
    k1 = fb(t, Z);
    k2 = fb(t+.5*h, Z+.5*h*k1);
    k3 = fb(t+.5*h, Z+.5*h*k2);
    k4 = fb(t+h, Z+h*k3);
    
    Z = Z+1/6*h*(k1+2*k2+2*k3+k4);
    t = t+h;
    
    
    zs = [zs Z(2)];
    vs = [vs Z(1)];
    ts = [ts t];
end

plot(ts, zs, 'Color', [b/14 0 1-b/14])
axis([0 20 -1 1])
hold on
drawnow
end