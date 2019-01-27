import java.net.*;
import java.io.*;

public class FileSend implements Runnable {
	public Socket socket;
	
	private File sendFile;
	private ChatWindow window;

	public FileSend(File f, ChatWindow windowIn) { //Klass för att skicka fil
		sendFile = f;
		window = windowIn;
	}

	public void run() {
		try {
			socket = new Socket("",15123);
			
			byte [] byteArray  = new byte [(int)sendFile.length()];
			FileInputStream fin = new FileInputStream(sendFile); 
			BufferedInputStream bin = new BufferedInputStream(fin); 
			bin.read(byteArray,0,byteArray.length); 
			OutputStream os = socket.getOutputStream(); 
			//System.out.println("Sending Files..."); 
			os.write(byteArray,0,byteArray.length); 
			os.flush(); 
			socket.close();	
		}
		catch (Exception e) {}	
	}
	
}