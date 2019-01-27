import java.util.*;
public class HW8 {
	public static void main(String[] args) {
		int earnings = 0;
		int wins = 0;
		int endgames = 0;
		for (int c = 0; c < 1<<20; c++) {
			List<Integer> cards = new ArrayList<Integer>();
			for (int i = 0; i < 53; i++) {
				cards.add(i/26);
			}
			Collections.shuffle(cards);
			int bc = 0;
			int rc = 0;
			int stop = 52;
			earnings--;
			for (int k = 0; k < 53; k++) {
				int i = cards.get(k);
				bc += i==0?0:1;
				rc += i==0?1:0;
				if (rc+1< bc) {
					stop = k+1;
					endgames++;
					break;
				}
			}
			if (cards.get(stop) == 0) {earnings+=2;wins++;}
			if (c%10000==0) {
				System.out.println(earnings + "   " + wins + "   " + endgames);
			}
		}
		System.out.println(53*earnings/(double)(1<<20));
	}
}