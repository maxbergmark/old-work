total = 0;
maxsum = 0;
L = pi;
y = @(x) sin(x);

count = 0;
dif = 10^-8;
prevVal = 0;
newVal = 1;

while count < 100
    prevVal = newVal;
    xval = L*rand;
    yval = y(pi/2)*rand;
    if yval <= y(xval)
        total = total+1;
    end
    maxsum = maxsum+1;

    newVal = total/maxsum*L*y(pi/2);
    if abs(newVal-prevVal) < dif
        count = count+1;
    else
        count = 0;
    end
    disp(total/maxsum*L*y(pi/2))
end
disp(total/maxsum*L*y(pi/2))