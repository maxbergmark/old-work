import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BlinkKnappDemo implements ActionListener {

	JButton button;
	JFrame frame;
	Timer timer;
	int state = 0;
	Color[] cols = {Color.RED, Color.BLUE};
	String[] texts = {"text1", "text2"};

	public BlinkKnappDemo() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(500,500));
		button = new JButton();
		button.setText(texts[0]);
		button.setBackground(cols[0]);
		frame.add(button);
		frame.pack();
		frame.setVisible(true);

		timer = new Timer(100, this);
		timer.setInitialDelay(1000);
		timer.start();
	}

	public void actionPerformed(ActionEvent e) {
		//timer.setDelay((int) (Math.random()*1000));
		toggleButton();
	}

	public void toggleButton() {
		button.setText(texts[++state%2]);
		button.setBackground(cols[state%2]);
		Toolkit.getDefaultToolkit().sync();
	}

	public static void main(String[] args) {
		new BlinkKnappDemo();
	}
}