#include <iostream>
#include <chrono>
#include <unordered_set>

using namespace std;
using std::chrono::nanoseconds;
using std::chrono::duration_cast;
using std::chrono::system_clock;

bool check(int n) {
    int digits = 0; int count = 0; int tmp;

    for (; n > 0; n /= 10, ++count)
    {
        if ((tmp = digits) == (digits |= 1 << (n - ((n / 10) * 10) - 1)))
            return false;
    }

    return digits == (1 << count) - 1;
}

bool check2(int n) {
	int t = 0; int tc;
	for (;n > 0;n/=10) {
		if ((tc = t) == (t |= 1 << (n%10 - 1)) ) {
			return false;
		}
	}
	return t == 511;
}

bool check3(int n) {
    int digits = 0; int tmp;int count=0;

    for (; n > 0; n /= 10,++count)
    {
        if ((tmp = digits) == (digits |= 1 << (n%10 - 1)))
            return false;
    }

    return digits == (1<<count)-1;
}

int main() {
	const auto start = system_clock::now();
	int s = 0;

	for (int asdf = 0; asdf < 1000; asdf++) {

	unordered_set<int> myset;
	for (int a = 4; a < 49; a++) {
		for (int b = 157; b < 7860/a; b++) {
			
			int c = a*b;
			int f1 = 1000;

			if (c >= 10000) {
				f1 = 100000;
			} else if (c >= 1000) {
				f1 = 10000;
			}
			int f2 = (b >= 1000 ? 10000*f1 : 1000*f1);
			int n = f2*a+f1*b+c;
			if (n >= 123456789 && check3(n)) {
				if (myset.find(c) == myset.end()) {
					s += c;
					myset.insert(c);
				}
			}
		}
	}
	}
	const auto stop = system_clock::now();
	const auto d_actual = duration_cast<nanoseconds>(stop - start).count();
	const auto d_new = d_actual/1000000;
	cout << s/1000 << endl;
	cout << d_new << endl;
	
}