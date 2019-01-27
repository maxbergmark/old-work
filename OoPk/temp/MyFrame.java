

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;


public class MyFrame extends JFrame {

);

	public MyFrame() {

		setPreferredSize(new Dimension(400, 300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE
		MyPanel mypanel = new MyPanel();
		add(mypanel);
		pack();
		setVisible(true);

	}

	public static void main(String[] args) {
		MyFrame myframe = new MyFrame();
	}
}