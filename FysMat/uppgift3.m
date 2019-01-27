clf
n = 50;x = linspace(1/(n+1),n/(n+1),n)';
A = -(diag(ones(n-1,1),1)+diag(ones(n-1,1),-1)-2*diag(ones(n,1),0));
rho = 1+.5*cos(2*pi*(x'+.2))';
A = diag(1./rho)*A*(n+1)^2;
%A = A.*(n+1)^2;

%Skapar A och rho på samma sätt som i föregående uppgift.

u0 = 1;
u = u0*x.^2.*(1-x);

%skapar u-vektorn enligt uppgiften.

[V,D] = eig(A);D = diag(D);
[D,i] = sort(D);
Vs = zeros(size(V));
for k = 1:size(D)
    Vs(:,k) = V(:,i(k));
end

%Hittar och sorterar egenvärden och egenvektorer, som i föregående uppgift.
min(D)
1/D(1)
lambda = 1/D(1);dt = lambda/5;

%Skapar lambda och dt i enighet med teori och uppgift.

c = Vs\u;
S = Vs*diag(c);

%Utvecklar u-vektorn i enhetsvektorerna, sparar koefficienterna i vektorn
%c, och skapar sedan den viktade matrisen S av de sorterade egenvektorerna.

for k = 0:dt:lambda
    plot(linspace(0,1,n+2), [0 (S*cos(k*sqrt(D)))' 0], 'Color', [k/lambda*.99 0 abs(1-k/lambda)]);
    
    axis([0 1 0 .2])
    drawnow
    hold on
end

%Animerar svängningen i fem steg, varierar färgen från blått till rött.
%Ritar ut alla lägena i samma figur.