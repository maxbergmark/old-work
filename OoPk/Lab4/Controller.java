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
		int xsize = 600;
		int ysize = 600;
		testmodel = new Model(num, xsize, ysize);
		testview = new View(testmodel, xsize + 20, ysize);
		testtimer = new Timer(testmodel.getDelta(), this);
		sliders = new JSlider[2];
		button = new JButton("Starta");
		button.addActionListener(this); 
		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				sliders[i] = new JSlider(JSlider.HORIZONTAL,0, 10, 1);
			}
			else {
				sliders[i] = new JSlider(JSlider.HORIZONTAL,1, 100, 10);
			}
			sliders[i].addChangeListener(new SliderClass());
			sliders[i].setMajorTickSpacing(10);
	        sliders[i].setMinorTickSpacing(1);
	        sliders[i].setPaintTicks(true);
	        sliders[i].setPaintLabels(true);
	        Font font = new Font("Serif", Font.ITALIC, 15);
	        sliders[i].setFont(font);
	    	testview.addSlider(sliders[i]);
	    	testview.addButton(button);

	    }

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
		//300 partiklar är gränsen när vi använder String istället för Stringbuilder
		
		
		double test = (double)testmodel.getTime()/1000;
		if (e.getSource().equals(button)){
			buttoncount++;

			if (buttoncount%2!=0){
				button.setText("Stoppa");
				sb.append(test); //lägger till tiden
				sb.append(", "); 
				for (int i = 0; i < testmodel.getPN(); i++) { //för alla partiklar
					sb.append((double)Math.round(testmodel.getParticle(i).getX()*1000)/1000); //lägger till x-pos
					sb.append(", ");
					sb.append((double)Math.round(testmodel.getParticle(i).getY()*1000)/1000); //lägger till y-pos 
				if (i < testmodel.getPN()-1) {
					sb.append(", ");
				}
				//sb.append("\n");
				}
			}
			else{
				button.setText("Starta");
			}
		}
		sb.append(System.getProperty("line.separator")); //gör att det blir en ny rad
		if (testmodel.getFrame() == 1000) {
			File f = new File("figuredata.txt");
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))) { 
	    		writer.write(sb.toString());
			}
			catch (Exception error) {
				return;
			}
			System.out.println("saved data");
		}		
		//System.out.println(sb);
		long t0 = System.nanoTime();
		testmodel.updateParticles();
		long t1 = System.nanoTime();
		System.out.println((t1-t0)/1000/1000.0);
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