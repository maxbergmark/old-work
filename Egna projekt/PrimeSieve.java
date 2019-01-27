import java.util.*;


public class PrimeSieve {

	public PrimeSieve() {
		long t0 = System.nanoTime();
		int[] test = sieve1(100);
		long t1 = System.nanoTime();
		System.out.println(Arrays.toString(test));

	}

	public int[] sieve1(int n) {
		HashMap<Integer, Integer> nums = new HashMap<Integer, Integer>();
		for (int i = 2; i < n; i++) {
			for (int j = 0; j < n; j++) {
				nums.put(i*j, 0);
			}
		}
		int[] test = new int[n];
		int c = 0;
		for (int i = 0; i < n; i++) {
			if (nums.get(i) == null) {
				test[c++] = i;
				System.out.println(c);
			} else {
				System.out.println(i);
			}
		}
		return test;
	}


	public static void main(String[] args) {
		PrimeSieve test = new PrimeSieve();
	}
}