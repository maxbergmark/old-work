import java.io.*;

public class PrintToFile {

	public static void main(String[] args) {
		try {
			File f = new File("output.txt");
			PrintWriter pw = new PrintWriter(f);
			for (String s : args) {
				pw.println(s);
			}
//			pw.flush();
			pw.close();
		} catch (Exception e) {}
	}
}