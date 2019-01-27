/* Reaction-diffusion equation in 1D
* Solution by Jacobi iteration
* simple MPI implementation
*
* C Michael Hanke 2006-12-12
*/


#define MIN(a,b) ((a) < (b) ? (a) : (b))


/* Use MPI */
#include "mpi.h"
#include <stdlib.h>
#include <stdio.h>
#include <iostream>
#include <unistd.h>
#include <fstream>
#include <chrono>

using namespace std;
using std::chrono::nanoseconds;
using std::chrono::duration_cast;
using std::chrono::system_clock;
/* define problem to be solved */
#define N 1000  /* number of inner grid points */
#define SMX 1000000 /* number of iterations */
#define TAG 100
#define TAG2 200
/* implement coefficient functions */



int main(int argc, char *argv[]) {
/* local variable */

/* Initialize MPI */
	const auto start = system_clock::now();
	MPI_Init(&argc, &argv);
	int p,P;
//	p = 0;
//	P = 2;
	MPI_Comm_size(MPI_COMM_WORLD, &P);
	MPI_Comm_rank(MPI_COMM_WORLD, &p);
	
	bool color = (p % 2 == 0);

	int local_index;
	int I = (N+P-p-1)/P;
	int R = N%P;
	int L = N/P;
	double h = 1/(double)(N+1);

	long int send_time = 0;
	//int n = p*L+MIN(p,R)+local_index;

	if (N < P) {
		fprintf(stdout, "Too few discretization points...\n");
		exit(1);
	}
	/* Compute local indices for data distribution */

	/* arrays */
	double *unew = (double *) malloc(I*sizeof(double));
	double *u = (double *) calloc(I+2, sizeof(double));
	double *ff = (double *) malloc(I*sizeof(double));
	double *rr = (double *) malloc(I*sizeof(double));

	for (int i = 0; i < I; i++) {
		int n = p*L+MIN(p,R)+i;
		double x = n/(double)(N+1.0);
			
		rr[i] = 1-x;
		ff[i] = 2*x*x*x*x-5*x*x*x+4*x*x+11*x-6; 
	}

	/* Jacobi iteration */
	for (int step = 0; step < SMX; step++) {
	/* RB communication of overlap */

	/* local iteration step */
		for (int i = 0; i < I; i++) {
			//cout << "X: " << x << endl;

			unew[i] = (u[i]+u[i+2]-h*h*ff[i])/(2.0-h*h*rr[i]);
			//cout << unew[i] << endl;
		}
		for (int i = 0; i < I; i++) {
			u[i+1] = unew[i];
		}
		const auto start_send = system_clock::now();
		
		if (color) {
			if (p < P-1) {
				MPI_Send(&u[I], 1, MPI_DOUBLE, p+1, TAG, MPI_COMM_WORLD);
				MPI_Recv(&u[I+1], 1, MPI_DOUBLE, p+1, TAG, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
			}
			if (p > 0) {
				MPI_Send(&u[1], 1, MPI_DOUBLE, p-1, TAG, MPI_COMM_WORLD);
				MPI_Recv(&u[0], 1, MPI_DOUBLE, p-1, TAG, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
			}
		} else {
			if (p > 0) {
				MPI_Recv(&u[0], 1, MPI_DOUBLE, p-1, TAG, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
				MPI_Send(&u[1], 1, MPI_DOUBLE, p-1, TAG, MPI_COMM_WORLD);
			}
			if (p < P-1) {
				MPI_Recv(&u[I+1], 1, MPI_DOUBLE, p+1, TAG, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
				MPI_Send(&u[I], 1, MPI_DOUBLE, p+1, TAG, MPI_COMM_WORLD);
			}
		}
		
		const auto stop_send = system_clock::now();
		const auto send_actual = duration_cast<nanoseconds>(stop_send - start_send).count();
		send_time += send_actual;
	}
	const auto stop = system_clock::now();
	const auto d_actual = duration_cast<nanoseconds>(stop - start).count();

	printf("%d: %ld %ld %f \n", p, d_actual/1000000, send_time/1000000,send_time/(double)d_actual);

	ofstream outfile;
	int temp;

	if (p == 0) {
		outfile.open("data3.txt");
		for (int i = 0; i < I+2; i++) {
			outfile << u[i] << " ";
			
		}
		outfile.close();
		if (p < P-1) {
			MPI_Send(&temp, 1, MPI_INT, p+1, TAG, MPI_COMM_WORLD);
		}
	} else {
		MPI_Recv(&temp, 1, MPI_INT, p-1, TAG, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		outfile.open("data3.txt",ios::app);

		for (int i = 2; i < I+2; i++) {
			outfile << u[i] << " ";
		}
		outfile.close();
		if (p < P-1) {
			MPI_Send(&temp, 1, MPI_INT, p+1, TAG, MPI_COMM_WORLD);
		} 
	}


	MPI_Finalize();
	
	exit(0);
}