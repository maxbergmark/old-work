#include <iostream>
#include <stdio.h>
#include <complex>
#include <chrono>

using namespace std;
using std::chrono::nanoseconds;
using std::chrono::duration_cast;
using std::chrono::system_clock;
typedef complex<double> dcomp;

#define M 2048
#define N 2048


char count(dcomp d,char n) {
	dcomp z = 0;
	char c = 0;
	while (abs(z) < 3 && c < n) {
		z = z*z+d;
		c++;
	}
	return c;
}

int main() {
	const auto start = system_clock::now();
	unsigned char* color=NULL;
	color = new unsigned char[M*N];
	FILE *fp,*fl;
	/* your computations */

	double xmin = -2;//-0.923168;//-0.95;
	double xmax =  1;//-0.923156;//-0.90;
	double ymin = -1.5;//0.310210;//0.23;
	double ymax =  1.5;//0.310225;//0.33;

	for (int j = 0; j < N; j++) {
		for (int i = 0; i < M; i++) {
			//cout << (1.0-1.0*i/M) << "   " << 1.0*i/M << endl;
			dcomp d(xmin*(1.0-1.0*i/M)+xmax*i/M, ymin*(1.0-1.0*j/N)+ymax*j/N);
			color[i+j*M] = count(d,50);
		}
	}
	const auto stop = system_clock::now();
	const auto d_actual = duration_cast<nanoseconds>(stop - start).count();

	cout << "Generating done in " << (d_actual/1000000)/1000.0 << " seconds." << endl;
	fp = fopen("color.txt","w");
	for (int j = 0; j < N; j++) {
		for (int i = 0; i < M; i++) {
			fprintf(fp, "%hhu ", color[i+j*M]);
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

}