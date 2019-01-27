

public class ExceptTest {
	
	private static int[] array = {0,1,2,3,4,5,6,7,8,9};

	private static void iterateArray(int n) throws ArrayIndexOutOfBoundsException, Exception {
		for (int i = 0; i < n; i++) {
			System.out.println(array[i]);
		}
//		int a = 1/0;
//		throw new Exception("testa exception");
		throw new ArithmeticException("pretend error");
	}


	public static void main(String[] a) {
		try {
			iterateArray(5);
			System.out.println();
			iterateArray(15);
		} catch (ArithmeticException e) {
			System.out.println("Found Arithmetic Exception: " + e);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Found Array Exception: " + e);
		} catch (Exception e) {
			System.out.println("Found Other Exception: " + e);
		}
	}
}