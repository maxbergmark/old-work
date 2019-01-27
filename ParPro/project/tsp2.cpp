#include <algorithm>
#include <cmath>
#include <cstdlib>
#include <functional>
#include <iostream>
#include <fstream>
#include <limits>
#include <list>
#include <random>
#include <tuple>
#include <vector>
#include "mpi.h"
#include <unistd.h>
#include <string.h>
#include <stdio.h>

static const int MAX = 11000;
static double TIME = 10.0;
static double syncFreq = 1.0;
static const int MAX_N = 120;

int myrank, P;
double start;
std::random_device rd;
std::default_random_engine generator;
int ds[MAX][MAX];

class disjoint_set {
private:
    struct disjoint_set_node {
	int rank;
	int parent;
    };
private:
    static const int MAX = 100001;
    std::vector<disjoint_set_node> m_v;
public:
    disjoint_set() : m_v() {
	m_v.resize(MAX);
    }

    void make_set(const int x) {
	disjoint_set_node node;
	node.parent = x;
	m_v[x] = node;
	node.rank = 0;
    }

    int find(const int x) {
	disjoint_set_node& node = m_v[x];
	if (node.parent == x) {
	    return x;
	} else {
	    int result = find(node.parent);
	    node.parent = result;
	    return result;
	}
    }

    void union_elements(const int x, const int y) {
	int x_root = find(x);
	int y_root = find(y);

	disjoint_set_node& x_root_node = m_v[x_root];
	disjoint_set_node& y_root_node = m_v[y_root];
	if (x_root != y_root) {
	    if (x_root_node.rank < y_root_node.rank) {
		x_root_node.parent = y_root;
	    } else if (y_root_node.rank < x_root_node.rank) {
		y_root_node.parent = x_root;
	    } else {
		y_root_node.parent = x_root;
		++x_root_node.rank;
	    }
	}
    }
};

int length(std::vector<int> path) {
    int l = 0;
    for (int i = 1; i < path.size(); ++i) l += ds[path[i-1]][path[i]];
    l += ds[path[path.size()-1]][path[0]];
    return l;
}

struct nn_sort {

    bool operator() (const std::tuple<int, int>& v1, const std::tuple<int, int>& v2) {
	return std::get<1>(v1) < std::get<1>(v2);
    }

};

void nnList(std::vector<std::vector<int>>& nns, int max_n) {
    int n = nns.size();
    for (int i = 0; i < n; ++i) {
	std::vector<std::tuple<int, int>> helper(n);
	for (int j = 0; j < n; ++j) {
	    if (j != i) {
		helper[j] = std::make_tuple(j, ds[i][j]);
	    } else {
		helper[j] = std::make_tuple(j, 10000000000);
	    }
	}

	std::sort(helper.begin(), helper.end(), nn_sort());

	for (int j = 0; j < max_n; ++j) {
	    nns[i][j] = std::get<0>(helper[j]);
	}
    }
}

void nearest_neighbor_path(std::vector<int>& path) {
    const auto n = path.size();
    std::list<int> list;
    for (int i = 0; i < n; ++i)
	list.emplace_back(i % n);

    for (int i = 0; i < n; ++i) {
	auto min = list.begin();
	for (auto k = list.begin(); k != list.end(); ++k) {
	    const auto d1 = ds[path[i-1]][*k],
		d2 = ds[path[i-1]][*min];
	    if (d1 < d2) min = k;
	}

	path[i] = *min;
	list.erase(min);
    }
}

void rev(std::vector<int>& path, std::vector<int>& indices, int s, int e) {
    int n = path.size();
    int i = s - 1;
    int j = e;
    int diff = -1;
    if (i + 1 < j) diff = j - (i + 1);
    else {
	diff = n - (i + 1) + j + 1;
    }

    diff = (diff + 1) / 2;

    int start = i + 1;
    int end = j;
    int tmp = -1;

    while (diff > 0) {
	start %= n;
	end = end == -1 ? n - 1 : end;

	tmp = path[start];
	path[start] = path[end];
	path[end] = tmp;

	++start;
	--end;
	--diff;
    }

    for (int i = 0; i < n; ++i) {
	indices[path[i]] = i;
    }
}


