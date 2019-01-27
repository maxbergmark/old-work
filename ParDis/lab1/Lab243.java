/*
In the Dining Philosophers Problem there are five philosophers sitting 
around a round table, as shown on the figure below. Each philosopher 
has a plate and a chopstick shared with the left- and right hand neighbour. 

Each philosopher alternately thinks and eats. To eat, a philosopher 
obviously needs two chopsticks. Using some of the mechanisms introduced 
in the lab, implement a solution to the Dining Philosophers Problem that 
ensures that each philosopher who some time tries to eat eventually does so. 

Your solution should work for an arbitrary number of philosophers. 
The solution should be deadlock-free, i.e. not be able to reach a
configuration that cannot progress, and starvation-free, i.e. in any 
infinite run, all philosophers get to eat infinitely often. Argue 
carefully why your solution meets these requirements. 

[See also exercise 1 in Herlihy and Shavit. There are a number of 
solutions to this problem on the web and in textbooks. Try to come up 
with a solution on your own, and declare if you have resorted to an 
external source for input.] 
*/
import java.util.*;


public class Lab243 implements Runnable {

	static Lab243[] philosophers;
	static Thread[] threads;
	static Semaphore[] semaphores;
	static Semaphore mainSemaphore;
	static int[] eatTimes;
	static volatile Queue<Integer> pQueue;
	static Object lock = new Object();
	static int n = 5;
	static int speed = 5;
	int id;

	public Lab243(int id) {
		this.id = id;
	}

	public static void addToQueue(int id) {
			pQueue.add(id);
			mainSemaphore.take();
	}

	public void managePhilosophers() {

		while (true) {
			synchronized (lock) {
				System.out.print("\r  " + Arrays.toString(eatTimes) + "    ");
			}
			int first;
			try {
				mainSemaphore.release();
			} catch (InterruptedException e) {}
			synchronized (lock) {
				first = pQueue.poll();
			}
			// System.out.print("f" + first);
			try {
				semaphores[first].release();
			} catch (InterruptedException e) {}
			try {
				semaphores[(first+1)%n].release();
			} catch (InterruptedException e) {}

			eatTimes[first]++;
			semaphores[first].take();
			semaphores[(first+1)%n].take();
			synchronized(philosophers[first]) {
				philosophers[first].notify();
			}
		}
	}


	public void run() {
		if (id == -1) {
			managePhilosophers();
		} else {
			while (true) {

				try {
					Thread.sleep((int) (speed*Math.random()));
				} catch (InterruptedException e) {}


				try {
					synchronized(this) {
						synchronized (lock) {
							addToQueue(this.id);
							//System.out.print(pQueue.toString());
							//System.out.print(" w" + this.id);
						}
						wait();
					}
				} catch (InterruptedException e) {System.out.println("woke up " + this.id);}
				synchronized(lock) {
					// System.out.print(pQueue.toString());
					// System.out.print("  r" + this.id);
				}
			}
		}

	}





	private static class Semaphore {

		volatile int n = -1;

		public int getN() {
			return n;
		}

		public synchronized void take() {
			n++;
			notify();
		}

		public synchronized void release() throws InterruptedException {
			while (n < 0) { wait();}
			n--;
		}
	}



	public static void main(String[] args) {
		Thread mainThread = new Thread(new Lab243(-1));
		philosophers = new Lab243[n];
		threads = new Thread[n];
		eatTimes = new int[n];
		semaphores = new Semaphore[n];
		mainSemaphore = new Semaphore();
		for (int i = 0; i < n; i++) {
			semaphores[i] = new Semaphore();
			semaphores[i].take();
		}
		pQueue = new LinkedList<>();
		for (int i = 0; i < n; i++) {
			philosophers[i] = new Lab243(i);
			threads[i] = new Thread(philosophers[i]);
			threads[i].start();
		}
		mainThread.start();
		try {
			mainThread.join();
			for (int i = 0; i < n; i++) {
				threads[i].join();
			}
		} catch (InterruptedException e) {}
	}
}