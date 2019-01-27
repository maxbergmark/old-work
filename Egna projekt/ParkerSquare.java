import java.util.*;


public class ParkerSquare {

	static final int LIMIT = 2000;
	static final int MAX = LIMIT*LIMIT*3;
	static final int MAX_COUNT = 400;
	static int[][][] elements;
	static int[] counters;
	static int created = 0;


	static void createArrays() {
		System.out.println("Total elements: " + MAX*MAX_COUNT*3/1e6 + " million");
		counters = new int[MAX];
		System.out.println("counters created.");
		elements = new int[MAX][][];
		System.out.println("elements created.");
	}

	static void fillElements() {
		long t0 = System.nanoTime();
		int count = 0;
		int i, j, k, sum = 0;
		for (i = 1; i < LIMIT-2; i++) {
			for (j = i+1; j < LIMIT-1; j++) {
				for (k = j+1; k < LIMIT; k++) {
					sum = i*i+j*j+k*k;
					if (counters[sum] == 0) {
						elements[sum] = new int[MAX_COUNT][];
						created++;
					}
					if (counters[sum] < MAX_COUNT) {
						elements[sum][counters[sum]] = new int[3];
						elements[sum][counters[sum]][0] = i;
						elements[sum][counters[sum]][1] = j;
						elements[sum][counters[sum]++][2] = k;
					}
					if (++count % 100000 == 0) {
						System.out.print("          \r" + count + ": " + i + " " +  j + " " + k + "  " + created + "  " + count/(double)created);
					}
				}
			}
		}
		long t1 = System.nanoTime();
		System.out.println(String.format("\nFilling done in %.3f seconds.", (t1-t0)*1e-9));
	}

	static void checkElements() {
		for (int i = 0; i < MAX; i++) {
			if (counters[i] > 0) {
				HashMap<Integer, Integer> distinct = getDistinct(elements[i], counters[i]);
				if (checkMap(distinct) && counters[i] >= 6 && distinct.size() < 15) {
					System.out.println(i + "  " +
					 elements[i].length + "   " + 
					 distinct.size() + "   " + distinct + "   " + checkMap(distinct) + "   " + 
					 Arrays.deepToString(elements[i]) + "\n");
				}
			}
		}
	}

	static boolean checkMap(HashMap<Integer, Integer> test) {
		int[] values = new int[5];
		int req2 = 6;
		int req3 = 3;
		for (int i : test.values()) {
			values[i>4?4:i]++;
		}
		req2 -= values[2];
		values[2] = 0;
		if (req2 > 0) {
			int min = Math.min(req2, values[3]);
			req2 -= min;
			values[3] -= min;
		}

		if (req2 > 0) {
			int min = Math.min(req2, values[4]);
			req2 -= min;
			values[4] -= min;
		}

		req3 -= values[3];
		values[3] = 0;
		if (req3 > 0) {
			int min = Math.min(req3, values[4]);
			req3 -= min;
			values[4] -= min;
		}
		return values[3] > 0 || values[4] > 0;

	}

	static HashMap<Integer, Integer> getDistinct(int[][] a, int count) {
		HashMap<Integer, Integer> distinct = new HashMap<>();
		int tempCount = 0;
		for (int[] a2 : a) {
			if (++tempCount > count) {
				break;
			}
			for (int i : a2) {
				if (distinct.get(i) == null) {
					distinct.put(i, 1);
				} else {
					int curr = distinct.get(i);
					distinct.put(i, curr+1);
				}
			}
		}
		return distinct;
	}

	public static void main(String[] args) {
		createArrays();
		fillElements();
		checkElements();
	}
}