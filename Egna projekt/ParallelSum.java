

public class ParallelSum extends Thread {

	public static int[] arr;
	public int min;
	public int max;
	public int sum;

	ParallelSum(int min, int max) {
		this.min = min;
		this.max = max;
	}

	public void run() {
		if (max == min) {
			this.sum = 0;
		} else if (max-min == 1) {
			this.sum = arr[min];
		} else {
			ParallelSum lS = new ParallelSum(min, min + (max-min)/2);
			ParallelSum rS = new ParallelSum(min + (max-min)/2, max);
			lS.start();
			rS.start();
			try {
				lS.join();
				rS.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.sum = lS.sum + rS.sum;
		}
	}

	public static int sum(int[] a) {
		ParallelSum.arr = a;
		ParallelSum s = new ParallelSum(0, a.length);
		s.start();
		try {
			s.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s.sum;
	}

	public static void main(String[] args) {
		int[] a = new int[1000];
		for (int i = 0; i < a.length; i++) {
			a[i] = i;
		}
		int s = sum(a);
		System.out.println(s);
	}
}