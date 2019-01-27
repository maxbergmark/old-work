figure(1)
clf
hold on
axis equal
axis([-3 3 -4 4])




[t, z] = ode45('difeq', [0 100], [0 0]);
%pause(1)
subplot(2, 1, 1);
hold on
axis equal
axis([-5 5 -4 4])
h0 = plot(0,0, '-b');
h1 = plot(0,0,'or', 'LineWidth', 3);
h2 = plot(0,0,'-r', 'LineWidth', 3);
subplot(2, 1, 2)
hold on
axis([0 100 -3 3])
h3 = plot(0,0);
for k = 1:length(z)
    set(0, 'CurrentFigure', 1)
    set(h0, 'XData', z(max(1,k-500):k,1), 'YData', z(max(1,k-500):k,2))
    set(h1, 'XData', z(k,1), 'YData', z(k,2))
    set(h2, 'XData', z(max(1, k-5):k,1), 'YData', z(max(1, k-5):k,2))
    drawnow
    set(h3, 'XData', t(1:k), 'YData', z(1:k,1))
    
    pause(.01)
end
pause(.5)
delete(h1)
delete(h2)