bool opt2(std::vector<int>& path,
			std::vector<int>& indices,
			std::vector<std::vector<int>>& nns,
			int max_n) {


		    
    const int n = path.size();
    bool improved = true;
    while (improved) {
		improved = false;

		for (int i = 0; i < n - 1; ++i) {

		    bool good_i = false;

		    for (int k = 0; k < max_n - 1; ++k) {
				const int a = path[i];
				const int b = path[i + 1];

				int vert = nns[a][k];

				int j = indices[vert];

				const int c = vert;
				const int d = path[(j + 1) % n];
				if (b == c || b == d) continue;

				if (ds[a][c] + ds[b][d] < ds[a][b] + ds[c][d]) {
				    good_i = true;
				    improved = true;

				    rev(path, indices, i + 1, j);
				}
	    	}
		}

		if ((MPI_Wtime()- start) > TIME) {
		    return true;
		}
    }

    return false;
}

void perturb(std::vector<int>& p, std::vector<int>& indices, int swaps) {
    const auto n = p.size();
    if (n < 8)
	return;

    static std::uniform_int_distribution<int> d(0, n - 1);
    static auto r = std::bind(d, ref(rd));

    auto pos = r();

    for (int i = 1; i <= swaps; i++) {

	    int s1 = (pos - i)%n;
	    int s2 = (pos + i)%n;

	    int tmp = p[s1];
	    p[s1] = p[s2];
	    p[s2] = tmp;

	    indices[p[s2]] = s2;
	    indices[p[s1]] = s1;
	}
}

// compile with "mpic++ tsp2.cpp -o tsp2.out -std=c++11 -O3"
// run with "clear && mpirun -np 8 ./tsp2.out test5 20 2"
// options are [Filename without extension, total time, time between syncs]

int main(int argc, char **argv) {

	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &P);
	MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
	char* fileName = argv[1];
	char txtName[80];
	strcpy(txtName, fileName);
	strcat(txtName, ".txt");
	char* timeAllowed = argv[2];
	char timearr[80];
	char syncarr[80];
	char* synctime = argv[3];
	strcpy(timearr, timeAllowed);
	strcpy(syncarr, synctime);

	TIME = std::atof(timearr);
	syncFreq = std::atof(syncarr);

	int n = 0;

    if (myrank == 0) {
    	std::ifstream myReadFile;
    	myReadFile.open(txtName);
    	if (myReadFile.is_open()) {
    		myReadFile >> n;
    	}
    	myReadFile.close();
	}


	MPI_Bcast(&n, 1, MPI_INT, 0, MPI_COMM_WORLD);
//	printf("n: %d\n", n);	

	    //std::cout << n << std::endl;
//	printf("After: %d %d\n", myrank, n);

//    double* Xs = new double[n];
//    double* Ys = new double[n];

	double Xs[n];
	double Ys[n];

//	Xs = new double[n];
//	Ys = new double[n];

	if (myrank == 0) {
    	std::ifstream myReadFile;
    	myReadFile.open(txtName);
    	char output[100];
    	int i = 0;
    	if (myReadFile.is_open()) {
    		myReadFile >> output;
    		while (!myReadFile.eof()) {
    			myReadFile >> Xs[i] >> Ys[i];

    			//std::cout << Xs[i] << "   " << Ys[i] << std::endl;
    			i++;
    		}
    	}
    	myReadFile.close();	    
	} 


//	printf("Before: %d %ld\n", myrank, sizeof(Xs));
	MPI_Bcast(&Xs, n, MPI_DOUBLE, 0, MPI_COMM_WORLD);
	MPI_Bcast(&Ys, n, MPI_DOUBLE, 0, MPI_COMM_WORLD);
//	printf("After: %d %d\n", myrank, n);


    for (int i = 0; i < n; ++i) {
		for (int j = i; j < n; ++j) {
		    int dist = round(sqrt(pow(Xs[i]-Xs[j], 2) + pow(Ys[i]-Ys[j], 2)));
		    ds[i][j] = ds[j][i] = dist;
		}
    }


    std::vector<int> path(n);
    nearest_neighbor_path(path);

