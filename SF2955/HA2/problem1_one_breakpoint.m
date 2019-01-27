clc
clear all
close all
load('coal_mine')
y = coal_mine;

[disasters, years] = hist(floor(y), unique(floor(y)));

y = disasters;

plot(years, disasters, '*');
axis([1840 1980 0 7])

k=10000;
n=length(y);
th1=50*ones(1,k);
th2=ones(1,k);th3=th2;th4=th3;

for i=1:k
    % Draw theta1 with RW-MH
    Li=getL(th1(i),th2(i),th3(i),y);
    th1prop=th1(i)+floor(11*rand)-5;
    Lp=getL(th1prop,th2(i),th3(i),y);
    if (rand<Lp/Li)
        th1(i+1)=th1prop;
    else
        th1(i+1)=th1(i);
    end
    th2(i+1)=gamrnd(1+sum(y(1:th1(i+1))),1/(th1(i+1)+th4(i)));
    th3(i+1)=gamrnd(1+sum(y(th1(i+1)+1:n)),1/(n-th1(i+1)+th4(i)));
    th4(i+1)=gamrnd(3,1/(0.1+th2(i+1)+th3(i+1)));
end
