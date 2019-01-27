import java.util.*;

public class HW9 {
	public static void main(String[] args) {
		int totc = 0,test = 66;
		for (int i = 0; i < 1<<15;i++) {
			Set<Integer> set = new HashSet<Integer>();
			Random rand = new Random();
			int c = 0;
			while (set.size() < test) {
				set.add(rand.nextInt(test));
				c++;
			}
			totc += c;
		}
		System.out.println(totc/(double)(1<<15));
	}
	
}