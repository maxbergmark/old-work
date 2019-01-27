function spherer(n, C, r)
X = (sin(0:pi/n:pi)'*cos(0:2*pi/n:2*pi)).*r+C(1);
Y = (sin(0:pi/n:pi)'*sin(0:2*pi/n:2*pi)).*r+C(2);
Z = ((ones(n+1, 1)*cos(0:pi/n:pi))').*r+C(3);
surf(X, Y, Z, 'EdgeColor', 'none')
colormap([1 0 0])
axis([C(1)-r C(1)+r C(2)-r C(2)+r C(3)-r C(3)+r])
