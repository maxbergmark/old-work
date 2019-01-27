

public class DeadLockEx {

	static Object lock = new Object();

	public static void main(String[] args) {
		int a = 5;
		synchronized(lock) {
			synchronized(lock) {
				System.out.println("test");
			}
		}
	}
}