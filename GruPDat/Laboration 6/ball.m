size = 120;
[x, y, z] = sphere(size);
k = 1:size;
c = mod(x*y + y*z + z*x, 30);
surf(x, y, z, c)