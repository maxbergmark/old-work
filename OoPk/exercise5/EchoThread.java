import java.io.*;
import java.net.*;


public class EchoThread extends Thread {

    // Strömmar för att läsa/skriva
    private PrintWriter out;
    private BufferedReader in;

    // texten som läses in/skickas tillbaka
    private String echo;

    private Socket clientSocket = null;

    public EchoThread(Socket socket) {
    	this.clientSocket = socket;
    }

    public void run() {

    	// Anslut till klienten
		try{
		    out = new PrintWriter(
					  clientSocket.getOutputStream(), true);
		}catch(IOException e){
		    System.out.println("getOutputStream failed: " + e);
		    System.exit(1);
		}

		try{
		    in = new BufferedReader(new InputStreamReader(
		            clientSocket.getInputStream()));
		}catch(IOException e){
		    System.out.println("getInputStream failed: " + e);
		    System.exit(1);
		}

		// Kommer vi hit har det gått bra
		// Vi skriver ut IP-adressen till klienten
		System.out.println("Connection Established: " 
				   + clientSocket.getInetAddress());

		// Läs från klienten och skicka tillbaka 
		// medelandet i versaler tills klienten
		// kopplar ner
		while(true){
		    try{
				echo = in.readLine();
				if(echo==null){
				    System.out.println("Client disconnect!");
				    System.exit(1);
				}
				System.out.println("Recieved: " + echo);
				out.println(echo.toUpperCase());
		    } catch(IOException e){
				System.out.println("readLine failed: " + e);
				System.exit(1);
		    }
		}
    }

}