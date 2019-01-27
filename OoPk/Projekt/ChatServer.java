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

public class ChatServer implements Runnable, ActionListener{ //Server- eller klient-klass, trådad. 

	ServerSocket server;
	int port;
	String name;
	ArrayList<ChatWindow> window = new ArrayList<ChatWindow>(); //Vill hålla koll på våra ChatWindows, görs via en ArrayList. 
	boolean cOS; //Anger om vi vill vara server eller klient.
	String address = "";
	ArrayList<SendMessage> sendMessages = new ArrayList<SendMessage>();
	ArrayList<ReceiveMessage> receiveMessages = new ArrayList<ReceiveMessage>();
	ArrayList<Socket> clients = new ArrayList<Socket>(); //Håller koll på klienternas
	Socket client;
	ChatChooser chatChoose;

	public ChatServer(String nameIn, String portIn, String address, boolean clientOrServer) { //Konstruktorn som anropas från MainWindow där vi fått välja hur vår användare ska se ut och uppföra sig. 
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
			openServerSocket(); //Ny ServerSocket skapas utifrån port
			while (true) {
				try {
					client = server.accept(); //Ligger och väntar på anslutningen
					//clients.add(client);
				} catch (Exception e) {}
				if (window.size() == 0) {
					addChat(client, window.size()); //Om vi inte har några konversationer tidigare öppnas ett nytt chattfönster.
				} else {
					chatChoose = new ChatChooser(window, this); //Välj användare.
				}
			}
		} else {
			try{
				client = new Socket(address, port); //Ny Socket i stället för en ServerSocket som ovan.
			} catch (Exception e) {}
			addChat(client, window.size());
		}
	}

	public void addChat(Socket clientIn, int index) { //Metod som skapar nya chattfönster.
		if (index == window.size()) {
			window.add(new ChatWindow(name)); //Lägger till nya Chattfönster i ArrayListan window. 
		}
		String uuid = UUID.randomUUID().toString(); //Skapar ett userID. s
		SendMessage temp = new SendMessage(clientIn, window.get(index), name, uuid); //Ny SendMessage skapas. 
		new Thread(temp).start(); //Startar en tråd för att skicka meddelanden.
		new Thread(new ReceiveMessage(clientIn, window.get(index), uuid)).start(); //Skapar tråd för att kunna ta emot meddelanden
		if (cOS) { //Sann när vi har en server. 
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

	private void openServerSocket() { //Metod som öppnar en ny ServerSocket.
		try {
			server = new ServerSocket(port);
		} catch (Exception e) {}
	}

}