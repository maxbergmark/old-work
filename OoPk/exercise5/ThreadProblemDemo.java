

public class ThreadProblemDemo extends Thread {

	static long sum;

	static Object lock = new Object();

	public void run() {
		for (int i = 0; i < 1000000; i++) {
			synchronized(lock) {
				sum += i;
				sum %= 1234;
			}
		}
	}

	public static void main(String[] args) {
		ThreadProblemDemo t1 = new ThreadProblemDemo();
		ThreadProblemDemo t2 = new ThreadProblemDemo();
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (Exception e) {

		}
		System.out.println(sum);
	}
}