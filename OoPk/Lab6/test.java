public class test{

	public test(){
		System.out.println("Åh vad kul!");
	}

	public static void main(String[] args){
		Human.create("Putte","D92",82);
		Human.create("Putte","F02",32);
		
		Human putte = new Datalog(70, "Putte", 1939); //Detta ger error, tror det är okej?
		//System.out.println(putte); //Har bara gjort om access till konstruktorn som privat. 
		
		Human putte = new Fysiker(70, "Putte", 1939);
		//System.out.println(putte);
        
    }



}