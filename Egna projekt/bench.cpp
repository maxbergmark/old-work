#include <iostream>
#include <chrono>
#include <unordered_set>

using namespace std;
using std::chrono::nanoseconds;
using std::chrono::duration_cast;
using std::chrono::system_clock;




int main() {
	bool *n = (bool *)malloc(sizeof(bool)*32000000);
	int *k = (int *)malloc(sizeof(int)*1000000);
	int s = 0;
	for (int i = 0; i < 32000000; i++) {
		n[i] = rand() % 2 == 0;
	}
	for (int i = 0; i < 1000000; i++) {
		k[i] = rand();
	}
	const auto start = system_clock::now();
	/*
	for (int i = 0; i < 32000000; i++) {
		s += n[i] ? 1:0;
	}
	*/
//	/*
	for (int i = 0; i < 32000000; i++) {
//		s += k[i/32]&(1<<i%32) > 0 ? 1:0;
		s += (k[i/32]&(1<<i%32))>>i%32;
	}
//	*/
	const auto stop = system_clock::now();
	const auto d_actual = duration_cast<nanoseconds>(stop - start).count();
	const auto d_new = d_actual/1000/1000.0;
	cout << s << endl;
	cout << d_new << endl;
	
}