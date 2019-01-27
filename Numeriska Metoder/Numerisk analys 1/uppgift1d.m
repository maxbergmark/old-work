ref = newton(.1, 10^-200);
ref = ref(end);

fzer = findzero(.1, 10^-15);
newzer = newton(.1, 10^-99);
quadzer = quadnewton(.1, 10^-99);
efzer = abs(fzer-ref);
enewzer = abs(newzer-ref);
equadzer = abs(quadzer-ref);


figure(1)
clf
semilogy(efzer, '*')
hold on
semilogy(enewzer, '*', 'Color', [1 0 0])
semilogy(equadzer, '*', 'Color', [0 1 0])

disp(newzer)
disp(quadzer)


figure(2)
clf
loglog(efzer(1:end-1), efzer(2:end), 'Color', [0 0 1])
hold on
loglog(efzer(1:end-1), efzer(2:end), '*', 'Color', [0 0 1])
%figure(3)
%clf
loglog(enewzer(1:end-1), enewzer(2:end), 'Color', [1 0 0])
hold on
loglog(enewzer(1:end-1), enewzer(2:end), '*', 'Color', [1 0 0])
%figure(4)
%clf
loglog(equadzer(1:end-1), equadzer(2:end), '*', 'Color', [0 1 0])
hold on
loglog(equadzer(1:end-1), equadzer(2:end), 'Color', [0 1 0])