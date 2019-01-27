mux=0;muy=0;sigmax=1;sigmay=1;rho=.7;
z1 = plot_mvnpdf(mux,muy,sigmax,sigmay,.7);

axis vis3d
%surf(z1-z2, 'EdgeColor', 'none')