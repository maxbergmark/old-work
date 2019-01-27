import java.net.*;
import java.io.*;

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

public class FileReceive implements Runnable { //Fil-mottagarklasss.
	private int port = 15123;
	private int filesize;
	private String name;
	private ChatWindow window;
	public ServerSocket server;

	public FileReceive(ChatWindow windowIn, int size, String name) {
		filesize = size+1; //+1 för att inte få några problem
		this.name = name;
		window = windowIn;
	}

	public void run() { //Trådanpassad
		//System.out.println("waiting for file connection");
		try {
			server = new ServerSocket(port); //Skapar här en Socket som ligger och väntar på att den skickande användaren ska ansluta och börja skicka filen.
		} catch (Exception e) {}
		try {
		//System.out.println("waiting for file socket");
		Socket socket = server.accept();
		//System.out.println("found");
		int bytesRead; 
		int tot = 0; 

		byte [] bytearray = new byte [filesize]; 
		InputStream is = socket.getInputStream(); 
		FileOutputStream fos = new FileOutputStream("A" + window.getUsername() + name); 
		BufferedOutputStream bos = new BufferedOutputStream(fos); 
		bytesRead = is.read(bytearray,0,bytearray.length); 
		tot = bytesRead; 
		int count = 0;
		double p = .01;
		do { 
			double progress = (double) (tot) / (double) (filesize); //progres går från 0 till 1 
			if (progress > p) {
				window.drawProgress(progress);
				window.sendToAll(new Message("<fileProgress> " + progress, "none", "", "#000000", "System"));
				p += .01;
			}
			//System.out.println((double) (tot) / (double) (filesize));
			if (progress>0.25 && count ==0 ) {
				count++;
				window.addMessage("       [|   ]",window.getColor(),window.getUsername());
				window.sendToAll(new Message("       [|   ]", "none", "", window.getUsername(), window.getColor()));
			} else if (progress>0.5 && count == 1) {
				count++;
				window.addMessage("       [||  ]",window.getColor(),window.getUsername());
				window.sendToAll(new Message("       [||  ]", "none", "", window.getUsername(), window.getColor()));
			}else if (progress>0.75 && count == 2) {
				count++;
				window.addMessage("       [||| ]",window.getColor(),window.getUsername());
				window.sendToAll(new Message("       [||| ]", "none", "", window.getUsername(), window.getColor()));
			}
			bytesRead = is.read(bytearray, tot, (bytearray.length-tot)); 
			if(bytesRead >= 0) tot += bytesRead;
		}
		/*
		do { 
			System.out.println((double) (tot) / (double) (filesize));
			bytesRead = is.read(bytearray, tot, (bytearray.length-tot)); 
			if(bytesRead >= 0) tot += bytesRead;
		}*/
		while(bytesRead > -1);
			window.drawProgress(1);
			window.sendToAll(new Message("<fileProgress> " + 1, "none", "", "#000000", "System"));
			bos.write(bytearray, 0 , tot); 
			bos.flush(); 
			bos.close(); 
			socket.close();
			server.close();
			//System.out.println("file received");
			window.drawProgress(0);
			window.sendToAll(new Message("<fileProgress> " + 0, "none", "", "#000000", "System"));
			window.addMessage("       [||||]",window.getColor(),window.getUsername());
			window.sendToAll(new Message("       [||||]", "none", "", window.getUsername(), window.getColor()));
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		//FileReceive receiver = new FileReceive();
	}
}