import java.util.*;
import java.util.stream.*;

public class Lab25 {

	int iterations = 0;
	long totalTime = 0;
	static boolean verbose = true;

	Lab25() {
		
		

		for (int i = 1000000; i <= 1000000000; i+=0*1000000) {
			// int l = (int) (Math.random()*i);
			System.out.println("sorting list of length: " + i);
			generateAndSort(i);
		}

		System.out.println(String.format("Total time: %.3f seconds.", totalTime*1e-9));
		

	}


	public void generateAndSort(int n) {
		int[] numbers = new int[n];
		int[] temp2 = new int[n];

		for (int i = 0; i < numbers.length; i++) {
			// numbers[i] = numbers.length-i;
			numbers[i] = (int) (Math.random()*1000000);
		}
		if (verbose) {
			// System.out.println("sorted: " + Arrays.toString(numbers));
		}

		// System.out.println("Starting sort.");
		long t0 = System.nanoTime();
//		Arrays.sort(numbers);
		sortList(numbers, temp2);

		long t1 = System.nanoTime();
		totalTime += t1-t0;
		iterations++;
		// System.out.println("Sort finished.");

		boolean b = isSorted(numbers);
		if (b & verbose) {
			// System.out.println("sorted: " + Arrays.toString(numbers));
			// System.out.println("sorted: " + Arrays.toString(temp2));
			System.out.println(String.format("Sort time: %6.3fms\nAverage time: %6.3fms", (t1-t0)*1e-6, totalTime*1e-6/iterations));
		} else if (!b) {
			System.out.println("FATAL ERROR!!! FATAL ERROR!!! FATAL ERROR!!!");
		}
		// System.out.println(iterations);
	}

	public void sortList(int[] numbers, int[] temp2) {

		int N = numbers.length;
		// System.out.println(Arrays.toString(numbers));

		int maxNumT = 0;
		int tempN = N;
		while (tempN > 0) {
			maxNumT++;
			tempN >>= 1;
		}
		final int maxNum = maxNumT+1;

		
		IntStream.range(0,maxNum)
		.map(n -> 1<<n)
		.forEach(n -> IntStream.iterate(0, i -> i + 2*n)
			.limit((N + n)/(2*n) + 1)
			.parallel()
			.forEach(i -> merge(numbers, temp2, i, i+2*n, maxNum))
		);
			// .forEach(i -> System.out.println(i + "  " + ((N + n)/(2*n) + 1))));
			// .forEach(i -> merge(numbers, temp2, i, i+2*n))
			// .forEach(System.out::println));


	}

	public boolean isSorted(int[] array) {
		for (int i = 0; i < array.length-1; i++) {
			if (array[i] > array[i+1]) {
				System.out.println(i);
				return false;
			}
		}
		return true;
	}


	public void merge(int[] numbers, int[] temp, int low, int high, int maxNum) {
		if (low >= numbers.length-1) {
			return;
		}

		int step = high - low;
		int half = (high+low)/2;
		int level = 0;
		int tempStep = step>>1;

		while (tempStep > 0) {
			level++;
			tempStep >>= 1;
		}
		int[] first = numbers;
		int[] second = temp;
		int N = numbers.length;

		if (level % 2 == 1) {
			first = numbers;
			second = temp;
		} else {
			first = temp;
			second = numbers;
		}

		high = numbers.length < high ? numbers.length : high;

		int li = low;
		int ri = half;
		int ti = low;

		boolean premerge = false;


		if (premerge) {
			int liTemp = low;
			int riTemp = half;

			// pre-merger
			
			while (liTemp < half & ri < N && first[liTemp] < first[ri] ) {
				liTemp++;
			}
			
			while (riTemp < high && first[riTemp] < first[li]) {
				riTemp++;
			
			}
			if (li < liTemp) {
				System.arraycopy(first, li, second, ti, liTemp-li);
				ti += liTemp-li;
				li = liTemp;
			} else if (ri < riTemp) {
				System.arraycopy(first, ri, second, ti, riTemp-ri);
				ti += riTemp-ri;
				ri = riTemp;
			}
		}

		// main merger
		while (li < half && ri < high) {
			second[ti++] = first[li] < first[ri] ? first[li++] : first[ri++];
		}

		// tailing merger
		half = half < N ? half : N;
		if (li < half) {
			System.arraycopy(first, li, second, ti, half-li);
		} else if (ri < high) {
			System.arraycopy(first, ri, second, ti, high-ri);		
		}

		// copy to numbers array if odd number of passes.
		if (level == maxNum && level % 2 == 1) {
			System.arraycopy(temp, 0, numbers, 0, numbers.length);
		}

		return;
	}

	public static void main(String[] args) {
		new Lab25();
	}
}