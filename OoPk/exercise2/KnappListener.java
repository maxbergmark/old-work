import java.awt.event.*;

public class KnappListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		KnappButton b = (KnappButton) e.getSource();
		b.setText("hej");
		System.out.println(e.getSource().toString());
	}
}

