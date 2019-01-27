
num = 2;

L = 1.2;
dx = 10;


matrix = ones(ones(1, num)*L*dx)
x = linspace(L/(2*dx), 1.2-L/(2*dx), L*dx);
%for l = 1:L*dx
    for k = 1:num
        matrix(:,:) = matrix(:,:)*diag(x)
    end
%end

for k = 1:num
    matrix = matrix'*diag(x);
    %matrix = ones(L*dx, 1)*matrix';
end
matrix = exp(matrix);
res = sum(sum(matrix))/dx^2
