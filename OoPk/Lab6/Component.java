import java.util.ArrayList;
import java.util.List;

public abstract class Component {
	private String name;
	private int weight;
	private List<Component> suitcaseStructure;
	
	public Component(String name, int weight){
		this.name = name;
		this.weight = weight;
		suitcaseStructure = new ArrayList<Component>();
	}
	
	public void add(Component thing){
		suitcaseStructure.add(thing);
	}
	
	public void remove(Component thing){
		suitcaseStructure.remove(thing);
	}
	
	public List<Component> getStructure(){
		return suitcaseStructure;
	}
	
	public String toString(){
		String t = name;
		for (Component c : suitcaseStructure) {
			t += " which contains " + c.toString() + ",";
		}
		if (t.endsWith(",")) {
			t = t.substring(0, t.length()-1);
		}
		return t;

	}

	public int getWeight(){
		int w = weight;
		for (Component c : suitcaseStructure) {
			w += c.getWeight();
		}
		return w; 

	}
/*
	public static void main(String[] args) {
		Component test1 = new Component("suitcase");
		Component test2 = new Component("bag");
		Component test3 = new Component("socks");
		Component test4 = new Component("sweater");
		Component test5 = new Component("case");
		Component test6 = new Component("rubber band");
		test1.add(test2);
		test2.add(test3);
		test1.add(test4);
		test1.add(test5);
		test5.add(test6);
		System.out.println(test1);
	}
*/


}