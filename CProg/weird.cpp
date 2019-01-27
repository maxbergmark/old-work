#include <iostream>
#include <iomanip>

int powerof (int x, int y) {
  int res = 1;

  for (int i = 0; i < y; ++i)
    res = res * x;

  return res;
}

void adding_a_decimal(float step) {
  float sum = step;
  for (int i = 0; i < 1000*1000; ++i) {
    sum += step;
  }
  std::cout << std::setw(4) << sum << std::endl;
}

union ufloat {
  float f;
  unsigned u;
};

void printbin(double n) {
	int k  = * ( long * ) &n;
	while (k) {
		if (k & 1) {
			printf("1");
		} else {
			printf("0");
		}
		k >>= 1;
	}
	printf("\n");
}

int main () {
  int const a = 2;
  int const b = 4;

  int   x = powerof(a, b);
  std::cout << a << "^" << b << " = " << x << ";\n";

  adding_a_decimal(0.1);

  float y;
  std::cout << "What is the value of PI? ";
  std::cin >> y;
  if (y == 0.125f)
    std::cout << y << " is equal to 3.1415!\n";
  else
    std::cout << y << " is not equal to 3.1415!\n";

	printf("%.100f\n", y-3.1415f);




ufloat u1;
u1.f = 0.3f;
printf("%X\n", u1.u);
//printbin(3.1415);
//printbin(y);
//printbin(3.1415-y);
}