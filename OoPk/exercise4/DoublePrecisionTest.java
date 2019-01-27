

public class DoublePrecisionTest {

	public static void main(String[] args) {
		float d = 10.0f;
		float s = 0.0f;
		int n = 1000000000;
		for (int i = 0; i < n; i++) {
			float e = Math.abs(s-d*i);
			if (i % 100000 == 0) {
				System.out.println(e/(d*i));
				//System.out.println(s);
			}
			s += d;

		}
		System.out.println(s);
		System.out.println(d*n);
	}
}