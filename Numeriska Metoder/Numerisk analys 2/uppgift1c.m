load('eiffel1.mat')

findmax = [0 0] ;
findmin = [100 0];

for i = 1:length(xnod)
    b = zeros(2*length(xnod), 1);
    b(i*2-1) = 1;
    x = sparse(A)\b;
    dist = sqrt(x'*x);
    if dist < findmin(1)
        findmin = [dist i];
    end
    if dist > findmax(1)
        findmax = [dist i];
    end
end

findmin
findmax

figure(1)
clf
trussplot(xnod, ynod, bars)
hold on
plot(xnod(findmin(2)), ynod(findmin(2)), 'o', 'Color', 'r')
plot(xnod(findmax(2)), ynod(findmax(2)), 'o', 'Color', 'r')