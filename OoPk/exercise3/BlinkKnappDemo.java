import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BlinkKnappDemo {

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
		frame.add(button);
		frame.pack();
		frame.setVisible(true);
	}

	public void toggleButton() {
		button.setText(texts[++state]);
		button.setBackground(cols[state]);
	}

	public static void main(String[] args) {
		new BlinkKnappDemo();
	}
}