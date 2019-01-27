import javax.swing.*;
import java.awt.*;

public class ScrollPanelDemo {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(400,300));
		JLabel label = new JLabel("<html>stor text<br>på flera rader<br>kan<br>inte<br>få<br>plats</html>");
		label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 100));
		//JScrollPane scrollPane = new JScrollPane(label);
		//frame.add(scrollPane);
		frame.add(label);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}
}