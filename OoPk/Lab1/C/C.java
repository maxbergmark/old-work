import java.util.*;

public class C{

	public C(){}

	public static void main(String[] args) {
		List<FysikerHuman> testlista = new ArrayList<FysikerHuman>();
		for (int i = 0; i < args.length; i++) {

			if (args[i].equals("-H")) {
				try {
				testlista.add(new Human(Integer.parseInt(args[i+1]), args[i+2]));
				}
				catch (Exception e) {
					System.out.println("FEL!");
					return;
				}
				i += 2;
				continue;
			}
			if (args[i].equals("-F")) {
				int age;
				int year;
				try{
				age = Integer.parseInt(args[i+1]); // Gör om String till int, går ej om strängen inte är ett heltal.
				
				year = Integer.parseInt(args[i+3]);
				}
				catch(Exception e) {
					System.out.println("FEL!");
					return;
				}
				
					if (year < 32) {
						year += 2000; 
					}
					else {
						year += 1900;
					}
				if (1932<=year && year<=2013){
				

					if(age-2014+year<15){
						System.out.println("För ung för att vara fysiker");
						
					}
				else{
					Fysiker temp = new Fysiker(age, args[i+2], year); 
					
					testlista.add(temp);
					
				}
				
					}
				else{

					System.out.println("Ej godkänt antagningsår");

				}
				i += 3;
				continue;
			}
			else {
				System.out.println(args[i]);
				System.out.println("FELAKTIG SYNTAX");
				break;
			}

		}
		System.out.println();
		for (int i = 0; i < testlista.size(); i++) {
			System.out.println(testlista.get(i));

		}

	}


}