class Diamond {
	public static String print(int n) {
		if (n < 0 || n % 2 == 0) {
			return null;
		}
		String s = "";
		for (int i = 0; i < (n-1)/2+1; i++) {
			String line = "";
			for (int j = 0; j < n/2-i; j++) {
				line += " ";
			}
			for (int j = 0; j < 2*i+1; j++) {
				line += "*";
			}
			line += "\n";
			s += line;
		}
		for (int i = (n-1)/2-1; i >= 0; i--) {
			String line = "";
			for (int j = 0; j < n/2-i; j++) {
				line += " ";
			}
			for (int j = 0; j < 2*i+1; j++) {
				line += "*";
			}
			line += "\n";
			s += line;
		}


		return s.substring(0, s.length()-1);
	}


	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println("" + print(i) + "");
		}
	}
}
