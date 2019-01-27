import java.util.*;


public class Person2 implements Comparable<Person2>{
	private int value;

	public static void main(String[] args){

		Person2[] personlist = new Person2[1000];
        for (int i = 0; i < 1000; i++) {
            personlist[i] = new Person2((int)(Math.random()*1000));
        }
        System.out.println(Arrays.toString(personlist));
		Arrays.sort(personlist);
        System.out.println(Arrays.toString(personlist));
	}

    public Person2(int valueIn){
        this.value = valueIn;
    }  

    public String toString() {
        return String.valueOf(value);
    }

    public int compareTo(Person2 personcomp){
        if (this.value < personcomp.value) {
            return -1;
        }
        else if (this.value > personcomp.value) {
            return 1;
        }
        return 0;
    }
}