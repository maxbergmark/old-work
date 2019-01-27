n = 50;
A = -(diag(ones(n-1,1),1)+diag(ones(n-1,1),-1)-2*diag(ones(n,1),0));
[V,D] = eig(A);D = diag(D);%D = flipud(diag(D));V = fliplr(V);
D;
for k = linspace(0,8*pi,100)
    plot(linspace(0,1,n+2),[0 sin(k)*V(:,end-40)' 0])
    axis([0 1 -.5 .5])
    drawnow
end