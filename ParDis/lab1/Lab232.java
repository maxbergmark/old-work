/*
Modify the program to use a condition variable to signal 
completion of the incrementing task by the first thread before 
the second thread prints the value.
*/

public class Lab232 implements Runnable {

	static Object o = new Object();
	static int sum = 0;
	static volatile boolean finished = false;
	int id;

	Lab232(int n) {
		this.id = n;
	}

	public void run() {
		if (id == 0) {
			for (int i = 0; i < 1000000; i++) {
				sum++;
			}
			finished = true;
		}
		if (id == 1) {
			while (!finished) {try {Thread.sleep(1);} catch (Exception e) {}}
			System.out.println(sum);
		}
		
	}

	public static void main(String[] args) {
		int n = 2;
		Thread[] thr = new Thread[n];
		for (int i = 0; i < n; i++) {
			thr[i] = new Thread(new Lab232(i));
			thr[i].start();
		}
		for (int i = 0; i < n; i++) {
			try {
				thr[i].join();
			} catch (Exception e) {}
		}
	}
}