//    std::cout << "l: " << length(path) << std::endl;
    if (myrank == 0) {
	    printf("\nOriginal path length: %d\n\n", length(path));
	}
	#ifdef VERBOSE
	    std::cout << "l: " << length(path) << std::endl;
	#endif
	#ifdef VERBOSE
	    for (const auto v : path)
		std::cout << v << std::endl;
	#endif

    int max_n = std::min(MAX_N, n);
    std::vector<std::vector<int>> nns(n, std::vector<int>(max_n));
    nnList(nns, max_n);

    std::vector<int> indices(n);
    for (int i = 0; i < n; ++i) {
		indices[path[i]] = i;
    }

    opt2(path, indices, nns, max_n);

    int best = length(path);
    std::vector<int> bestPath = path;

    int iters = 0;
    //double syncFreq = 1.0;
    int syncs = 1;
    start = MPI_Wtime();

    while (true) {
		#ifdef VERBOSE
			std::cout << "l: " << length(bestPath) << std::endl;
			std::cout << "i: " << iters << std::endl;
		#endif
		++iters;

		for (int hej = myrank-1; hej < myrank; hej++) {
			perturb(path, indices, myrank+1);
		}

		bool to = opt2(path, indices, nns, max_n);


		int l = length(path);

		if (l < best) {
		    bestPath = path;
		    best = l;
		}
		
		if (myrank == 0 & false) {
			const auto elapsed = (MPI_Wtime()-start);
			float progress = (float)(elapsed)/(float) TIME;
			printf("\r%f\t%d\t%d\t%d\t%.0f", progress, best, iters, syncs-1, iters/progress);
		}

		double tempTime = MPI_Wtime();

		
		
		if (P > 1 & (tempTime-start) > (double)syncs*syncFreq) {
			
			int paths[P];
			MPI_Allgather(&best, 1, MPI_INT, &paths, 1, MPI_INT, MPI_COMM_WORLD);
			
			int minDist = paths[0];
			int minIndex = 0;
			for (int i = 1; i < P; i++) {
				if (paths[i] < minDist)	{
					minDist = paths[i];
					minIndex = i;
				}
			}
			if (minDist < 0) {break;}
			best = minDist;

			/*
			if (myrank == 0) {
				for (int i = 0; i < P; i++) {
					std::cout << paths[i] << std::endl;
				}
				printf("Best path found: %d\n", minIndex);
			}
			*/

			//printf("length %d: %d\n", myrank, length(bestPath));
			double t0 = MPI_Wtime();
			MPI_Bcast(&bestPath.front(), bestPath.size(), MPI_FLOAT, minIndex, MPI_COMM_WORLD);
			double t1 = MPI_Wtime();
			if (myrank==0) {
				//printf("%f\n", 1000000*(t1-t0));
				printf("\rPath length: %d", best);
				printf(" %2.1f%% done", 100.0*(MPI_Wtime()-start)/TIME);
				printf(" %d   ", minIndex);
			}
			//printf("length %d: %d\n", myrank, length(bestPath));
			syncs++;
		} else if ((tempTime-start) > TIME) {
			//printf("TIME EXCEEDED!!!\n");
			int paths[P];
			int best = -1000000;
			MPI_Allgather(&best, 1, MPI_INT, &paths, 1, MPI_INT, MPI_COMM_WORLD);
			break;
		}
		
/*
		if (MPI_Wtime()-start > TIME) {
			break;
		}
*/
    }

    for (const auto v : bestPath) {
//		std::cout << v << std::endl;
    }
//    std::cout << "l: " << length(bestPath) << std::endl;
	printf("\nFinal length %d: %d\nNumber of iterations: %d\n", myrank, length(bestPath), iters);    
	#ifdef VERBOSE
		std::cout << "l: " << length(bestPath) << std::endl;
		std::cout << "i: " << iters << std::endl;
	#endif

	int totIters[P];
	int allPaths[P];
	MPI_Allgather(&iters, 1, MPI_INT, &totIters, 1, MPI_INT, MPI_COMM_WORLD);
	MPI_Allgather(&best, 1, MPI_INT, &allPaths, 1, MPI_INT, MPI_COMM_WORLD);

	if (myrank == 0) {
		int sum = 0;
		int minPath = allPaths[0];
		for (int i = 0; i < P; i++) {
			sum += totIters[i];
			if (allPaths[i] < minPath) {
				minPath = allPaths[i];
			}
		}

		usleep(10000);
		printf("Total iterations: %d\n", sum);
		printf("Best path: %d\n", minPath);

	}
	if (myrank == 0) {
		std::ofstream myfile;
		char outName[80];
		strcpy(outName, fileName);
		strcat(outName, "_result.txt");
		myfile.open(outName);
		myfile << best << std::endl;
		myfile << std::endl;
		for (int i = 0; i < n; i++) {
			myfile << bestPath[i] << std::endl;
		}
		myfile.close();
	}

    MPI_Finalize();
	exit(0);

}
