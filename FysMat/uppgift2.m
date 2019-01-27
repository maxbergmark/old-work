clf
n = 50;
A = -(diag(ones(n-1,1),1)+diag(ones(n-1,1),-1)-2*diag(ones(n,1),0)); 
rho = 1+.5*cos(2*pi*(linspace(1/(n+1),n/(n+1),n)+.2))';
axis([0 1 -1 2])
hold on

%Skapar en tridiagonal matris A med 2 l�ngs huvuddiagonalen och -1 l�ngs de
%tv� n�stkommande diagonalera.

A = diag(1./rho)*A*(n+1)^2;

%Multiplicerar varje rad i A med motsvarande densitetsv�rde rho(x).

[V,D] = eig(A);D = diag(D);
[D,i] = sort(D);
Vs = zeros(size(V));
for k = 1:size(D)
    Vs(:,k) = V(:,i(k));
end

%Hittar alla egenvektorer och egenv�rden till A, och sorterar dem relativt
%varandra med avseende p� egenv�rdena.

for a = 1:3
    subplot(3,1,a)
    plot(linspace(0,1,n+2),[0 -Vs(:,a)' 0])
    axis([0 1 -1.5*max(max(Vs(:,1:3))) 1.5*max(max(Vs(:,1:3)))])
end

%Plottar ut de tre l�gsta egenvektorerna.