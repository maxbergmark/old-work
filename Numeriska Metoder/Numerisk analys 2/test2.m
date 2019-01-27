load('eiffel1.mat')
%b = zeros(2*length(xnod), 1);
%j = 100;
%b(j*2-1) = 1;

%b = zeros(2*length(xnod), 1);
%b(2*randi(length(xnod))-1) = 1;
b = .1*(rand(2*length(xnod), 1)-.5);
A = sparse(A);
t = cputime;
for i = 1:1
x = A\b;
end
(cputime-t)/1;

xbel = xnod + x(1:2:end);
ybel = ynod + x(2:2:end);

figure(1)
clf
%trussplot2(xnod, ynod, xbel, bars)
hold on
%pause(1)
%trussplot2(xbel, ybel, x(1:2:end), bars, 'k')
trussanim2(xnod, ynod, x(1:2:end), bars, x)
