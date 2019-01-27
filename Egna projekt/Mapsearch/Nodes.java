import java.util.*;

public class Nodes {
	Node[] ns;
	Random r = new Random(3);
	public Nodes() {
		ns = new Node[100];
		for (int i = 0; i < 100; i++) {
			ns[i] = new Node(r.nextInt(3));
		}
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if (i != j && r.nextDouble() < 0.5){ns[i].addConn(j);}
			}
		}
		int k = 0;
		int t = 0;
		for (int i = 0; i < 100; i++) {
			for (int j : ns[i].getConns()) {
				if (i != j){
					t++;
					if (ns[i].getCol() != ns[j].getCol()) {
						k++;
					}
				}
			}
		}
		//System.out.println(k + "/" + t + "   " + (k/(double)t));

		for (int i = 0; i < 100; i++) {
			int[] cs = {0,0,0};
			for (int j : ns[i].getConns()) {
				if (i != j){
					t++;
					cs[ns[j].getCol()]++;
				}
			}
			if (cs[0] <= cs[1] && cs[0] <= cs[2]) {
				ns[i].setCol(0);
			} else if (cs[1] <= cs[0] && cs[1] <= cs[2]) {
				ns[i].setCol(1);
			} else if (cs[2] <= cs[1] && cs[2] <= cs[0]) {
				ns[i].setCol(2);
			}
		}



		k = 0;
		t = 0;
		for (int i = 0; i < 100; i++) {
			for (int j : ns[i].getConns()) {
				if (i != j){
					t++;
					if (ns[i].getCol() != ns[j].getCol()) {
						k++;
					}
				}
			}
		}
		if ((k/(double)t) < 2/3.0) {System.out.println("asdf");}
		System.out.println(k + "/" + t + "   " + (k/(double)t));


	}


	private class Node {
		HashSet<Integer> conns;
		int col;
		public Node(int i) {
			conns = new HashSet<Integer>();
			col = i;
			//col = 0;
		}
		public void addConn(int i) {
			conns.add(i);
		}
		public int getCol() {return col;}
		public void setCol(int i) {col = i;}
		public HashSet<Integer> getConns() {return conns;}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Nodes();
		}
	}
}