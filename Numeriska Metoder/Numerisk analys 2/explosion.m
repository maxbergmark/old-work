matrix = zeros(100);
expl = [50 50];
figure(1)
clf
imagesc(matrix)
drawnow
pause(1)
for k = 1:1000;
    matrix = zeros(100);
    temp = unique(round([k*sin(linspace(0, 2*pi))+expl(1); k*cos(linspace(0, 2*pi))+ expl(2)]'), 'rows')
    for m = 1:length(temp);
        val = temp(m,:);
        matrix(min(abs(val(1))+1, abs(199-val(1))+1), min(abs(val(2))+1, abs(199-val(2))+1)) = 1;
    end
    
    imagesc(matrix)
    drawnow

end
