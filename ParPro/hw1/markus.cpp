#include <iostream>
#include <stdio.h>
#include <complex>
#include <chrono>
#include <mpi.h>
#include <unistd.h>

using namespace std;
using std::chrono::nanoseconds;
using std::chrono::duration_cast;
using std::chrono::system_clock;
typedef complex<double> dcomp;

// 2048
#define M 8
#define N 8


char count(dcomp d,char n) {
    dcomp z = 0;
    char c = 0;
    while (abs(z) < 3 && c < n) {
        z = z*z+d;
        c++;
    }
    return c;
}

int main(int argc, char **argv) {
    const auto start = system_clock::now();
    unsigned char* global_color=NULL;
    global_color = new unsigned char[M*N];
    FILE *fp,*fl;

    double xmin = -2;//-0.923168;//-0.95;
    double xmax =  1;//-0.923156;//-0.90;
    double ymin = -1.5;//0.310210;//0.23;
    double ymax =  1.5;//0.310225;//0.33;
    
    // Define stuff
    int my_id, num_procs, rc;
    int row = 0;
    MPI_Status status;
    int tag = 66;       // not in use, maybe have a send and a recv. tag? probably not needed. 
    int root_process = 0;
    
    // int counter = 0; // not needed?
    
    // Initialize parallel processes. Find out MY process ID and how many processes were started:
    rc = MPI_Init(&argc, &argv);
    rc = MPI_Comm_rank(MPI_COMM_WORLD, &my_id);
    rc = MPI_Comm_size(MPI_COMM_WORLD, &num_procs);
    
    // interval length that each process need to crunch. Assume num_procs evenly divide N.
    int dy = N/num_procs;
    unsigned char* local_color=NULL;
    local_color = new unsigned char[M*dy];
    
/* We implement a static task assignment to parallelize the calculation of pixels
 * We assume that the number of pixels is evenly divided by the number of processes.
 **/
    //cout << my_id << " | " << dy << " | " << (my_id+1)*dy << " | " << N << endl; //testline
	usleep(10000*my_id);
    for (int j = my_id*dy; j < (my_id+1)*dy; j++) {
        for (int i = 0; i < M; i++) {
            //cout << (1.0-1.0*i/M) << "   " << 1.0*i/M << endl;
		cout << j << "   " << i << "   " << i+(j-my_id*dy)*M << endl;
            dcomp d(xmin*(1.0-1.0*i/M)+xmax*i/M, ymin*(1.0-1.0*j/N)+ymax*j/N);
            local_color[i+(j-my_id*dy)*M] = count(d,50);
        }
    }
    printf("%i\n",my_id);
    // Gather all local_color to global_color
    rc = MPI_Gather(local_color,M*dy,MPI_CHAR,global_color,M*dy,MPI_CHAR,root_process,MPI_COMM_WORLD);
    
    if (my_id == root_process){
        printf("hund %i\n",my_id);
        // Clocking program time and saving to file
        const auto stop = system_clock::now();
        const auto d_actual = duration_cast<nanoseconds>(stop - start).count();

        cout << "Generating done in " << (d_actual/1000000)/1000.0 << " seconds." << endl;
        fp = fopen("color.txt","w");
        for (int j = 0; j < N; j++) {
            for (int i = 0; i < M; i++) {
                fprintf(fp, "%hhu ", global_color[i+j*M]);
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
    rc = MPI_Finalize();
}
