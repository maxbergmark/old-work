import java.util.HashMap;

public class FindOdd {
	public static int findIt(int[] A) {
		HashMap<Integer, Integer> m = new HashMap<>();
		for (int i : A) {
			Integer g = m.get(i);
			if (g == null) {
				m.put(i, 1);
			} else {
				m.put(i, g+1);
			}
		}
		for (Integer i : m.keySet()) {
//			System.out.println(i + "\t" + m.get(i));
			if (m.get(i) % 2 == 1) {
				return i;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		int[] a = {1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4};
		System.out.println(findIt(a));
	}

}