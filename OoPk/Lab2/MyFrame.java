

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {


	public MyFrame() {

		setPrefferedSize(new Dimension(400, 300));
//		setDefaultCloseOperation(Jframe.EXIT_ON_CLOSE);
/*
		MyPanel mypanel = new MyPanel();
		add(mypanel);
		pack();
		setVisible(true);
*/
	}

	public static void main(String[] args) {
		MyFrame myframe = new MyFrame();
	}
}