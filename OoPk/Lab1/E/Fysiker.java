public class Fysiker extends Human implements FysikerHuman, Comparable<Human>{
	private int year;
	private String name;
	private int age;


	public Fysiker(int ageIn, String nameIn,int yearIn){ //E4.1
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
		return name + " " + age + " " + year;
	}

	public int compareTo(Fysiker fysiker){ //E5.1
        int comp = ((Fysiker) fysiker).getAge();
        return this.age-comp;
    }

	public int getYear() {

		return year;
	}


}