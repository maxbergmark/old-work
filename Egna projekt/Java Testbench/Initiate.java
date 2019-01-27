

public class Initiate {


	public Initiate() {
		Node head = new Node("head");
		Node last = head;
		for (int i = 0; i < 1000000; i++) {
			Node n = new Node("chain" + i);
			last.addNext(n);
			n.addPrev(last);
			last = n;
		}

		print(head);
	}

	public void print(Node n) {
		if (n == null) {
			return;
		} else {
			System.out.println(n.text);
			print(n.next);
		}
	}


	public class Node {
		public Node prev;
		public Node next;
		public String text;

		public Node(String text) {
			this.text = text;
		}

		public void addPrev(Node n) {
			this.prev = n;
		}

		public void addNext(Node n) {
			this.next = n;
		}
	}

	public static void main(String[] args) {
		Initiate i = new Initiate();
	}
}