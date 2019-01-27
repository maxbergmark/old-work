import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;

public class TimeTest {
	
	private Random k = new Random();
	private int n = 300000;
	private int v = 0;


	public TimeTest() {
/*
		System.out.println("\nLinkedList");
		List<Integer> list2 = new LinkedList<Integer>();
		long time1 = System.nanoTime();
		for (int i = 0; i < n; i++) {
			list2.add(a(list2.size()), new Integer(5));
		}
		long time2 = System.nanoTime();
		for (int i = 0; i < n; i++) {
			list2.get(b());
		}
		long time3 = System.nanoTime();
		System.out.println((time2-time1)/1000000);
		System.out.println((time3-time2)/1000000);
*/
		System.out.println("\nLinkList");
		LinkList<Integer> list1 = new LinkList<Integer>();
		long time4 = System.nanoTime();
		for (int i = 0; i < n; i++) {
			list1.add(a(list1.size()), new Integer(5));
		}
		long time5 = System.nanoTime();
		long time123 = System.nanoTime();
		list1.castToArray();
		long time456 = System.nanoTime();
		for (int i = 0; i < n; i++) {
			list1.fastGet(b());
			//Object s = list1.a[b()];
		}
		long time6 = System.nanoTime();
		System.out.println((time5-time4)/1000000);
		System.out.println((time6-time5)/1000000);
		System.out.println((time456-time123)/1000000);

		System.out.println("\nArrayList pre-allocated");
		List<Integer> list3 = new ArrayList<Integer>(n);
		long time7 = System.nanoTime();
		for (int i = 0; i < n; i++) {
			list3.add(a(list3.size()), new Integer(5));
		}
		long time8 = System.nanoTime();
		for (int i = 0; i < n; i++) {
			list3.get(b());
		}
		long time9 = System.nanoTime();
		System.out.println((time8-time7)/1000000);
		System.out.println((time9-time8)/1000000);

		System.out.println("\nArrayList dumb");
		List<Integer> list4 = new ArrayList<Integer>();
		time7 = System.nanoTime();
		for (int i = 0; i < n; i++) {
			list4.add(a(list4.size()), new Integer(5));
		}
		time8 = System.nanoTime();
		for (int i = 0; i < n; i++) {
			list4.get(b());
		}
		time9 = System.nanoTime();
		System.out.println((time8-time7)/1000000);
		System.out.println((time9-time8)/1000000);
	}

	public int a(int x){
		//return v++;
		if (x == 0) {
			return 0;
		}
		return k.nextInt(x);
	}
	
	public int b(){
		return k.nextInt(n);
	}
	

	public static void main(String[] args) {
		new TimeTest();
	}
}