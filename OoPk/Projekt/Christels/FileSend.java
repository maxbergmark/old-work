import java.net.*;
import java.io.*;

public class FileSend implements Runnable {
	public Socket socket;
	
	private File sendFile;

	//public FileSend(File f, ChatWindow window, int port) {
	public FileSend(File f) {
		sendFile = f;
		try {
		socket = new Socket("",15123);
		
		byte [] byteArray  = new byte [(int)sendFile.length()];
		FileInputStream fin = new FileInputStream(sendFile); 
		BufferedInputStream bin = new BufferedInputStream(fin); 
		bin.read(byteArray,0,byteArray.length); 
		OutputStream os = socket.getOutputStream(); 
		System.out.println("Sending Files..."); 
		os.write(byteArray,0,byteArray.length); 
		os.flush(); 
		socket.close();	
		}
		catch (Exception e) {}		

		
	}

	public void run() {}
	
	public static void main(String[] args) {
		File fil = new File("Christmas.jpg");
		//FileSend test = new FileSend(fil);
	
	
	}
}