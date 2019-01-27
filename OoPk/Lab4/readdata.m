disp('reading data')
% A = csvread('figuredatacenter.txt');
figure(1)
clf
disp('data read')
xmins = [];
ymins = [];
xmaxs = [];
ymaxs = [];
xmeds = [];
ymeds = [];
xstds = [];
ystds = [];
for i = 1:1001
    plot(A(i, 2:2:end), A(i, 3:2:end), '.')
    xmin  = min(A(i, 2:2:end));
    ymin  = min(A(i, 3:2:end));
    xmax  = max(A(i, 2:2:end));
    ymax  = max(A(i, 3:2:end));
    xmed = sum(A(i, 2:2:end))/length(A(i, 2:2:end));
    ymed = sum(A(i, 3:2:end))/length(A(i, 3:2:end));
    xstd = std(A(i, 2:2:end));
    ystd = std(A(i, 3:2:end));
    xmins = [xmins xmin];
    ymins = [ymins ymin];
    xmaxs = [xmaxs xmax];
    ymaxs = [ymaxs ymax];
    xmeds = [xmeds xmed];
    ymeds = [ymeds ymed];
    xstds = [xstds xstd];
    ystds = [ystds ystd];
%     disp([xmin xmax ymin ymax])
    axis([0, 600, 0, 600])
%     hold on
    drawnow
%     pause(.01)
end

figure(2)
clf
hold on
plot(A(:, 1), xmins, 'r')
plot(A(:, 1), ymins, 'b')
plot(A(:, 1), xmaxs, 'g')
plot(A(:, 1), ymaxs, 'm')

figure(3)
clf
hold on
plot(A(:, 1), xmeds, 'r')
plot(A(:, 1), ymeds, 'b')

figure(4)
clf
hold on
plot(A(:, 1), xstds, 'r')
plot(A(:, 1), ystds, 'b')
