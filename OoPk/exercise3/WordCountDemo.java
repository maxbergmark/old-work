import java.util.regex.*;
import java.util.ArrayList;

public class WordCountDemo {


	public static void printMatches(String s, ArrayList<Integer> l) {
		System.out.println(s);
		int c = 0;
		String show = "";
		for (int i = 0; i < s.length(); i++) {
			if (c >= l.size() || i < l.get(c)) {
				show += " ";
			} else {
				show += "M";
				c++;
			}
		}
		System.out.println(show);
	}
	public static void main(String[] args) {
		System.out.println("\n\n");
		String s;
		if (args.length > 0) {
			s = "";
			for (String a: args) {
				s += a + " ";
			}
			s.trim();
		} else {
			s = "Ett test där ett räknas, orden ettan och dubblett inte räknas. ett!ett ett";
		}
		s = s.toLowerCase();
		int lastIndex = 0;
		String target = "ett";
		int count = 0;
		ArrayList<Integer> simpleIndices = new ArrayList<>();
		while (lastIndex != -1) {
			lastIndex = s.indexOf(target, lastIndex);
			if (lastIndex != -1) {
				count++;
				simpleIndices.add(lastIndex);
				lastIndex += target.length();
			}
//			System.out.println(lastIndex);
		}
		printMatches(s, simpleIndices);
		System.out.println(String.format("Med första metoden hittades %d förekomster\n\n\n", count));

//    String patternStr = "(^| )+ett[,.!? ]+";
//    String patternStr = "([., !?]|^)+ett([,.!? ]|$)+";
    String patternStr = "([.,!? ]|^)ett([ .,!?]|$)";

    Pattern pattern = Pattern.compile(patternStr);
    Matcher matcher = pattern.matcher(s);
    ArrayList<Integer> indices = new ArrayList<>();
    count = 0;
    if (matcher.find()){
    	do {
    		count++;
//    		System.out.println(matcher.start());
    		indices.add(matcher.start());
    	} while (matcher.find(matcher.start()+1));
//	    System.out.println(matcher.start());//this will give you index
    }
    printMatches(s, indices);
	System.out.println(String.format("Med andra metoden hittades %d förekomster", count));


	}
}