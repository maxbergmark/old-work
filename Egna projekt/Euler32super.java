import java.util.*;

public class Euler32super {

	static boolean check(int n)
    {
        int digits = 0; int count = 0; int tmp;

        for (; n > 0; n /= 10, ++count)
        {
            if ((tmp = digits) == (digits |= 1 << (n - ((n / 10) * 10) - 1)))
                return false;
        }

        return digits == (1 << count) - 1;
    }

	public static void main(String[] args) {
		long t1 = System.nanoTime();
		int s = 0;
		for (int m = 0; m < 1000; m++) {
		HashSet<Integer> set = new HashSet<Integer>();
		for (int a = 4; a < 49; a++) {
			for (int b = 157; b < 7860/a; b++) {
				
				int c = a*b;
				int f1 = 1000;

				if (c >= 10000) {
					f1 = 100000;
				} else if (c >= 1000) {
					f1 = 10000;
				}
				int f2 = (b >= 1000 ? 10000*f1 : 1000*f1);
				int n = f2*a+f1*b+c;
				if (n >= 123456789 && check(n) && !set.contains(c)) {
					//System.out.println(a + "   " + b + "   " + c);
					s += c;
					set.add(c);
				}
			}
		}
		}
		long t2 = System.nanoTime();
		System.out.println((t2-t1)/1000000);
		System.out.println(s);
	}

}