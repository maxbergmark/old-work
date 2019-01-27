import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Controller implements ActionListener {
	
	Timer t;
	Model m;
	View v;
	double dt = 0.02;  // framerate på 50Hz för animering

	public Controller() {
		m = new Model(dt);
		v = new View(m);
		t = new Timer((int)(dt*1000), this);
		t.setInitialDelay(1000);
		t.start();
	}

	public void actionPerformed(ActionEvent e) {
		m.move();
		v.repaint();
		// varje gång som Timer t tickar så anropas actionPerformed,
		// som flyttar bollen och ritar upp.
	}

	public static void main(String[] args) {
		new Controller();
	}
}