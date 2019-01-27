A = [2 1; 4 5];
B = [1 1; 2 3];
x = [7; 9];
C = A*B;
D = B*A;
F = A.*B;
G = B.*A;
z = A*x;
p = z'*z;
E = A'*A;

disp(A);
disp(B);
disp(x);
disp(C);
disp(D);
disp(E);
disp(F);
disp(G);
disp(z);

H = [eye(2) A; -B eye(2)];

disp(H);

h3 = H(:,3);
disp(h3);

h2 = H(2,:);
disp(h2);

prod = H(1,:)*H(:,4);
disp(prod);

H(2,2) = 5;
disp(H);

H(2,:) = [0 1 4 5];
disp(H)

J = H(2:end-1,2:end-1);
disp(J);

K = H;
K(end+1,:) = [1 1 1 1];
disp(K);

K = H;
K(end+1,:) = ones(1,length(K));
disp(K);

L = H;
[m,n] = size(L);
L(end+1,:) = zeros(1,m);
L(:,end+1) = ones(n+1,1);
disp(L);

disp(K);
disp(K(:)); %skapar en vektor av alla kolumnvektorer i K
disp(K([4 9 12])); %plockar ut element 4, 9, 12 i vektorn

p = [9 3 1 2 1 1 3 6 7 4];
p2 = p(2:10) - p(1:9);
disp(p2);

p3 = p(2:end) - p(1:end-1);
disp(p3);

q = p;
disp(q.^2);

disp(sum(sum(K.^2)));