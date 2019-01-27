public class DepthFirst extends Component {
	
	public DepthFirst(String name, int weight) {
		super(name, weight);
	}
	public static void goSearch() {
		while(suitcaseStructure.hasNext()) {
			System.out.println(suitcaseStructure.next());
		}
	}


	public static void main(String[] args) {
		DepthFirst hej = new DepthFirst("suitcase", 10);
		DepthFirst hej2 = new DepthFirst("bag", 3);
		hej.add(hej2);
		hej.add(new DepthFirst("sweater", 2));
		hej2.add(new DepthFirst("sock cake", 12));
		System.out.println(hej);
		System.out.println(hej.getWeight());
		System.out.println(hej2.getWeight());
		goSearch();
		
	}
}