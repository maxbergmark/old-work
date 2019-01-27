#include <algorithm>
#include <chrono>
#include <cmath>
#include <cstdlib>
#include <functional>
#include <iostream>
#include <limits>
#include <list>
#include <random>
#include <tuple>
#include <vector>
#include "mpi.h"

static const int MAX = 1000;
static const long TIME = 1L*1000000000L;
static const int MAX_N = 120;
bool firstprint = true;


std::chrono::time_point<std::chrono::system_clock> start;
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

		if ((std::chrono::system_clock::now() - start).count() > TIME) {
		    return true;
		}
    }

    return false;
}

void perturb(std::vector<int>& p, std::vector<int>& indices) {
    const auto n = p.size();
    if (n < 8)
	return;

    static std::uniform_int_distribution<int> d(2, n - 3);
    static auto r = std::bind(d, ref(rd));

    auto pos = r();

    for (int i = 1; i <= 5; i++) {

	    int s1 = (pos - i)%n;
	    int s2 = (pos + i)%n;
	    if (firstprint)
		    std::cout << "s1 s2: " << s1 << "    " << s2 << std::endl;

	    int tmp = p[s1];
	    p[s1] = p[s2];
	    p[s2] = tmp;

	    indices[p[s2]] = s2;
	    indices[p[s1]] = s1;
	}
}

int main(int argc, char **argv) {

	MPI_Init(&argc, &argv);
	int myrank,P;
	MPI_Comm_size(MPI_COMM_WORLD, &P);
	MPI_Comm_rank(MPI_COMM_WORLD, &myrank);

    start = std::chrono::system_clock::now();

    int n = 0;

    if (myrank == 0) {
	    std::cin >> n;
	}
	MPI_Bcast(&n, 1, MPI_INT, 0, MPI_COMM_WORLD);
	

	    //std::cout << n << std::endl;
//	printf("After: %d %d\n", myrank, n);

//    double* Xs = new double[n];
//    double* Ys = new double[n];

	double Xs[n];
	double Ys[n];

	if (myrank == 0) {
	    for (int i = 0; i < n; ++i) {
			std::cin >> Xs[i] >> Ys[i];
			//std::cout << Xs[i] << "   " << Ys[i] << std::endl;
	    }
	    
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
	    printf("Original path length: %d\n", length(path));
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
    int syncFreq = 20000;

    while (true) {
	#ifdef VERBOSE
		std::cout << "l: " << length(bestPath) << std::endl;
		std::cout << "i: " << iters << std::endl;
	#endif
	++iters;

	if (firstprint) {
		for (int i = 0; i < path.size(); i++) {
			std::cout << path[i] << "   " << i << std::endl;
		}
	}

	perturb(path, indices);

	if (firstprint) {
		std::cout << std::endl;
		for (int i = 0; i < path.size(); i++) {
			std::cout << path[i] << "   " << i << std::endl;
		}
	}
	firstprint = false;

	bool to = opt2(path, indices, nns, max_n);


	int l = length(path);

	if (l < best) {
	    bestPath = path;
	    best = l;
	}
	if (myrank == 0) {
		const auto elapsed = (std::chrono::system_clock::now()-start).count();
		float progress = (float)(elapsed)/(float) TIME;
		printf("\r%f\t%d\t%d\t%d\t%.0f", progress, best, iters, iters/syncFreq, iters/progress);
	}

	if (P > 0 & (iters + 1) % syncFreq == 0) {
		
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
		/*
		if (myrank == 0) {
			for (int i = 0; i < P; i++) {
				std::cout << paths[i] << std::endl;
			}
			printf("Best path found: %d\n", minIndex);
		}
		*/
		//printf("length %d: %d\n", myrank, length(bestPath));
		MPI_Bcast(&bestPath.front(), bestPath.size(), MPI_FLOAT, minIndex, MPI_COMM_WORLD);
		//printf("length %d: %d\n", myrank, length(bestPath));
	}


	if (to) {
	    break;
	}
    }

    for (const auto v : bestPath) {
//		std::cout << v << std::endl;
    }
//    std::cout << "l: " << length(bestPath) << std::endl;
	printf("\nFinal length %d: %d\nNumber of iterations: %d\n\n", myrank, length(bestPath), iters);    
	#ifdef VERBOSE
		std::cout << "l: " << length(bestPath) << std::endl;
		std::cout << "i: " << iters << std::endl;
	#endif

	int totIters[P];
	MPI_Allgather(&iters, 1, MPI_INT, &totIters, 1, MPI_INT, MPI_COMM_WORLD);
	if (myrank == 0) {
		int sum = 0;
		for (int i = 0; i < P; i++) {
			sum += totIters[i];
		}
		printf("Total iterations: %d\n", sum);
	}

    MPI_Finalize();
	exit(0);
}
