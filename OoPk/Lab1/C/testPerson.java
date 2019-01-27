import java.util.Arrays;

public class testPerson extends Person {

	public testPerson(int value){
		super(value);

	}

	public static void main(String[] args) {
		Person[] personlista = new Person[2];
		personlista[0] = new Person(10);
		personlista[1] = new Person(15);

		Arrays.sort(personlista);
	}
}