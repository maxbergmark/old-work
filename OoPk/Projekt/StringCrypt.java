


public class StringCrypt {
	String test;
	String output;

	public StringCrypt(String text, String cryptoType, String key, boolean cryptOrDecrypt) {
		test = "abcdef";
		output = "";

		for(int i = 0; i < 6; i++) {
			char a = test.charAt(i);
			int b = (int) a;
			output += (char) (b+5);
			System.out.println(a + "   " + b);
		}
		System.out.println(output);
	}

	public static void main(String[] args) {
		StringCrypt hej = new StringCrypt();
	}
}