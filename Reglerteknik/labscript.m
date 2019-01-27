clf
clear all

[J, umax] = lab3robot(931211);

G = tf([38/20], [8 86 40 0]);
lab3robot(G, 931211)

K1 = 5.1;

K2 = 40.1231;
K2 = 24.5;
% K2 = 3.873;
K2 = 16;


% bode(K1*G) %Proportional system
% bode(K1*G/(1+K1*G)) %Closed loop


% Lead-Lag coefficients
wc = .2138*4;
B = .15;    %Beta
C = .05*30.4/40;    %Gamma
TD = 1/(wc*sqrt(B)); %tau-D
TI = 15/wc; %tau_I
% K2 = sqrt(B)*1/abs(evalfr(G, 1i*wc))


F = K2*tf([TD*TI (TD+TI) 1], [B*TD*TI (C*B*TD+TI) C]);
%F = K2*tf([TD^2*TI (TD^2+2*TI*TD) (2*TD+TI) 1], [B^2*TI*TD^2 (B^2*TD^2*C+2*B*TI*TD*C) (2*TD*C*B+TI) C])

Go = F*G;
step = tf([1],[1 0]);
%bode(F/(1+Go)*step)
% bode(K1*G)
% bode(K1*G/(1+K1*G)) 
bode(G)

%%

t = linspace(0, 100, 1000); 
u1 = [linspace(0,0,500) linspace(1,1,500)]; 
u2 = t;

figure(1)
lsim(F/(1+G*F), u1, t)   %U
figure(2)
%lsim(F/(1+G*F), u1, t)
% plot(t,u)
%U is below 110, our umax. 
lsim(G*F/(1+G*F), u2, t) %Y
E = lsim(1/(1+G*F), u2, t);   %E
hold on
plot(t,E)
%%
%------------3.4-----------------------------------------------

S1 = 1/(1+K1*G); %blue
S2 = 1/(1+F*G); %green
x = linspace(.000001,100);
y = zeros(1,100);
figure(3)
clf
bodemag(S1,S2)
hold on
plot(x,y, 'r--')
%%
%------------3.5-----------------------------------------------

% T1=1-S1
T2=1-S2

dG1 = tf([1 10], [40])  %green
dG2 = tf([1 10], [4 .04]) %red
figure(4)
clf
bodemag(1/T2, dG1, dG2)


%%
%-----------extra---------------------------------------------

Lm = 2; Rm = 21; b = 1; KT = 38; Km = .5; n = 1/20;

c = [1 0 0];
B = [0 0 1/Lm]';
A = [0 n 0; 0 -b/J KT/J; 0 -Km/Lm -Rm/Lm];

p1 = -2.5;
p2 = -2+2i;
p3 = -2-2i;
p = [p1 p2 p3];

l1 = p1*p2*p3/.2375;
l3 = (p1+p2+p3-10.75)/.5;
l2 = (p1*p2+p2*p3+p1*p3-.5-.125*l3)/4.75;

L = [67.3684 3.8289 21.5];
L = -1*[l1 l2 l3]
L = place(A,B,p);

[num, den] = ss2tf(A-B*L, B, c, 0, 1);
H = tf(num, den)

lab3robot(G, K1, F, A, B, c, L, L(1), 931211);