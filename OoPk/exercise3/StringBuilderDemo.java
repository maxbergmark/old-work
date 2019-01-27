

public class StringBuilderDemo {

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		String s = "";
		long t0 = System.nanoTime();
		for (int i = 0; i < 100000; i++) {
			s += "a";
		}
		long t1 = System.nanoTime();
		for (int i = 0; i < 100000; i++) {
			sb.append("a");
		}
		long t2 = System.nanoTime();
		System.out.println((t1-t0)/(t2-t1));

	}
}