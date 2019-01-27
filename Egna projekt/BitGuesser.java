import java.util.Random;
import java.util.LinkedList;



public class BitGuesser {

	private int[] setOnes;
	private Random rand = new Random();

	public BitGuesser(int n) {
		long t0 = System.nanoTime();
		setOnes = new int[(1<<8)];
		for (int i = 0; i < (1<<8); i++) {
			setOnes[i] = popcount(i);
		}

		for (int i = 0; i < n; i++) {
			int length = 1000;
			int totGuess = 0;
			long[] secrets = new long[length];
			System.out.println("playing game: " + (i+1));
			for (int j = 0; j < length; j++) {
				//secrets[j] = (long)(rand.nextInt((long) ((1L<<i) + 1L)));
				secrets[j] = nextLong(rand, (long) ((1L<<i) + 1L));

				//System.out.println("secret: " + secrets[j]);
				//System.out.println();
				totGuess += playGame(secrets[j], i+1);
			}
			System.out.println(totGuess / (double) length);
		}
		long t1 = System.nanoTime();
		System.out.println((t1-t0)/(double) 1000000000);
	}

	public long nextLong(Random rng, long n) {
		// error checking and 2^x checking removed for simplicity.
		long bits, val;
		do {
			bits = (rng.nextLong() << 1) >>> 1;
			val = bits % n;
		} while (bits-val+(n-1) < 0L);
		return val;
	}

	public int playGame(long secret, int length) {
		int turns = 0;
		LinkedList<Long[]> wrongs = new LinkedList<Long[]>();		
		long score = 0;

		long guess = 0;



		turns++;

		
		score = getScore(guess, secret, length);
		if (score == length) {
			return turns;
		}
		wrongs.add(new Long[] {guess, score});
		
		guess = (1<<(length-score))-1;
		turns++;
		long score2 = getScore(guess, secret, length);
		wrongs.add(new Long[] {guess, score2});
		if(score2 == length) {
			return turns;
		}

		int num1s = (int) (length+score2-2*score)/2;
		int num0s = (int) (length-score2)/2;
		long guessx = ((1<<(num0s))-1)<<(length-score);
		long guessy = ((1<<(num1s))-1);


		guess = guessx ^ guessy;
		long score3 = getScore(guess, secret, length);
		turns++;
		wrongs.add(new Long[] {guess, score3});
		if(score3 == length) {
			return turns;
		}
		
		

		while (guess >= 0) {
			if (score == length) {
				return turns;
			}
			turns++;
			guess = makeGuess(wrongs, length);
			score = getScore(guess, secret, length);
			wrongs.add(new Long[] {guess, score});
			//System.out.println(getString(length, guess) + "  " + getString(length, secret));

		}
		return turns;
	}

	public String getString(int length, long num) {
		String numStr = Long.toBinaryString(num);
		String addStr = "";
		for (int i = 0; i < length-numStr.length(); i++) {
			addStr += "0";
		}
		
		return addStr + numStr;
	}

	public long getScore(long guess, long secret, int length) {
		return (long) length - calcOnes((long) (guess ^ secret));
	}


	public long makeGuess(LinkedList<Long[]> wrongs, int length) {
		int s = wrongs.size();
		if (s == 0) {
			return 0;
		}
		for (long i = wrongs.get(s-1)[0]; i < 2L<<length; i++) {
			int k = 0;
			for (Long[] wrong: wrongs) {
				k++;
				if (length - calcOnes(i^wrong[0]) != wrong[1]) {
					break;
				} else if (k == s) {
					return i;
				}
			}
		}
		return -1;
	}

	public int calcOnes(long num) {
		int totOnes = 0;
		while (num > 0) {
			totOnes += (int) setOnes[(int) num & 255];
			num >>= 8;
		}
		return totOnes;
	}

	public int popcount(int v) {
	    v = v - ((v >> 1) & 0x55555555);                // put count of each 2 bits into those 2 bits
	    v = (v & 0x33333333) + ((v >> 2) & 0x33333333); // put count of each 4 bits into those 4 bits  
	    return v = ((v + (v >> 4) & 0xF0F0F0F) * 0x1010101) >> 24;
	}


	public static void main(String[] args) {
		BitGuesser instance = new BitGuesser(Integer.valueOf(args[0]));

	}
}