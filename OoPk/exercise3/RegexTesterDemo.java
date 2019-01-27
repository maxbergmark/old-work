import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.util.regex.*;

public class RegexTesterDemo extends JFrame implements KeyListener {
	
	JTextArea textArea;
	JLabel label;
	JTextField textField;

	public RegexTesterDemo() {
		
		setPreferredSize(new Dimension(400,300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		label = new JLabel("The output will be displayed here.");
		
		textField = new JTextField("This is where the regular expression goes.");
		textField.setBackground(new Color(255,150,150));
		
		textArea = new JTextArea("This is where you input the string to be checked.");
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBackground(new Color(200,200,255));
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		textArea.addKeyListener(this);
		textField.addKeyListener(this);

		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		add(label, c);
		
		c.gridy = 1;
		c.weighty = 0;
		add(textField, c);
		
		c.gridy = 2;
		c.weighty = 1;
		add(textArea, c);

		pack();
		setVisible(true);


	}

	public void keyPressed(KeyEvent e) {}

	public void keyTyped(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {
		String text = textArea.getText();
		String reg = textField.getText();
		try {
			Matcher m = Pattern.compile(reg).matcher(text);
			String s = "<html>";
			String[] cols = {"#ff0000","#00ff00"};
			int count = 0;
			int last = 0;
			while (m.find()) {
				s += text.substring(last, m.start());
				String c = m.group(0);
				s += "<span style=\"background-color: " + cols[count++%2] + "\">" + c + "</span>";
				last = m.end();
			}
			s += text.substring(last);
			s += "</html>";

			label.setText(s);
		} catch (Exception ex) {}
	}

	public static void main(String[] args) {
		new RegexTesterDemo();
	}
}