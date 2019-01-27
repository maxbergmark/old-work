import java.util.Arrays;

public class E5 extends Fysiker{

	public E5(int ageIn, String nameIn, int yearIn){
		super(ageIn, nameIn, yearIn);
	}

	public static void main(String[] args) {

		Human putte1 = createRandom();
		Human putte2 = createRandom();

		String[] temp = {"younger than", "the same age as", "older than"}; //E5.2
		int val = putte1.compareTo(putte2)+1;
		String tempstring1 = String.format("%s, who is %d years old, is ", putte1.getName(), putte1.getAge()); //%s: string, %d: digits
		String tempstring2 = String.format(" %s, who is %d years old.", putte2.getName(), putte2.getAge());
		System.out.println(tempstring1 + temp[val] + tempstring2);
		System.out.println();

		Fysiker fysiker = randomFysiker(); //E5.3 (det går att jämföra)
		System.out.println(putte1); 
		System.out.println(fysiker);
		System.out.println(fysiker.compareTo(putte1));
		System.out.println();

		FysikerHuman[] fysikerhuman = new FysikerHuman[10]; //E5.4
		for (int i = 0; i < 5; i++){
			fysikerhuman[i] = randomFysiker();
			fysikerhuman[i+5] = createRandom();
		}

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