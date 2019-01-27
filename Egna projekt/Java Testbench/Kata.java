public class Kata {
	public static String HighAndLow(String numbers) {
    // Code here or
		int last = 0;
		int low = Integer.MAX_VALUE;
		int high = Integer.MIN_VALUE;

		if (numbers.indexOf(" ") == -1) {
			return numbers + " " + numbers;
		}
		int index;
		boolean b = true;
		while (b) {
			index = numbers.indexOf(" ", last);
			if (index == -1) {
				index = numbers.length();
				b = false;
			}
			int num = Integer.parseInt(numbers.substring(last, index));
			if (num > high) {
				high = num;
			}
			if (num < low) {
				low = num;
			}
			last = index+1;
		}
		return high + " " + low;
	}

	public static void main(String[] args) {
		System.out.println(HighAndLow("1 -1"));
	}
}