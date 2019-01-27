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

public class FileReceive {
	private int port = 15123;
	public ServerSocket server;

	//public FileReceive(ChatWindow window) {
	public FileReceive() {
		try {
			server = new ServerSocket(port);
		} catch (Exception e) {}
		try {
		Socket socket = server.accept();
		
		//int filesize=1022386; 
		int filesize = 1500000;
		int bytesRead; 
		int tot = 0; 

		byte [] bytearray = new byte [filesize]; 
		InputStream is = socket.getInputStream(); 
		FileOutputStream fos = new FileOutputStream("christmas4.jpg"); 
		BufferedOutputStream bos = new BufferedOutputStream(fos); 
		bytesRead = is.read(bytearray,0,bytearray.length); 
		tot = bytesRead; 
		do { 
			bytesRead = is.read(bytearray, tot, (bytearray.length-tot)); 
			if(bytesRead >= 0) tot += bytesRead;
		}
		while(bytesRead > -1);
			bos.write(bytearray, 0 , tot); 
			bos.flush(); 
			bos.close(); 
			socket.close();
		
		}
		catch (Exception e) {}
	}
	
	

	
	
	
	public static void main(String[] args) {
		FileReceive receiver = new FileReceive();
	
	
	}


}