import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.lang.Cloneable;

public abstract class CloneComponent implements Cloneable {
	private String name;
	private int weight;
	public List<CloneComponent> suitcaseStructure;
	private int count = 0;
	public List<CloneComponent> suitcaseStructure2;
	
	
	public CloneComponent(String name, int weight){
		this.name = name;
		this.weight = weight;
		suitcaseStructure = new ArrayList<CloneComponent>();
		suitcaseStructure2 = new ArrayList<CloneComponent>();
	}
	
	public CloneComponent clone() {
		CloneComponent klon = null;
		try {
			klon = (CloneComponent) super.clone();
			if (suitcaseStructure.size() > 0) { 
				for (CloneComponent c: suitcaseStructure) {
					klon.add2(c.clone());
				}
			}		
			suitcaseStructure = suitcaseStructure2;	
		}
		catch (CloneNotSupportedException e) {
         e.printStackTrace();
		}
		return klon;
		
	}
	public void add2(CloneComponent thing) {
		suitcaseStructure2.add(thing);
	}
	public String toString(){
		String t = name;
		for (CloneComponent c : suitcaseStructure) {
			t += " which contains " + c.toString() + ",";
		}
		if (t.endsWith(",")) {
			t = t.substring(0, t.length()-1);
		}
		return t;
	}
	public String toString2(){
		String t = name;
		for (CloneComponent c : suitcaseStructure2) {
			t += " which contains " + c.toString() + ",";
		}
		if (t.endsWith(",")) {
			t = t.substring(0, t.length()-1);
		}
		return t;
	}
	public void remove(){
	
	}


	public void add(CloneComponent thing){
		suitcaseStructure.add(thing);
	}
	public String toString2(CloneComponent comp) {
		String thingName = comp.name;
		return thingName;
	}
	
	public void remove(CloneComponent thing){
		suitcaseStructure.remove(thing);
	}
	
	public List<CloneComponent> getStructure(){
		return suitcaseStructure;
	}
	
	public String getName() {
		return name;
	}
	public int getWeight(){
		int w = weight;
		for (CloneComponent c : suitcaseStructure) {
			w += c.getWeight();
		}
		return w; 
	}


}