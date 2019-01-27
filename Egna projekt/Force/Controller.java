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
	private JSlider[] sliders;
	private StringBuilder sb;
	private JButton button;
	private int buttoncount;


	public Controller(int num) {
		sb = new StringBuilder();
		int xsize = 800;
		int ysize = 800;
		double scale = 2.5;
		testmodel = new Model(num, xsize, ysize, scale);
		testview = new View(testmodel, xsize, ysize);
		testtimer = new Timer(testmodel.getDelta(), this);

		testtimer.start();

	}
	class SliderClass implements ChangeListener {

		public void stateChanged(ChangeEvent e){
			JSlider source = (JSlider)e.getSource();
			if(!source.getValueIsAdjusting()){
				if (source == sliders[0]) {
					testmodel.setL((double)source.getValue());
				}
				else {
					testmodel.setDelta((int)source.getValue());
					testtimer.setDelay((int)source.getValue());
				}
			}
		}
	}

	public void updater() {
		System.out.println("fdsa");
		testmodel.updateParticles();
		testview.repaint();
	}

	public void actionPerformed(ActionEvent e){ //varje klockslag blir ett event
		testmodel.updateParticles();
		testview.repaint();
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