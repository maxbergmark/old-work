lamps = {};
P = {};
Plist = [];
Ulist = [];
Ilist = [];
nums = [7 17 27 37 47 57 62 67 72 77 82];

for i = nums
    lamps{i} = load(['lamp' num2str(i) '.txt']);
    [P{i} I] = max(lamps{i}(:,5));
    Plist = [Plist P{i}];
    Ulist = [Ulist lamps{i}(I,2)];
    Ilist = [Ilist lamps{i}(I,3)];
end
figure(1);
clf;
for i = nums
    plot(lamps{i}(3:5:end,1), lamps{i}(3:5:end,5));
    hold on;
end
xlabel('Voltage [V]');
ylabel('Current [A]');

triangle1 = load('triangle1.txt');
triangle2 = load('triangle2.txt');
triangleAvg = load('triangleaverage.txt');
solardata = load('solardata1.txt');

figure(2);
clf;
plot(triangleAvg(3:5:end,2), triangleAvg(3:5:end,4));
hold on;
xlabel('Voltage [V]');
ylabel('Current [A]');
% plot(triangleAvg(3:5:end,1), triangleAvg(3:5:end,4));

%%
t = lamps{7}(3:5:end,1);
U = lamps{7}(3:5:end,2);
I = lamps{7}(3:5:end,4);
P = U.*I;
R = -Ulist./(2*Ilist);
dlist = nums.^2./(nums+3.97).^2;
figure(6)
clf;
plot(dlist,R);
axis([0 1 0 60]);



%%
figure(3);
clf;
% plot(triangleAvg(1:1:end,2), triangleAvg(1:1:end,3));
% hold on;
% plot(triangleAvg(3:5:end,2), triangleAvg(3:5:end,4), 'r-');
% hold on;

U = triangle1(:,2);
I = triangle1(:,3);
Ichange = (I(1)+I(end))/2;
I = I-Ichange;
plot(U,I);
hold on;
initials = rand(1,2);
options = optimset('Display', 'iter', 'MaxIter', 2000, 'MaxFunEvals', 2000);
axis([-.8 .6 -.05 .01]);
params = fminsearch(@diode, initials, options,U,I);
Isat = params(1);
Vsat = params(2);
e = 1.60217657e-19;
kb = 1.3806488e-23;
T = 293;
n = e*Vsat/(kb*T)



Icalc = Isat*(1-exp(-e*U./(n*kb*T)));
% Icalc = .0030*(30-exp(-U/0.155));
plot(U, Icalc, 'r-')
xlabel('Voltage [V]');
ylabel('Current [A]');
text(-.3, -.01, sprintf(['I=I_{sat}*(1-e^{(e*U/(n*k*T)}))\nn=' num2str(n) '\nI_{sat}=' num2str(Isat) '\nI_{change}=' num2str(Ichange)]));

%%
par2 = fminsearch(@diodedist, initials, options, nums, Plist);

Pcalc = par2(1)./(nums+par2(2)).^2;
Pzero = par2(1)./(0+par2(2)).^2;
Pten = par2(1)./(10).^2;
figure(4);
clf;
plot(nums, Plist);
hold on;
plot(nums, Pcalc, 'r-');
xlabel('Distance [m]');
ylabel('Power [W]');


figure(5);
clf;
plot(Pcalc/Pzero, Pcalc);
xlabel('f(d)/f(0) [1]');
ylabel('Power f(d) [W]');









