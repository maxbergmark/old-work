

class Human {
	private static String[] names = {"Arne", "Laser", "Grabb"};
	private int age = 0;
	private String name = "";

	Human(int ageIn, String nameIn) {
		age = ageIn;
		name = nameIn;
        
	}
	Human(){
		age = (int)(100*Math.random());
		name = names[(int)(names.length*Math.random())];
	}
	public String toString(){return "namn: " + this.getName() + ", ålder " + this.getAge() + " år";}
	public int getAge(){return this.age;}
	public String getName(){return this.name;}

	public static void main(String[] a) {
		Human h = new Human(5, "Arne");
		System.out.println(h);
	}
}