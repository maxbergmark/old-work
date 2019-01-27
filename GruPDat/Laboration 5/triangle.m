

p1 = [0 4 6.5 0];
p2 = [1.42 6.18 4.75 1.42];

line(p1, p2);
hold on;

l1 = diff(p1);
l2 = diff(p2);

%r1 = 5.320392683;
%r2 = 0.89713094;
%r3 = 1.982955864;

disp(l1);
disp(l2);
s = (l1.^2+l2.^2).^0.5;
disp(s);

A = [1 1 0; 0 1 1; 1 0 1];

r = A\s';
disp(r);


circle(0,1.42,r(1),'y');
hold on;
circle(4,6.18,r(2),'y');
hold on;
circle(6.5,4.75,r(3),'y');
hold on;

line(p1, p2);
hold on;