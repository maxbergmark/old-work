c = -.75+.1i;
d = rand/8000+rand/8000*1i;
z = c;
ze = c+d;
dif = [];
siz = [];
errs = [];
errs2 = [2*c*d+d^2];

for j = 1:4
    z = z^2+c;
    ze = ze^2+c+d;
    dif = [dif abs(ze-z)];
    siz = [siz; abs(z) abs(ze)];
    err = 2^j*z^(j-1)*c*d + 4^(j-1)*z^(max(0,2*j-4))*c^(min(2,2*j-2))*d^2;
    
    errs = [errs abs(err)];
end

figure(1)
clf
semilogy(dif)
hold on
semilogy(0*dif+1e-3)
semilogy(siz(:,1))
semilogy(errs)
legend('show')
