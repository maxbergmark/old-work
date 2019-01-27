


public class VolatileTest implements Runnable {

	static volatile int i = 0;
	public void run() {
		for (int k = 0; k < 1000000; k++) {
			i++;
		}
	}

	public static void main(String[] args) {
		Thread t0 = new Thread(new VolatileTest());
		Thread t1 = new Thread(new VolatileTest());
		t0.start();
		t1.start();
		try {
			t0.join();
			t1.join();
		} catch (Exception e) {}
		System.out.println(i);
	}
}