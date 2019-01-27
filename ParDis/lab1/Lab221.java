/*
Write a short program in which two threads both increment a shared 
integer repeatedly, without proper synchronisation, 1,000,000 times, 
printing the resulting value at the end of the program. 
Run the program on a multicore system and attempt to exercise 
the potential race in the program.
*/
public class Lab221 implements Runnable {

	static Object o = new Object();
	static int sum = 0;
	int id;

	Lab221(int n) {
		this.id = n;
	}

	public void run() {
		// synchronized (o) {
		for (int i = 0; i < 1000000; i++) {
			sum++;
		}
		// }
	}

	public static void main(String[] args) {
		int n = 5;
		Thread[] thr = new Thread[n];
		for (int i = 0; i < n; i++) {
			thr[i] = new Thread(new Lab221(i));
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