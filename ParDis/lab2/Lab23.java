import java.util.*;
import java.util.concurrent.*;

public class Lab23{


	Lab23() {
		int n = 10000;
		int[] values = new int[n];
		for (int i = 0; i < n; i++) {
			values[i] = n-i;
		}
		// System.out.println(Arrays.toString(values));
		long t0 = System.nanoTime();
		new Sorter(values).sort();
		long t1 = System.nanoTime();
		System.out.println(String.format("%.3fms", (t1-t0)*1e-6));
		// System.out.println(Arrays.toString(values));
	}

	public static class Sorter implements Callable<Void> {
		
		static ExecutorService executor = Executors.newCachedThreadPool();
		int[] values;
		Sorter parent = null;


		Sorter(int[] values) {
			this.values = values;
		}


		public void sort() {
			Future result = executor.submit(this);
			while (!result.isDone()) {}
			executor.shutdown();
			try {
				executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException e) {

			}
			System.out.println("done");
		}

		public Void call() {
			// System.out.println("start: " + Arrays.toString(values));
			if (values.length <= 1) {
				return null;
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
			// Sorter s0 = new Sorter(left);
			// Sorter s1 = new Sorter(right);
			Collection<Sorter> temp = new ArrayList<>();
			temp.add(new Sorter(left)); temp.add(new Sorter(right));
			try {
				executor.invokeAll(temp);
			} catch (InterruptedException e) {
				// System.out.println("interrupted");
			}

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
			return null;
		}
	}

	public static void main(String[] args) {
		new Lab23();
	}
}