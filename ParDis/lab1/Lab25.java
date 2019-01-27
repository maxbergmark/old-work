/*
Deadlocks are an inherent problem in concurrent systems using 
locks or other blocking primitives. Implement a deadlock 
involving two threads and two locks in Java. What debugging 
tools does the Java environment offer that might help us 
debug this deadlock.
*/

public class Lab25 implements Runnable {

	static Integer sum0 = 0;
	static Integer sum1 = 0;
	int id;

	public Lab25(int id) {
		this.id = id;
	}



	public void sendOneWay() {
		while (true) {
			synchronized(sum0) {
				synchronized(sum1) {
					sum0--;
					sum1++;
				}
			}
			System.out.println("OneWay: " + sum0 + "   " + sum1);
		}
	}

	public void sendOtherWay() {
		while (true) {
			synchronized(sum1) {
				synchronized(sum0) {
					sum0++;
					sum1--;
				}
			}
			System.out.println("OtherWay: " + sum0 + "   " + sum1);
		}
	}



	public void run() {
		if (id == 0) {
			sendOneWay();
		}
		if (id == 1) {
			sendOtherWay();
		}

	}



	public static void main(String[] args) {
		Thread t0 = new Thread(new Lab25(0));
		Thread t1 = new Thread(new Lab25(1));
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