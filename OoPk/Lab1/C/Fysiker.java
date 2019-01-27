import java.io.*;

public class Fysiker extends Human implements FysikerHuman, Comparable<Human>{
	private int year;
	private String name;
	private int age;


	public Fysiker(int ageIn, String nameIn,int yearIn){
		super(ageIn, nameIn);
		name = nameIn;
		age = ageIn;
		year = yearIn;
		if (1932<=yearIn && yearIn<=2013){
				

			if(ageIn-2014+yearIn<15){
				age = 0;
				
				year = 0;
				System.out.println(name + " är för ung för att vara fysiker");
				name = "";
			}
		else{
			
		}
		
			}
		else{
			age = 0;
			name = "";
			year = 0;
			System.out.println("Ej godkänt antagningsår");

		}
		

	}

	public static Fysiker randomFysiker() {

        int idx = (int)(Math.random()*names.length);
        String tempname = names[idx];
        int tempyear  = 1932 + (int)(Math.random()*(2014-1932));
        int tempage = 2014-tempyear+15 + (int)(Math.random()*(100-2014+tempyear-15));
        Fysiker tempfysiker = new Fysiker(tempage, tempname, tempyear);

        return tempfysiker;

	}

	public String toString() {

		return name + " " + age + " F" + String.valueOf(year).substring(2, 4); //C1 (gör om till sträng, och tar en substring)
	}

	public int compareTo(Human fysiker){
		
        int comp = ((Fysiker) fysiker).getAge();
        if (this.age < comp) {
            return -1;
        }
        else if (this.age > comp) {
            return 1;
        }

        else {
        	if (fysiker instanceof Fysiker) {
	        	int compyear = ((Fysiker) fysiker).getYear(); 
	        	if (this.year < compyear) {
	        		return -1;
	        	}
	        	else if (this.year > compyear) {
	        		return 1;
	        	}
	        }
        }
        return 0;
    }

	public int getYear() {

		return year;
	}


}