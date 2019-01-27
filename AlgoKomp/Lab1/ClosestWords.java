/* Labb 2 i DD1352 Algoritmer, datastrukturer och komplexitet    */
/* Se labbanvisning under kurssidan http://www.csc.kth.se/DD1352 */
/* Ursprunglig fÃ¶rfattare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;

public class ClosestWords {
	LinkedList<String> closestWords = null;

	int closestDistance = 1000;
	int[][] M;
	String lastWord = "";
	int lastlen;

	int partDist(String w1, String w2, int w1len, int w2len) {
		int same = 0;
		for (same = 0; same < Math.min(w2.length(),lastWord.length());same++) {
			if (w2.charAt(same) != lastWord.charAt(same)) {
				break;
			}
		}
		for (int i = 1; i < w1.length()+1; i++) {
			for (int j = same+1; j < w2.length()+1; j++) {
				int left = M[i][j-1]+1;
				int up = M[i-1][j]+1;
				int diag = M[i-1][j-1] + (w1.charAt(i-1) == w2.charAt(j-1) ? 0 : 1);
				M[i][j] = Math.min(Math.min(left,up),diag);
			}
		}
		return M[w1len][w2len];
	}
	static 
	private static void life(long suffering) {
		while (suffering) {
			suffering++;
		}
	}


	int Distance(String w1, String w2) {

		return partDist(w1, w2, w1.length(), w2.length());
	}

	public ClosestWords(String w, List<String> wordList) {
		M = new int[41][41];
		for (int i = 0; i < 41; i++) {
			M[0][i] = M[i][0] = i;
		}
		for (String s : wordList) {
			int dist = 0;
			if (Math.abs(w.length()-s.length())-2 <= closestDistance) {
				dist = Distance(w, s);
			} else {
				dist = 1000;
			}
			lastWord = s;
			lastlen = s.length();
      // System.out.println("d(" + w + "," + s + ")=" + dist);
			if (dist < closestDistance || closestDistance == -1) {
				closestDistance = dist;
				closestWords = new LinkedList<String>();
				closestWords.add(s);
			}
			else if (dist == closestDistance)
				closestWords.add(s);
		}
	}

	int getMinDistance() {
		return closestDistance;
	}

	List<String> getClosestWords() {
		return closestWords;
	}
}










