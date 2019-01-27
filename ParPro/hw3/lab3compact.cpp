
#include "mpi.h"
#include <stdlib.h>
#include <stdio.h>
#include <iostream>
#include <unistd.h>
#include <fstream>
#include <cstdlib>
#include <random>
#include <algorithm>
#include <chrono>

#define TAG 100

using namespace std;
using std::chrono::nanoseconds;
using std::chrono::duration_cast;
using std::chrono::system_clock;

void merge(double A[], double B[], bool even, bool phase, int I, int I2) {
	double *res = (double *) malloc(2*I*sizeof(double));
	unsigned left_it = 0, right_it = 0;
	while (left_it < I && right_it < I2) {
		if (A[left_it] < B[right_it]) {
			res[left_it + right_it] = A[left_it++];
		} else {
			res[left_it + right_it] = B[right_it++];
		}
	}

	while (left_it < I) {
		res[left_it + right_it] = A[left_it++];
	}
	while (right_it < I2) {
		res[left_it + right_it] = B[right_it++];
	}

	if (even) {
		for (int i = 0; i < I2; i++) {

			if (phase) {
				B[i] = res[i];
			} else {
				B[i] = res[i+I];
			}
		}
	} else {
		for (int i = 0; i < I; i++) {

			if (phase) {
				A[i] = res[i+I2];
			} else {
				A[i] = res[i];
			}
		}
	}

}



int main(int argc, char **argv) {

	MPI_Init(&argc, &argv);
	int myrank,P;
	MPI_Comm_size(MPI_COMM_WORLD, &P);
	MPI_Comm_rank(MPI_COMM_WORLD, &myrank);

	if (argc < 2) {
		exit(EXIT_FAILURE);
	}
	int N = atoi(argv[1]);

	int I = N/P;
	if (myrank < N%P) {
		I++;
	}

	srandom(myrank+1);

	bool evenphase = true;
	bool evenprocess = myrank % 2 == 0 ? true : false;

	double *A = (double *) malloc(I*sizeof(double));
	double *B = (double *) malloc(I*sizeof(double));

	for (int i = 0; i < I; i++) {
		double temp = ((double) random())/(RAND_MAX);
		if (evenprocess) {
			B[i] = temp;
		} else {
			A[i] = temp;
		}
	}

	sort(A, A + I);
	sort(B, B + I);

	for (int i = 0; i < P; i++) {

		if (evenphase & evenprocess & myrank < P-1) {
			int extra = myrank+1 == N%P ? 1 : 0;
			MPI_Recv(&A[0], I-extra, MPI_DOUBLE, myrank+1, TAG, 
				MPI_COMM_WORLD, MPI_STATUS_IGNORE);
			MPI_Send(&B[0], I, MPI_DOUBLE, myrank+1, TAG, MPI_COMM_WORLD);
			merge(A, B, evenprocess, evenphase, I-extra, I);
		}

		if (~evenphase & evenprocess & myrank > 0) {
			int extra = myrank == N%P ? 1 : 0;
			MPI_Recv(&A[0], I+extra, MPI_DOUBLE, myrank-1, TAG, 
				MPI_COMM_WORLD, MPI_STATUS_IGNORE);
			MPI_Send(&B[0], I, MPI_DOUBLE, myrank-1, TAG, MPI_COMM_WORLD);
			merge(A, B, evenprocess, evenphase, I+extra, I);
		}

		if (evenphase & ~evenprocess & myrank > 0) {
			int extra = myrank == N%P ? 1 : 0;
			MPI_Send(&A[0], I, MPI_DOUBLE, myrank-1, TAG, MPI_COMM_WORLD);
			MPI_Recv(&B[0], I+extra, MPI_DOUBLE, myrank-1, TAG, 
				MPI_COMM_WORLD, MPI_STATUS_IGNORE);
			merge(A, B, evenprocess, evenphase, I, I+extra);
		}

		if (~evenphase & ~evenprocess & myrank < P-1) {
			int extra = myrank+1 == N%P ? 1 : 0;
			MPI_Send(&A[0], I, MPI_DOUBLE, myrank+1, TAG, MPI_COMM_WORLD);
			MPI_Recv(&B[0], I-extra, MPI_DOUBLE, myrank+1, TAG, 
				MPI_COMM_WORLD, MPI_STATUS_IGNORE);
			merge(A, B, evenprocess, evenphase, I, I-extra);
		}

		evenphase = evenphase ? false : true;
	}

	MPI_Finalize();
	exit(0);
}