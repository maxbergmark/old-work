n = 100;
matrix = gpuArray.zeros(n);
cmap = [];
for k = 1:128
    cmap = [cmap; k/128 0 k/128-k^2/128^2];
end
colormap(cmap)
%colormap(lines)
for k = 1:200
    matrix(end,:) = rand(1,n)+3*sin(1:n)+3;
    matrix(1:end-1, 2:end-1) = (matrix(2:end, 1:end-2) + 10*matrix(2:end, 2:end-1) + matrix(2:end, 3:end-0))/12;
    
    imagesc(matrix)
    drawnow
    
end
