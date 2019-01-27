

import java.io.*;
import java.net.*;

/**
 * Detta program startar en server som lyssnar på port 4444
 * och konverterar alla inkommande meddelanden till versaler
 * innan den skickar tillbaks dem till klienten
 * Programmet bryts om klienten kopplar ifrån.
 */
public class MultiServer {

    // Sockets till uppkopplingen
    private ServerSocket serverSocket;


    public static void main(String[] args){
		MultiServer servDemo = new MultiServer();
    }

    public MultiServer(){

		// Koppla upp serverns socket
		try {
		    serverSocket = new ServerSocket(4444);
		} catch (IOException e) {
		    System.out.println("Could not listen on port: 4444");
		    System.exit(-1);
		}
		
		// Lyssna efter en klient
		while (true) {
			try {
			    Socket s = serverSocket.accept();
			    EchoThread tempThread = new EchoThread(s);
			    tempThread.start();

			} catch (IOException e) {
			    System.out.println("Accept failed: 4444");
			    System.exit(-1);
			}
		}


    }
    
}