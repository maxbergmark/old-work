import java.util.Arrays;

public class StringSortDemo {

	public static void main(String[] args) {
		String s;
		if (args.length > 0) {
			s = "";
			for (String a: args) {
				s += a + " ";
			}
			s.trim();
		} else {
			s = "En exempelsträng";
		}
		char[] charArray = s.toCharArray();
		Arrays.sort(charArray);
		String sortedString = new String(charArray);
		System.out.println(sortedString);
	}
}