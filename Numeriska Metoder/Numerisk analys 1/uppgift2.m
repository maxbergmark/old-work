a = 2;
L1 = 1;
L2 = 1;

L3s = [1 1 2 1];
m1s = [1 1 1 10];
m2s = [1 2 1 1];

guesses = [1 .1 -1; .8 .1 -1.2; 1 .7 -1.2; 1.2 -.3 -.5];
guesses = vpa(guesses);
digits(100)
figure(5)
clf
figure(6)
clf


for i = 1:4
U = guesses(i,:);
Us = U;
L3 = L3s(i);
m1 = m1s(i);
m2 = m2s(i);

figure(i)
clf

for k = 1:20

D = [-L1*sin(U(1)) -L2*sin(U(2)) -L3*sin(U(3)); L1*cos(U(1)) L2*cos(U(2)) L3*cos(U(3)); m2/cos(U(1))^2 -(m1+m2)/cos(U(2))^2 m1/cos(U(3))^2];
f = [L1*cos(U(1))+L2*cos(U(2))+L3*cos(U(3))-a; L1*sin(U(1))+L2*sin(U(2))+L3*sin(U(3)); m2*tan(U(1))-(m1+m2)*tan(U(2))+m1*tan(U(3))];

d = D\(-f);
U = U+d';
Us = [Us; U];

points = [0 0; L1*[cos(U(1)) -sin(U(1))]; [a 0] + L3*[-cos(U(3)) sin(U(3))]; a 0];
hold off
plot(points(:,1), points(:,2), '-', 'Color', [0 0 0], 'LineWidth', 3)
hold on
plot(points(2:3,1), points(2:3,2), 'o', 'Color', [1 0 0], 'LineWidth', 20)
axis equal
drawnow

end

figure(5)
hold on
semilogy(abs(Us(:,1)-Us(end,1)),'Color', [i/4 0 1-i/4])
semilogy(abs(Us(:,2)-Us(end,2)),'Color', [i/4 0 1-i/4])
semilogy(abs(Us(:,3)-Us(end,3)),'Color', [i/4 0 1-i/4])
set(gca,'YScale','log');

figure(6)
hold on
loglog(abs(Us(1:end-1,1)-Us(end,1)), abs(Us(2:end,1)-Us(end,1)),'Color', [i/4 0 1-i/4])
loglog(abs(Us(1:end-1,2)-Us(end,2)), abs(Us(2:end,2)-Us(end,2)),'Color', [i/4 0 1-i/4])
loglog(abs(Us(1:end-1,3)-Us(end,3)), abs(Us(2:end,3)-Us(end,3)),'Color', [i/4 0 1-i/4])
set(gca,'XScale','log');
set(gca,'YScale','log');

disp(guesses(i,:))
disp(U)
disp(180/pi*U)
disp(' ')
end