/*
In the producer-consumer problem we have a producer thread wishing 
to pass data to a consumer thread using a shared, fixed-size buffer. 
Implement a ProducerConsumer class using synchronized, wait(), and 
notify() in Java, and use it to pass a sequence of integer values 
from one thread (the producer) to a second that prints them (the consumer).
*/


public class Lab241 implements Runnable {

	int id;
	static Thread pThread;
	static Thread cThread;
	static int latest;
	static int newLine = 0;

	public Lab241(int n) {
		this.id = n;
	}

	public static int getLatest() {
		return latest;
	}

	public void produce() {
		while (true) {
			try {
				Thread.sleep((int)(1000*Math.random()));
			} catch (InterruptedException e) {

			}
			latest = (int) (1000*Math.random());
			synchronized (cThread) {
				cThread.notify();
			}
		}
	}

	public void consume() {
		while (true) {
			try {
				synchronized (cThread) {
					cThread.wait();
				}
			} catch (InterruptedException e) {

			}
			System.out.print(String.format("%3d ",getLatest()));
			if (++newLine % 15 == 0) {
				System.out.println();
			}
		}
	}

	public void run() {
		if (id == 0) {
			produce();
		}
		if (id == 1) {
			consume();
		}

	}



	public static void main(String[] args) {
		pThread = new Thread(new Lab241(0));
		cThread = new Thread(new Lab241(1));
		pThread.start();
		cThread.start();
		try {
			pThread.join();
			cThread.join();
		} catch (InterruptedException e) {
			
		}
	}
}