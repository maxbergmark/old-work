/*
A (counting) semaphore is a shared variable with two atomic 
operations signal() and wait(). 

Semaphores are used to govern access to some shared resource. 

A positive value n of the semaphore indicates that there are n amounts 
of resource available. 

signal() increments the value of the semaphore by 1 to indicate that a 
resource has been made available. This causes a waiting thread 
to be activated, if at time of calling the value of the semaphore 
was negative. 

wait() decrements the value of the semaphore by 1, if the value of 
the semaphore at time of calling was not negative. 

If the value of the semaphore is now negative, the calling 
thread is suspended. 

Implement a counting semaphore using 
synchronized(), wait(), and notify() in Java.
*/
import java.util.*;



public class Lab242 implements Runnable {

	static Thread pThread;
	static Thread cThread;
	static Thread cThread2;
	static Semaphore semaphore;
	static Queue<Integer> q;
	static int prints = 0;

	int id;

	public Lab242(int n) {
		this.id = n;
	}

	public void run() {

		if (id == 0 || id == 2) {
			produce();
		}

		if (id == 1) {
			consume();
		}
	}

	public void produce() {

		while (true) {
/*
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
*/
			synchronized(semaphore) {
				q.add((int) (1000*Math.random()));
			}
			semaphore.take();
		}
	}

	public void consume() {
		while (true) {
			try {
				semaphore.release();
			} catch (InterruptedException e) {}
			synchronized(semaphore) {
				int val = q.poll();
				System.out.print(String.format("%3d ", val));
			}
			if (++prints % 15 == 0) {
				System.out.println();
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
		semaphore = new Semaphore();
		q = new LinkedList<>();
		pThread = new Thread(new Lab242(0));
		cThread = new Thread(new Lab242(1));
		cThread2 = new Thread(new Lab242(0));
		pThread.start();
		cThread.start();
		cThread2.start();
		try {
			pThread.join();
			cThread.join();
			cThread2.join();
		} catch (Exception e) {}
	}
}