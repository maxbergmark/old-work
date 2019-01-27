
#include "mpi.h"
#include <stdlib.h>
#include <stdio.h>
#include <iostream>
#include <unistd.h>
#include <fstream>
#include <cstdlib>
#include <random>
#include <algorithm>
#include <vector>

#define TAG 100

using namespace std;

double (&m(double (&arr)[2]))[2]{     // declare fn1 as returning refernce to array
   return arr;
}

vector<double> f(vector<double> a) {
	return a;
}

void a(double A[]) {
	A[0] = 5;
}

int main(int argc, char **argv) {

	double A[] = {1.0,2.0,3.0};

	for (int i = 0; i < 3; i++) {
		cout << A[i] << endl;
	}

	a(A);

	for (int i = 0; i < 3; i++) {
		cout << A[i] << endl;
	}

}