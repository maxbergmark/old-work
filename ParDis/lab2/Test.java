import java.util.stream.*;
import java.util.*;


public class Test {


	public static void main(String[] args) {
		int limit = 1000000000;
		int low = 100;
		int high = 500;
		int[] array = new Random().ints(limit, low, high).parallel().toArray();
		//System.out.println(Arrays.toString(array));
	}
}