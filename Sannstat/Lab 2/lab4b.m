figure(1)
clf
load poly.mat
subplot(311)
plot(x1,y1)
subplot(312)
plot(x2,y2)
subplot(313)
plot(x3,y3)

n1 = length(x1);
X1 = [ones(n1,1) x1 x1.^2 x1.^3];
B1 = regress(y1,X1)
x = linspace(min(x1),max(x1),n1);
subplot(311)
hold on
plot(x, X1*B1,'r')

n2 = length(x2);
X2 = [ones(n2,1) x2 x2.^2 x2.^3 x2.^4];
B2 = regress(y2,X2)
x = linspace(min(x2),max(x2),n2);
subplot(312)
hold on
plot(x, X2*B2,'r')

n3 = length(x3);
X3 = [ones(n3,1) x3 x3.^2 x3.^3 x3.^4 x3.^5];
B3 = regress(y3,X3)
x = linspace(min(x3),max(x3),n3);
subplot(313)
hold on
plot(x, X3*B3,'r')

figure(2)
clf

subplot(311)
normplot(X1*B1-y1)

subplot(312)
normplot(X2*B2-y2)

subplot(313)
normplot(X3*B3-y3)
