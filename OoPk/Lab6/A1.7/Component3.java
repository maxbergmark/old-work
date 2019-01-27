import java.util.ArrayList;
import java.util.List;
import java.util.*;

public abstract class Component3 implements Iterable{
	private String name;
	private int weight;
	public List<Component3> suitcaseStructure;
	private int count = 0;
		//public List<Component3> allThings = new ArrayList<Component3>();
	
	
	public Component3(String name, int weight){
		this.name = name;
		this.weight = weight;
		suitcaseStructure = new ArrayList<Component3>();
		//allThings.add(this);
	}

	public static class BreadthIterator implements Iterator {

		private Component3 component;
		private boolean iterateStart = false;
		private static Queue<Component3> nextQueue = new LinkedList<Component3>();

		public BreadthIterator(Component3 comp) {
			component = comp;
		}

		public boolean hasNext(){
			return !nextQueue.isEmpty() || !iterateStart;
		}

		public Component3 next(){
			iterateStart = true;
			if (nextQueue.isEmpty()) {
				nextQueue.add(component);
			}
			Component3 temp = nextQueue.remove();
			for (Component3 c : temp.suitcaseStructure) {
				nextQueue.add(c);
			}
			return temp;
		}
		
		public void remove() {}
	}
	
	public static class DepthIterator implements Iterator {

		private Component3 component;
		private boolean iterateStart = false;
		private static Stack<Component3> nextQueue = new Stack<Component3>();

		public DepthIterator(Component3 comp) {
			component = comp;
		}

		public boolean hasNext(){
			return !nextQueue.isEmpty() || !iterateStart;
		}

		public Component3 next(){
			iterateStart = true;
			if (nextQueue.isEmpty()) {
				nextQueue.push(component);
			}
			Component3 temp = nextQueue.pop();
			for (int i = temp.suitcaseStructure.size(); i > 0; i--) {
			//for (Component3 c : temp.suitcaseStructure) {
				nextQueue.push(temp.suitcaseStructure.get(i-1));
			}
			return temp;
		}
		
		public void remove() {}
	}


	public Iterator iterator() {
		return new BreadthIterator(this);
		//return new DepthIterator(this);
	}

	public Iterator dIterator() {
		return new DepthIterator(this);
	}


	public void add(Component3 thing){
		suitcaseStructure.add(thing);
	}
	public void remove(Component3 thing){
		suitcaseStructure.remove(thing);
	}
	
	public List<Component3> getStructure(){
		return suitcaseStructure;
	}
	
	public String toString(){
		String t = name;
		for (Component3 c : suitcaseStructure) {
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
		for (Component3 c : suitcaseStructure) {
			w += c.getWeight();
		}
		return w; 

	}



}