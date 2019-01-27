/**
 * The HelloWorldApp class implements an application that
 * simply prints "Hello World!" to standard output.
 */

import java.math.*;

class HelloWorldApp {
    public static void main(String[] args) {
    	final long startTime = System.currentTimeMillis();
    	for (int i=0; i<2000; i++) {
    	//System.out.print("Hello World! "); // Display the string.
        //System.out.print(test() + " "); // Display the string.
        //System.out.println(i);
        //System.out.println(fac(i));
        BigInteger n = fac(i);
    	}
    	final long endTime = System.currentTimeMillis();
    	System.out.println(endTime-startTime);
    }

    public static String test() {
    	return "asdf";
    }

    public static BigInteger fac(int n) {

    	if (n==0){
    		return BigInteger.valueOf(1);
    	}
    	return BigInteger.valueOf(n).multiply(fac(n-1));

    }
}