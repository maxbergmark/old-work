import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.event.*;
import java.io.*;
import java.awt.GridLayout;
import java.net.*;
import java.util.*;
//import org.w3c.dom.Document;
import javax.swing.text.*;

public class ChatWindow extends JFrame implements ActionListener{ //Meddelanderuta
	private GridBagConstraints c;
	private JTextField adress;
	private JButton gobutton;
	private JButton xbutton;
    private JButton colorbutton;
    private JButton keyRequestButton;
    private JButton sendFileButton;
	private JTextPane pane;
    private JScrollPane scroll;
    private StyledDocument doc;
    private JFrame framen;
    private String color = "#000000";
    private String username;
    private String crypto = "none";
    private String key = "";
    private ChatTimer waitingForKey = new ChatTimer();
    private Queue<String> messages = new LinkedList<String>();
    private HashMap<String, Queue<Message>> messageMap = new HashMap<String, Queue<Message>>();
    private HashMap<String, String> nameMap = new HashMap<String, String>();
    private int recipients = 0;
    private int tempSent = 0;
    private int tempWritten = 0;
    private boolean canSend = false;

	public ChatWindow(String name){
        //server = master;
		framen = new JFrame("MSN Messenger " + name);
        username = name;
        

    	framen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
    	framen.setPreferredSize(new Dimension(300, 300));
    	framen.setLayout(new GridBagLayout());

		c = new GridBagConstraints();
		c.insets = new Insets(2, 2, 2, 2);	
    	c.fill = GridBagConstraints.BOTH;
    	c.gridx = 0;
    	c.gridy = 1;
    	c.weightx = .9;
    	c.weighty = 0;
    	c.anchor = GridBagConstraints.NORTH;
    	adress = new JTextField();
    	framen.add(adress, c);

    	gobutton = new JButton("GO"); //Skicka-knappen
    	gobutton.setMargin(new Insets(0, 0, 0, 0));
    	gobutton.setPreferredSize(new Dimension(40, 40));
    	c.gridx = 1;
    	c.weightx = 0;
    	c.fill = GridBagConstraints.NONE;
    	gobutton.addActionListener(this);
    	framen.add(gobutton, c);

    	xbutton = new JButton("X");
    	xbutton.setMargin(new Insets(0, 0, 0, 0));
    	xbutton.setPreferredSize(new Dimension(30, 40));
    	c.gridx = 5;
    	c.weightx = 0;
    	c.fill = GridBagConstraints.NONE;
    	xbutton.addActionListener(this);
    	framen.add(xbutton, c);

        colorbutton = new JButton("C"); //Färgknapp
        colorbutton.setMargin(new Insets(0, 0, 0, 0));
        colorbutton.setPreferredSize(new Dimension(30, 40));
        c.gridx = 2;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        colorbutton.addActionListener(this);
        framen.add(colorbutton, c);

        keyRequestButton = new JButton("KEY"); //Kryptoknapp
        keyRequestButton.setMargin(new Insets(0, 0, 0, 0));
        keyRequestButton.setPreferredSize(new Dimension(30, 40));
        c.gridx = 3;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        keyRequestButton.addActionListener(this);
        framen.add(keyRequestButton, c);

        sendFileButton = new JButton("F"); //Filknapp
        sendFileButton.setMargin(new Insets(0, 0, 0, 0));
        sendFileButton.setPreferredSize(new Dimension(30, 40));
        c.gridx = 4;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        sendFileButton.addActionListener(this);
        framen.add(sendFileButton, c);

    	pane = new JTextPane();
    	pane.setContentType("text");
    	pane.setEditable(false);
        doc = pane.getStyledDocument();

    	c.gridx = 0;
    	c.gridy = 0;
    	c.gridwidth = 6;
    	c.weightx = 0;
    	c.weighty = 1;
    	c.fill = GridBagConstraints.BOTH;
    	scroll = new JScrollPane(pane);
    	framen.add(scroll, c);

    	
    	framen.pack(); //framen sätts in i fönstret
    	framen.getContentPane().setBackground(new Color(154, 217, 234));
        framen.setVisible(true);
   	}
    public int getUsers() {
        return recipients;
    }

    public HashMap<String,String> getNameMap() { //Använder HashMap för att kunna koppla uuid till meddelandelistan vi vill skicka
        return nameMap;
    }

    public void setName(String uuid, String name) {
        nameMap.put(uuid, name);
    }

    public void addRecipient(String uuid) {
        messageMap.put(uuid, new LinkedList<Message>());
        nameMap.put(uuid, "Generic username");
        recipients++;
    }

