#include <iostream>
#include <stdio.h>

using namespace std;

int main() {
	double lowbound = 0.29;
	double highbound = 1;

	for (int j = 0; j < 1000; j++) {

		double a = (lowbound+highbound)/2.0;
		double b = a;
		int i;
		int MAX = 1<<27;
//		cout << a << "   " << b << "   " << j << endl;
		printf("%.14f  %.14f  %d\n",a,b,j);
		for (i = 0; i < MAX & a < 100; i++) {
			a = a + (b=a*b)/a;
			
		}
		printf("   %.14f  %.14f  %d\n",a,b,i);
		if (i == MAX) {
			lowbound = (lowbound+highbound)/2.0;
		} else {
			highbound = (lowbound+highbound)/2.0;
		}
	}
}