import java.util.regex.*;

public class EmailDemo {

	public static void main(String[] args) {
		String s;
		if (args.length > 0) {
			s = args[0];
		} else {
			s = "max.bergmark@gmail.com";
		}
		System.out.println(s);
		String p = "^[a-zA-Z][a-zA-Z0-9_\\.]{4,14}@[a-zA-Z0-9]{1,10}\\.(com|se)$";
		Pattern pattern = Pattern.compile(p);
    	Matcher matcher = pattern.matcher(s);
    	if (matcher.find()) {
    		System.out.println("valid email address");
    	} else {
    		System.out.println("invalid!");
    	}
	}
}