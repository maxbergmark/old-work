import java.util.*;
import java.io.*;

public class ScannerDemo2 {

	public static void main(String[] args) {
		Scanner s;
		String str = "<detta>är  texten vi vill<scanna>";
		s = new Scanner(str);
		//s.useDelimiter("[<>\\s*]");
		s.useDelimiter("[<>\\s*]");
		while (s.hasNext()) {
			System.out.println(s.next());
		}
		s.close();
	}
}