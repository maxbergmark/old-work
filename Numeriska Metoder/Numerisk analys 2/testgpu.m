format long
A = rand(2^20, 1);
tic;
for k = 1:100
    B = fft(A);
end
time1 = toc;

C = gpuArray(rand(2^20,1));
tic
for k = 1:100
    D = fft(C);
end
time2 = toc;

disp(time1/time2)

A = rand(2^10, 2^10);
tic;
for k = 1:100
    B = A.*A;
end
time1 = toc;

C = gpuArray(rand(2^10,2^10));
tic;
for k = 1:100
    D = C.*C;
end
time2 = toc;

disp(time1/time2)

A = zeros(2^10, 2^10);
B = ones(2^10, 2^10);
tic;
for k = 1:10^3
    C = A+B;
end
time1 = toc;

C = gpuArray(zeros(2^10,2^10));
D = gpuArray(ones(2^10,2^10));
tic;
for k = 1:10^3
    E = C+D;
end
time2 = toc;

disp(time1/time2)