function chess(m, n, col)
if nargin == 2
    col = [0 0 0;1 1 1];
end
A = [1 0;0 1];
B = repmat(A, m, n);
imagesc(B(1:m, 1:n))
colormap(col)
axis off