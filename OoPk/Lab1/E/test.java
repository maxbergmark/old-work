import java.util.Arrays;

interface FysikerHuman {}

public class test extends Fysiker{

	public test(int ageIn, String nameIn, int yearIn){
		super(ageIn, nameIn, yearIn);
	}

	public static void main(String[] args) {
		Human putte = new Human(15, "Putte");
		System.out.println(putte);
		System.out.println(putte.toString());
		System.out.println(putte.getName());
		System.out.println(putte.getAge());
		System.out.println();
		//double fdsa = Math.random();
		Human[] idollista = new Human[15];
		for (int i = 0; i < 15; i++){
			idollista[i] = createRandom();
			System.out.println(idollista[i]);	
		}
		System.out.println();
		String[] namnlista = new String[15];
		for (int i = 0; i < 15; i++){
			namnlista[i] = idollista[i].toString();
		}
		//System.out.println(Arrays.toString(idollista));
		System.out.println(Arrays.toString(namnlista));
		System.out.println();

		Fysiker fysiker = new Fysiker(22, "Patrik", 1932);
		System.out.println(fysiker);
		System.out.println();

		Fysiker testfysiker = randomFysiker();
		System.out.println(testfysiker);
		//System.out.println(testfysiker.getYear());
		System.out.println();

		Fysiker[] fysikerlista = new Fysiker[15];
		for (int i = 0; i < 15; i++){
			fysikerlista[i] = randomFysiker();
			System.out.println(fysikerlista[i]);	
		}
		System.out.println();

		FysikerHuman[] fysikerhuman = new FysikerHuman[10];
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