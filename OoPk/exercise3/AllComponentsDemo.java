import javax.swing.*;
import java.awt.*;

public class AllComponentsDemo {


	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(400,300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton button = new JButton("<html><i>JButton</i></html>");
		JLabel label = new JLabel("<html><i>JLabel</i></html>");
		JTextArea textArea = new JTextArea("<html><i>JTextArea</i></html>");
		JTextField textField = new JTextField("<html><i>JTextField</i></html>");
		JPasswordField passwordField = new JPasswordField("<html><i>JPasswordField</i></html>");
		JEditorPane editorPane = new JEditorPane("text/html", "<html><i>JEditorPane</i></html>");

		FlowLayout flow = new FlowLayout();
		frame.setLayout(flow);

		frame.add(button);
		frame.add(label);
		frame.add(textArea);
		frame.add(textField);
		frame.add(passwordField);
		frame.add(editorPane);

		frame.pack();
		frame.setVisible(true);

	}
}