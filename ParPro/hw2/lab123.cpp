
#include "mpi.h"
#include <stdlib.h>
#include <stdio.h>
#include <iostream>
#include <unistd.h>
#include <fstream>
#include <cstdlib>
#include <random>
#include <algorithm>

#define TAG 100

using namespace std;




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

	srandom(myrank+1);
	double *x = (double *) malloc(I*sizeof(double));
	for (int i = 0; i < I; i++) {
		x[i] = ((double) random())/(RAND_MAX);
		//printf("%f\n",x[i]);
	}
	sort(x, x + I);


	double *A = (double *) malloc(I*sizeof(double));
	A = x;


	if (myrank == 0) {
		MPI_Send(&A, I, MPI_DOUBLE, myrank+1, TAG, MPI_COMM_WORLD);
	} else {
		MPI_Recv(&A, I, MPI_DOUBLE, myrank-1, TAG, 
				MPI_COMM_WORLD, MPI_STATUS_IGNORE);
	}


	MPI_Finalize();
	exit(0);

}