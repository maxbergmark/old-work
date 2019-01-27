import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KnappApplet extends JApplet {


	public KnappApplet() {
//		setPreferredSize(new Dimension(400,300));
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		KnappPanel panel = new KnappPanel();
		add(panel);
		//pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		new KnappApplet();
	}
}