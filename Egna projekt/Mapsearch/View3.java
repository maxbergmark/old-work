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


public class View3 extends JPanel implements FocusListener, ChangeListener {

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
	private int scale;
	private boolean drawAll = true;
	public boolean completed = false;
	private boolean lastDraw = false;
	private volatile boolean readyToCompute = false;
	private BufferedImage bimg;
//	private static Color[] cols = {new Color(255,255,255), new Color(0,0,0), 
//		new Color(0,255,0), new Color(255,0,0), new Color(255,0,255), new Color(0,255,255), new Color(255,150,150)};
	private static int[] cols2 = {0xFFFFFF, 0x000000, 0x00FF00, 0xFF0000, 0xFF00FF, 0x00FFFF, 0xFF8888};

	public View3(SearchClass m, int xs, int ys, int sc, String fN, BufferedImage bimg) {
		xsize = xs;
        ysize = ys;
        scale = sc;
		testmodel = m;
		this.bimg = bimg;
//		bimg = (BufferedImage) bimg.getScaledInstance(bimg.getWidth()*scale, bimg.getHeight()*scale, bimg.getType());
		BufferedImage new_img = new BufferedImage(bimg.getWidth()*scale, bimg.getHeight()*scale, bimg.getType());
		Graphics g = new_img.createGraphics();
		g.drawImage(bimg, 0, 0, new_img.getWidth(), new_img.getHeight(), null);
		this.bimg = new_img;
		testframe = new JFrame("Maze");
//		testframe.setLayout(new GridBagLayout());
//		c = new GridBagConstraints();
//		c2 = new GridBagConstraints();
		testframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    	testframe.setPreferredSize(new Dimension(round(scale*xsize+20), round(scale*ysize+40)));
    	setPreferredSize(new Dimension(this.bimg.getWidth(), this.bimg.getHeight()));
    	scrollPane = new JScrollPane(this);

    	
    	testframe.add(scrollPane);
    	testframe.pack();
    	testframe.setVisible(true);

//    	testframe.addFocusListener(this);

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

	public BufferedImage getImage() {
		return bimg;
	}

	public void paint(Graphics g) {
		if (testmodel.completed) {
			lastDraw = true;
		}
		if (testmodel.ready && !completed) {
			int tot = 0;
			for (int i = 0; i < ysize; i++) {
				for (int j = 0; j < xsize; j++) {

					//System.out.println(i + "   " + j);
					SearchClass.Node t = testmodel.getNode(i,j);

					int ci = t.ofPath() ? 4 : (t.getV()>0 & t.getM() < 2) ? (t.getV() == 1 ? 5 : 6) : t.getM();

					if (t.changed()) {
//						g2D.fill(new Rectangle2D.Double(t.getY()+offsetY, t.getX()+offsetX, 1, 1));
						for (int y = 0; y < scale; y++) {
							for (int x = 0; x < scale; x++) {
								bimg.setRGB(t.getY()*scale+y, t.getX()*scale+x, cols2[ci]);
								//bimg.setRGB(t.getY(), t.getX(), cols2[ci]);
								//System.out.println(bimg.getWidth());
							}
						}
					}				}
			}
			sliders = 0;
		}
		g.drawImage(bimg,0,0,null);
		Toolkit.getDefaultToolkit().sync();
		readyToCompute = true;
		if (lastDraw) {
			completed = true;			
		}

	}

}