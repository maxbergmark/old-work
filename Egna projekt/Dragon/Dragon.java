import java.util.*;




public class Dragon {
	private ArrayList<Integer> dragon;
	private byte[] dragon2 = {1};
	private boolean[] dragon3 = {true};
	private short[] xpos;
	private short[] ypos;

	public Dragon(int n) {
		System.out.println("Iterations: " + n);
		final long startTime = System.nanoTime();
		//dragon = new ArrayList<Integer>((int)Math.pow(2, n)-1);
		dragon = new ArrayList<Integer>();
		dragon.add(1);
		for (int i = 0; i < n; i++) {
			//dragon = iterateFast(dragon);
			dragon2 = iterateArray(dragon2, (i == n-1));
			//dragon3 = iterateBoolean(dragon3);
		}
		if (n == 0) {
			dragon2 = new byte[] {1, 0};
		}
		dragon.add(0);
		double dragonTime = ((double)(System.nanoTime()-startTime)/1000000);
		final long startTime2 = System.nanoTime();
		//createDragon(dragon);
		createDragonFast(dragon2);
		//createDragonBoolean(dragon3);
		double coordTime = ((double)(System.nanoTime()-startTime2)/1000000);
		double totTime = ((double)(System.nanoTime()-startTime)/1000000000);
		double perSec = (long)(3)*dragon2.length/totTime;
		//double perSec = dragon.size()*3/totTime;
		System.out.println("Dragon array created in: " + dragonTime + " ms.");
		System.out.println("Coordinate array created in: " + coordTime + " ms.");
		System.out.println("Total execution time: " + totTime*1000 + " ms.");
		System.out.println("Array length: " + dragon2.length);
		System.out.println("Created " + perSec + " elements per second.");
		//System.out.println(Arrays.toString(dragon2));
		System.out.println((12+1*(dragon2.length+1))/1024/1024);
	}

	public byte[] iterateArray(byte[] drag, boolean addExtra) {
		int dragLength = drag.length;
		byte[] retdrag = new byte[dragLength*2+1+(addExtra ? 1 : 0)];
		System.arraycopy(drag, 0, retdrag, 0, dragLength);
		retdrag[dragLength] = drag[dragLength/2];
		for (int i = 1; i < dragLength+1; i++) {
			retdrag[dragLength+i] = (byte)(-1*drag[dragLength-i]);
		}
		return retdrag;
	}

	public boolean[] iterateBoolean(boolean[] drag) {
		int dragLength = drag.length;
		boolean[] retDrag = new boolean[dragLength*2+1];
		System.arraycopy(drag, 0, retDrag, 0, dragLength);
		retDrag[dragLength] = drag[dragLength/2];
		for(int i = 1; i < dragLength+1; i++) {
			retDrag[dragLength+i] = !drag[dragLength-i];
		}
		return retDrag;
	}

	public void createDragonFast(byte[] drag) {
		short[] ew = {1, 0, -1, 0};
		short[] ns = {0, 1, 0, -1};
		xpos = new short[drag.length+1];
		ypos = new short[drag.length+1];
		byte direction = 0;
		for(int i = 0; i < drag.length; i++) {
			xpos[i+1] = (short)(xpos[i]+ew[direction%4]);
			ypos[i+1] = (short)(ypos[i]+ns[direction%4]);
			direction += drag[i];
		}
	}

	public void createDragonBoolean(boolean[] drag) {
		short[] ew = {1, 0, -1, 0};
		short[] ns = {0, 1, 0, -1};
		xpos = new short[drag.length+2];
		ypos = new short[drag.length+2];
		byte direction = 0;
		for(int i = 0; i < drag.length; i++) {
			xpos[i+1] = (short)(xpos[i]+ew[direction%4]);
			ypos[i+1] = (short)(ypos[i]+ns[direction%4]);
			direction += drag[i] ? 1 : -1;
		}
	}

	public void createDragon(ArrayList<Integer> drag) {
		int[] ew = {1, 0, -1, 0};
		int[] ns = {0, 1, 0, -1};
		int[] xpos2 = new int[drag.size()+1];
		int[] ypos2 = new int[drag.size()+1];
		int direction = 0;
		for(int i = 0; i < drag.size(); i++) {
			xpos2[i+1] = xpos2[i]+ew[direction%4];
			ypos2[i+1] = ypos2[i]+ns[direction%4];
			direction += drag.get(i);
		}
	}

	public ArrayList<Integer> iterate(ArrayList<Integer> drag) { //Slow and memory-inefficient
		ArrayList<Integer> revdrag = new ArrayList<Integer>(drag);
		Collections.reverse(revdrag);
		for (int i = 0; i < revdrag.size(); i++) {
			revdrag.set(i, -1*revdrag.get(i));
		}
		drag.add(drag.get(drag.size()/2));
		drag.addAll(revdrag);
		return drag;
	}

	public ArrayList<Integer> iterateFast(ArrayList<Integer> drag) { //Fast and optimised
		drag.add(drag.get(drag.size()/2));		
		for (int i = drag.size()-2; i > 0; i--) {
			drag.add(-1*drag.get(i));
		}
		return drag;
	}	


	public static void main(String[] args) {
		Dragon instance = new Dragon(Integer.valueOf(args[0]));

	}
}