load('eiffel1.mat')
ti = cputime;
mineig = min(eig(A));
A = sparse(A);
x = ones(length(A), 1);
q0 = 0;
count = 0;
dif = 1;
format long
while abs(dif) > 10^-10
    t = x/norm(x);
    x = A\t;
    q = x'*x;
    dif = q-q0;
    q0 = q;
    count = count+1;
end
disp(cputime-ti)
disp(x'*A*x/(x'*x))
disp(mineig)
disp(abs(x'*A*x/(x'*x) - mineig)) 
disp(count)