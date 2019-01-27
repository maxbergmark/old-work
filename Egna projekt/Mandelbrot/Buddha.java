import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.*;

public class Buddha extends Thread {


	private static final double R = 3;

	static int X_RES = 1000;
	static int Y_RES = 1000;
	static double contrast = 2.5;
	static int NUM_THREADS = 8;
	static int N = 20;
	private static BufferedImage output;
	private static volatile boolean finished = false;
	static Calendar END_TIME;
	static Calendar runtime;
	static String runtimeString;

	private static double min_x = -2.2;
	private static double max_x =  1.0;
	private static double min_y = -1.5;
	private static double max_y =  1.5;


	static AtomicInteger[][] m;
	private static int[][] approx;
	private int threadID;
	private Random rand = new Random();
	private Calendar t0;
	private long total;
	private long fast = 0;
	private long allTotal = 0;



	public Buddha(int threadID) {
		this.threadID = threadID;

	}

	public void run() {
		t0 = Calendar.getInstance();
		if (threadID == 0) {
			System.out.println(String.format(
				"  %8s\t%9s\t %9s\t %9s",
				"Progress", "Speed(Hz)",
				"Elapsed", "Remaining"));
		}
		calcMandel();
		if (threadID == 0) {
			System.out.println("\n");
			finished = true;
		}
		Calendar t1 = Calendar.getInstance();
		while (!finished) {
			try {
				Thread.sleep(10);
			} catch (Exception e) {

			}
		}
		System.out.println(String.format(
			"  Thread %d finished with %.2e iterations.",
			threadID, (double)total));
	}

	public static void createArray() {

		m = new AtomicInteger[Y_RES][X_RES];
		output = new BufferedImage(X_RES, Y_RES, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < Y_RES; i++) {
			for (int j = 0; j < X_RES; j++) {
				m[i][j] = new AtomicInteger(0);
			}
		}
	}

	public static int[] round(Complex c) {
		int[] ret = new int[2];
		ret[0] = (int)((c.a-min_x)/(max_x-min_x)*X_RES);
		ret[1] = (int)((c.b-min_y)/(max_y-min_y)*Y_RES);
		return ret;
	}

	public static void getApproxArray() {
		approx = new int[Y_RES][X_RES];
		for (int i = 0; i < Y_RES; i++) {
			System.out.print(String.format(
				"   \r  Generating approximate array... %.2f%%"
				, 100*i/(double)Y_RES));

			double y = min_y + (max_y-min_y)*i/(double)Y_RES;
			for (int j = 0; j < X_RES; j++) {
				double x = min_x + (max_x-min_x)*j/(double)X_RES;
				Complex c0 = new Complex(x,y);
				Complex z = new Complex(0,0);
				int count = 0;
				while(z.abs()<R && count < 2*N) {
					count++;
					z.mult(z);
					z.add(c0);
				}
				approx[i][j] = count;
			}
		}
		System.out.println(String.format(
				"   \r  Generating approximate array... %.2f%%"
				, 100.0));
	}

	public void printProgress(long total) {

		double elapsed = (new Date().getTime()
			- t0.getTimeInMillis())*1e-3;
		double progress = 100*elapsed/(double)(END_TIME.getTimeInMillis()*1e-3
			-t0.getTimeInMillis()*1e-3);
		int l = 5;
		if (progress >= 10) {
			l++;
			if (progress >= 100) {
				progress = 100.0;
				l++;
			}
		}
		double remaining = Math.max(0,END_TIME.getTimeInMillis()*1e-3
		-new Date().getTime()*1e-3);
		// System.out.print("\r                                                           ");
		System.out.print(String.format(
			"        \r  %-"+l+".3f%%\t%9.3e\t%9.1fs\t%9.1fs",
			progress,
			NUM_THREADS*total/elapsed,
			elapsed,
			remaining));
	}



	public int firstCheck(Complex c0) {
		int[] pos = round(c0);
		int p0 = pos[0];
		int p1 = pos[1];
		if (0<=p0 && p0<X_RES-1 && 0<=p1 && p1<Y_RES-1) {
			if (approx[p1][p0]>N && approx[p1+1][p0]>N &&
				approx[p1][p0+1]>N && approx[p1+1][p0+1]>N) {
				return N;
			}
		}

		Complex z = new Complex(0,0);
		int count = 0;

		while(z.abs()<R && count < N) {
			count++;
			z.mult(z);
			z.add(c0);
		}
		return count;
	}

	public void secondCheck(Complex c0, int count) {

		int coord0;
		int coord1;
		int[] coords;
		Complex z = new Complex(0,0);
		for (int j = 0; j < count; j++) {

			z.mult(z);
			z.add(c0);

			coords = round(z);
			coord0 = coords[0];
			coord1 = coords[1];

			if (0<=coord0 && coord0 < Y_RES
				&& 0<=coord1 && coord1 < X_RES) {
				m[coord0][coord1].getAndIncrement();
				total++;
			}

		}
	}

	public void calcMandel() {

		total = 0;

		while (new Date().getTime() < END_TIME.getTimeInMillis()) {
			for (int i = 0; i < 100000; i++) {

				Complex c0 = new Complex(rand.nextDouble()*3.5-2.5,
										rand.nextDouble()*3.0-1.5);
				int count = firstCheck(c0);
				allTotal++;

				if (count < N) {
					fast++;
					secondCheck(c0, count);
				}

			}
			if (threadID == 0) {
				printProgress(total);
			}
		}
	}





	public static void main(String[] args) {

		if (args[0].equals("-help")) {
			BuddhaPrint.printHelp();
			System.exit(1);
		}

		BuddhaPrint.getResolution(args);

		System.out.println();
		long t0 = System.nanoTime();
		getApproxArray();
		createArray();
		long t1 = System.nanoTime();
		System.out.println(String.format("  Arrays created in %.2f seconds.\n", (t1-t0)*1e-9));
		BuddhaPrint.setEnd(args);
		Buddha[] threads = new Buddha[NUM_THREADS];
		long t2 = System.nanoTime();
		for (int i = 0; i < NUM_THREADS; i++) {
			threads[i] = new Buddha(i);
			threads[i].start();
		}
		for (int i = 0; i < NUM_THREADS; i++) {
			try {
				threads[i].join();
			} catch (Exception e) {

			}
		}
		long t3 = System.nanoTime();
		BuddhaPrint.drawImage(0, output);
		long t4 = System.nanoTime();


		System.out.println("\n  Execution complete!\n");
	}

}