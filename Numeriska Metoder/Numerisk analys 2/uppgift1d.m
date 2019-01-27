clf
times = zeros(1, 4);
ltimes = zeros(1, 4);
stimes = zeros(1, 4);
lstimes = zeros(1, 4);
sltimes = zeros(1, 4);
sizes = zeros(1, 4);
for i = 1:4
    load(['eiffel' num2str(i) '.mat'])

    b = .1*(rand(2*length(xnod), 1)-.5);
    
    count = 20/i;
    t1 = cputime;
    for j = 1:count
        x1 = A\b;
    end
    times(i) = (cputime-t1)/count;

    count2 = 100/i;
    t2 = cputime;
    [L,U] = lu(A);
    
    
    for j = 1:count2
        bt = L\b;
        x2 = U\bt;
    end
    ltimes(i) = (cputime-t2)/count2;

    sA = sparse(A);
    count3 = 100/i;
    t3 = cputime;
    for j = 1:count3
        x3 = sA\b;
    end
    stimes(i) = (cputime-t3)/count3;

    [lL, lU] = lu(sparse(A));
    count4 = 500/i;
    t4 = cputime;
    for j = 1:count4
        bt = lL\b;
        x4 = lU\bt;
    end
    lstimes(i) = (cputime-t4)/count4;    

    [sL,sU] = lu(A);
    sL = sparse(sL);
    sU = sparse(sU);
    count5 = 500/i;
    t5 = cputime;
    for j = 1:count5
        bt = sL\b;
        x5 = sU\bt;
    end
    sltimes(i) = (cputime-t5)/count5;
    
    sizes(i) = length(xnod);

end
disp([times;ltimes;stimes;lstimes;sltimes])
%ntimes
disp(sizes)

loglog(sizes, times, 'Color', [0 0 1])
hold on
loglog(sizes, ltimes, 'Color', [1 0 0])
loglog(sizes, stimes, 'Color', [0 1 0])
loglog(sizes, lstimes, 'Color', [1 0 1])
loglog(sizes, sltimes, 'Color', [0 1 1])
