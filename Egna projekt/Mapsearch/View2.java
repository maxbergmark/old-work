import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.awt.GridLayout;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;


public class View2 extends JPanel implements FocusListener, ChangeListener {

	private SearchClass testmodel;
	private JFrame testframe;
	private JScrollPane scrollPane;
	private JLabel jLabel2;
	private int sliders = 0;
	private int a = 0;
	private GridBagConstraints c;
	private GridBagConstraints c2;
	private int xsize;
	private int ysize;
	private double scale;
	private boolean drawAll = true;
	private volatile boolean readyToCompute = false;
	private BufferedImage bimg;
	private static Color[] cols = {new Color(255,255,255), new Color(0,0,0), 
		new Color(0,255,0), new Color(255,0,0), new Color(255,0,255), new Color(0,255,255), new Color(255,150,150)};

	public View2(SearchClass m, int xs, int ys, double sc, String fN, BufferedImage bimg) {
		xsize = xs;
        ysize = ys;
        scale = sc;
		testmodel = m;
		this.bimg = bimg;
		testframe = new JFrame("Maze");
//		testframe.setLayout(new GridBagLayout());
//		c = new GridBagConstraints();
//		c2 = new GridBagConstraints();
		testframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	testframe.setPreferredSize(new Dimension(round(scale*xsize+20), round(scale*ysize+40)));
    	//testframe.add(this);
//    	setLayout(new GridBagLayout());
//    	setPreferredSize(new Dimension(xsize, ysize));

    	
    	ImageIcon imgThisImg = new ImageIcon("/home/max/Google Drive/Programmering/Egna projekt/Mapsearch/Mazes/"+fN);
    	jLabel2 = new JLabel(imgThisImg);
//    	jLabel2 = new JLabel("asdf");
//		jLabel2.setPreferredSize(new Dimension(xsize, ysize));
/*
    	c.fill = GridBagConstraints.BOTH;
    	c.gridx = 0;
    	c.gridy = 0;
    	c.weightx = 1;
    	c.weighty = 1;

    	c2.fill = GridBagConstraints.BOTH;
    	c2.gridx = 0;
    	c2.gridy = 0;
    	c2.weightx = 1;
    	c2.weighty = 1;
*/		
    	setLayout(new GridBagLayout());
    	GridBagConstraints gbc = new GridBagConstraints();
    	gbc.anchor = GridBagConstraints.NORTHWEST;
    	gbc.weighty = 1;
    	gbc.weightx = 1;
    	add(jLabel2, gbc);
    	scrollPane = new JScrollPane(this);
    	scrollPane.getViewport().addChangeListener(this);
    	//scrollPane.add(jLabel2);
		//testframe.add(jLabel2, c);
		gbc.fill = GridBagConstraints.BOTH;
    	
		testframe.setLayout(new GridBagLayout());
    	testframe.add(scrollPane, gbc);
  		//testframe.add(this);
    	testframe.pack();
    	testframe.setVisible(true);

    	testframe.addFocusListener(this);

	}

	public void stateChanged(ChangeEvent e) {
		drawAll = true;
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
		/*
		if (drawAll) {
			super.paint(g);
		}
		int offsetX = 0;
		int offsetY = 0;
		//System.out.println("\n\n" + jLabel2.getWidth() + getHeight());
		Toolkit.getDefaultToolkit().sync();
		Graphics2D g2D = (Graphics2D)g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);

//		if (drawAll) {g2D.setColor(cols[0]);g2D.fillRect(0, 0, xsize, ysize);}
		//System.out.println(testmodel);
		if (testmodel.ready) {
			int tot = 0;
			for (int i = 0; i < ysize; i++) {
				for (int j = 0; j < xsize; j++) {

					//System.out.println(i + "   " + j);
					SearchClass.Node t = testmodel.getNode(i,j);

					int ci = t.ofPath() ? 4 : (t.getV()>0 & t.getM() < 2) ? (t.getV() == 1 ? 5 : 6) : t.getM();
					if (drawAll && ci >= 4) {
						t.setPainted(false);
					}
					if (t.changed()) {
						g2D.setColor(cols[ci]);
						//g2D.fillRect(round(scale*t.getY()), round(scale*t.getX()), Math.max(1,round(scale)), Math.max(1,round(scale)));
						g2D.fill(new Rectangle2D.Double(t.getY()+offsetY, t.getX()+offsetX, 1, 1));
					} else if (ci >= 4 && tot < 1000 && !t.getPainted()) {
						//System.out.println(Math.random());
;						g2D.setColor(cols[ci]);
						tot++;
						t.setPainted(true);
						//g2D.fillRect(round(scale*t.getY()), round(scale*t.getX()), Math.max(1,round(scale)), Math.max(1,round(scale)));
						g2D.fill(new Rectangle2D.Double(t.getY()+offsetY, t.getX()+offsetX, 1, 1));
					}
				}
			}
			sliders = 0;
		}
		drawAll = false;
		readyToCompute = true;
		//g2D.setColor(Color.BLACK);
		//g2D.fill(new Rectangle2D.Double(0, 800, 800, 200));
	*/
	}
}