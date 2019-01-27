found = [];
format long
for i = -5:.5:10
    disp(['Checking' num2str(i)])
    temp = round(findzero(i)*10000)/10000;
    temp = temp(end);
    if temp ~= inf
    if any(temp==found) == 0
        found = [found temp];
    end
    end
end
disp(found)