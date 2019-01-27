import java.util.*;
import java.io.*;
import java.util.regex.*;

public class Ruzzle {

	//abcdefghijklmnop

	// abcd
	// efgh
	// ijkl
	// mnop

	char[][] boardArray;
	byte[][] bonusArray;
	HashSet<String> words;
	HashSet<String> pre5;
	ArrayList<Word> finalWords;
	HashMap<Character, Integer> points;

	public Ruzzle(String board, String bonus) {
		processBoard(board);
		processBonus(bonus);
		byte[][] b = new byte[4][4];
		words = new HashSet<>();
		pre5 = new HashSet<>();
		finalWords = new ArrayList<>();
		points = new HashMap<>();
		fillPoints();
		readWords();
		readPrefix();
		startIteration();
//		iterate(b, 0, null, null);
		printResult();
	}

	public void processBonus(String bonus) {
		bonusArray = new byte[4][4];
		for (int i = 0; i < 4; i++) {
			bonusArray[i] = new byte[4];
		}
		int count = 0;
		String patternStr = "[0-9]{2}[a-z]{2}";

		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(bonus);
		ArrayList<Integer> indices = new ArrayList<>();
		count = 0;
		if (matcher.find()){
			do {
				count++;
	//			System.out.println(matcher.start());
				indices.add(matcher.start());
			} while (matcher.find(matcher.start()+1));
	//		System.out.println(matcher.start());//this will give you index
		}
		for (int i : indices) {
			try {

				int b1 = Character.getNumericValue(bonus.charAt(i));
				int b2 = Character.getNumericValue(bonus.charAt(i+1));
				String bv = bonus.substring(i+2, i+4);
				byte bi = 0;
				if (bv.equals("dl")) {
					bi = 1;
				}
				if (bv.equals("tl")) {
					bi = 2;
				}
				if (bv.equals("dw")) {
					bi = 3;
				}
				if (bv.equals("tw")) {
					bi = 4;
				}
				bonusArray[b1-1][b2-1] = bi; 

			} catch (Exception e) {
				System.out.println("Input error!");
			}
		}
	}

	public void fillPoints() {
		points.put('a', 1);
		points.put('b', 3);
		points.put('c', 8);
		points.put('d', 1);
		points.put('e', 1);
		points.put('f', 3);
		points.put('g', 2);
		points.put('h', 3);
		points.put('i', 1);
		points.put('j', 7);
		points.put('k', 3);
		points.put('l', 2);
		points.put('m', 3);
		points.put('n', 1);
		points.put('o', 2);
		points.put('p', 4);
		points.put('q', 1	);
		points.put('r', 1);
		points.put('s', 1);
		points.put('t', 1);
		points.put('u', 4);
		points.put('v', 3);
		points.put('w', 1	);
		points.put('x', 8);
		points.put('y', 7);
		points.put('z', 8);
		points.put('å', 4);
		points.put('ä', 4);
		points.put('ö', 4);
	}

