import java.util.Arrays;

public class SortString {

	public static void main(String[] args) {
		String s = "En exempelsträng";
		char[] ca = s.toCharArray();
		Arrays.sort(ca);
		String sorted = new String(ca);
		System.out.println(sorted);
	}
}