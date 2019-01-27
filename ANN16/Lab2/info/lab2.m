
x = (0:0.1:2*pi)';

f = square(2*x)-sin2x(x);
units = 40;
makerbf;

Phi = calcPhi(x,m,var);

w = Phi\f;
y = Phi*w;
rbfplot1(x,y,f,units);
%%

%plotinit
data=read('cluster');
units=3;
vqinit;
singlewinner=1;
% pause(2);
vqiter

%%

plotinit
data=read('cluster');
units=3;
vqinit;
singlewinner=1;
% pause(2);
emiterb

%%

plotinit

[xtrain ytrain] = readxy('ballist',2,2);
[xtest ytest] = readxy('balltest',2,2);

units = 20;
data = xtrain;
vqinit;
singlewinner = 0;
emiterb;

%%
Phi = calcPhi(xtrain,m,var);
d1 = ytrain(:,1);
d2 = ytrain(:,2);
dtest1 = ytest(:,1);
dtest2 = ytest(:,2);

w1 = Phi\d1;
w2 = Phi\d2;
y1 = Phi*w1;
y2 = Phi*w2;
Phitest = calcPhi(xtest,m,var);
ytest1 = Phitest*w1;
ytest2 = Phitest*w2;
figure();
clf;
xyplot(d1,y1,'train1');
figure();
clf;
xyplot(d2,y2,'train2');
figure();
clf;
xyplot(dtest1,ytest1,'test1');
figure();
clf;
xyplot(dtest2,ytest2,'test2');

