#include <stdio.h>

using namespace std;

int main() {
  for (int i = 0; i < 1200; i++) {
    int values[12];
    for(int x = 0; x < 12; x++) {
      int xval = 100*x;
      values[x] = 0;
      int yval = -((xval-i)*(xval-i))/4200 + 15;
      
      if (yval > 0) {
        int dist = (int) yval;
        values[x] = dist;
      }
    }
    for (int k = 0; k < 12; k++) {
    	printf("%d, ", values[k]);
    }
    printf("\n");
  }
}