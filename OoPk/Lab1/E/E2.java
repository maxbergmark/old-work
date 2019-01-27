import java.util.Arrays;



public class E2 extends Human{

	public E2(int ageIn, String nameIn){
		super(ageIn, nameIn);
	}

	public static void main(String[] args) {
		Human putte = new Human(15, "Putte"); //E2.1
		System.out.println(putte);		//E2.2.E2.3 (toString anropas automatiskt, då det är ett prioriteringsval)
		System.out.println(putte.toString());
		System.out.println(putte.getName());
		System.out.println(putte.getAge());
	
	}

}