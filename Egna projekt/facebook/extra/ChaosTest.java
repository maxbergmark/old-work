

public class ChaosTest {

	public static double chaos(int iters, double l, double start) {
		for (int i = 0; i < iters; i++) {
			start = l*start*(1-start);
		}
		return start;
	}


	public static void main(String[] args) {
		for (double l = 0.1; l < 10; l += 0.1) {


		double start = 0.5;
//		double l = 3.49;
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		int iters = 10000000;
		double[] values = new double[iters];
		values[0] = start;
		int buck = 50;
		int[] buckets = new int[buck+1];
		for (int i = 1; i < iters; i++) {
			values[i] = chaos(1, l, values[i-1]);
			min = values[i] < min ? values[i] : min;
			max = values[i] > max ? values[i] : max;
//			System.out.println();
		}
		max = Math.max(start, max);
		min = Math.min(start, min);
		System.out.println(String.format("Min: %.5f\nMax: %.5f", min, max));
		int maxBucket = 0;
		for (double d : values) {
			int i = (int) (buck * (d - min) / (max-min));
			buckets[i]++;
			maxBucket = buckets[i] > maxBucket ? buckets[i] : maxBucket;

		}
		for (int i : buckets) {
			int mod = 70*i/maxBucket;
			for (int k = 0; k < mod; k++) {
				System.out.print("*");
			}
			System.out.println();
		}
		}
	}
}