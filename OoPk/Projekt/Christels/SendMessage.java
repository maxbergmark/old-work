import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;



public class SendMessage implements Runnable {
  private Socket client = null;
  private ChatWindow window = null;
  private PrintWriter out = null;
  private String username;
  private String uuid;

  public SendMessage(Socket clientSocket, ChatWindow windowIn, String userIn, String uuidIn) {
    username = userIn;
    client = clientSocket;
    window = windowIn;
    uuid = uuidIn;
    window.setName(username);
  }

  public void setUsername(String user) {
    username = user;
    window.setName(username);
  }

  public void run() {
    try {
      while (true) {
        Thread.sleep(10); //F�r att inte kr�va f�r mycket resurser
        //System.out.println(window.willSend);
        
          
        try{
          Message message = window.getMessageText(uuid); //H�mtar meddelanden fr�n v�rt chattf�nster, throws exception if empty
          OutputStream output = client.getOutputStream();
          out = new PrintWriter(output, true); //Vill anv�nda en PrintWriter f�r att den s�ger hur m�nga bytes vi hanterar osv.
          String xmlMessage;
          if (!message.getIfXML()) {
            if (message.getText().equals("<keyRequest>")) {
              window.startTimer();
            }
            CryptoClass crypt = new CryptoClass(message.getText(), message.getCrypto(), message.getKey(), true); //Klassen m�ste ligga h�r inne i och med att vi skickar in v�rt meddelande
            String cryptMessage = crypt.getMessage(); //Krypterar v�rt meddelande
            XMLParser xml = new XMLParser(message.getUsername(), message.getCrypto(), message.getKey(), message.getColor(), cryptMessage, message.getText());
            xmlMessage = xml.getText(); //Skapar XML
          } else {
            xmlMessage = message.getText();
          }
          //System.out.println(xmlMessage);
          out.println(xmlMessage);
        } catch (Exception err) {}
      
      }
    } catch (Exception e) {
      System.out.println("FATAL ERROR" + e);
    }
  }
}