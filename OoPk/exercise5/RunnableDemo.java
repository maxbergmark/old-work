



public class RunnableDemo implements Runnable {
	

	public void run() {
		System.out.println("Hello from thread!");
	}

	public static void main(String[] args) {
		Thread thr = new Thread(new RunnableDemo());
		thr.start();
		System.out.println("Hello from main!");
	}
}
