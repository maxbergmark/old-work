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

public class ChatServer implements Runnable, ActionListener{

	ServerSocket server;
	int port;
	String name;
	ArrayList<ChatWindow> window = new ArrayList<ChatWindow>();
	boolean cOS;
	String address = "";
	ArrayList<SendMessage> sendMessages = new ArrayList<SendMessage>();
	ArrayList<ReceiveMessage> receiveMessages = new ArrayList<ReceiveMessage>();
	ArrayList<Socket> clients = new ArrayList<Socket>();
	Socket client;
	ChatChooser chatChoose;

	public ChatServer(String nameIn, String portIn, String address, boolean clientOrServer) {
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
			openServerSocket(); //Ny ServerSocket sksapas utifrån port
			while (true) {
				try {
					client = server.accept(); //Ligger och väntar på anslutningen
					//clients.add(client);
				} catch (Exception e) {}
				if (window.size() == 0) {
					addChat(client, window.size());
				} else {
					chatChoose = new ChatChooser(window, this);
				}
			}
		} else {
			try{
				client = new Socket(address, port); //Ny Socket i stället för en ServerSocket som ovan
			} catch (Exception e) {}
			addChat(client, window.size());
		}
	}

	public void addChat(Socket clientIn, int index) {
		if (index == window.size()) { //window är en ArrayList
			window.add(new ChatWindow(name));
		}
		String uuid = UUID.randomUUID().toString();
		SendMessage temp = new SendMessage(clientIn, window.get(index), name, uuid);
		new Thread(temp).start(); //Startar en tråd för att skicka meddelanden
		new Thread(new ReceiveMessage(clientIn, window.get(index), uuid)).start();
		if (cOS) {
			Message tempMessage = new Message("new user joined the chat", "none", "", "System", "#400000");
			window.get(index).sendToAll(tempMessage);
			window.get(index).addMessage(tempMessage.getText(), tempMessage.getColor(), tempMessage.getUsername());
		}
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		System.out.println(cmd);
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

	private void openServerSocket() {
		try {
			server = new ServerSocket(port);
		} catch (Exception e) {}
	}

}