	public int getScore(Word w) {
		int score = 0;
		for (int i = 0; i < w.length; i++) {
			score += points.get(w.word.charAt(i));
		}
		int mult = 1;
		int add = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (w.used[i][j] > 0) {
					if (bonusArray[i][j] == 1) {
						add += points.get(boardArray[i][j]);
					}
					if (bonusArray[i][j] == 2) {
						add += 2*points.get(boardArray[i][j]);
					}
					if (bonusArray[i][j] == 3) {
						mult *= 2;
					}
					if (bonusArray[i][j] == 4) {
						mult *= 3;
					}
				}
			}
		}
		int lScore = 0;
		if (w.length >= 5) {
			lScore = (w.length-4)*5;
		}
		return (score+add)*mult+lScore;
	}


	public void printResult() {
		HashSet<String> printed = new HashSet<>();
		Collections.sort(finalWords, new Comparator<Word>() {
			public int compare(Word w1, Word w2) {
				return Integer.compare(w2.score, w1.score);
			} 
		});
		for (int i = 0, j = 0; j < Math.min(15, finalWords.size()); j++) {
			String curr = finalWords.get(j).word;

			if (!printed.contains(curr)) {
				printWord(finalWords.get(j));
				printed.add(curr);
				i++;
			}

		}
	}

	public void readPrefix() {
		try {
			BufferedReader r = new BufferedReader(new FileReader("prefixlist5.txt"));
			String s;
			while ((s = r.readLine()) != null) {
				pre5.add(s);
			}
		} catch (Exception e) {

		}		
	}

	public void readWords() {
		try {
			BufferedReader r = new BufferedReader(new FileReader("sscopy.txt"));
			String s;
			while ((s = r.readLine()) != null) {
				words.add(s.toLowerCase());
			}
		} catch (Exception e) {

		}
	}

	public void printWord(Word w) {
		System.out.println(w.word + "  " + w.score);
		printPath(w.firstPos, w.used);
	}

	public void printPath(int[] firstPos, byte[][] used) {
		// System.out.println("+----+");
		for (int i = 0; i < 4; i++) {
			System.out.print("|");
			for (int j = 0; j < 4; j++) {
				System.out.print((firstPos[0] == i && firstPos[1] == j) ? boardArray[i][j] : " ");
			}
			System.out.print("| |");
			for (int j = 0; j < 4; j++) {
				System.out.print(used[i][j] > 0 ? boardArray[i][j] : " ");
			}
			System.out.println("|");
		}
		// System.out.println("+----+");
	}

	public void startIteration() {
		byte[][] used = new byte[4][4];
		for (int i = 0; i < 4; i++) {
			used[i] = new byte[] {0, 0, 0, 0};
		}
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				iterate(used, 1, new int[] {i, j}, new StringBuilder(), new int[] {i, j});
			}
		}
		
	}

	public void iterate(byte[][] used, int level, int[] lastPos, StringBuilder s, int[] firstPos) {

		used[lastPos[0]][lastPos[1]] = 1;
		s.append(boardArray[lastPos[0]][lastPos[1]]);

		if (level == 5) {
			String word5 = s.toString();
			if (pre5.contains(word5)) {
				// System.out.println(word5);
				// printPath(used);
				// System.out.println();
			} else {
				s.setLength(s.length()-1);
				used[lastPos[0]][lastPos[1]] = 0;
				return;
			}
		}

		if (level > 5) {
			// System.out.println(s);
			if (words.contains(s.toString())) {
				byte[][] usedCopy = new byte[4][4];
				for (int i = 0; i < 4; i++) {
					usedCopy[i] = new byte[4];
					System.arraycopy(used[i], 0, usedCopy[i], 0, 4);
				}
				Word w = new Word(s.toString(), usedCopy, level, firstPos);
				w.addScore(getScore(w));
				finalWords.add(w);
			}
		}

		for (int i = Math.max(0, lastPos[0]-1);
			i < Math.min(4, lastPos[0]+2); i++) {

			for (int j = Math.max(0, lastPos[1]-1);
				j < Math.min(4, lastPos[1]+2); j++) {

				if (used[i][j] == 0) {
					// System.out.println(i + "   " + j);
					// System.out.println(Arrays.deepToString(used));
					iterate(used, level+1, new int[] {i, j}, s, firstPos);
				}

			}

		}
		s.setLength(s.length() - 1);
		used[lastPos[0]][lastPos[1]] = 0;

	}

	public static class Word {

		public String word;
		public byte[][] used;
		public int length;
		public int[] firstPos;
		public int score;

		public Word(String word, byte[][] used, int length, int[] firstPos) {
			this.word = word;
			this.used = used;
			this.length = length;
			this.firstPos = firstPos;
		}

		public void addScore(int score) {
			this.score = score;
		}
	}

	private void processBoard(String board) {
		boardArray = new char[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				boardArray[i][j] = board.charAt(4*i+j);
			}
		}
	}


	public static void main(String[] args) {

		System.out.println("Board: ");
		// String inp = System.console().readLine();
		Scanner reader = new Scanner(System.in);
		String inp = reader.nextLine();
		System.out.println("Bonuses: ");
		String bonus = reader.nextLine();
		// String bonus = System.console().readLine();
		Ruzzle r = new Ruzzle(inp, bonus);

		// Ruzzle r = new Ruzzle("frensonsnitkalov", "22dw24tw32dw33tl42tl43dl");
		
	}

}