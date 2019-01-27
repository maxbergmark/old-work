import java.util.*;

public class TestComp extends CloneComponent {
	
	public TestComp(String name, int weight) {
		super(name, weight);
	}


	public static void main(String[] args) {
		TestComp hej = new TestComp("0,0", 10);
		TestComp hej2 = new TestComp("0,1", 3);
		hej.add(hej2);
		TestComp hej3 = new TestComp("1,1", 2);
		hej.add(hej3);
		hej3.add(new TestComp("2,2", 2));
		hej3.add(new TestComp("3,2", 2));
		hej3.add(new TestComp("4,2", 2));

		hej.add(new TestComp("2,1", 2));
		hej.add(new TestComp("3,1", 2));
		hej2.add(new TestComp("0,2", 12));
		hej2.add(new TestComp("1,2",5));
		//System.out.println(hej);
		//System.out.println(hej2);
		System.out.println(hej2);
		TestComp typ = (TestComp) hej2.clone();
		System.out.println(typ);
		hej2.add(new TestComp("2,2",10));
		System.out.println("hej2 "+hej2);
		System.out.println("typ "+typ);
		//System.out.println(hej.getWeight());
		//System.out.println(hej2.getWeight());
		/*
		Iterator asdf = hej.iterator();
		while (asdf.hasNext()) {
			TestComp fdsa = (TestComp) asdf.next();
			System.out.println("next: " + fdsa.getName());
		}*/
	}
}