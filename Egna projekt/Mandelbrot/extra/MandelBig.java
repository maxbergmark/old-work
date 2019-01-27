import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.math.*;

public class MandelBig extends Thread {

	private static final int X_RES = 100;
	private static final int Y_RES = 100;
	private static final boolean colorBlack = true;
	private static final boolean normalize = false;
	private static final boolean blackEdge = false;
	private static final int N = 500;
	private static final double R = 1e50;

	private MyDecimal min_x = new MyDecimal(-2.5);//-0.923168;//
	private MyDecimal max_x = new MyDecimal( 1.0);//-0.923156;//
	private MyDecimal min_y = new MyDecimal(-1.5);//0.310210;//
	private MyDecimal max_y = new MyDecimal( 1.5);//0.310225;//

	private double[][] m;
	private Complex[][] c;
	private BufferedImage output;
	private int threadID;

	public MandelBig(MyDecimal min_x, MyDecimal max_x, MyDecimal min_y, MyDecimal max_y, int threadID) {
		this.min_x = min_x;
		this.max_x = max_x;
		this.min_y = min_y;
		this.max_y = max_y;
		this.threadID = threadID;
	}

	public static class MyDecimal extends BigDecimal {

		private static MathContext context = new MathContext(120, RoundingMode.HALF_UP);

		public MyDecimal(String s) {
			super(s);
			// this.setScale(12, BigDecimal.ROUND_HALF_UP);
		}
		public MyDecimal(double d) {
			super(d);
			// this.setScale(12, BigDecimal.ROUND_HALF_UP);
		}
		public MyDecimal(BigDecimal bd) {
			this(bd.toString()); // (Calls other constructor)
		}
		public MyDecimal(int i) {
			super(i);
			// this.setScale(12, BigDecimal.ROUND_HALF_UP);
		}
		public MyDecimal divide( BigDecimal divisor ){
			return new MyDecimal( super.divide( divisor, context ) );
		}
		public MyDecimal add( BigDecimal augend ){
			return new MyDecimal( super.add( augend ) );
		}
		public MyDecimal multiply(BigDecimal m) {
			return new MyDecimal(super.multiply(m, context));
		}
		public MyDecimal subtract(BigDecimal s) {
			return new MyDecimal(super.subtract(s));
		}

	}


	public void run() {
		output = new BufferedImage(X_RES, Y_RES, BufferedImage.TYPE_INT_RGB);
		System.out.println(String.format("%d: Creating arrays...", threadID));
		long t0 = System.nanoTime();
		createArray();
		long t1 = System.nanoTime();
		System.out.println(String.format("%d: Arrays created in %.3f seconds.", threadID, (t1-t0)*1e-9));
		System.out.println(String.format("%d: Generating MandelBigbrot...", threadID));
		long t2 = System.nanoTime();
		generateMandelBig();
		long t3 = System.nanoTime();
		System.out.println(String.format("%d: MandelBigbrot generated in %.3f seconds.", threadID, (t3-t2)*1e-9));
		System.out.println(String.format("%d: Writing to file...", threadID));
		long t4 = System.nanoTime();
		drawImage();
		long t5 = System.nanoTime();
		System.out.println(String.format("%d: File written in %.3f seconds.", threadID, (t5-t4)*1e-9));
		long total = (t5-t4)+(t3-t2)+(t1-t0);
		System.out.println(String.format("%d: Done in %.3f seconds!", threadID, total*1e-9));
		for (double v = 0; v < 1; v+= .01) {
			getColorMap(v);
		}
	}



	public void createArray() {
		m = new double[Y_RES][X_RES];
		c = new Complex[Y_RES][X_RES];
		MyDecimal XB = new MyDecimal(X_RES);
		MyDecimal YB = new MyDecimal(Y_RES);

		for (int i = 0; i < Y_RES; i++) {
			MyDecimal iB = new MyDecimal(i);

			MyDecimal y = /*(MyDecimal)*/ min_y .add( iB.multiply(max_y.subtract(min_y)).divide(YB));
			for (int j = 0; j < X_RES; j++) {
				MyDecimal jB = new MyDecimal(j);
				MyDecimal x = /*(MyDecimal)*/min_x .add( jB.multiply(max_x.subtract(min_x)).divide(XB));
				// double x = min_x + j*(max_x-min_x)/(double)X_RES;
				c[i][j] = new Complex(x, y);
			}
		}
	}

