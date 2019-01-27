import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KnappFrame extends JFrame {

	/**
	* Constructor for KnappFrame.
	*
	*/
	public KnappFrame() {
		setPreferredSize(new Dimension(400,300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		KnappPanel panel = new KnappPanel();
		add(panel);
		pack();
		setVisible(true);
	}

	public static void main(String[] a) {
		new KnappFrame();
	}
}