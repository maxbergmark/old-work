

public class Arithmetic {
	
	public int randN() {
		return random(1,6);
	}

	public static void main(String[] args) {
		int a = 2;
		int b = a++;
		int c = ++a;
		System.out.println(a+"\t"+b+"\t"+c);
		if (randN() == 6) {
			doSomething();
		} else if (randN() == 3) {
			doSomethingElse();
			break;
		}

	}
}