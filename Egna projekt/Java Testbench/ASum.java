public class ASum {

	public static long findNb(long m) {
		double sq = Math.sqrt(m);
		if (sq % 1 != 0) {
			return -1;
		}
		double n = 0.5*(Math.sqrt(8*sq+1)-1);
		if (n % 1 != 0) {
			return -1;
		}
		long num = (long) n;
		if (num*num*(num+1)*(num+1) != 4*m) {
			return -1;
		}
		return num;
	}	


	public static void main(String[] args) {
		System.out.println(findNb(2147815493455852901L));
	}
}