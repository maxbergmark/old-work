import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class KnappPanelDemo extends JPanel implements ActionListener {

	MyButtonDemo knapp;
	int radie = 25;
	int clicks = 0;

	public KnappPanelDemo() {
		knapp = new MyButtonDemo("knapp1");
		add(knapp);
		knapp.addActionListener(this);

		Timer timer = new Timer(100, this);

		timer.start();

	}
	/**
	* Handles events.
	*@param e an ActionEvent generated by Java. Provides information about the source of the action.
	*/

	public void actionPerformed(ActionEvent e) {
//		System.out.println((int)(25 + 10*Math.sin(System.nanoTime()/100000000.0)));
		clicks++;
		radie = (int)(25 + 10*Math.sin(clicks/5.0));
		repaint();
	}

	public void paint(Graphics g) {
		double theta = clicks/5.0;
		Toolkit.getDefaultToolkit().sync();
		g.setColor(Color.white);
		g.clearRect(0,50,getWidth(), getHeight());
		int x = getWidth()/2 + (int)(50*Math.cos(theta));
		int y = getHeight()/2 + (int)(50*Math.sin(theta));
		g.setColor(Color.red);
		g.fillOval(x-radie, y-radie, 2*radie, 2*radie);
	}
}