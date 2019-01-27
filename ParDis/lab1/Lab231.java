/*
Write a short program in which one thread increments an integer 
1,000,000 times, and a second thread prints the integer -- without 
waiting for it to finish.
*/


public class Lab231 implements Runnable {

	// static Object o = new Object();
	static int sum = 0;
	int id;

	Lab231(int n) {
		this.id = n;
	}

	public void run() {
		if (id == 0) {
			for (int i = 0; i < 1000000; i++) {
				sum++;
			}
		}
		if (id == 1) {
			System.out.println(sum);
		}
		
	}

	public static void main(String[] args) {
		int n = 2;
		Thread[] thr = new Thread[n];
		for (int i = 0; i < n; i++) {
			thr[i] = new Thread(new Lab231(i));
			thr[i].start();
		}
		for (int i = 0; i < n; i++) {
			try {
				thr[i].join();
			} catch (Exception e) {}
		}
	}
}