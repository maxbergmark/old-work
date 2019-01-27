import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditorPaneDemo extends JFrame implements KeyListener {
	
	JTextArea textArea;
	JLabel label;

	public EditorPaneDemo() {
		setPreferredSize(new Dimension(400,300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		label = new JLabel();
		textArea = new JTextArea();
		label.setBackground(Color.red);
		textArea.setBackground(new Color(200,200,255));

		GridLayout grid = new GridLayout(2,1);
		setLayout(grid);

		textArea.addKeyListener(this);


		add(label);
		add(textArea);

		pack();
		setVisible(true);


	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			//e.consume();
			System.out.println("Enter pressed");
			String text = textArea.getText();
			label.setText(text);	
		}

	}

	public void keyTyped(KeyEvent e) {

	}
	public void keyReleased(KeyEvent e) {
		//String text = textArea.getText();
		//label.setText(text);
	}

	public static void main(String[] args) {
		new EditorPaneDemo();
	}
}