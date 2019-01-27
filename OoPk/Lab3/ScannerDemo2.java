import java.util.*; 
import java.io.*;

public class ScannerDemo2{

    public static void main(String[] args){

	Scanner sc;

	try{

	    sc = new Scanner(new File("Liv.xml"));
	}catch(Exception e){
	    System.out.println("Error: " + e.toString());
	    return;
	}	

	sc.useDelimiter("(: )|\n|<|>");

	while(sc.hasNext()){
	    sc.nextLine();

	    String s2 = sc.next();
	    System.out.println(s2);
	}

	sc.close();
	
    }
}