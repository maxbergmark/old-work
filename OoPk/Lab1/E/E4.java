import java.util.Arrays;



public class E4 extends Fysiker{

	public E4(int ageIn, String nameIn, int yearIn){
		super(ageIn, nameIn, yearIn);
	}

	public static void main(String[] args) {

		Fysiker[] fysikerlista = new Fysiker[15]; //E4.4
		for (int i = 0; i < 15; i++){
			fysikerlista[i] = randomFysiker();
			System.out.println(fysikerlista[i]);	
		}

		System.out.println();

		FysikerHuman[] fysikerhuman = new FysikerHuman[10]; //E4.5
		for (int i = 0; i < 5; i++){
			fysikerhuman[i] = randomFysiker();
			System.out.println(fysikerhuman[i]);	
		}
		for (int i = 5; i < 10; i++){
			fysikerhuman[i] = createRandom();
			System.out.println(fysikerhuman[i]);	
		}
	
	}

}