import java.util.Random;

public class RandomTimer {

	public RandomTimer() {
		Random rand = new Random(4);
		int[] randList = new int[100000];
		int tries = 10000;
		for (int i = 0; i < randList.length; i++) {
			randList[i] = rand.nextInt(randList.length);
		}


		long time1 = System.nanoTime();
		int j = 0;
		
		for (int i = 0; i < tries; i++) {
			for (int m = 0; m < randList.length; m++) {
				if (randList[m] < randList.length/2) {
					j++;
				}
			}
		}
		
		long time2 = System.nanoTime();
		System.out.println((double) (time2-time1)/1000000000.0 + " seconds");
		


		long time3 = System.nanoTime();
		int k = 0;

		for (int m = 0; m < randList.length; m++) {
			for (int i = 0; i < tries; i++) {
				if (randList[m] < randList.length/2) {
					k++;
				}
			}
		}
		
		long time4 = System.nanoTime();
		System.out.println((double) (time4-time3)/1000000000.0 + " seconds");
	}

	public static void main(String[] args) {
		new RandomTimer();
	}
}