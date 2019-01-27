import java.io.*;
import java.util.*;

public class PrintWriterDemo {

	public static void main(String[] args) {
		PrintWriter p;
		try {
			p = new PrintWriter(new File("output.txt"));
		} catch (Exception e) {
			System.out.println(e);
			return;
		}
		for (String s : args) {
			p.println(s);
		}
		p.flush();
		p.close();
	}
}