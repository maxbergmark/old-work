import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextFieldDemo {

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setPreferredSize(new Dimension(400,300));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTextField t = new JTextField(10);
		t.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(e);
			}
		});
		f.add(t);
		f.pack();
		f.setVisible(true);
	}
}