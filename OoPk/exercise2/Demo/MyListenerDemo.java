import java.awt.event.*;

public class MyListenerDemo implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getSource().toString());
	}
}