#include <iostream>
#include <stdio.h>
#include <complex>
#include <chrono>
#include <mpi.h>
#include <unistd.h>
#include <math.h>

using namespace std;
using std::chrono::nanoseconds;
using std::chrono::duration_cast;
using std::chrono::system_clock;
typedef complex<double> dcomp;

#define M (1024*4*4) //width
#define N (1024*4*3) //height


double count(dcomp d,int n) {
	dcomp z = d;
	d = dcomp(-0.835, 0.2321);
	double c = 0;
	double wall = 1e150;
	while (abs(z) < wall) {
//	while (abs(z) < 3 && c < n) {
		z = z*z+d;
		c++;
	}
	double rem = log2(log(abs(z)+1)/log(wall));
	if (!(abs(rem) < 1000)) {
		printf("%.3f\n", log((abs(z)+1)));
	}
	//printf("%.3f\n", rem);
	double v = c - rem;
	return v;
}

int main(int argc, char* argv[]) {
	const auto start = system_clock::now();
	double* color=NULL;
	color = new double[M*N];
	int rank, size, rc;
	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD,&rank);
	rc = MPI_Comm_size(MPI_COMM_WORLD, &size);
	FILE *fp,*fl;
	if (rank == 0) {
		printf("Total size: %dx%d\n", M, N);
	}

	/* your computations */

	double xmin = -2;//-0.923168;//-0.95;
	double xmax =  2;//-0.923156;//-0.90;
	double ymin = -1.5;//0.310210;//0.23;
	double ymax =  1.5;//0.310225;//0.33;

	int dy = N/(size);
	double* cTemp=NULL;
    	//cTemp = new unsigned char[M*dy];
	int ll,ul;
//	usleep(100000*rank);
	ll = rank*dy;
	ul = (rank+1)*dy;
	
	cTemp = new double[M*dy];
	long c = 0;
	for (int j = ll; j < ul; j++) {
		for (int i = 0; i < M; i++) {
			//cout << j << "   " << i << "   " << i+(j-(rank)*dy)*M << endl;
			dcomp d(xmin*(1.0-1.0*i/M)+xmax*i/M, ymin*(1.0-1.0*j/N)+ymax*j/N);
			cTemp[i+(j-(rank)*dy)*M] = count(d,50);
			c++;
			if (c % (1<<20) == 0) {
				printf("\rProcess %d has completed %.2f%%", rank, 100*c/(double)(M*dy));
			}

		}
	}
	const auto stop = system_clock::now();
	const auto d_actual = duration_cast<nanoseconds>(stop - start).count();

	printf("\rGenerating number %d done in %.3f seconds.\n", 
			rank, (d_actual/1e9));

	rc = MPI_Gather(cTemp,M*dy,MPI_DOUBLE,color,M*dy,MPI_DOUBLE,0,MPI_COMM_WORLD);
	if (rank == 0) {
		printf("Data gathered to root process\n");
	}
	delete[] cTemp;
	if (rank == 0) {
		printf("Writing to file...\n\n");
		fp = fopen("color2.txt","w");
		long c2 = 0;
		for (int j = 0; j < N; j++) {
			for (int i = 0; i < M; i++) {
				//printf("%6.3f \n", color[i+j*M]);
				fprintf(fp, "%.6f ", color[i+j*M]);
				if (c2++ % 1024 == 0) {
					printf("\r   %.2f%%", 100*c2/(double)(M*N));
				}
			}
			fprintf(fp, "\n");
		}
		fclose(fp);


		fl = fopen("limits.txt","w");
		fprintf(fl, "%f ", xmin);
		fprintf(fl, "%f ", xmax);
		fprintf(fl, "%f ", ymin);
		fprintf(fl, "%f ", ymax);
		fclose(fl);
		printf("\n\nDone!\n\n");
	}
	delete[] color;
	printf("Process %d has finished.\n");
	MPI_Finalize();

}
