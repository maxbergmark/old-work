public class Datalog extends Human{
	private int year;
	private String name;
	private int age;


	private Datalog(int ageIn, String nameIn,int yearIn){ //E4.1
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

	public static Datalog randomFysiker() {

        int idx = (int)(Math.random()*names.length);
        String tempname = names[idx];
        int tempyear  = 1932 + (int)(Math.random()*(2014-1932));
        int tempage = 2014-tempyear+15 + (int)(Math.random()*(100-2014+tempyear-15));
        Datalog tempfysiker = new Datalog(tempage, tempname, tempyear);

        return tempfysiker;

	}
	public static Datalog newDatalog(int ageIn, String nameIn,int yearIn){
		Datalog datastudent = new Datalog(ageIn,nameIn, yearIn);
		return datastudent;
	}

	public String toString() {
		return name + ", " + age + ", började data " + year;
	}

	public int compareTo(Datalog datalog){ //E5.1
        int comp = ((Datalog) datalog).getAge();
        return this.age-comp;
    }

	public int getYear() {

		return year;
	}


}