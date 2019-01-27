total = 0;
maxsum = 0;
L = 1.2;
y = @(X) exp(prod(X));

count = 0;
dif = 10^-10;
prevVal = 0;
newVal = 1;
maxVal = exp(1.2^10);
counter = 0;
format long

while count < 100
    prevVal = newVal;
    points = L*rand(1, 2);
    yval = maxVal*rand;
    if yval <= y(points)
        total = total+1;
    end
    maxsum = maxsum+1;

    newVal = total/maxsum;
    if abs(newVal-prevVal) < dif
        count = count+1;
    else
        count = 0;
    end
    counter = counter+1;
    if mod(counter, 10000) == 0
        disp([total/maxsum*L^2*maxVal maxsum])
    end
end
disp(total/maxsum*L^10*maxVal)