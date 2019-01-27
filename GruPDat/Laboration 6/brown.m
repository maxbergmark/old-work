function brown(n, size)
clf
col = [1 0 0;
       0 1 0;
       0 0 1;
       1 1 0;
       1 0 1;
       0 1 1;
       1 0.5 0;
       1 0 0.5;
       0.5 1 0;
       0.5 0 1;
       0 0.5 1;
       0 1 0.5;];

x = zeros(n, 1);
y = zeros(n, 1);
for j = 1:1000
   x = [x x(:, end)+randn(n, 1)];
   y = [y y(:, end)+randn(n, 1)];

   for i = 1:n
      plot(x(i,:)', y(i,:)', 'Color', col(mod(i, length(col)),:));
      hold on 
   end
   hold off
   
   axis([-size size -size size])
   pause(0.001)
    
end