import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class MyButtonDemo extends JButton {

	public MyButtonDemo(String s) {
		super(s);
		setPreferredSize(new Dimension(80,30));
		addActionListener(new MyListenerDemo());
	}
}