import java.util.Arrays;



public class E3 extends Human{

	public E3(int ageIn, String nameIn){
		super(ageIn, nameIn);
	}

	public static void main(String[] args) {

		Human[] idollista = new Human[15]; //E3.2
		for (int i = 0; i < 15; i++){
			idollista[i] = createRandom();
			System.out.println(idollista[i]);	
		}
	
	}

}