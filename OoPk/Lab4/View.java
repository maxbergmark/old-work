import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.awt.GridLayout;


public class View extends JPanel{

	private Model testmodel;
	private JFrame testframe;
	private int sliders = 0;
	private GridBagConstraints c;
	private int xsize;
	private int ysize;

	public View(Model m, int xs, int ys) {
		xsize = xs;
        ysize = ys;
		testmodel = m;
		testframe = new JFrame("Party go Partay!");
		testframe.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		testframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Hängde sig direkt utan detta...
    	testframe.setPreferredSize(new Dimension(xsize, ysize+150));
    	//testframe.add(this);
    	
    	c.fill = GridBagConstraints.BOTH;
    	c.gridx = 0;
    	c.gridy = 0;
    	c.weightx = 1;
    	c.weighty = 1;
    	testframe.add(this, c);
    	testframe.pack();
    	testframe.setVisible(true);
	}

	public void addButton(JButton button){
		c.gridx = 0;
		c.gridy = 3; //anger vilken position vår slider ska ha i layouten, och är generaliserad för godtyckligt många sliders.
		c.weighty = 0;
		testframe.add(button, c);
		testframe.setVisible(true);
	}

	public void addSlider(JSlider slider) {
		c.gridx = 0;
		c.gridy = ++sliders; //anger vilken position vår slider ska ha i layouten, och är generaliserad för godtyckligt många sliders.
		c.weighty = 0;
		testframe.add(slider, c);
		testframe.setVisible(true);

	}

	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D)g;
		//g2D.setColor(testmodel.getParticle(0).getColor());
		g2D.setColor(new Color(255,255,255));
		g2D.fillRect(0, 0, xsize, ysize+200);

		for (int i = 0; i < testmodel.getPN(); i++) {
			int tempx = (int)testmodel.getParticle(i).getX();
			int tempy = (int)testmodel.getParticle(i).getY();
			g2D.setColor(testmodel.getParticle(i).getColor());
			g2D.fill(new Ellipse2D.Double(tempx, tempy, 2, 2));
		}
		//g2D.setColor(Color.BLACK);
		//g2D.fill(new Rectangle2D.Double(0, 800, 800, 200));
	}

	public static void main(String[] args) {
		
	}

}