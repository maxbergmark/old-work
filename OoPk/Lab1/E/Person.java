import java.util.Arrays;


public class Person implements Comparable<Person>{
	private int value;

	public static void main(String[] args){

		Person[] personlist = new Person[2];
		personlist[0] = new Person(1);
		personlist[1] = new Person(0);
		System.out.println(personlist[0].compareTo(personlist[0]));
		System.out.println(personlist[0].compareTo(personlist[1]));
		System.out.println(personlist[1].compareTo(personlist[0]));
		System.out.println(personlist[1].compareTo(personlist[1]));
		Arrays.sort(personlist);
        //System.out.println(Arrays.toString(personlist));
	}

    public Person(int valueIn){
        this.value = valueIn;
    }  

    public String toString() {
        return String.valueOf(value);
    }

    public int compareTo(Person personcomp){
        if (this.value < personcomp.value) {
            return -1;
        }
        else if (this.value > personcomp.value) {
            return 1;
        }
        return 0;
    }
}