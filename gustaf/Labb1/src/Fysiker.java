public class Fysiker extends Human {

    int year;

    public Fysiker(int age, String name, int year){
        super(age, name);
        if(year < 1932 || year > 2016 || 2016-year+15 > age)
            throw new Error("Omöjligt");
        else
            this.year = year;
    }

    public Fysiker(){
        this.year = 1932 + (int)(84*Math.random());
        setAge((2016-year+15) + (int)((100-(2016-year+15))*Math.random()));
        //setName(names[(int)(names.length*Math.random())]);

    }
    // Om ingen annan super-konstruktor specificeras, så körs super() (utan argument) som första instruktion
    // i en konstruktor för en ärvande klass. Därför behövs inte setName() här, den körs ändå.


    public String toString(){
        return "Namn: " + getName() + " ålder " + getAge() + " år, Började Fysik årskursen F" + (String.format("%02d", (year%100)));
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
