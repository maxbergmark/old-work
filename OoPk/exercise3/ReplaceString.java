

public class ReplaceString {

	public static void main(String[] args) {
		String s = "plocka bort alla förekomster av ordet alla, men inte hallar, så de är borta";
		//s = s.replace("alla", "");
		s = s.replaceAll("\\balla\\b","");
		System.out.println(s);
	}
}