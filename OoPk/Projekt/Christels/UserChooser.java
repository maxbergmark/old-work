import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.event.*;
import java.io.*;
import java.awt.GridLayout;
import java.net.*;
import java.util.*;
//import org.w3c.dom.Document;
import javax.swing.text.*;

public class UserChooser extends JFrame implements ActionListener {

	private GridBagConstraints c = new GridBagConstraints();; //Den layout vi använt oss av
	private JButton[] buttons;
	private JFrame framen;
	private FileProcess process;
	private ChatWindow window;



	public UserChooser(ChatWindow windowIn, FileProcess processIn) {
		window = windowIn;
		process = processIn;
		buttons = new JButton[window.getUsers()];
		framen = new JFrame("Choose user");
		framen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
   		framen.setPreferredSize(new Dimension(200, 300));
    	framen.setLayout(new GridBagLayout());

		c.insets = new Insets(2, 2, 2, 2);
    	c.fill = GridBagConstraints.BOTH;
    	c.gridx = 0;
    	c.weightx = 1;
    	c.weighty = 1;

    	int i = 0;

    	for (String s : window.getNameMap().keySet()) {
    		System.out.println(s);
    		buttons[i] = new JButton(window.getNameMap().get(s));
    		buttons[i].addActionListener(this);
    		c.gridy = i;
    		framen.add(buttons[i++], c);
    	}
    	System.out.println("length: " + i);


    	framen.pack();
    	framen.setVisible(true);

	}

	public void terminate() {
		framen.dispose();
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		for (String s : window.getNameMap().keySet()) {
			if (window.getNameMap().get(s).equals(cmd)) {
				process.sendFileToUser(s);
			}
		}
	}

}