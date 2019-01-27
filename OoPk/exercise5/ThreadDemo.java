









public class ThreadDemo extends Thread {
	
	public void run() {
		System.out.println("Hello from thread!");
	}

	public static void main(String[] args) {
		ThreadDemo thr = new ThreadDemo();
		thr.start();
		try {
			Thread.sleep(500);
		} catch(Exception e) {}
		System.out.println("Hello from main!");
	}
}
