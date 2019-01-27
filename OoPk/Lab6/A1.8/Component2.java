import java.util.ArrayList;
import java.util.List;
import java.util.*;

public abstract class Component2 implements Iterable{
	private String name;
	private int weight;
	public List<Component2> suitcaseStructure;
	private int count = 0;
	private boolean iterateStart = false;
	private static Queue<Component2> nextQueue = new LinkedList<Component2>();
	//public List<Component2> allThings = new ArrayList<Component2>();
	
	
	public Component2(String name, int weight){
		this.name = name;
		this.weight = weight;
		suitcaseStructure = new ArrayList<Component2>();
		//allThings.add(this);
	}

	public static class DepthIterator implements Iterator {
		
	}
	public boolean hasNext(){
		//System.out.println(name + "   " + nextQueue.isEmpty());
		return !nextQueue.isEmpty() || !iterateStart;
	}

	public Component2 next(){
		iterateStart = true;
		if (nextQueue.isEmpty()) {
			nextQueue.add(this);
		}
		Component2 temp = nextQueue.remove();
		for (Component2 c : temp.suitcaseStructure) {
			//System.out.println("looping: " + c.getName());
			nextQueue.add(c);
			//System.out.println(nextQueue.size());
		}
		return temp;
	}
	
	public void remove(){
	
	}

	public void widthFirst(){
		System.out.println(hasNext());
		while (hasNext()) {
			System.out.println(toString2(next()));
		}
	}
	public void add(Component2 thing){
		suitcaseStructure.add(thing);
	}
	public String toString2(Component2 comp) {
		String thingName = comp.name;
		return thingName;
	}
	
	public void remove(Component2 thing){
		suitcaseStructure.remove(thing);
	}
	
	public List<Component2> getStructure(){
		return suitcaseStructure;
	}
	
	public String toString(){
		String t = name;
		for (Component2 c : suitcaseStructure) {
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
		for (Component2 c : suitcaseStructure) {
			w += c.getWeight();
		}
		return w; 

	}



}