tic
count = 0;
format long
while true
    p = gpuArray.rand(2^13);
    count = count+1;
    if mod(count, 10) == 0
        
        disp(count*2^26/toc)
    end
end