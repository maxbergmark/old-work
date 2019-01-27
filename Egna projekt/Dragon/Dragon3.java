

public class Dragon3 {
	private FastStorage dragon;
	private short[] xpos;
	private short[] ypos;

	public Dragon3(int n) {
		System.out.println("Iterations: " + n);
		final long startTime = System.nanoTime();
		dragon = new FastStorage((int)Math.pow(2,n+1)-1);
		dragon.setSize(1);
		for (int i = 0; i < n; i++) {
			iterate(dragon);
		}
		double dragonTime = ((double)(System.nanoTime()-startTime)/1000000);
		System.out.println(dragonTime);
		double memSize = (12+8*(dragon.getSize()/64+1))/1024/1024;
		//System.out.println(dragon);
		System.out.println(memSize);
		
	}

	public class FastStorage {
		public long[] storage;
		public int size;
		
		public FastStorage(int n) {
			storage = new long[(n-1)/64+1];
			size = n;
		}
		public int getSize() {
			return size;
		}

		public void setSize(int n) {
			size = n;
		}

		public void expand(int n) {
			long[] tempStorage = new long[(n-1)/64+1];
			System.arraycopy(storage, 0, tempStorage, 0, (size-1)/64+1);
			storage = tempStorage;
			size = n;
		}

		public void toggle(int n) {
			storage[n/64] ^= (1L << n);
		}
		public void set(int n, long val) {
			storage[n/64] ^= (val << n);
		}
		public void set2(int n, int ind) {
			storage[n/64] ^= (~storage[ind/64] & (1L << ind)) << (n-ind);
		}

		public long get(int n) {
			return (storage[n/64]) & (1L << n);
		}
		public long getInv(int n) {
			return ~(storage[n/64]) & (1L << n);			
		}
		public String toString() {
			String t = "";
			for (int i =  0; i < storage.length; i++) {
				t += Long.toBinaryString(storage[i]);
			}
			return t;
		}

	}

	public void iterate(FastStorage drag) {
		int dragLength = drag.getSize();
		drag.setSize(2*dragLength+1);
		//drag.toggle(dragLength);
		for (int i = 1; i < dragLength+1; i++) {
			//drag.set2(dragLength+i, dragLength-i);
			drag.storage[(dragLength+i)/64] ^= (~drag.storage[(dragLength-i)/64] & (1L << (dragLength-i))) << (2*i);
			//drag.set(dragLength+i, drag.getInv(dragLength-i));
		}
	}

	public static void main(String[] args) {
		Dragon3 instance = new Dragon3(Integer.valueOf(args[0]));
	}
}