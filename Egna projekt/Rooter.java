import java.util.*;

public class Rooter {

	public static ArrayList<Integer> root() {
		ArrayList<Integer> nums = new ArrayList<Integer>();
		for (int i = 0; i < 1000000; i++) {
			if ((int) Math.sqrt(i)* (int) Math.sqrt(i) == i) {
				if (!nums.contains(i & 0b1111)) {
					nums.add(i & 0b1111);
				}
			}
		}
		return nums;

	}

	public static void main(String[] args) {
		double n = Double.parseDouble(args[0]);
		ArrayList<Integer> r = root();
		System.out.println(r);
	}
}