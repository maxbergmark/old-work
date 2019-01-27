import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.UUID;
import javax.swing.event.*;
import java.awt.event.*;

public class ChatServer implements Runnable, ActionListener{ //Server- eller klient-klass, tr�dad. 

	ServerSocket server;
	int port;
	String name;
	ArrayList<ChatWindow> window = new ArrayList<ChatWindow>(); //Vill h�lla koll p� v�ra ChatWindows, g�rs via en ArrayList. 
	boolean cOS; //Anger om vi vill vara server eller klient.
	String address = "";
	ArrayList<SendMessage> sendMessages = new ArrayList<SendMessage>();
	ArrayList<ReceiveMessage> receiveMessages = new ArrayList<ReceiveMessage>();
	ArrayList<Socket> clients = new ArrayList<Socket>(); //H�ller koll p� klienternas
	Socket client;
	ChatChooser chatChoose;

	public ChatServer(String nameIn, String portIn, String address, boolean clientOrServer) { //Konstruktorn som anropas fr�n MainWindow d�r vi f�tt v�lja hur v�r anv�ndare ska se ut och uppf�ra sig. 
		try {
			port = Integer.parseInt(portIn);
		} catch (Exception e) {
			port = 9090;
		}
		name = nameIn;
		cOS = clientOrServer;
		//window = new ChatWindow();
	}

	public void run() {
		if (cOS) { //True vid server
			openServerSocket(); //Ny ServerSocket skapas utifr�n port
			while (true) {
				try {
					client = server.accept(); //Ligger och v�ntar p� anslutningen
					//clients.add(client);
				} catch (Exception e) {}
				if (window.size() == 0) {
					addChat(client, window.size()); //Om vi inte har n�gra konversationer tidigare �ppnas ett nytt chattf�nster.
				} else {
					chatChoose = new ChatChooser(window, this); //V�lj anv�ndare.
				}
			}
		} else {
			try{
				client = new Socket(address, port); //Ny Socket i st�llet f�r en ServerSocket som ovan.
			} catch (Exception e) {}
			addChat(client, window.size());
		}
	}

	public void addChat(Socket clientIn, int index) { //Metod som skapar nya chattf�nster.
		if (index == window.size()) {
			window.add(new ChatWindow(name)); //L�gger till nya Chattf�nster i ArrayListan window. 
		}
		String uuid = UUID.randomUUID().toString(); //Skapar ett userID. s
		SendMessage temp = new SendMessage(clientIn, window.get(index), name, uuid); //Ny SendMessage skapas. 
		new Thread(temp).start(); //Startar en tr�d f�r att skicka meddelanden.
		new Thread(new ReceiveMessage(clientIn, window.get(index), uuid)).start(); //Skapar tr�d f�r att kunna ta emot meddelanden
		if (cOS) { //Sann n�r vi har en server. 
			Message tempMessage = new Message("new user joined the chat", "none", "", "System", "#400000");
			window.get(index).sendToAll(tempMessage);
			window.get(index).addMessage(tempMessage.getText(), tempMessage.getColor(), tempMessage.getUsername());
		}
	}

	public void actionPerformed(ActionEvent e) { //Kopplad till UserChooser.
		String cmd = e.getActionCommand();
		//System.out.println(cmd);
		if (cmd.equals("New Chat")) {
			addChat(client, window.size());
		}
		for (int i = 0; i < window.size(); i++) {
			if (cmd.equals(window.get(i).toString())) {
				addChat(client, i);
				break;
			}
		}
		chatChoose.terminate();
	}

	private void openServerSocket() { //Metod som �ppnar en ny ServerSocket.
		try {
			server = new ServerSocket(port);
		} catch (Exception e) {}
	}

}