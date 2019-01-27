public class DRoot {
	public static int digital_root(int n) {

		int temp = n;
		int sum = temp;
		while (temp >= 10) {
			sum = 0;
			while (temp > 0) {
				sum += temp % 10;
				temp /= 10;
			}
			temp = sum;
		}
		return sum;
    // ...
	}

	public static void main(String[] args) {
		int[] val = {16, 942, 132189, 493193};
		for (int i : val) {
			System.out.println(i + "\t" + digital_root(i));
		}
	}
}