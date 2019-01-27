import java.util.Arrays;



public class test2 extends Human{

public test2(int ageIn, String nameIn){
		super(ageIn, nameIn);
	}

	public static void main(String[] args) {
		int n = 2;
		Human[] puttes = new Human[n];
		for (int i = 0; i < n; i++) {
			puttes[i] = createRandom();
			System.out.println(puttes[i]);
		}
		System.out.println();
		System.out.println(puttes[0].compareTo(puttes[0]));
		System.out.println(puttes[0].compareTo(puttes[1]));
		System.out.println(puttes[1].compareTo(puttes[0]));
		System.out.println(puttes[1].compareTo(puttes[1]));
		System.out.println();
		Integer[] testlista = {1, 3, 2, 5, 4, 7, 6, 9, 8, 0};
		for (int i = 0; i < 10; i++) {
			System.out.println(testlista[i]);
		}

		Arrays.sort(testlista);
		for (int i = 0; i < 10; i++) {
			System.out.println(testlista[i]);
		}

		System.out.println(testlista[2].compareTo(testlista[7]));

		Arrays.sort(puttes);
		/*
		for (int i = 0; i < n; i++) {
			System.out.println(puttes[i].toString());
		}
		*/

	}


}