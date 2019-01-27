import java.util.*;


public class FastMath {
	public static void main(String[] args) {
		int n = 200000;
		HashSet<Long> s = new HashSet<Long>(2*n);
		long u = 1;
		long t0 = System.nanoTime();
		for (int i = 0; i < n; i++) {
			//System.out.println(u);
			u = ((u&1L)==1?9*u+1:u/2);

			
			if (s.contains(u)) {
				System.out.println("Match! " + u);
			}
			s.add(u);
			
		}
		long t1 = System.nanoTime();
		System.out.println((t1-t0)/1000000.0 + "ms");
	}
}