

public class B extends A {
	B(int n) {
		super(n);
	}

	public static void main(String[] a) {
		B b = new B(5);
		b.getN();
	}	
}