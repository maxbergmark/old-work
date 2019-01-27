load birth.dat

x = birth(birth(:,20) < 3,3);
y = birth(birth(:,20) == 3,3);

mean(x)-mean(y);

M = 1e3;
thetaboot = bootstrp(M,@mean,x) - bootstrp(M,@mean,y);
quantile(thetaboot, [.025 .975])
hist_density(thetaboot)