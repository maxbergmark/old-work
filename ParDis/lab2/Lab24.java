import java.util.*;
import java.util.concurrent.*;

public class Lab24 {

	Lab24() {
		int n = 1000000;
		int[] values = new int[n];
		for (int i = 0; i < n; i++) {
			// values[i] = n-i;
			values[i] = (int) (Math.random()*1000000);
		}
		// System.out.println(Arrays.toString(values));
		ForkJoinPool fjp = new ForkJoinPool();
		long t0 = System.nanoTime();
		fjp.invoke(new Sorter(values));
		long t1 = System.nanoTime();
		System.out.println(String.format("%.3fms", (t1-t0)*1e-6));
		// System.out.println(Arrays.toString(values));
	}


	public static class Sorter extends RecursiveAction {

		int[] values;
		volatile boolean complete = false;

		Sorter(int[] values) {
			this.values = values;
		}

		public void compute() {
			// System.out.println("start: " + Arrays.toString(values));
			if (values.length <= 1) {
				this.complete = true;
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
			Sorter s0 = new Sorter(left);
			Sorter s1 = new Sorter(right);
			invokeAll(s0, s1);

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
			// System.out.println("final: " + Arrays.toString(values));

			// notifyParent();
			this.complete = true;
			return;
		}

	}

	public static void main(String[] args) {
		new Lab24();
	}
}