import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class KnappFrameDemo extends JFrame {


	/**
	* Constructor for KnappFrameDemo.
	*/
	public KnappFrameDemo() {
		setPreferredSize(new Dimension(400,300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		KnappPanelDemo myPanel = new KnappPanelDemo();
		add(myPanel);
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		KnappFrameDemo myFrame = new KnappFrameDemo();
	}
}