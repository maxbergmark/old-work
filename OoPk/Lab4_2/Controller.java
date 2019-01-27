import java.util.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.io.*;

public class Controller implements ActionListener {

	Model m;
	View v;
	StringBuilder s;
	static File f;
	static PrintWriter p;
	int c = 0;
	double average;
	double maxAverage;
	double drawAverage;

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public Controller(int n, int xsize, int ysize, int grain, int collision) {
		m = new Model(n, xsize, ysize, grain, collision);
		View.getCircle();
		v = new View(m, xsize, ysize);
		s = new StringBuilder();
		Timer t = new Timer(10, this);
		t.setInitialDelay(500);
		t.start();
		System.out.println("\n Times below are in milliseconds.");
		System.out.println(String.format("%7s\t%7s\t%7s\t%7s\t%7s", "move()", "moveavg", "max_avg", "drawavg", "moving"));

	}

	public void actionPerformed(ActionEvent e) {
		long t0 = System.nanoTime();
		m.move();
		long t1 = System.nanoTime();
		double dt = (t1-t0)/1e6;
		c++;
		average = (1-1/(double)c)*average + (1/(double)c)*dt;
		drawAverage = (1-1/(double)c)*drawAverage + (1/(double)c)*v.lastDT/1e6;
		maxAverage = c>20?average>maxAverage?average:maxAverage:maxAverage;
		int moving = m.getN()-m.getFrozen();
		/*
		s.append(dt + ", ");
		c++;
		if (c % 1000 == 0) {
			System.out.println("printing");
			p.print(s);
			p.flush();
			s = new StringBuilder();
		}
		*/

		String f0 = "";
		String f1 = "";
		String f2 = "";
		String f3 = "";

		if (average > 10) {
			f0 = ANSI_RED;
			f1 = ANSI_RESET;
		}
		if (drawAverage > 10) {
			f2 = ANSI_RED;
			f3 = ANSI_RESET;
		}

		System.out.print(String.format("\r%7.3f\t%s%7.3f%s\t%7.3f\t%s%7.3f%s\t%7d", 
			dt, f0, average, f1, maxAverage, f2, drawAverage, f3, moving));
		v.repaint();
	}

	public static void main(String[] args) {
		int n = 10000;
		int xsize = 500;
		int ysize = 500;
		int grain = 1;
		int collision = 1;
		try {
			n = Integer.parseInt(args[0]);
			xsize = Integer.parseInt(args[1]);
			ysize = Integer.parseInt(args[2]);
			grain = Integer.parseInt(args[3]);
			collision = Integer.parseInt(args[4]);
			f = new File("output2.txt");
			p = new PrintWriter(f);
		} catch (IOException e) {
			return;
		} catch (Exception e) {

		}
		new Controller(n, xsize, ysize, grain, collision);
	}
}