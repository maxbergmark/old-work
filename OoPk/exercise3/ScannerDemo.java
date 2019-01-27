import java.util.*;
import java.io.*;

public class ScannerDemo {

	public static void main(String[] args) {
		String s = "<detta>är texten vi vill<scanna>";
		Scanner sc;
		try {
			sc = new Scanner(new File("textdemo.txt"));
		} catch (Exception e) {
			return;
		}
		//sc.useDelimiter("[<>\\s]");

		while (sc.hasNextLine()) {
			System.out.println(sc.nextLine());
		}

		sc.close();
	}
}