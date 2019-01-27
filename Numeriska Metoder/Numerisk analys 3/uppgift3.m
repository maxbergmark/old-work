load eiffel1

a = 517;
b = 517;

bel = zeros(2*length(xnod),1);
bel(a) = .1;
%bel(b) = -2;

A = sparse(A);
x = A\bel;
xny = xnod + x(1:2:end);
yny = ynod + x(2:2:end);
figure(1)
clf
trussplot2(xny, yny, bars)
drawnow

dt = .001;
B = A.*dt;
for k = 1:100
    test = x;
    x = x+B*x;
    disp((x-test)'*(x-test))
    xny = xnod + x(1:2:end);
    yny = xnod + x(2:2:end);
    trussplot2(xny, yny, bars)
    drawnow
end
