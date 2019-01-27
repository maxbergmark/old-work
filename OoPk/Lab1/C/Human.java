public class Human implements FysikerHuman, Comparable<Human>{
    private int age;
    private String name;
    public static String[] names = {"Hjördis", "Papa-Dee", "Inga-Britt", "Putte2", "Doctor Bombay", "Sims-gubbe", "Hund med täcke", "Hilda-Maja", "Gösta", "Ali", "Cassandra", "Prins", "Pippi", "Moses", "Lotta på Bråkmakargatan", "Anne", "Cruella", "Hedda", "Svante", "Millan", "Lillemor", "Olof Mellberg"};

    public static void main(String[] args){
        
        Human putte = new Human(15, "putte");
        Human anders = new Human(20, "anders");
        System.out.println(putte);
        System.out.println(putte.getAge());
        System.out.println(putte.getName());
        System.out.println(anders.getName());
        
    }

    
    public Human(int ageIn, String nameIn){
        this.age = ageIn;
        this.name = nameIn;
    }   
    public String toString(){
        return name + " " + age;

    }

    public int compareTo(Human human){

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

    public static Human createRandom(){
        int tempage = (int)(Math.random()*100);
        int idx = (int)(Math.random()*names.length);
        String tempname = names[idx];

        return new Human(tempage, tempname);

    }

}

