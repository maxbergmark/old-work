

public class ReplaceDemo {

	public static void main(String[] args) {
		String s = "plocka bort alla förekomster av ordet alla, men inte hallar. alla ska bort, inget alla kvar.";
		s = s.replace("alla", "");
		//s = s.replaceAll("\\balla\\b", "");
		System.out.println(s);
	}
}