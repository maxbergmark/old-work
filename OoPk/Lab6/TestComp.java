

public class TestComp extends Component {
	
	public TestComp(String name, int weight) {
		super(name, weight);
	}


	public static void main(String[] args) {
		TestComp hej = new TestComp("suitcase", 10);
		TestComp hej2 = new TestComp("bag", 3);
		hej.add(hej2);
		hej.add(new TestComp("sweater", 2));
		hej.add(new TestComp("sweater2", 2));
		hej2.add(new TestComp("sock", 12));
		hej2.add(new TestComp("handbag",5));
		//System.out.println(hej);
		System.out.println(hej);
		//typ = hej2.clone();
		//System.out.println(typ);
		System.out.println(hej.getWeight());
		//System.out.println(hej2.getWeight());
	}
}