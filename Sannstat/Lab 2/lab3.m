clf
load resistorer.mat
subplot(211), normplot(y(1:end))
subplot(212), hist_density(y)