import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


public class ReceiveMessage implements Runnable {
  private Socket client = null;
  private ChatWindow window = null;
  private String uuid;
  private String cryptoType = "none";
  private String cryptoKey = "";
  private HashMap<String, String> cryptoMap = new HashMap<String, String>();

  public ReceiveMessage(Socket clientSocket, ChatWindow windowIn, String uuidIn) {
    client = clientSocket;
    window = windowIn;
    uuid = uuidIn;
    window.addRecipient(uuid);
  }

  public void run() {
    try {
      while(true) {
        Thread.sleep(1);
        //System.out.println("reading messages");
        BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));

        String answer = "";
        String temp = null;
        while (true) {
          if(input.ready()) {
            temp = input.readLine();
            answer += temp;
          } else {
            break;
          }
          answer += "\n";
        }
        //System.out.println("message read");
        if (!answer.equals("")) {
          //System.out.println(answer);
          XMLParser tempXML = new XMLParser(answer); //Vill parsea
          //System.out.println(tempXML.getRequest());
          if (tempXML.getRequest().equals("fileRequest")) {
            String[] tempArray = tempXML.getText().split(" ");
            window.nextFileSize = Integer.valueOf(tempArray[tempArray.length-2]);
            window.nextFileName = tempArray[1];
          }

          if (tempXML.getRequest().equals("keyAnswer")) {
            if (window.checkTimer()) {
              //cryptoKey = tempXML.getText();
              cryptoMap.put(tempXML.getUsername(), tempXML.getText());
              //System.out.println(cryptoKey);
              window.addMessage("Key received", tempXML.getColor(), tempXML.getUsername());
              window.resetTimer();
            }
          } else if (tempXML.getRequest().equals("fileAccept")) {
            if (window.checkFileTimer()) {
              //window.getFileProc().setFileAnswer(true);
              window.getFileProc().terminate();
              window.resetFileTimer();
            }

          } else if (tempXML.getRequest().equals("fileProgress")) {
            double val = Double.valueOf(tempXML.getText());
            window.drawProgress(val);
          } else {
            String tempName = "";
            if (cryptoMap.containsKey(tempXML.getUsername())) {
              tempName = cryptoMap.get(tempXML.getUsername());
            }


            CryptoClass tempCrypto = new CryptoClass(tempXML.getText(), tempXML.getCrypto(), tempName, false); //Avkrypterar
          

            window.addMessage(tempCrypto.getMessage(), tempXML.getColor(), tempXML.getUsername()); //Lägger till vårt meddelande i chattrutan
          }
          window.setName(uuid, tempXML.getUsername());
          window.sendToOthers(uuid, answer);
        }
      }

    } catch (Exception e) {
      System.out.println(client);
      System.out.println("FATAL ERROR IN ReceiveMessage: " + e);
      System.out.println(e.getStackTrace().toString());
    }
  }
}