	public int getRainbow(double v) {
		double r = 0;
		double g = 0;
		double b = 0;
		if (v < 1.0/3.0) {
			double rv = Math.cos(v*3.0*Math.PI/2.0);
			r = rv*rv;
		}
		if (v < 2.0/3.0) {
			double gv = Math.sin(v*3.0*Math.PI/2.0);
			g = gv*gv;			
		}
		if (v > 1.0/3.0) {
			double bv = Math.sin((v-1.0/3.0)*3.0*Math.PI/2.0);
			b = bv*bv;
		}
		if (v > 2.0/3.0) {
			double rv = Math.sin(v*3.0*Math.PI/2.0);
			r = rv*rv;			
		}
		// System.out.println(String.format("%.3f %.3f %.3f", r, g, b));
		int rgb = (int)(r*256) << 16 | (int)(g*256) << 8 | (int)(b*256);
		return rgb;
	}

	public int getColorMap(double v) {
		double[] c0 = new double[] {0, 0, 0};
		double[] c1 = new double[] {.8, .6, 0};
		double[] c2 = new double[] {.99, .99, .99};
		double[] c3 = new double[] {0, 0, .5};

		double r,g,b;

		if (v < .25) {
			r = c1[0]*v*4.0 + c0[0]*(1.0-v*4.0);
			g = c1[1]*v*4.0 + c0[1]*(1.0-v*4.0);
			b = c1[2]*v*4.0 + c0[2]*(1.0-v*4.0);
		} else if (v < .5) {
			r = c2[0]*(v-.25)*4.0 + c1[0]*(1.0-(v-.25)*4.0);
			g = c2[1]*(v-.25)*4.0 + c1[1]*(1.0-(v-.25)*4.0);
			b = c2[2]*(v-.25)*4.0 + c1[2]*(1.0-(v-.25)*4.0);
		} else if (v < .75) {
			r = c3[0]*(v-.5)*4.0 + c2[0]*(1.0-(v-.5)*4.0);
			g = c3[1]*(v-.5)*4.0 + c2[1]*(1.0-(v-.5)*4.0);
			b = c3[2]*(v-.5)*4.0 + c2[2]*(1.0-(v-.5)*4.0);			
		} else {
			r = c0[0]*(v-.75)*4.0 + c3[0]*(1.0-(v-.75)*4.0);
			g = c0[1]*(v-.75)*4.0 + c3[1]*(1.0-(v-.75)*4.0);
			b = c0[2]*(v-.75)*4.0 + c3[2]*(1.0-(v-.75)*4.0);
		}

		// System.out.println(String.format("%.3f: %.3f %.3f %.3f", v, r, g, b));

		int rgb = (int)(r*256) << 16 | (int)(g*256) << 8 | (int)(b*256);
		return rgb;
	}

	public int BWColor(double v) {
		return (int)(v*256) << 16 | (int)(v*256) << 8 | (int)(v*256);
	}

	public void generateMandelBig() {
		// try (PrintStream out = new PrintStream(new FileOutputStream("filename.txt"))) {;
		for (int i = 0; i < Y_RES; i++) {
			for (int j = 0; j < X_RES; j++) {
				// System.out.println(i + "  " + j);
				Complex z = new Complex(new MyDecimal(0), new MyDecimal(0));
				int count = 0;
				while (z.abs() < R && count < N) {
					count++;
					z = Complex.add(Complex.mult(z, z), c[i][j]);
				}
				double v = (double)count;
				if (count < N) {
					double log_zn = Math.log(z.abs());
					double rem = Math.log(log_zn/Math.log(R))/Math.log(2);
					// System.out.println(z + "	" + rem);

					v = v-rem;
				}
				// v = n - log2(log(|z_n|)/log(R))
				m[i][j] = v/(double)N;
				// out.print(String.format("%.3f ", m[i][j]));
			}
			// out.print("\n");
		}
		// out.close();
		// } catch (Exception e) {

		// }
	}

	public double maxEdge() {
		double max = 0;
		for (int i = 0; i < X_RES; i++) {
			max = m[0][i] > max ? m[0][i] : max;
			max = m[Y_RES-1][i] > max ? m[Y_RES-1][i] : max;
		}
		for (int i = 0; i < Y_RES; i++) {
			max = m[i][0] > max ? m[i][0] : max;
			max = m[i][X_RES-1] > max ? m[i][X_RES-1] : max;			
		}
		return max;
	}

