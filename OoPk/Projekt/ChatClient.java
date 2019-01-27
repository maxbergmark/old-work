import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.OutputStream;
import java.io.PrintWriter;


public class ChatClient {

	public ChatClient(String name, String address, String port) {
		System.out.println("chat client created");
		//ChatWindow window = new ChatWindow();
		try {
			Socket s = new Socket("", 9090);
			System.out.println("sending message");
			OutputStream output = s.getOutputStream();
			output.write("hej".getBytes());
			System.out.println("message sent");

			BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String answer = input.readLine();
			System.out.println("Message recieved\n" + answer);
			output.close();
		} catch (Exception e) {
			System.out.println("exception" + e);
		}
	}
}