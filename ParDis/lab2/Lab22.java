import java.util.*;

public class Lab22 {

	Lab22() {
		int n = 10000000;
		int[] values = new int[n];
		for (int i = 0; i < n; i++) {
			values[i] = (int)(Math.random()*1000000);
		}
		// System.out.println(Arrays.toString(values));
		long t0 = System.nanoTime();
		mergeSort(values);
		long t1 = System.nanoTime();
		System.out.println(String.format("%.3fms", (t1-t0)*1e-6));
		// System.out.println(Arrays.toString(values));
	}

	void mergeSort(int[] values) {
		if (values.length <= 1) {
			return;
		}
		int half = values.length/2;
		int other = values.length-half;
		int[] left = new int[half];
		int[] right = new int[other];
		for (int i = 0; i < half; i++) {
			left[i] = values[i];
		}
		for (int i = 0; i < other; i++) {
			right[i] = values[i+half];
		}
		mergeSort(left);
		mergeSort(right);
		int leftCount = 0;
		int rightCount = 0;
		int count = 0;
		while (leftCount < half && rightCount < other) {
			// System.out.println(leftCount + "   " + rightCount);
			if (left[leftCount] < right[rightCount]) {
				values[count++] = left[leftCount++];
			} else {
				values[count++] = right[rightCount++];
			}
		}
		for (; leftCount < half; leftCount++) {
			values[count++] = left[leftCount];
		}
		for (; rightCount < other; rightCount++) {
			values[count++] = right[rightCount];
		}
	}

	public static void main(String[] args) {
		new Lab22();
	}
}