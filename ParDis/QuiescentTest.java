import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class QuiescentTest implements Runnable {

	static AtomicInteger counter = new AtomicInteger(0);
	static int[] list;
	int id;

	QuiescentTest(int i) {
		this.id = i;
	}

	public void run() {
		int i = getCount();
		list[id] = i;
	}

	static int getCount() {
		return counter.addAndGet(1);
	}




	public static void main(String[] args) {
		AtomicInteger test = new AtomicInteger((2<<30)-100);
		while (!test.compareAndSet(Integer.MIN_VALUE, 0)) {
			test.getAndIncrement();
			System.out.println(test);
		}
		int n = 10000;
		Thread[] t = new Thread[n];
		list = new int[n];
		for (int i = 0; i < n; i++) {
			t[i] = new Thread(new QuiescentTest(i));
			t[i].start();
		}
		try {
			for (int i = 0; i < n; i++) {
				t[i].join();
			}
		} catch (Exception e) {}
		Arrays.sort(list);
		for (int i = 0; i < n-1; i++) {
			if (list[i] == list[i+1]) {
				System.out.println(i + "   " + list[i] + "   " + list[i+1]);
				System.out.println("error");
			}
		}
	}
}