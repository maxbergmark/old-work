import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class View extends JPanel {

	Model m;
	int xsize, ysize;
	BufferedImage i;
	Graphics2D iG;
	public static int[] circleX;
	public static int[] circleY;
	public long lastDT;
	private boolean drawing = true;

	public View(Model m, int xsize, int ysize) {

		this.m = m;
		this.xsize = xsize;
		this.ysize = ysize;
		i = new BufferedImage(xsize, ysize, BufferedImage.TYPE_INT_RGB);
		iG = i.createGraphics();
		setPreferredSize(new Dimension(xsize, ysize));
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(this);
		f.pack();
		f.setVisible(true);

	}

	public static void getCircle() {
		ArrayList<Integer> tempX = new ArrayList<>();
		ArrayList<Integer> tempY = new ArrayList<>();
		for (int y = -10; y <= 10; y++) {
			for (int x = -10; x <= 0; x++) {
				double d = Math.pow(x*x+y*y, 0.5);
				if (Math.round(d) == 10) {
					tempX.add(x);
					tempY.add(y);
					if (x != 0) {
						tempX.add(-x);
						tempY.add(y);
					}
					
				}
			}
		}
		int c = 0;
		circleX = new int[tempX.size()];
		circleY = new int[tempY.size()];
		for (Integer i : tempX) {
			circleX[c++] = i;
		}
		c = 0;
		for (Integer i : tempY) {
			circleY[c++] = i;
		}

	}


	public void paint(Graphics g) {
		long t0 = System.nanoTime();

		if (drawing) {

			iG.setPaint(Color.WHITE);
			iG.fillRect(0, 0, i.getWidth(), i.getHeight());
			for (Model.Particle p : m.getParticles()) {
				if (p.x > 0 & p.x < xsize & p.y > 0 & p.y < ysize) {
					int color = (1<<8)-1;
					if (p.frozen) {
						color <<= 16;
					}
					i.setRGB((int)p.x,(int)p.y,color);
					if (!p.frozen && m.getN()-m.getFrozen() < 1000) {
						for (int k = 0; k < circleX.length; k++) {
							int x = circleX[k];
							int y = circleY[k];
							if (p.x+x > 0 & p.x+x < xsize & p.y+y > 0 & p.y+y < ysize) {
								i.setRGB((int)p.x+x,(int)p.y+y,255<<8);
							}
						}
					}
				}
			}
		} 

		if(m.getN() == m.getFrozen()) {
			drawing = false;
		}

		/*
		int c = 0;
		for (int y = 100; y <= 600; y++) {
			for (int x = 100; x <= 600; x++) {
				double d = Math.pow((300-x)*(300-x)+(300-y)*(300-y), 0.5);
				if (Math.round(d) == 100) {
					//System.out.println(d);
					i.setRGB(x, y, 0);
					c++;
				}
			}
		}
		System.out.println(c);
		*/

		//g.drawImage(i.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH),0,0,null);
		g.drawImage(i,0,0,null);
		long t1 = System.nanoTime();
		lastDT = t1-t0;
		//System.out.println((t1-t0)/1000/1000.0);
	}


/*
	public void paint2(Graphics g) {
		long t0 = System.nanoTime();
		Graphics2D g2 = (Graphics2D) g;

		g2.clearRect(0,0,xsize,ysize);
		Rectangle2D.Double d = new Rectangle2D.Double();
		for (Model.Particle p : m.getParticles()) {
			d.setFrame(p.x,p.y,1,1);
			g2.fill(d);
		}
		g2.setPaint(Color.RED);
		for (Integer i : m.getSet()) {
			g2.fill(new Rectangle2D.Double(i%xsize, i/xsize, 1, 1));
		}
		long t1 = System.nanoTime();
		//System.out.println((t1-t0)/1000/1000.0);
	}
*/
}