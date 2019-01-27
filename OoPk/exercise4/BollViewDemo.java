import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class BollViewDemo extends JPanel {

	final int XS = 500;
	final int YS = 500;
	double x;
	double y;
	boolean p = false;

	public BollViewDemo() {
		super();
		setPreferredSize(new Dimension(XS,YS));
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(this);
		f.pack();
		f.setVisible(true);
	}

	public void newEvent(double x, double y) {
		this.x = x;
		this.y = y;
		repaint();
		p = true;
	}

	public void paint(Graphics g) {
		if (p) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(Color.WHITE);
			
			//g2.fill(new Rectangle2D.Double(0,0,500,500));
			g2.setPaint(Color.RED);
			g2.fill(new Ellipse2D.Double(x, y, 15, 15));

			Toolkit.getDefaultToolkit().sync();
		}
	}
}