import java.util.*;

public class TestClass {

	public static void main(String[] args) {
		Random r = new Random();
		int n = 20000000;
		boolean[] bA = new boolean[32*n];
		int[] iA = new int[n];
		/*
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 32; j++) {
				bA[32*i+j] = Math.random() < 0.5;
			}
			int temp = 0;
			for (int j = 0; j < 4; j++) {
				temp<<=8;
				temp += r.nextInt(256);
			}
			iA[i] = temp;
		}
		*/
		long t0 = System.nanoTime();
		for (int i = 0; i < n; i++) {
			int g = r.nextInt(32*n);
			int s = r.nextInt(32*n);
			boolean b = bA[g];
			bA[s] = true;
		}
		long t1 = System.nanoTime();
		for (int i = 0; i < n; i++) {
			int g = r.nextInt(32*n);
			int s = r.nextInt(32*n);
			boolean b = (iA[g/32]&(1<<(g%32)))>0;
			iA[s/32] |= 1<<(s%32);
		}
		long t2 = System.nanoTime();
		System.out.println((t1-t0)/1000/1000.0);
		System.out.println((t2-t1)/1000/1000.0);
	}
}