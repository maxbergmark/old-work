

p = [.5 .5];

for i = 1:300
    J = [2*(p(1)^2+.5*p(2)^2)*2*p(1)-2*p(1) 2*(p(1)^2+.5*p(2)^2)*p(2)-p(2); 2*p(1) 1];
    F = [(p(1)^2+.5*p(2)^2)^2-p(1)^2+.5*p(2)^2; p(1)^2-.5-p(2)];
    p = p-(J\F)';
    
end
disp(p)