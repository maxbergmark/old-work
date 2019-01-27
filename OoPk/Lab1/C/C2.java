import java.util.Arrays;

interface FysikerHuman {

}

public class C2 extends Fysiker{

	public C2(int ageIn, String nameIn, int yearIn){
		super(ageIn, nameIn, yearIn);
	}

	public static void main(String[] args) {
/*
		Fysiker test1 = new Fysiker(20, "asdf", 2012);


		Fysiker fysiker1 = new Fysiker(50, "Gretel", 1990);
		Fysiker fysiker2 = new Fysiker(50, "Gritle", 1990);
		Fysiker fysiker3 = new Fysiker(50, "Gritel", 1991);
		System.out.println(fysiker1.compareTo(fysiker2)); // Skriver ut 0 eftersom de är "lika"
		System.out.println(fysiker1.compareTo(fysiker3)); // Skriver ut -1 eftersom 1991 > 1990


		Fysiker[] fysiker = new Fysiker[10]; //E5.4
		for (int i = 0; i < 10; i++){
			fysiker[i] = randomFysiker();
		}
		fysiker[0] = fysiker3;
		fysiker[1] = fysiker1;

		for (int i = 0; i < 10; i++){
			System.out.println(fysiker[i]);	
		}

		Arrays.sort(fysiker);
		System.out.println();
		for (int i = 0; i < 10; i++){
			System.out.println(fysiker[i]);	
		}







*/
		Fysiker fysiker1 = new Fysiker(50, "Gretel", 1990);
		Fysiker fysiker2 = new Fysiker(50, "Gritle", 1990);
		Fysiker fysiker3 = new Fysiker(50, "Gritel", 1991);



		FysikerHuman[] fysikerhuman = new FysikerHuman[10]; //E5.4
		for (int i = 0; i < 5; i++){
			fysikerhuman[i] = randomFysiker();
			fysikerhuman[i+5] = createRandom();
		}
		fysikerhuman[1] = fysiker1;
		fysikerhuman[0] = fysiker3;

		for (int i = 0; i < 10; i++){
			System.out.println(fysikerhuman[i]);	
		}

		Arrays.sort(fysikerhuman);
		System.out.println();
		for (int i = 0; i < 10; i++){
			System.out.println(fysikerhuman[i]);	
		}

	}

}