import java.util.HashMap;


public class Word {
	private String word;
	private HashMap<String, Integer> prevWords = new HashMap<>();
	private HashMap<String, Integer> nextWords = new HashMap<>();
	private int freq;

	public Word(String newWord) {
		word = newWord;
	}	

	public void addNext(String nextWord) {
		Integer nextFreq = nextWords.get(nextWord);
		if (nextFreq != null) {
			nextWords.put(nextWord, ++nextFreq);
		} else {
			nextWords.put(nextWord, 1);
		}
	}

	public void addPrev(String prevWord) {
		Integer prevFreq = prevWords.get(prevWord);
		if (prevFreq != null) {
			prevWords.put(prevWord, ++prevFreq);
		} else {
			prevWords.put(prevWord, 1);
		}
	}
}