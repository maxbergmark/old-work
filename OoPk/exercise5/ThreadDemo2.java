

public class ThreadDemo2 implements Runnable {

	public void run() {
		System.out.println("Hello from thread!");
	}

	public static void main(String[] args) {
		Thread test = new Thread(new ThreadDemo2());
		test.start();
		System.out.println("Hello from main!");
	}
}