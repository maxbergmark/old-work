function val = difeq(t,z)
m = 1;
k = 1;
a = .008;

val = zeros(2, 1);

val(1) = z(2);
val(2) = -a/m*z(2)-k/m*z(1)-1/m*z(1).^3+6/m*cos(t/2);