	public double[] getMinMax() {
		double min = 1;
		double max = 0;
		for (int i = 0; i < Y_RES; i++) {
			for (int j = 0; j < X_RES; j++) {
				min = m[i][j] < min ? m[i][j] : min;
				max = m[i][j] > max ? m[i][j] : max;
			}
		}
		return new double[] {min, max};
	}

	public void drawImage() {

		double max = blackEdge ? maxEdge() : 0;
		double[] normal = normalize ? getMinMax() : new double[] {0, 1};

		for (int i = 0; i < Y_RES; i++) {
			for (int j = 0; j < X_RES; j++) {
				// 
				int rgb = 0;
				if (m[i][j] < 1 | !colorBlack) {
					double v;
					if (m[i][j] == 1.0) {
						v = 1.0-1.0/(double)N;
					} else {
						v = m[i][j]<max ? 0 : ((m[i][j]-max)-normal[0])/(normal[1]-normal[0])*10 % 1; 
					}
					rgb = getColorMap(v);
				}
				output.setRGB(j, i, rgb);
			}
		}
		/*
		Graphics2D g2d = output.createGraphics();
		g2d.setPaint(Color.red);
		  g2d.setFont(new Font("Courier", Font.BOLD, 20));
		g2d.drawString("min_x: " + min_x, 10, 30);
		g2d.drawString("max_x: " + max_x, 10, 55);
		g2d.drawString("min_y: " + min_y, 10, 80);
		g2d.drawString("max_y: " + max_y, 10, 105);
		*/
		System.out.println(String.format("%d: Image generated, saving...", threadID));
		try {
			ImageIO.write(output, "png", new File(String.format("output/mandel%04d.png", threadID)));
		} catch (Exception e) {
			System.out.println("error");
			e.printStackTrace();
		}
	}

	public static class Complex {

		private MyDecimal a;
		private MyDecimal b;

		public Complex(MyDecimal a, MyDecimal b) {
			this.a = a;
			this.b = b;
		}

		public MyDecimal getA() {
			return this.a;
		}

		public MyDecimal getB() {
			return this.b;
		}

		public double abs() {
			return Math.sqrt(a.multiply(a).add(b.multiply(b)).doubleValue());
		}

		public static Complex add(Complex c1, Complex c2) {
			return new Complex(/*(MyDecimal)*/ c1.getA().add(c2.getA()), /*(MyDecimal)*/ c1.getB().add(c2.getB()));
		}

		// public static Complex sub(Complex c1, Complex c2) {
			// return new Complex(c1.getA()-c2.getA(), c1.getB()-c2.getB());
		// }

		public static Complex mult(Complex c1, Complex c2) {
			return new Complex(/*(MyDecimal)*/ c1.getA().multiply(c2.getA()).subtract(c1.getB().multiply(c2.getB())), 
				/*(MyDecimal)*/c1.getA().multiply(c2.getB()).add(c1.getB().multiply(c2.getA())));
		}

		public String toString() {
			return String.format("%7.3e + %7.3ei", a, b);
		}
	}

	public static void main(String[] args) {
		int images = 100;
		MyDecimal zoom = new MyDecimal(.2);
		int min = 0;
		// -0.088,0.654
		// -0.1002,0.8383
		MyDecimal center_x = new MyDecimal("-.10044328862809998");
		MyDecimal center_y =  new MyDecimal(".8380577884663519");
		MyDecimal x_dist = new MyDecimal(4);
		MyDecimal y_dist = new MyDecimal(4);

		ExecutorService executor = Executors.newFixedThreadPool(8);
		for (int i = 0; i < min; i++) {
			x_dist = /*(MyDecimal)*/ x_dist.multiply(zoom);
			y_dist = /*(MyDecimal)*/ y_dist.multiply(zoom);
		}
		// MandelBig[] ms = new MandelBig[images];
		for (int i = min; i < images; i++) {
			MandelBig m = new MandelBig(/*(MyDecimal)*/ center_x.subtract(x_dist), 
				/*(MyDecimal)*/ center_x.add(x_dist), 
				/*(MyDecimal)*/ center_y.subtract(y_dist), 
				/*(MyDecimal)*/center_y.add(y_dist), i);
			// ms[i].start();
			executor.execute(m);
			x_dist = /*(MyDecimal)*/ x_dist.multiply(zoom);
			y_dist = /*(MyDecimal)*/ y_dist.multiply(zoom);
		}
		executor.shutdown();

		while (!executor.isTerminated()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}