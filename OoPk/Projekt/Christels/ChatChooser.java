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

public class ChatChooser extends JFrame implements ActionListener {

	private GridBagConstraints c = new GridBagConstraints();; //Den layout vi använt oss av
	private JButton[] buttons;
	private JFrame framen;



	public ChatChooser(ArrayList<ChatWindow> windows, ChatServer server) {

		buttons = new JButton[windows.size()+1];
		framen = new JFrame("Choose chat");
		framen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
   		framen.setPreferredSize(new Dimension(200, 300));
    	framen.setLayout(new GridBagLayout());

		c.insets = new Insets(2, 2, 2, 2);	
    	c.fill = GridBagConstraints.BOTH;
    	c.gridx = 0;
    	c.weightx = 1;
    	c.weighty = 1;

    	for (int i = 0; i < windows.size(); i++) {
    		buttons[i] = new JButton(windows.get(i).toString());
    		buttons[i].addActionListener(server);
    		c.gridy = i;
    		framen.add(buttons[i], c);
    	}

		buttons[windows.size()] = new JButton("New Chat");
		buttons[windows.size()].addActionListener(server);
		c.gridy = windows.size();
		framen.add(buttons[windows.size()], c);

    	framen.pack();
    	framen.setVisible(true);

	}

	public void terminate() {
		framen.dispose();
	}

	public void actionPerformed(ActionEvent e) {

	}

}