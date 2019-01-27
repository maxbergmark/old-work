
import java.math.BigDecimal;


public class BigDecimalExample {


	public static void main(String[] args) {
		BigDecimal b  = new BigDecimal("1.1111111111222222222233333333334444444444000000");
		BigDecimal b2 = new BigDecimal("1.1111111111222222222233333333334444444444900000");
		for (int i = 0; i < 100; i++) {
			BigDecimal a = new BigDecimal(i/(double)100);
			// System.out.println(BigDecimal.ONE.subtract(a) + " " + a);
			System.out.println(b.multiply(BigDecimal.ONE.subtract(a)).add(b2.multiply(a)));
			System.out.println();
		}
		
		System.out.println(b);
		System.out.println(b2);
	}

}