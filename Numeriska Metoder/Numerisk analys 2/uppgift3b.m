format long
p = 1000;
L = 2.6;
y = @(x) (1-(1/pi)*atan(p*(x-1)))./(2-cos(pi*x));
x = linspace(0, L);

TrapAreas = [];
SimpAreas = [];
refSimp = 0;
dt = .00001;
for t = 0:dt:L-dt
    refSimp = refSimp + pi*((dt/6)*(y(t)^2+4*y(t+dt/2)^2+y(t+dt)^2));
end

for dt = linspace(.01, .001, 50)
areaTrap = 0;
areaSimp = 0;
    
%dt = .1;
for t = 0:dt:L-dt
    areaTrap = areaTrap + pi*((.5*(y(t)+y(t+dt)))^2*dt);
    areaSimp = areaSimp + pi*((dt/6)*(y(t)^2+4*y(t+dt/2)^2+y(t+dt)^2));
end
%disp(' ')
%disp(['   Area:  ' num2str(areaTrap, 17)])
%disp(['   Area2: ' num2str(areaSimp, 17)])

TrapAreas(end+1) = areaTrap;
SimpAreas(end+1) = areaSimp;

end

figure(1)
whitebg('w')
clf
hold on


    
semilogy(abs(TrapAreas-refSimp), '*b')
semilogy(abs(SimpAreas-refSimp), '*r')
set(gca, 'Yscale', 'log')


figure(2)
clf
hold on
loglog(abs(TrapAreas(1:end-1)-refSimp), abs(TrapAreas(2:end)-refSimp), '*b')
loglog(abs(SimpAreas(1:end-1)-refSimp), abs(SimpAreas(2:end)-refSimp), '*r')
set(gca, 'Xscale', 'log')
set(gca, 'Yscale', 'log')
grid on