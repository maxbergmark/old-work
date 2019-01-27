import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BlinkKnapp implements ActionListener {

	JButton b;
	Color[] cols = {Color.RED, Color.BLUE};
	String[] texts = {"test1", "test2"};
	int state = 0;
	
	public BlinkKnapp() {

		JFrame f = new JFrame();
		b = new JButton();
		b.setText(texts[0]);
		b.setBackground(cols[0]);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setPreferredSize(new Dimension(500,500));

		f.add(b);
		f.pack();
		f.setVisible(true);

		MyTimer t = new MyTimer(500, this);
		t.setInitialDelay(1000);
		t.start();

	}

	public void actionPerformed(ActionEvent e) {
		b.setText(texts[++state%2]);
		b.setBackground(cols[state%2]);
		Toolkit.getDefaultToolkit().sync();
	}

	public static void main(String[] args) {
		new BlinkKnapp();
	}
}