import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.event.*;
import java.io.*;


public class Controller extends JPanel implements ActionListener {
	private Model testmodel;
	private View testview;
	private Timer testtimer;
	private int frame = 0;
	private int frameSpawn;


	public Controller(int num) {
		int xsize = 1200;
		int ysize = 600;
		frameSpawn = num;
		testmodel = new Model(0, xsize, ysize);
		testview = new View(testmodel, xsize + 20, ysize);
		testtimer = new Timer(testmodel.getDelta(), this);
		testtimer.start();

	}

	public void updater() {
		testmodel.updateParticles();
		testview.repaint();
	}

	public void actionPerformed(ActionEvent e){
		testmodel.addParticles(frameSpawn);
		testmodel.updateParticles();
		testview.repaint();
		frame++;
    }



	public static void main(String[] args) {
		int n;
		if (args.length == 1) {
			n = (int) Integer.parseInt(args[0]);
		}
		else {
			n = 10;
		}
		Controller testcontroller = new Controller(n);

	}
}