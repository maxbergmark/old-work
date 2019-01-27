import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;


public class Controller extends JPanel implements ActionListener {
	private SearchClass testmodel;
	private View3 testview;
	private Timer testtimer;
	private JSlider[] sliders;
	private StringBuilder sb;
	private JButton button;
	private int buttoncount;


	public Controller(String str, String dist, String searchWay, String algorithm, String walking, int scale, long tStep, boolean saving) {
		String dir = "Mazes/";
		try {
			BufferedImage bimg = ImageIO.read(new File(dir+str+".png"));
			int width          = bimg.getWidth();
			int height         = bimg.getHeight();
			testmodel = new SearchClass(bimg, str, dist, searchWay, algorithm, walking, tStep, saving);
			testview = new View3(testmodel, width, height, scale, str, bimg);
			testmodel.setView2(testview);
			testtimer = new Timer(10, this);
			

		} catch (Exception e) {
			System.out.println("File Error" + e);
		}
		testtimer.start();

		testmodel.readData();
		try {Thread.sleep(2000);} catch (Exception e) {}
		testmodel.compute();

	}


	public void actionPerformed(ActionEvent e){
		testview.repaint();
    }



	public static void main(String[] args) {
		String fileName = "tricky.png";
		String dist = "manhattan";
		String searchWay = "StoF";
		String algorithm = "BFS";
		String walking = "4ways";
		boolean saving = true;
		int scale = 1;
		long tStep = 200000;
		System.out.println();
		for (String s: args) {
			if (s.matches("^.*\\.png")) {
				System.out.println("fileName: " + s);
				fileName = s.substring(0,s.length()-4);
			}
			if (s.matches("(manhattan|pyth|orto)")) {
				System.out.println("dist: " + s);
				dist = s;
			}
			if (s.matches("(StoF|FtoS|bidir)")) {
				System.out.println("Search Method: " + s);
				searchWay = s;
			}
			if (s.matches("(BFS|DFS|astar|heuristic|new)")) {
				System.out.println("Algorithm: " + s);
				algorithm = s;
			}
			if (s.matches("(4ways|8ways)")) {
				System.out.println("Walking: " + s);
				walking = s;
			}
			if (s.matches("^s(\\d*\\.?\\d*)$")) {
				scale = Integer.parseInt(s.substring(1,s.length()));
				System.out.println("Scale: " + scale);
			}
			if (s.matches("^t(\\d*)")) {
				tStep = Long.parseLong(s.substring(1,s.length()));	
				System.out.println("Timestep: " + tStep + "ns");
			}
			if (s.matches("(no)?save")) {
				System.out.println("Saving: " + s);
				saving = !s.startsWith("n");
			}
		}
		
		Controller testcontroller = new Controller(fileName, dist, searchWay, algorithm, walking, scale, tStep, saving);

	}
}