/*
The monitor obj.wait() operation locks obj's intrinsic object 
until the calling thread has been suspended. Give an example 
program likely to exhibit unexpected behaviour if suspension 
was not protected by a lock.
*/

public class Lab26 implements Runnable {

	static int sum = 0;
	static Object lock = new Object();
	static Lab26 l0;
	static Lab26 l1;
	int id;

	public Lab26(int id) {
		this.id = id;
	}




	public void run() {
		if (id == 0) {

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
			sum = 1;
			synchronized(l1) {
				l1.notify();
			}
		}
		if (id == 1) {
			try {
				synchronized(this) {
					wait();
				}
			} catch (InterruptedException e) {}
			System.out.println("hej: " + sum);
		}

	}



	public static void main(String[] args) {
		l0 = new Lab26(0);
		l1 = new Lab26(1);
		Thread t0 = new Thread(l0);
		Thread t1 = new Thread(l1);
		t0.start();
		t1.start();
		try {
			t0.join();
			t1.join();
		} catch (InterruptedException e) {

		}
		System.out.println("Done!");
	}
}