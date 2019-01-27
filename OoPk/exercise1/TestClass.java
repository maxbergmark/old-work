

public class TestClass {

	private int n;
	
	TestClass(int nIn) {
		this.n = nIn;
	}

	public static int mirrorValue(int a, int b) 
	throws Exception {
		if (b==0) {
			throw new Exception("testfel");
		}	
		return (int) (a/b);
	}

	public int getN() {
		return n;
	}

	public static void main(String[] args) {
		//TestClass test = new TestClass(5);
		//TestClass test2 = new TestClass(7);
		try {
			System.out.println(mirrorValue(25, 0));
		} catch (ArithmeticException e) {

		} catch (Exception e) {
			System.out.println("FEL! " + e);
		}
		//System.out.println(test.getN());
		//System.out.println(test2.getN());
		System.out.println("Kommer vi hit?");
	}
}