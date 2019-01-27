function X = testtrap(dim, dim0)
if nargin == 0
    dim0 = 10;
    dim = 10;
end
if nargin == 1
    dim0 = dim;
end
dx = 5;
L = 1.2;
x = linspace(L/dx/2, L-L/dx/2, dx);
if dim == 1
    X = x;
    if dim == dim0
        X = sum(exp(X))*L^dim0/dx^dim0;
    end
    return
end
X = [];
for k = x
    for m = testtrap(dim-1, dim0)
        X = [X k*m];
    end
end

if dim == dim0
    X = sum(exp(X))*L^dim0/dx^dim0;
end
