

public class ThreadDemo extends Thread {

	public void run() {
		System.out.println("Hello from thread!");
	}

	public static void main(String[] args) {
		ThreadDemo test = new ThreadDemo();
		test.start();
		System.out.println("Hello from main!");
	}
}