

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel implements ActionListener {
	private int radii = 5;
	private JButton  mybutton;


	public MyPanel() {
		mybutton = new JButton("Press Me!");
		mybutton.setPreferredSize(new Dimension(150, 150));
		mybutton.addActionListener(this);
		mybutton.setOpaque(false);
		mybutton.setContentAreaFilled(false);
		mybutton.setBorderPainted(false);
		add(mybutton);
	}

	public void actionPerformed(ActionEvent e) {
		radii += 5;
		repaint();
	}

	public void paint(Graphics g) {
		int x = getWidth()/2;
		int y = getHeight()/2;
		g.setColor(Color.red);
		g.fillOval(x-radii, y-radii, 2*radii, 2*radii);
	}
}