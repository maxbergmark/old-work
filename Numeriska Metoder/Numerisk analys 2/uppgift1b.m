times = zeros(1, 4);
sizes = zeros(1, 4);
for i = 1:4
    load(['eiffel' num2str(i) '.mat'])
    b = .1*(rand(2*length(xnod), 1)-.5);
    t = cputime;
    count = 20/i;
    for j = 1:count
        x = A\b;
    end
    times(i) = (cputime-t)/count;
    sizes(i) = length(xnod);

end
times
sizes

loglog(sizes, times)
hold on
loglog(sizes, sizes.^3)

dt = diff(log(times));
ds = diff(log(sizes));
mean = sqrt((dt./ds)*(dt./ds)'/length(dt))
axis equal
