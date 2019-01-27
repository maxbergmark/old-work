kmax = 100;

tic
for k = 1:kmax
A = gpuArray.rand(2^20,10);
prod(A, 2);
end
t = toc;
disp(kmax*2^21/toc)

