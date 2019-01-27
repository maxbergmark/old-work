

public class Primitives {

	public static void main(String[] a) {
		byte b = 127;
		char c = 'A';
		int i = 0x7fffffff;
		long l = 0x7fffffffL;
		double d = Math.pow(3.14159, 2);

		System.out.println(b);
		System.out.println(c);
		System.out.println(i + "\t" + (i+1));
		System.out.println(l + "\t " + (l+1));
		System.out.println(d);
	}

}