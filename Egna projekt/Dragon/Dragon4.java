
public class Dragon4 {
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		int k = 35;
	    for(long n=1 ; n<=Math.pow(2,k+1)-1 ; n++ ){
	        long fold = 1L - ((n/(n&-n))&2);
	        //System.out.print(" " + fold);
	    }
	    long endTime = System.nanoTime();
	    System.out.println((double)(endTime-startTime)/1000000);
	}
}