

public class Human{
    private int age;
	private static int yearIn;
    private String name;
    public static String[] names = {"Hjördis", "Papa-Dee", "Inga-Britt", "Putte2", "Doctor Bombay", "Sims-gubbe", "Hund med täcke", "Hilda-Maja", "Gösta", "Ali", "Cassandra", "Prins", "Pippi", "Moses", "Lotta på Bråkmakargatan", "Anne", "Cruella", "Hedda", "Svante", "Millan", "Lillemor", "Olof Mellberg"};

	public static Human create(String name, String year, int age) {
		if (year.startsWith("D")){
			year = year.replace("D","");
			yearIn = Integer.parseInt(year);
			Datalog datalog = Datalog.newDatalog(age, name, ((year.startsWith("0") ? 2000 : 1900)+yearIn)); //boolean-fråga (x ? y : z)
			System.out.println(datalog);
			return (Human) datalog;
		}
		if (year.startsWith("F")){
			year = year.replace("F","");
			yearIn = Integer.parseInt(year);
			Human fysiker = Fysiker.newFysiker(age, name, ((year.startsWith("0") ? 2000 : 1900)+yearIn));
			System.out.println(fysiker);
			return (Human) fysiker;
		}
		else{		
			Human putte = new Human(10,"putte");
			return putte;
		}
	}

    
    protected Human(int ageIn, String nameIn){
        this.age = ageIn;
        this.name = nameIn;
    }   
    public String toString(){
        return name + " " + age;

    }


    public int compareTo(Human human){ //E5.1

        if (this.age < human.getAge()) {
            return -1;
        }
        else if (this.age > human.getAge()) {
            return 1;
        }
        else return 0;


    }


    public int getAge(){
        return age;
}
    public String getName(){
        return name;
    }

    /*public static Human createRandom(){ //E3.1
        int tempage = (int)(Math.random()*100);
        int idx = (int)(Math.random()*names.length);
        String tempname = names[idx];

        return new Human(tempage, tempname);

    }*/
	public static void main(String[] args){
        //Human putte = new Human(15, "putte");
        //Human anders = new Human(20, "anders");
        //System.out.println(putte);
        //System.out.println(putte.getAge());
        //System.out.println(putte.getName());
        //System.out.println(anders.getName());
		create("Putte","D02",32);
		create("Putte","F02",32);
        
    }


}

