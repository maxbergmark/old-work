import java.util.*;

public class Test {

	public static void main(String[] args) {

		Composite bag = new Composite("bag");
		Composite item1 = new Composite("item1");
		Composite item2 = new Composite("item2");
		Leaf leaf1 = new Leaf("leaf1");
		Leaf leaf2 = new Leaf("leaf2");
		Leaf leaf3 = new Leaf("leaf3");
		Leaf leaf4 = new Leaf("leaf4");
		bag.add(item1);
		bag.add(item2);
		item1.add(leaf1);
		item1.add(leaf2);
		item2.add(leaf3);
		item2.add(leaf4);

		Iterator t = bag.iterator();
		while (t.hasNext()) {
			System.out.println(t.next());
		}
		System.out.println();
		for (Composite c : bag) {
			System.out.println(c);
		}
	}
}