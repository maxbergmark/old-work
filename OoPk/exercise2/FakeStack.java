

public class FakeStack {
	static int stack = 0;

	static void push() {
		stack++;
	}
	static int pop() throws Exception {
		if (stack == 0) {
			throw new Exception("tom stack!");
		}
		return stack--;
	}

	public static void main(String[] a) {
		push();
		push();
		try {
			System.out.println(pop());
			System.out.println(pop());
			System.out.println(pop());
			System.out.println(pop());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}