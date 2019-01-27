import java.io.*;
import java.net.*;

/**
 * Detta program startar en server som lyssnar på port 4444
 * och konverterar alla inkommande meddelanden till versaler
 * innan den skickar tillbaks dem till klienten
 * Programmet bryts om klienten kopplar ifrån.
 */
public class MultiServer2 {



    // Sockets till uppkopplingen
    private ServerSocket serverSocket;



    public static void main(String[] args){
		MultiServer2 servDemo = new MultiServer2();
    }

    public MultiServer2(){

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
			    Socket clientSocket = serverSocket.accept();
			    EchoThread2 temp = new EchoThread2(clientSocket);
			    temp.start();
			} catch (IOException e) {
			    System.out.println("Accept failed: 4444");
			    System.exit(-1);
			}
		}


    }
    
}