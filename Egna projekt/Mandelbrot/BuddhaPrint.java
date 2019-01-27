import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.*;

public class BuddhaPrint {

	public static void printHelp() {
		System.out.println("  -d\tnumber of days to add to runtime");
		System.out.println("  -h\tnumber of hours to add to runtime");
		System.out.println("  -m\tnumber of minutes to add to runtime");
		System.out.println("  -s\tnumber of seconds to add to runtime");
		System.out.println("  -np\tnumber of threads to start");
		System.out.println("  -i\tmax iterations for buddhabrot image");
		System.out.println("  -c\timage contrast (double)");
		System.out.println("  -rx\tx resolution");
		System.out.println("  -ry\ty resolution");
		System.out.println("  -help\tdisplay this help\n");
	}

		public static void setEnd(String[] args) {
		Buddha.END_TIME = Calendar.getInstance();
		Calendar startTime = (Calendar) Buddha.END_TIME.clone();
		Buddha.runtime = Calendar.getInstance();
		Buddha.runtime.set(0,0,0,0,0,0);
		if (args.length == 0) {
			Buddha.END_TIME.add(Calendar.MINUTE, 1);
			Buddha.runtime.add(Calendar.MINUTE, 1);
			// Buddha.END_TIME.set(2017, 3, 26, 19, 45, 0);
		} else {
			try {
				int val = 0;
				double dVal = .0;
				for (int i = 0; i < args.length;i += 2) {
					if (i+1 < args.length) {
						try {
							dVal = Double.valueOf(args[i+1]);
							val = Integer.valueOf(args[i+1]);
						} catch (Exception e) {

						}
					}
					switch (args[i]) {
						case "-d": Buddha.END_TIME.add(Calendar.DAY_OF_YEAR, val);
									Buddha.runtime.add(Calendar.DAY_OF_YEAR, val);
									break;
						case "-h": Buddha.END_TIME.add(Calendar.HOUR, val);
									Buddha.runtime.add(Calendar.HOUR, val);
									break;
						case "-m": Buddha.END_TIME.add(Calendar.MINUTE, val);
									Buddha.runtime.add(Calendar.MINUTE, val);
									break;
						case "-s": Buddha.END_TIME.add(Calendar.SECOND, val);
									Buddha.runtime.add(Calendar.SECOND, val);
									break;
						case "-np": Buddha.NUM_THREADS = val;break;
						case "-i": Buddha.N = val;break;
						case "-c": Buddha.contrast = dVal;break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
			if (startTime.getTimeInMillis() == Buddha.END_TIME.getTimeInMillis()) {
				Buddha.END_TIME.add(Calendar.MINUTE, 1);
				Buddha.runtime.add(Calendar.MINUTE, 1);
			}

			int hours = Buddha.runtime.get(Calendar.HOUR);
			int minutes = Buddha.runtime.get(Calendar.MINUTE);
			int seconds = Buddha.runtime.get(Calendar.SECOND);
			String allotted = (hours > 0 ? hours + " hour" : "") +
								(hours > 1 ? "s" : "") +
								(hours > 0 && minutes > 0 ? ", " : "") +
								(minutes > 0 ? minutes + " minute" : "") +
								(minutes > 1 ? "s" : "") +
								(minutes > 0 && seconds > 0 ? ", " : "") +
								(hours > 0 && minutes == 0 && seconds > 0 ? ", " : "") +
								(seconds > 0 ? seconds + " second" : "") +
								(seconds > 1 ? "s" : "");

			System.out.println("  Execution started at: " + startTime.getTime());
			System.out.println("  Completion scheduled for: " +
				Buddha.END_TIME.getTime());
			System.out.println("  Allotted runtime: " + allotted);
			System.out.println("  Number of threads: " + Buddha.NUM_THREADS);
			System.out.println("  Iteration depth: " + Buddha.N);
			System.out.println("  Image contrast: " + Buddha.contrast);
			System.out.println("  Image resolution: " + Buddha.X_RES + "x" + Buddha.Y_RES + "\n");
		}
		SimpleDateFormat format = new SimpleDateFormat("HH-mm-ss");
		Buddha.runtimeString = format.format(Buddha.runtime.getTime());
	}

	public static void getResolution(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-rx")) {
				try {
					Buddha.X_RES = Integer.parseInt(args[i+1]);
				} catch (Exception e) {
					System.exit(1);
				}
			}
			if (args[i].equals("-ry")) {
				try {
					Buddha.Y_RES = Integer.parseInt(args[i+1]);
				} catch (Exception e) {
					System.exit(1);
				}
			}
		}
	}

	public static int getColorMap(double v) {
		v = Math.min(Buddha.contrast*v, v*.1+.899);
		double[] c0 = new double[] {0, 0, 0};
		double[] c1 = new double[] {.999, .999, .999};
		double r,g,b;

		// double v2 = v;

		r = c1[0]*v + c0[0]*(1.0-v);
		g = c1[1]*v + c0[1]*(1.0-v);
		b = c1[2]*v + c0[2]*(1.0-v);
		// b = Math.min(.999,2*b);

		int rgb = (int)(r*256) << 16 | (int)(g*256) << 8 | (int)(b*256);
		return rgb;
	}

	public static String getFileName() {
		String name = "buddha/buddha";
		name += "_" + Buddha.N;
		name += "_" + Buddha.X_RES + "x" + Buddha.Y_RES;
		name += "_" + Buddha.runtimeString;
		name += ".png";
		return name;
	}

	public static int getMax() {
		int max = 0;
		for (int i = 0; i < Buddha.Y_RES; i++) {
			for (int j = 0; j < Buddha.X_RES; j++) {
				max = Buddha.m[i][j].get() > max ? Buddha.m[i][j].get() : max;
			}
		}
		return max;
	}

	public static void calcMinAndSum() {
		int min = Integer.MAX_VALUE;
		long sum = 0;
		for (int i = 0; i < Buddha.Y_RES; i++) {
			for (int j = 0; j < Buddha.X_RES; j++) {
				min = Buddha.m[i][j].get() < min ? Buddha.m[i][j].get() : min;
				sum += (long) Buddha.m[i][j].get();
			}
		}
		double perPixel = sum/(double)(Buddha.X_RES*Buddha.Y_RES);
		String form = "%.2f";
		if (perPixel > 1e3) {
			form = "%.2e";
		}
		System.out.println(String.format("\n  Total iterations: %.2e", (double)sum));
		System.out.println(String.format("  Iterations per pixel: "+form, perPixel));
		System.out.println("  Minimum frequency: " + min);

	}

	public static void drawImage(int name, BufferedImage output) {
		calcMinAndSum();
		int max = getMax();
		int count = 0;
		System.out.println("  Maximum frequency: " + max + "\n");
		for (int i = 0; i < Buddha.Y_RES; i++) {
			for (int j = 0; j < Buddha.X_RES; j++) {
				if (++count % 100000 == 0) {
					System.out.print(String.format(
						"     \r  Image processing... %.2f%%",
						100.0*count/(double)(Buddha.Y_RES)/(double)(Buddha.X_RES)));
				}
				double sum = Buddha.m[i][j].get();
				int rgb = getColorMap(sum/(double)(max));
				output.setRGB(j, i, rgb);
			}
		}
		System.out.println("\n  Image generated, saving...");
		try {
			String fileName = getFileName();
			System.out.println("  File name: " + fileName);
			ImageIO.write(output, "png", new File(fileName));
		} catch (Exception e) {
			System.out.println("error");
			e.printStackTrace();
		}
	}
}