

public class Money {
	public static int calculateYears(double principal, 
		double interest,  double tax, double desired) {
		if (principal == desired) {
			return 0;
		}
		double lastyear = principal;
		int count = 0;
		while (principal < desired) {
			System.out.println(principal + "\t" + lastyear);
			principal *= 1+interest;
			principal -= (principal-lastyear)*tax;
			lastyear = principal;
			count++;
		}
		return count;
	}

	public static void main(String[] args) {
		System.out.println(calculateYears(1000, 0.05, 0.18, 1100));
	}
}