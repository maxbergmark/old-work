clf
n = 100;x = linspace(1/(n+1),n/(n+1),n)';
A = -(diag(ones(n-1,1),1)+diag(ones(n-1,1),-1)-2*diag(ones(n,1),0));
rho = 1+.75*cos(2*pi*(x'+.7))';
A = diag(1./rho)*A;

%Skapar A och rho som vanligt.

u = A\(-ones(n,1));
plot(linspace(0,1,n+2),[0 u'/n^2 0])

%Kraften beskrivs av -ones(n,1), och vi vill lösa Au = -f. Sedan plottar vi
%u, som kommer att beskriva läget. 