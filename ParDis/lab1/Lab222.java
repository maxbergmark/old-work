/*
Modify the program to use "synchronized" to ensure that increments 
on the shared variable are atomic.
*/



public class Lab222 implements Runnable {

	static Object o = new Object();
	static int sum = 0;
	int id;

	Lab222(int n) {
		this.id = n;
	}

	public void run() {
		for (int i = 0; i < 1000000; i++) {
			synchronized (o) {
				sum++;
			}
		}
	}

	public static void main(String[] args) {
		int n = 5;
		Thread[] thr = new Thread[n];
		for (int i = 0; i < n; i++) {
			thr[i] = new Thread(new Lab222(i));
			thr[i].start();
		}
		for (int i = 0; i < n; i++) {
			try {
				thr[i].join();
			} catch (Exception e) {}
		}
		System.out.println(sum);
	}
}