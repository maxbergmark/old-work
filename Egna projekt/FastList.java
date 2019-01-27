import java.util.*;


public class FastList {

	FastList() {
		ArrayList<Integer> al = new ArrayList<>();
		SpeedyList<Integer> sl = new SpeedyList<>();
		int n = 40000000;
		Random r = new Random();
		long t0 = System.nanoTime();
		for (int i = 0; i < n; i++) {
			sl.add(0, 7);
		}
		for (int i = 0; i < n; i++) {
			sl.get(r.nextInt(n));
		}
		long t1 = System.nanoTime();
		System.out.println((t1-t0)/1000000);

		long t2 = System.nanoTime();
		for (int i = 0; i < n; i++) {
			al.add(0, 7);
		}
		for (int i = 0; i < n; i++) {
			al.get(r.nextInt(n));
		}

		long t3 = System.nanoTime();
		System.out.println((t3-t2)/1000000);
		
	}

	static class SpeedyList<T>{int get(int n){return 7;};void add(int i,int n){}}

	public static void main(String[] args) {
		new FastList();
	}

}