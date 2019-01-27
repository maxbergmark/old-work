/*
Modify the program to print "Hello world" five times, 
once from each of five different threads. Ensure that 
the strings are not interleaved in the output.
*/
public class Lab212 implements Runnable {

	static Object o = new Object();

	public void run() {
		synchronized (o) {
			System.out.println("Hello, World!");
		}
	}

	public static void main(String[] args) {
		Thread[] t = new Thread[5];
		for (int i = 0; i < 5; i++) {
			t[i] = new Thread(new Lab212());
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