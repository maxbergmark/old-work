gputimeit
A = gpuArray.randi(1024, 10240000,1);
mod(A,2);
toc