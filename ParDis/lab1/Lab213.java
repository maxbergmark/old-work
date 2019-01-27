/*
Modify the printed string to include the thread number; 
ensure that all threads have a unique thread number.
*/
public class Lab213 implements Runnable {

	static Object o = new Object();
	int id;

	Lab213(int n) {
		this.id = n;
	}

	public void run() {
		synchronized (o) {
			System.out.println("Hello, World! This is thread "
			 + id + " reporting.");
		}
	}

	public static void main(String[] args) {
		Thread[] t = new Thread[5];
		for (int i = 0; i < 5; i++) {
			t[i] = new Thread(new Lab213(i));
			t[i].start();
		}
		for (int i = 0; i < 5; i++) {
			try {
				t[i].join();
			} catch (InterruptedException e) {
				
			}
		}
	}
}