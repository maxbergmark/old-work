p = 2^24; kmax = 500; num = 10; L = 1.2; I = 0; %ref = 6.231467927023725;
tic    %förbered er på fantastisk optimering
for k = 1:kmax
    I = ((k-1)*I + ((L^num)/p)*sum(exp(L^num*prod(gpuArray.rand(p, num), 2))))/k;
end; disp([I kmax*p/toc])