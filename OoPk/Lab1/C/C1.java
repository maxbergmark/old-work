import java.util.Arrays;

interface FysikerHuman {}

public class C1 extends Fysiker{

	public C1(int ageIn, String nameIn, int yearIn){
		super(ageIn, nameIn, yearIn);
	}

	public static void main(String[] args) {
		Fysiker fysiker = randomFysiker();
		System.out.println(fysiker);

	}

}