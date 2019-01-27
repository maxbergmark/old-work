s = serial('COM5');
set(s,'BaudRate',9600);
fopen(s);
for i = 1:4
    data = fread(s, 13);
    count = str2num([dec2hex(data(1:2:7)) dec2hex(data(2:2:8))])    
end

fclose(s)
delete(s)
clear s