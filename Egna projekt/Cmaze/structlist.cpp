#include <iostream>
#include <stdio.h>
#include <sstream>
#include <cstdlib>
#include <chrono>
#include <bitset>

struct Tuple {
	int x;
	int y;
	char p;
};


struct Node {
	char c;
	// bool c0, c1, c2, c3;
};

void shuffleArray(int* ar, int length) {
	for (int i = length - 1; i > 0; i--) {
		int index = rand() % (i + 1);
		// Simple swap
		int a = ar[index];
		ar[index] = ar[i];
		ar[i] = a;
	}
}

void addC(Node & n, int conn) {
	if (conn == 0) {
		n.c |= 1;
		// n.c0 = true;
	}
	if (conn == 1) {
		n.c |= 2;
		// n.c1 = true;
	}
	if (conn == 2) {
		n.c |= 4;
		// n.c2 = true;
	}
	if (conn == 3) {
		n.c |= 8;
		// n.c3 = true;
	}

}

void writeFile2(Node **nodes, int n) {

	FILE *f;
	unsigned int *img;
	
	img = new unsigned int[(long)(n)*(long)(n/16)];
	//img = new unsigned long long[size];
	// img = (unsigned int *)malloc((unsigned long) (n*n/16+20000));
	//memset(img,0,sizeof(img));

	int count = 0;
	unsigned long long elem = 0;

	f = fopen("img2.txt","wb");


	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			
			if (nodes[i][j].c & 1) {
				img[elem] |= 1<<count;
			}
			count++;

			if (nodes[i][j].c & 2) {
				img[elem] |= 1<<count;
			}
			count++;
			if (count == 32) {
				count = 0;
				elem++;
			}
		}
	}

	fwrite(img, (unsigned long long) (n)*(unsigned long long)(n)/16ULL, sizeof(unsigned int), f);

	fclose(f);
	delete[] img;
}

void writeFile(Node **nodes, int n) {

	FILE *f;
	unsigned int *img;
	unsigned long long size = (unsigned long long) (n)/16ULL;
	if (size == 0) {
		size = 1;
	}
	printf("%llu\n", size);

	img = new unsigned int[size];

	int count = 0;
	unsigned long long elem = 0;

	f = fopen("img2.txt","wb");


	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			
			if (nodes[i][j].c & 1) {
				img[elem] |= 1<<count;
			}
			count++;

			if (nodes[i][j].c & 2) {
				img[elem] |= 1<<count;
			}
			count++;
			if (count == 32) {
				count = 0;
				elem++;
			}
			if (elem == size) {
				elem = 0;

				fwrite(img, size, sizeof(unsigned int), f);
				for (unsigned long long k = 0; k < size; k++) {
					img[k] = 0ULL;
				}

			}
		}
	}

	fclose(f);
	delete[] img;


}


int main(int argc, char *argv[]) {

//	const int n = atoi(argv[1]);
//	printf("%d\n", nodes);
	const int n = 4*8192; // must be divisible by 4.
	std::chrono::time_point<std::chrono::system_clock> t0;
    t0 = std::chrono::system_clock::now();


	Node **nodes;
	nodes = new Node *[n];
	// bool **visited;
	// visited = new bool *[n];
	Tuple *s;
	//const unsigned int n2 = 1<<20;
	const unsigned int n2 = 923433256;
	s = new Tuple[n2];
	int c = -1;
	int maxc = 0;
	long deadEnds = 0;
	long count = 0;

	static Tuple dirs[4];
	int* d = new int[4];
	d[0] = 0; d[1] = 1; d[2] = 2; d[3] = 3;


	for (int i = 0; i < n; i++) {
		nodes[i] = new Node[n];
		// visited[i] = new bool[n];
		for (int j = 0; j < n; j++) {
			nodes[i][j].c = 0;
//			nodes[i][j].c0 = false;
//			nodes[i][j].c1 = false;
//			nodes[i][j].c2 = false;
//			nodes[i][j].c3 = false;
			// visited[i][j] = false;
		}
	}

    //writeTofile(nodes, n);

    //return 0;


	//addC(nodes[5][5], 0);
	//printf("%s\n", nodes[5][5].c0 ? "true" : "false");



	Tuple start; start.x = n/2; start.y = n/2; start.p = -1;
	s[0] = start;
	c++;


	dirs[0].x =	0; dirs[0].y =	1; dirs[0].p = -1;
	dirs[1].x =	1; dirs[1].y =	0; dirs[1].p = -1;
	dirs[2].x =	0; dirs[2].y = -1; dirs[2].p = -1;
	dirs[3].x = -1; dirs[3].y =	0; dirs[3].p = -1;

	bool br = false;
	Tuple curr;

//	curr = start;

	printf("Generating maze of size %d...\n", n);


	while (true) {
		//printf("%d\n", c);
		// printf("\n");

		do {
			if (c == -1) {
				br = true;
				break;
			}
			//delete &curr;
			curr = s[c];
			c--;
		} while (nodes[curr.x][curr.y].c != 0);
			// printf("%d\n", nodes[curr.x][curr.y].c);
		// } while (visited[curr.x][curr.y]);
		if (br) {
			break;
		}


		// visited[curr.x][curr.y] = true;

		if (count++ % 10000 == 0) {
			printf("\r   %.3f%%   ", 100 * count / (double) (n) / (double) (n));
		}

		if (curr.p != -1) {

			addC(nodes[curr.x][curr.y], (curr.p + 2) % 4);
			Tuple prev;
			prev.x = curr.x + dirs[(curr.p + 2) % 4].x;
			prev.y = curr.y + dirs[(curr.p + 2) % 4].y;
			prev.p = curr.p + dirs[(curr.p + 2) % 4].p;
			addC(nodes[prev.x][prev.y], curr.p);

		}


		shuffleArray(d, 4);
		char added = 0;


		for (int i = 0; i < 4; i++) {

			Tuple temp;
			temp.x = curr.x + dirs[d[i]].x;
			temp.y = curr.y + dirs[d[i]].y;
			temp.p = d[i];
			int max = temp.x > temp.y ? temp.x : temp.y;
			int min = temp.x < temp.y ? temp.x : temp.y;

			if ((max < n) & (min >= 0)) {
				// if (!visited[temp.x][temp.y]) {
				if (nodes[temp.x][temp.y].c == 0) {
					s[++c] = temp;
					added++;
					if (c > maxc) {
						maxc = c;
					}
				}
			}
		}
		if (added == 0) {
			deadEnds++;
		}
	}
	
	delete[] s;

	printf("\n%d\t%d\t%ld\n", maxc, n2, deadEnds);
	std::chrono::time_point<std::chrono::system_clock> t1;
    t1 = std::chrono::system_clock::now();
	const auto elapsed = (t1-t0).count();
	printf("Maze generated in %.3f seconds.\nSaving to file...\n", elapsed/1e9);
	writeFile(nodes, n);
	//writeTofile(nodes, n);
	std::chrono::time_point<std::chrono::system_clock> t2;
    t2 = std::chrono::system_clock::now();
	const auto elapsed2 = (t2-t1).count();
	printf("File saved in %.3f seconds.\nExiting.\n", elapsed2/1e9);

	delete[] d;

	for (int i = 0; i < n; i++) {
		delete[] nodes[i];
	}
	delete[] nodes;

}

