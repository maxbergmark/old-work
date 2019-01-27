

public class Operators {
	public static void main(String[] args) {
		int a = 1;
		int b = a++;
		int c = ++a;
		int d = (c = (a = ++b))+1;
		System.out.println("a=" + a);
		System.out.println("b=" + b);
		System.out.println("c=" + c);
		System.out.println("d=" + d);
	}
}