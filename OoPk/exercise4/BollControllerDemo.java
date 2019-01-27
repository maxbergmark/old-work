import java.awt.event.*;
import javax.swing.*;

public class BollControllerDemo implements ActionListener {

	BollModelDemo m;

	public BollControllerDemo() {

		double dt = .02;
		m = new BollModelDemo(dt);

		Timer t = new Timer((int)(dt*1000), this);
		t.start();

	}

	public void actionPerformed(ActionEvent e) {
		m.move();
	}

	public static void main(String[] args) {
		new BollControllerDemo();
	}
}