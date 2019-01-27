import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class BollStudsDemo extends JFrame implements ActionListener {


	double x = 250;
	double y = 250;
	double vx = 500;
	double vy = 500;
	double fx = 0;
	double fy = 0;
	ArrayList<Double> xpos;
	ArrayList<Double> ypos;
	final int DT = 20;
	final double GRAVITY = 1000;
	Timer timer;

	public BollStudsDemo() {
		xpos = new ArrayList<>();
		ypos = new ArrayList<>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(500,500));
		pack();
		setVisible(true);

		timer = new Timer(DT, this);
		timer.setInitialDelay(1000);
		timer.start();
	}

	public void actionPerformed(ActionEvent e) {
		move();
		repaint();
	}

	public void move() {
		if (x > 480 || x < 0) {
			vx = -vx;
		}
		if (y > 450 || y < 0) {
			vy = -vy;
		}

		double newvy = vy + (GRAVITY)*DT/1000.0;
		double newvx = vx + (0)*DT/1000.0;

		y = y + .5*(vy+newvy)*DT/1000.0;
		x = x + .5*(vx+newvx)*DT/1000.0;

		vy = newvy;
		vx = newvx;
		xpos.add(x);
		ypos.add(y);

	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(Color.WHITE);
		
		//g2.fill(new Rectangle2D.Double(0,0,500,500));
		if (xpos.size() > 1) {
			g2.setPaint(Color.RED);
			g2.fill(new Ellipse2D.Double(xpos.get(xpos.size()-2), ypos.get(ypos.size()-2), 15, 15));
		}
		g2.setPaint(Color.BLUE);
		g2.fill(new Ellipse2D.Double(x, y, 15, 15));

		//Line2D q = new Line2D.Double(x+7, y+7, 250, 0);
		//g2.draw(q);



		Toolkit.getDefaultToolkit().sync();
	}

	public static void main(String[] args) {
		new BollStudsDemo();
	}
}