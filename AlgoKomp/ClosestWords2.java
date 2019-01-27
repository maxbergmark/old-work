/* Labb 2 i DD1352 Algoritmer, datastrukturer och komplexitet    */
/* Se labbanvisning under kurssidan http://www.csc.kth.se/DD1352 */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;

public class ClosestWords2 {
  LinkedList<String> closestWords = null;

  int closestDistance = -1;
  private String lastWord;
  private int[][] M;

  int Distance(String w1, String w2) {
      int same = 0;
      for (same = 0; same < Math.min(w2.length(),lastWord.length());same++) {
          if (w2.charAt(same) != lastWord.charAt(same)) {
              break;
          }
      }
      for (int i = 1; i <= w1.length() ; i++) {
          for (int j = same+1; j <= w2.length() ; j++) {
              int a = M[i][j-1]+1;
              int b = M[i-1][j]+1;
              int c = M[i-1][j-1];
              c += w1.charAt(i-1) == w2.charAt(j-1) ? 0 : 1;
              M[i][j] = Math.min(a,Math.min(b,c));
          }
      }
      return M[w1.length()][w2.length()];
  }

  public ClosestWords2(String w, List<String> wordList) {
    M = new int[41][41];
    for (int i = 0; i < 41;i++) {
        M[i][0] = i;
        M[0][i] = i;
    }
    lastWord = "";
    for (String s : wordList) {
      int dist = Distance(w, s);
      lastWord = s;
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
