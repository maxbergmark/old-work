public class HumanC {
    private int age;
    private String name;

    public static void main(String[] args){
        
        HumanC putte = new HumanC(15, "putte");
        System.out.println(putte.getAge());
        System.out.println(putte.getName());
        System.out.println(putte); //Denna ger samma output som nedan.
        System.out.println(putte.toString());
    }

    
    public HumanC(int ageIn, String nameIn){
        age = ageIn;
        name = nameIn;
    }   
    public String toString(){
    return name + " " + age;

}        
    public int getAge(){
        return age;
}
    public String getName(){
        return name;
    }
}

