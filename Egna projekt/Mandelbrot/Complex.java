

public class Complex {

	double a;
	double b;

	public Complex(double a, double b) {
		this.a = a;
		this.b = b;
	}

	public double getA() {
		return this.a;
	}

	public double getB() {
		return this.b;
	}

	public double abs() {
		return Math.sqrt(a*a+b*b);
	}

	public static Complex add(Complex c1, Complex c2) {
		return new Complex(c1.a+c2.a, c1.b+c2.b);
	}

	public void add(Complex c) {
		a = a+c.a;
		b = b+c.b;
	}

	public static Complex sub(Complex c1, Complex c2) {
		return new Complex(c1.a-c2.a, c1.b-c2.b);
	}

	public static Complex mult(Complex c1, Complex c2) {
		return new Complex(c1.a*c2.a-c1.b*c2.b, c1.a*c2.b+c1.b*c2.a);
	}

	public void mult(Complex c) {
		double ta = a;
		double ca = c.a;
		a = a*c.a-b*c.b;
		b = ta*c.b+b*ca;
	}

	public String toString() {
		return String.format("%7.3e + %7.3ei", a, b);
	}

	public Complex copy() {
		return new Complex(a, b);
	}
}