import java.util.*;
import java.io.*;
import java.lang.*;

public class Dragon2 {
	private DragonArray dragon;
	//private ArrayList<Integer> xpos;
	//private ArrayList<Integer> ypos;
	private int[] xpos;
	private int[] ypos;


	public Dragon2(int n) {
		final long startTime = System.nanoTime();
		//dragon = new ArrayList<Integer>((int)Math.pow(2, n)-1);
		Integer hej = new Integer(5);
		dragon = new DragonArray();
		for (int i = 0; i < n; i++) {
			DragonArray temp = new DragonArray();
			temp.setLeft(dragon);
			temp.setRight(dragon);
			dragon.setRoot(temp);
			dragon = temp;
		}
		
		Integer[] test = dragon.toList(1, 0, n); //ger korrekt resultat


		
		System.out.println("Total execution time: " + ((double)(System.nanoTime()-startTime)/1000000) + " ms.");
		//System.out.println(Arrays.toString(test));
		//System.out.println(dragon.toString(1));

	}


	public class DragonArray implements Iterator<Integer>{
		private DragonArray left;
		private int mid;
		private DragonArray right;
		private DragonArray root;

		public DragonArray() {
			mid = 1;
		}

		public void setRoot(DragonArray r) {
			root = r;
		}

		public Integer next() { //den här delen kan dra åt skogen
			Integer next;
			if (left != null) {
				next = left.next();
			}
			next = new Integer(mid);
			if (right != null) {
				next = right.next();
			}
			return next;
		}

		public boolean hasNext() { //den här också
			return (right != null);
		}

		public void setLeft(DragonArray d) {
			left = d;
		}

		public void setRight(DragonArray d) {
			right = d;
		}

		public DragonArray getLeft() {
			return left;
		}

		public int getMid(int n) {
			return mid*n;
		}

		public DragonArray getRight() {
			return right;
		}

		public Integer[] toList(int n, int level, int maxlevel) {
			Integer[] t = new Integer[(int) Math.pow(2, maxlevel-level+1)-1];
			int size = (int) Math.pow(2, maxlevel-level)-1;
			
			if (left != null) {
				Integer[] tl = getLeft().toList(1, level+1, maxlevel);
				for (int i = 0; i < size; i++) {
					t[i] = tl[i];
				}
			}
			t[size] = n*mid;
			if(right != null) {
				Integer[] tr = getRight().toList(-1, level+1, maxlevel);
				for (int i = 0; i < size; i++) {
					t[size+1+i] = tr[i];
				}
			}
			return t;
		}

		public String toString(int n) {
			StringBuilder t = new StringBuilder();
			if (left != null) {
				t.append(getLeft().toString(1));
			}
			t.append(Integer.toString(n*mid));
			if (right != null) {
				t.append(getRight().toString(-1));
			}
			return t.toString();
		}
	}



	public static void main(String[] args) {
		Dragon2 instance = new Dragon2(Integer.valueOf(args[0]));
	}

}