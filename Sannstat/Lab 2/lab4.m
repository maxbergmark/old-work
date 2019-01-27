clf
load moore.mat
plot(xtime,log10(y))
b = regress(log10(y),xtime-xtime(1))
hold on
plot(xtime, b*xtime-510)

m = sum(log10(y)-b*xtime)/length(xtime)
plot(xtime, b*xtime+m,'r')
xnew = 2011:2020;
plot(xnew, b*xnew+m,'g')
10^(b*xnew(end)+m)