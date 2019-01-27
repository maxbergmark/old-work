public class WidthFirst extends Component {
	
	public WidthFirst(String name, int weight) {
		super(name, weight);
	}


	public static void main(String[] args) {
		WidthFirst hej = new WidthFirst("suitcase", 10);
		WidthFirst hej2 = new WidthFirst("bag", 3);
		WidthFirst hej3 = new WidthFirst("case",1);
		hej.add(hej2);
		hej.add(hej3);
		hej.add(new WidthFirst("sweater", 2));
		hej2.add(new WidthFirst("sock cake", 12));
		hej2.add(new WidthFirst("hejsan", 3));
		System.out.println(hej);
		System.out.println(hej.getWeight());
		System.out.println(hej2.getWeight());
		
		hej.widthFirst();
	}
}