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

public class MainWindow extends JFrame implements ActionListener{ //GUI som kan liknas vid tidigare "kontaklistesidan" på MSN
	private GridBagConstraints c; //Den layout vi använt oss av
	private JTextField nameField;
	private JTextField portField;
	private JTextField addressField;
	private JButton gobutton;
	private JButton xbutton;
	private JButton serverbutton;
    private JFrame framen;
    private ChatServer server;
    private ArrayList<ChatClient> clients;
    private ArrayList<Thread> threadList = new ArrayList<Thread>();

	public MainWindow(){ 
		framen = new JFrame("MSN Messenger");

    	framen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    	framen.setPreferredSize(new Dimension(250, 200));
    	framen.setLayout(new GridBagLayout());
    	clients = new ArrayList<ChatClient>();

		c = new GridBagConstraints();
		c.insets = new Insets(2, 2, 2, 2);	
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.gridx = 0;
    	c.gridy = 0;
    	c.gridwidth = 2;
    	c.weightx = .5;
    	c.weighty = 0;
    	c.anchor = GridBagConstraints.NORTH;
    	nameField = new JTextField(""); //Via detta fönster kan man skriva in det namn man vill använda när man skickar meddelanden
    	nameField.addMouseListener(new MouseAdapter(){ 
            @Override
            public void mouseClicked(MouseEvent e){
                nameField.setText("");
            }
        });
    	framen.add(nameField, c);

    	c = new GridBagConstraints();
		c.insets = new Insets(2, 2, 2, 2);	
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.gridx = 0;
    	c.gridy = 1;
    	c.gridwidth = 2;
    	c.weightx = .9;
    	c.weighty = 0;
    	c.anchor = GridBagConstraints.NORTH;
    	addressField = new JTextField(""); //Man kan skriva in vilken server man vill till 
    	addressField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                addressField.setText("");
            }
        });
    	framen.add(addressField, c);

    	c = new GridBagConstraints();
		c.insets = new Insets(2, 2, 2, 2);	
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.gridx = 0;
    	c.gridy = 2;
    	c.gridwidth = 2;
    	c.weightx = .9;
    	c.weighty = 0;
    	c.anchor = GridBagConstraints.NORTH;
    	portField = new JTextField("9090"); //Vilken port man vill ansluta till
    	portField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                portField.setText("");
            }
        });
    	framen.add(portField, c);

    	gobutton = new JButton("CONNECT"); //Och sedan ansluta till den valda servern
    	gobutton.setMargin(new Insets(0, 0, 0, 0));
    	gobutton.setPreferredSize(new Dimension(40, 40));
    	c.gridx = 0;
    	c.gridy = 3;
    	c.gridwidth = 2;
    	c.weightx = 1;
    	c.anchor = GridBagConstraints.WEST;
    	c.fill = GridBagConstraints.HORIZONTAL;
    	gobutton.addActionListener(this);
    	framen.add(gobutton, c);

    	xbutton = new JButton("CLOSE");
    	xbutton.setMargin(new Insets(0, 0, 0, 0));
    	xbutton.setPreferredSize(new Dimension(30, 40));
    	c.gridx = 1;
    	c.gridy = 4;
    	c.gridwidth = 1;
    	c.weightx = .5;
    	c.anchor = GridBagConstraints.EAST;
    	c.fill = GridBagConstraints.HORIZONTAL;
    	xbutton.addActionListener(this);
    	framen.add(xbutton, c);

    	serverbutton = new JButton("SERVER"); //Eller starta en egen server
    	serverbutton.setMargin(new Insets(0, 0, 0, 0));
    	serverbutton.setPreferredSize(new Dimension(30, 40));
    	c.gridx = 0;
    	c.gridy = 4;
    	c.gridwidth = 1;
    	c.weightx = .5;
    	c.anchor = GridBagConstraints.WEST;
    	c.fill = GridBagConstraints.HORIZONTAL;
    	serverbutton.addActionListener(this);
    	framen.add(serverbutton, c);

    	framen.pack(); //framen sätts in i fönstret
    	framen.getContentPane().setBackground(new Color(154, 217, 234));
        framen.setVisible(true);
        
        //ChatServer test = new ChatServer();
   	}

    public void errorFunc() {
        JOptionPane.showMessageDialog(framen, "FATAL ERROR");
    }

	public void actionPerformed( ActionEvent e ) {
		String cmd = e.getActionCommand();
		if (cmd.equals("CLOSE")) {
			framen.dispose();
		}
		if (cmd.equals("CONNECT")) {
			String name = nameField.getText();
			String address = addressField.getText();
			String port = portField.getText();
			//clients.add(new ChatClient(name, address, port));
			threadList.add(new Thread(new ChatServer(name, port, address, false))); //Ny tråd skapas för Chatservern
			threadList.get(threadList.size()-1).start();
		}
		if (cmd.equals("SERVER")) {
			String name = nameField.getText();
			String port = portField.getText();
			threadList.add(new Thread(new ChatServer(name, port, "", true))); //Ny tråd skapas för Chatservern
			threadList.get(threadList.size()-1).start();
		}
	}

    public static void main(String[] args) {
        MainWindow chat = new MainWindow();

    }
}