    public void sendToSpecific(String uuid, Message messageIn) {
        messageMap.get(uuid).add(messageIn);
    }

    public void sendToOthers(String uuid, String messageIn) {
        //System.out.println("sending to others");
        //System.out.println("own uuid: " + uuid);
        for (String i : messageMap.keySet()) {
            //System.out.println("other uuid: " + i);
                if (!i.equals(uuid)) {
                    //System.out.println("adding now");
                    messageMap.get(i).add(new Message(messageIn));
                }
            }
    }
    
    public void addMessage(String message, String colorIn, String usernameIn) { //Lägger till meddelande där vi vill läsa konversationen. 
        canSend = true;
        SimpleAttributeSet keyWord = new SimpleAttributeSet();
        StyleConstants.setForeground(keyWord, Color.decode(colorIn));
        try {
            if (doc.getLength() != 0) {
                doc.insertString(doc.getLength(), "\n", keyWord);
            }
            doc.insertString(doc.getLength(), usernameIn + ": " + message, keyWord); //Har lagt in ett doc i vår pane redan. Här lägger vi in mer i docet.
        } catch (Exception e) {
            System.out.println(e);
        }
        pane.setCaretPosition(pane.getDocument().getLength()); //Scrollar längst ner

    }

    public String toString() {
        String t = "";
        for (String i : nameMap.keySet()) {
            t += nameMap.get(i) + ", ";
        }
        if (t.length() > 2) {
            return t.substring(0, t.length()-2);
        }
        return t;
    }

    public void errorFunc() {
        JOptionPane.showMessageDialog(framen, "FATAL ERROR");
    }

    public synchronized Message getMessageText(String uuid) throws Exception { //Metod för att yttre program ska kunna hämta det som vi vill skicka
        Message message = messageMap.get(uuid).remove();
        return message;
    }

    public void sendToAll(Message messageIn) {
        for (String i : messageMap.keySet()) {
            messageMap.get(i).add(messageIn);
        }
    }

	public void actionPerformed( ActionEvent e ) {
		String cmd = e.getActionCommand(); //För att kunna identifiera vilken knapp vi tryckt på 
        //System.out.println(cmd);
        if (cmd.equals("GO") && canSend) {
            System.out.println("\nsending message");
            String tempMessage = adress.getText();
            addMessage(tempMessage, color, username);
            sendToAll(new Message(tempMessage, crypto, key, username, color));
            adress.setText("");

        }
        if (cmd.equals("KEY")) {
            String tempMessage = adress.getText();
            if (tempMessage.toLowerCase().equals("request")) {
                addMessage("sending keyRequest", color, username);
                sendToAll(new Message("<keyRequest>", "none", key, username, color));
                adress.setText("");
            } else if (tempMessage.toLowerCase().equals("ja")) {
                addMessage("sending key", color, username);
                sendToAll(new Message("<sendKey>", "none", key, username, color));
                adress.setText("");
            } else if (tempMessage.toLowerCase().startsWith("aes:")) {
                if (tempMessage.length() == 20 && isInteger(tempMessage.substring(4), 16)) {
                    changeKey("AES", tempMessage.substring(4));
                    adress.setText("");
                }
            } else if(tempMessage.toLowerCase().startsWith("caesar:")) {
                if (isInteger(tempMessage.substring(7), 10)) {
                    changeKey("Caesar", tempMessage.substring(7));
                    adress.setText("");
                }
            }
        }
        if (cmd.equals("C")) {
            try{
                Color.decode(adress.getText()); //Vi testar att avkoda det som står i textrutan, om ej hexfärg ges exception
                color = adress.getText(); //Hämtar färgen från där den står i adress-rutan
                adress.setText(""); //Ändrar tillbaka till tom
            } catch (Exception ex) {}
            if (adress.getText().startsWith("name:")) {
                username = adress.getText().substring(5);
                adress.setText("");
            }
        }
        if (cmd.equals("F")) {
            FileProcess test = new FileProcess(this);
        }
        if (cmd.equals("X")) {
            //addMessage("<disconnectUser>", color, username);
            sendToAll(new Message("<disconnectUser>", "none", "", username, color));
            framen.dispose();
        }
	}

    public void changeKey(String cryptoIn, String keyIn) {
        crypto = cryptoIn;
        key = keyIn;
    }

    public String getColor() {
        return color;
    }

    public String getUsername() {
        return username;
    }

    public void startTimer() {
        new Thread(waitingForKey).start();
    }

    public boolean checkTimer() {
        return waitingForKey.checkTimer();
    }

    public void resetTimer()  {
        waitingForKey.resetTimer();
    }

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
}
