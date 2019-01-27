import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class View extends JPanel {

	Model m;

	public View(Model m) {
		this.m = m;
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(500,500));
		f.add(this);
		f.pack();
		f.setVisible(true);
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// casta till Graphics2D, som har 2D-objekten som ska ritas.

		g2.clearRect(0,0,500,500);

		g2.setPaint(Color.RED);
		g2.fill(new Ellipse2D.Double(m.getX(), m.getY(), 15, 15)); 

		Toolkit.getDefaultToolkit().sync();
		// behövs i Java 8 på Linux för animering.

	}
}