public class Fysiker extends Human{
	private int year;
	private String name;
	private int age;


	public Fysiker(int ageIn, String nameIn,int yearIn){
		super(ageIn, nameIn);
		name = nameIn;
		age = ageIn;
		year = yearIn;

	}

	public static Fysiker randomFysiker() {
		int tempage = (int)(Math.random()*100);
        int idx = (int)(Math.random()*names.length);
        String tempname = names[idx];
        int tempyear  = 1932 + (int)(Math.random()*(2014-1932));
        if (1932<tempyear && tempyear<2013){				
			if(tempage-2014+tempyear<15){				
				System.out.println("För ung för att vara fysiker");
			}
			else{
				return new Fysiker(tempage, tempname, tempyear)
			}
		}
 		else{
 			System.out.println("Ej godkänt antagningsår");
 		}

	}

	public String toString() {
		return name + " " + age + " " + year;
	}



}