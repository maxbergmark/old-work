import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.awt.GridLayout;


public class View extends JPanel implements FocusListener {

	private SearchClass testmodel;
	private JFrame testframe;
	private int sliders = 0;
	private int a = 0;
	private GridBagConstraints c;
	private int xsize;
	private int ysize;
	private double scale;
	private boolean drawAll = true;
	private volatile boolean readyToCompute = false;
	private static Color[] cols = {new Color(255,255,255), new Color(0,0,0), 
		new Color(0,255,0), new Color(255,0,0), new Color(255,0,255), new Color(0,255,255)};

	public View(SearchClass m, int xs, int ys, double sc) {
		xsize = xs;
        ysize = ys;
        scale = sc;
		testmodel = m;
		testframe = new JFrame("Maze");
		testframe.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		testframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	testframe.setPreferredSize(new Dimension(round(scale*xsize+20), round(scale*ysize+40)));
    	//testframe.add(this);
    	
    	c.fill = GridBagConstraints.BOTH;
    	c.gridx = 0;
    	c.gridy = 0;
    	c.weightx = 1;
    	c.weighty = 1;
    	testframe.add(this, c);
    	testframe.pack();
    	testframe.setVisible(true);

    	testframe.addFocusListener(this);

	}

	public void focusGained(FocusEvent e) {
		drawAll = true;
		repaint();
	}

	public void focusLost(FocusEvent e) {
		drawAll = true;
		repaint();
	}

	private int round(double d) {
		return (int)(d+0.5);
	}

	public void setReady(boolean b) {
		readyToCompute = b;
	}

	public boolean getReady() {
		return readyToCompute;
	}

	public void paint(Graphics g) {
		if (!drawAll) {
			Toolkit.getDefaultToolkit().sync();
		}
		Graphics2D g2D = (Graphics2D)g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);

		if (drawAll) {g2D.setColor(cols[0]);g2D.fillRect(0, 0, xsize, ysize);}
		//System.out.println(testmodel);
		if (testmodel.ready) {
			for (int i = 0; i < ysize; i++) {
				for (int j = 0; j < xsize; j++) {
					//System.out.println(i + "   " + j);
					SearchClass.Node t = testmodel.getNode(i,j);
					if (drawAll || t.changed()) {
						int ci = t.ofPath() ? 4 : (t.getV()>0 & t.getM() < 2) ? 5 : t.getM();
						g2D.setColor(cols[ci]);
						//g2D.fillRect(round(scale*t.getY()), round(scale*t.getX()), Math.max(1,round(scale)), Math.max(1,round(scale)));
						g2D.fill(new Rectangle2D.Double(scale*t.getY(), scale*t.getX(), scale, scale));
					}
				}
			}
			sliders = 0;
		}
		drawAll = false;
		readyToCompute = true;
		//g2D.setColor(Color.BLACK);
		//g2D.fill(new Rectangle2D.Double(0, 800, 800, 200));
	}
}