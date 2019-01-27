import java.util.ArrayList;
import java.util.List;
import java.util.*;

public abstract class Component implements Iterator{
	private String name;
	private int weight;
	public List<Component> suitcaseStructure;
	private int count = 0;
	private boolean topReturned = false;
	//public List<Component> allThings = new ArrayList<Component>();
	
	public Component(String name, int weight){
		this.name = name;
		this.weight = weight;
		suitcaseStructure = new ArrayList<Component>();
		//allThings.add(this);
	}
	public boolean hasNext(){
		return !topReturned;
	}

	public Component next(){
		if (count < suitcaseStructure.size()) {
			if (!suitcaseStructure.get(count).hasNext()) {
				count++;
			}
			if (count < suitcaseStructure.size()) {
				return suitcaseStructure.get(count).next();
			}
		}
		topReturned = true;
		return this;
	}
	public void remove(){
	
	}

	public void widthFirst(){
		System.out.println(hasNext());
		while (hasNext()) {
			System.out.println(toString2(next()));
		}
	}
	public void add(Component thing){
		suitcaseStructure.add(thing);
	}
	public String toString2(Component comp) {
		String thingName = comp.name;
		return thingName;
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

	public String getName() {
		return name;
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