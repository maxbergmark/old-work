n=4;
n=5;
n=10;
n=50;
%n=100;
L=1;
p=1;
S=1;
A=zeros(n,n)
for j=1:n
k=j;
if not (k-1==0)
A(j,k-1)=1;
end
A(j,k)=-2;
if not ((k+1)==(n+1))
A(j,k+1)=1;
end
end
A
c=S/p;
dx=1/(n+1);
u0=ones(1,n)';
cAb=(1/(dx*dx))*A;
u0=ones(1,n)';
[V,D]=eig(cAb); %V matris med egenvektorer D egenvärden
sev=sort(eig(cAb));
lagstaev=sev(3:1);
lagstaev=sev(size(sev)-2:size(sev))
lagstaevektor=V(:,1:3);
[Y,I]=sort(sev,1); %I är vektor med indexen för egenvärdena i ev
lva1=lagstaev(1);
lva2=lagstaev(2);
lva3=lagstaev(3);
lve1=V(:,I(1));
lve2=V(:,I(2));
lve3=V(:,I(3));
lfr1=(abs(lva1))^(1/2);
lfr2=(abs(lva2))^(1/2);
lfr3=(abs(lva3))^(1/2);
ck1=u0'*lve1;
ck2=u0'*lve2;
ck3=u0'*lve3;
x=1:n;
u1=ck1.*(cos(lfr1.*x)+sin(lfr1.*x)).*lve1';
u2=ck2.*(cos(lfr2.*x)+sin(lfr2.*x)).*lve2';
u3=ck3.*(cos(lfr3.*x)+sin(lfr3.*x)).*lve3';
pause(1)
hold on
plot(x,u1,'b')
plot(x,u2,'r')
plot(x,u3,'g')
%c)
x=1:n;
P=[];
for j=1:n;
xj=j*dx;
p(j)=1+0.45*cos(2*pi*(xj+0.7));
end
for j=1:n;
P(:,j)=1./p;
end
c=S./p;
cAc=(1/(dx*dx))*(A.*P)
[V,D]=eig(cAc); %V matris med egenvektorer D egenvärden
ev=eig(cAc);
sev=sort(eig(cAc));
lagstaev=sev(size(sev)-2:size(sev))
[Y,I]=sort(ev,1); %I är vektor med indexen för egenvärdena i ev
lva1=lagstaev(3);
lva2=lagstaev(2);
lva3=lagstaev(1);
lve1=V(:,I(1));
lve2=V(:,I(2));
lve3=V(:,I(3));
lfr1=(abs(lva1))^(1/2);
lfr2=(abs(lva2))^(1/2);
lfr3=(abs(lva3))^(1/2);
ck1=u0'*lve1;
ck2=u0'*lve2;
ck3=u0'*lve3;
x=1:n;
u1=ck1*(cos(lfr1.*x)+sin(lfr1.*x)).*lve1';
u2=ck2*(cos(lfr2.*x)+sin(lfr2.*x)).*lve2';
u3=ck3*(cos(lfr3.*x)+sin(lfr3.*x)).*lve3';
pause(1)
figure
hold on
plot(x,u1,'b')
plot(x,u2,'r')
plot(x,u3,'g')
%d)
figure
v=[];
for j=1:n
v(j)=(j/(n+1))^2*(1-j/(n+1))
end
v
V=[]
for j=1:n
V(:,j)=v';
end
V
cAc
A2=cAc.*V
sev=eig(A2);
l1=min(abs(sev)); %minsta egenvärdet till matrisen
t1=0;
t2=1/l1;
dt=(t2-t1)/5;
ck=u0'*lve1
lfr1=(lva1)^(1/2);
t=0;
for k=1:5
t=t+dt;
u=ck*exp(lfr1.*t).*lve1;
u./u0
plot(u,u./u0)
end
plot(u,u./u0,'r')
%e)
ustat=(1./p).*(x.^2/2+0.9*cos(2*pi+0.7.*x)/0.7^2+0.9*(x.^2/4+(-cos(2*(2*pi+0.7*x)))/(4*0.7.^2))) + x
plot(x, ustat)