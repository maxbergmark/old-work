

public class VariableAssignTest {
	int a;
	int b;
	static int c;

	public VariableAssignTest(int a, int b) {
		this.a = a;
		this.b = b;
	}

	public VariableAssignTest() {
		this(c = (int)(100*Math.random()), 2*c);
	}

	public String toString() {
		return String.format("%d   %d\n", a, b);
	}

	public static void main(String[] args) {
		VariableAssignTest t = new VariableAssignTest();
		System.out.println(t);
	}
}