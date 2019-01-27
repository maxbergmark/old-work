import java.applet.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ButtonApplet extends JApplet {

	public void init() {
		Button test = new Button(Color.cyan, Color.magenta, "Message 1","Message 2");
		add(test);
		setVisible(true);

	}
}