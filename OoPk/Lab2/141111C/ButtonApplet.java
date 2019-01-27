import java.applet.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ButtonApplet extends JApplet {

	public void init() {
		MyButton test = new MyButton(Color.cyan, Color.magenta, "Message 1","Message 2");
		add(test);
		setVisible(true);

	}
}