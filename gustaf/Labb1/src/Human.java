
public class Human implements Comparable<Human> {
    public static String[] names = {"Laser", "Arne", "Grabb"}; // Kan vara private om du använder super() i Fysiker.
    private int age;
    private String name;

    public Human(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public Human(){
        this.age = (int)(100*Math.random());
        this.name = names[(int)(names.length*Math.random())];
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return "Namn: " + name + " ålder " + age + " år.";
    }

    public int compFys(Fysiker a, Fysiker b){
        if (a.getYear() < b.getYear())
            return -1;
        if (a.getYear() > b.getYear())
            return 1;
        return 0;
    }

    // Istället för att använda compFys här i Human, testa att overrida metoden compareTo
    // i Fysiker, och lägg all kännedom om Fysiker där. Humanklassen bör inte ha någon
    // kännedom alls om vilka klasser som ärver från den. Logiken är dock helt rätt.

    public int compareTo(Human b) {
        if ((this.age) < (b.age))
            return -1;
        if ((this.age) > (b.age))
            return 1;
        if ((this.age) == (b.age) && this instanceof Fysiker && b instanceof Fysiker)
            return compFys((Fysiker)this, (Fysiker)b);
        return 0;
    }
    public void jamfor(Human a, Human b){
        if(a.compareTo(b) == 0)
            System.out.println(a.getName() + " som är " + a.getAge() + " är lika gammal som " + b.getName() + " som också är " + b.getAge() + " år.");
        if (a.compareTo(b) < 0)
            System.out.println(a.getName() + " som är " + a.getAge() + " är yngre än " + b.getName() + " som är " + b.getAge() + " år.");
        else
            System.out.println(a.getName() + " som är " + a.getAge() + " är äldre än " + b.getName() + " som är " + b.getAge() + " år.");
    }
}
