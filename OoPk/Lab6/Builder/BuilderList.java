import java.util.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.LinkedList;
public abstract class BuilderList<E> implements List{
	private List<String> list = new ArrayList<String>();
	private Random k = new Random();
	private int element = 0;
	private int N=5;
	private int i;
	private String hej = "Hej";
	private ArrayList<String> myArrayList = new ArrayList<String>();
    private LinkedList<String> myLinkedList = new LinkedList<String>();
	
	public BuilderList(){
		doThis();
	}

	public void add(int index, String obj){
		++element;
		list.add(a(),obj);
	}
	
	public String get(int index){ //Finns redan i List
		return list.get(index);
	}

	public int a(){
		//int aIndex = (int) Math.random();
		return k.nextInt(list.size());
		
	}
	
	public int b(){
		return k.nextInt(N);
	}
	
	public void doThis(){
		for (i=0; i<N; i++){
			list.add(a(),hej);
		}
		for (i=0; i<N; i++){
			list.get(b());
		} 
	
	}
	public static void main(String[] args) {
		BuilderList<String> test = new ArrayList<String>();
	}

}