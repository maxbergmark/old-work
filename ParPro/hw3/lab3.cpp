
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
//	usleep(100000 - 10000*I);
//	printf("\nMerging: %d %d \n", I, I2);
	for (int i = 0; i < max(I,I2); i++) {
//		printf("A: %f, B: %f\n", A[i], B[i]);
	}
	double *res = (double *) malloc(2*I*sizeof(double));
	unsigned left_it = 0, right_it = 0;
	while (left_it < I && right_it < I2) {
		if (A[left_it] < B[right_it]) {
			res[left_it + right_it] = A[left_it++];
		} else {
			res[left_it + right_it] = B[right_it++];
		}
	}
/*
	for (int i = 0; i < I+I2; i++) {
		printf("asdf12: %f\n", res[i]);
	}
*/
	while (left_it < I) {
		res[left_it + right_it] = A[left_it++];
	}
	while (right_it < I2) {
		res[left_it + right_it] = B[right_it++];
	}
/*
	for (int i = 0; i < I+I2; i++) {
		printf("asdf: %f\n", res[i]);
	}
*/

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

	const auto start = system_clock::now();


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


	//usleep(10000*myrank);
	//printf("Process: %d\n", myrank);

	srandom(myrank+1);

	bool evenphase = true;
	bool evenprocess = myrank % 2 == 0 ? true : false;

//	int extra = myrank+1 == N%P ? 1 : 0;

	double *A = (double *) malloc(I*sizeof(double));
	double *B = (double *) malloc(I*sizeof(double));

	for (int i = 0; i < I; i++) {
		double temp = ((double) random())/(RAND_MAX);
		if (evenprocess) {
			B[i] = temp;
		} else {
			A[i] = temp;
		}
		//printf("%f\n",x[i]);
	}
//	const auto start1 = system_clock::now();


	sort(A, A + I);
	sort(B, B + I);
//	const auto start2 = system_clock::now();
//	const auto d_actual1 = duration_cast<nanoseconds>(start2 - start1).count();
//	printf("Time: %ld\n", d_actual1);


	//cout << endl;
	for (int i = 0; i < I; i++) {
		//printf("%f\n", A[i]);
	}
	//cout << endl;
	for (int i = 0; i < I; i++) {
		//printf("%f\n", B[i]);
	}

	long sendTime = 0;


	for (int i = 0; i < P; i++) {

/*
		usleep(1000000 + 1000*myrank);
		printf("\nProcess: %d, Total: %d, Phase: %d\n", myrank, P, evenphase ? 1:0);

		for (int i = 0; i < I; i++) {
			printf("%f   %f\n", A[i], B[i]);
		}
*/
		if (evenphase & evenprocess & myrank < P-1) {
//			printf("Process: %d, EE\n", myrank);
			int extra = myrank+1 == N%P ? 1 : 0;
			const auto startsend = system_clock::now();

			MPI_Recv(&A[0], I-extra, MPI_DOUBLE, myrank+1, TAG, 
				MPI_COMM_WORLD, MPI_STATUS_IGNORE);
			MPI_Send(&B[0], I, MPI_DOUBLE, myrank+1, TAG, MPI_COMM_WORLD);
			const auto stopsend = system_clock::now();
			const auto d_send = duration_cast<nanoseconds>(stopsend - startsend).count();
			sendTime += d_send;

//			printf("DataEE (B) sent between %d and %d\n", myrank, myrank+1);

			merge(A, B, evenprocess, evenphase, I-extra, I);
		}

		if (~evenphase & evenprocess & myrank > 0) {
//			printf("Process: %d, OE\n", myrank);
			int extra = myrank == N%P ? 1 : 0;
			const auto startsend = system_clock::now();
			MPI_Recv(&A[0], I+extra, MPI_DOUBLE, myrank-1, TAG, 
				MPI_COMM_WORLD, MPI_STATUS_IGNORE);
			MPI_Send(&B[0], I, MPI_DOUBLE, myrank-1, TAG, MPI_COMM_WORLD);
			const auto stopsend = system_clock::now();
			const auto d_send = duration_cast<nanoseconds>(stopsend - startsend).count();
			sendTime += d_send;
//			printf("DataOE (B) sent between %d and %d\n", myrank, myrank-1);
			merge(A, B, evenprocess, evenphase, I+extra, I);
		}

		if (evenphase & ~evenprocess & myrank > 0) {
//			printf("Process: %d, EO\n", myrank);
			int extra = myrank == N%P ? 1 : 0;
			const auto startsend = system_clock::now();
			MPI_Send(&A[0], I, MPI_DOUBLE, myrank-1, TAG, MPI_COMM_WORLD);
			MPI_Recv(&B[0], I+extra, MPI_DOUBLE, myrank-1, TAG, 
				MPI_COMM_WORLD, MPI_STATUS_IGNORE);
			const auto stopsend = system_clock::now();
			const auto d_send = duration_cast<nanoseconds>(stopsend - startsend).count();
			sendTime += d_send;
//			printf("DataEE (A) sent between %d and %d\n", myrank, myrank-1);
			merge(A, B, evenprocess, evenphase, I, I+extra);
		}

		if (~evenphase & ~evenprocess & myrank < P-1) {
//			printf("Process: %d, OO\n", myrank);
			int extra = myrank+1 == N%P ? 1 : 0;
			const auto startsend = system_clock::now();
			MPI_Send(&A[0], I, MPI_DOUBLE, myrank+1, TAG, MPI_COMM_WORLD);
			MPI_Recv(&B[0], I-extra, MPI_DOUBLE, myrank+1, TAG, 
				MPI_COMM_WORLD, MPI_STATUS_IGNORE);
			const auto stopsend = system_clock::now();
			const auto d_send = duration_cast<nanoseconds>(stopsend - startsend).count();
			sendTime += d_send;
//			printf("DataOO (A) sent between %d and %d\n", myrank, myrank+1);
			merge(A, B, evenprocess, evenphase, I, I-extra);
		}

		evenphase = evenphase ? false : true;


	}
/*
	usleep(100000*myrank);
	printf("My rank: %d, Elements: %d\n", myrank, I);
	for (int i = 0; i < I; i++) {
		if (evenprocess) {
			printf("B: %f\n", B[i]);
		} else {
			printf("A: %f\n", A[i]);			
		}
	}
*/
	MPI_Finalize();
	const auto stop = system_clock::now();
	const auto d_actual = duration_cast<nanoseconds>(stop - start).count();
	
	usleep(100000*myrank);
	printf("\nProcess: %d, Time: %ld\n", myrank, d_actual);
	printf("SendTime: %ld\n", sendTime);
	printf("%f\n", (double) sendTime/(double)d_actual);
	
	//printf("asdf");
	exit(0);

}