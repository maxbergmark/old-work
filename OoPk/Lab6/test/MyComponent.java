import java.awt.Component;
import java.util.*;

public class MyComponent extends Component implements Iterable<Composite> {
	public String name;
	public MyComponent(String name) {
		this.name = name;
	}

	public Iterator<Composite> iterator() {
		return new WidthFirst((Composite) this);
	}

	public class WidthFirst implements Iterator<Composite> {

		Composite root;
		Queue<Composite> queue;
		Stack<Composite> stack;
		ArrayList<Composite> order;
		int count;

		public WidthFirst(Composite root) {
			this.root = root;
			this.count = 0;
			queue = new LinkedList<>();
			stack = new Stack<>();
			order = new ArrayList<>();
			queue.add(root);
			stack.push(root);
			fillQueue();
		}

		public void fillQueue() {

			while (!queue.isEmpty()) {
			// while (!stack.empty()) {
				Composite c = queue.poll();
				// Composite c = stack.pop();
				order.add(c);
				if (!(c instanceof Leaf)) {
					for (Composite c2 : c.getChildren()) {
						queue.add(c2);
						stack.push(c2);
					}
				}
			}
		}


		public boolean hasNext() {
			return count < order.size();
		}

		public Composite next() {
			return order.get(count++);
		}
	}

	public String toString() {
		return name;
	}


}