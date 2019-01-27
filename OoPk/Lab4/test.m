a = vpa(0);
digits(10000)
for i = 1:1439
    a = a+nchoosek(1440, i)*stirling(11000, i);
    i
end
