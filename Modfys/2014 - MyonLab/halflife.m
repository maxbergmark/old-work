s = serial('COM5');
set(s,'BaudRate',9600);
set(s,'Timeout',3000);
fopen(s);
load halfdata
save halfbackup timelist

while 1
    data = fread(s, 2)';
    time = clock;
    count = data*[256 1]';
    timelist(end+1) = count;
    disp([length(timelist) count time(2:5)])
    save halfdata timelist time
end

fclose(s)
delete(s)
clear s