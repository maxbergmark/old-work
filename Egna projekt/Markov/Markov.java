import java.util.HashMap;
import java.util.ArrayList;
import java.util.*;
import java.util.regex.*;

public class Markov {
	private ArrayList<String> text = new ArrayList<String>();
	private HashMap<String, Word> words = new HashMap<>();

	public Markov() {
		//text.add("This is the way that the game is played. That is not how I play it.");
		text.add("This.");
		analyzeText();
	}

	public String[] splitSentences(String str) {
		ArrayList<String> retList = new ArrayList<String>();
		Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
    	Matcher reMatcher = re.matcher(str);
	    while (reMatcher.find()) {
	    	String tempSentence = reMatcher.group();
	    	tempSentence = tempSentence.replaceAll("[.]", "").toLowerCase();
	        //System.out.println(tempSentence);
	        retList.add(tempSentence);
	    }
	    String[] stringArray = retList.toArray(new String[retList.size()]);
	    return stringArray;
	}

	public String[] addPadding(String[] arr) {
		String[] retArr = new String[arr.length+2];
		System.arraycopy(arr, 0, retArr, 1, arr.length);
		retArr[0] = "";
		retArr[arr.length+1] = "";		
		return retArr;
	}

	public String[] splitWords(String str) {
		return addPadding((str).split(" "));
	}


	public void analyzeText() {
		for (String t : text) {
			System.out.println(t);
			String[] sentences = splitSentences(t);
			for (String s : sentences) {
				String[] sentenceWords = splitWords(s);
				for (int i = 0; i < sentenceWords.length; i++) {
					System.out.println(sentenceWords[i]);
					Word w = words.get(sentenceWords[i]);
					if (w == null) {
						words.put(sentenceWords[i], new Word(sentenceWords[i]));
						w = words.get(sentenceWords[i]);
					} 
					if (i > 0) {
						w.addPrev(sentenceWords[i-1]);
					}
					if (i < sentenceWords.length-1) {
						w.addNext(sentenceWords[i+1]);
					}
					words.put(sentenceWords[i], w);
				}
			}
		}
	}


	public static void main(String[] args) {
		new Markov();
	}
}