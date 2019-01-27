


class Persist {
	public static int persistence(long n) {
		long result = 1;
		long temp = n;
		int count = 0;
		while (temp >= 10) {
			result = 1;
			while (temp > 0) {
				result *= temp%10;
				temp /= 10;
			}
			temp = result;
			count++;
		}
		return count;
	}

	public static void main(String[] args) {
		System.out.println(persistence(25));
	}

}