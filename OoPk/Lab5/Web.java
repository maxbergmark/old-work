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

/**
 * A web browser.
 * @author Max Bergmark
 * @author Christel Sundberg
 * 
 */

public class Web extends JFrame implements ActionListener{
	private GridBagConstraints c;
	private JTextField adress;
	private JButton xbutton;
	private JButton fbutton;
	private JButton pbutton;
	private JButton gobutton;
    private JButton historybutton;
	private JEditorPane pane;
	private ArrayList<String> navigation;
	private ArrayList<String> history;
    private JFrame framen;
    private int navNumber;
    /**
     * Creates the interface and adds the ActionListeners
     */
	public Web(){
		framen = new JFrame("Webb 2.0");
        navigation = new ArrayList<String>();
        history = new ArrayList<String>();
        

    	framen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    	framen.setPreferredSize(new Dimension(300, 300));
    	framen.setLayout(new GridBagLayout());

		c = new GridBagConstraints();
		c.insets = new Insets(2, 2, 2, 2);	
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.gridx = 3;
    	c.gridy = 0;
    	c.weightx = .9;
    	c.weighty = 0;
    	c.anchor = GridBagConstraints.NORTH;
    	adress = new JTextField();
    	framen.add(adress, c);

    	
    	xbutton = new JButton("x");
    	xbutton.setMargin(new Insets(0, 0, 0, 0));
    	xbutton.setPreferredSize(new Dimension(40, 20));
    	c.gridx = 5;
    	c.weightx = 0;
    	c.fill = GridBagConstraints.NONE;
    	xbutton.addActionListener(this);
    	framen.add(xbutton, c);

    	gobutton = new JButton("GO");
    	gobutton.setMargin(new Insets(0, 0, 0, 0));
    	gobutton.setPreferredSize(new Dimension(20, 20));
    	c.gridx = 4;
    	c.weightx = 0;
    	c.fill = GridBagConstraints.NONE;
    	gobutton.addActionListener(this);
    	framen.add(gobutton, c);

    	fbutton = new JButton(">");
    	fbutton.setMargin(new Insets(0, 0, 0, 0));
    	fbutton.setPreferredSize(new Dimension(20, 20));
    	c.gridx = 1;
    	c.weightx = 0;
    	c.fill = GridBagConstraints.NONE;
    	fbutton.addActionListener(this);
    	framen.add(fbutton, c);

    	pbutton = new JButton("<");
    	pbutton.setMargin(new Insets(0, 0, 0, 0));
    	pbutton.setPreferredSize(new Dimension(20, 20));
    	c.gridx = 0;
    	c.weightx = 0;
    	c.fill = GridBagConstraints.NONE;
    	pbutton.addActionListener(this);
    	framen.add(pbutton, c);

        historybutton = new JButton("History");
        historybutton.setMargin(new Insets(0, 0, 0, 0));
        historybutton.setPreferredSize(new Dimension(20, 20));
        c.gridx = 2;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        historybutton.addActionListener(this);
        framen.add(historybutton, c);

    	pane = new JEditorPane();
    	pane.setContentType("text/html");
    	pane.setEditable(false);
    	pane.addHyperlinkListener(new HyperlinkListener() {
    		public void hyperlinkUpdate(HyperlinkEvent e) {
    			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
		            if (Desktop.isDesktopSupported()) {
		                try {
		                    pane.setPage(e.getURL());
                            addHistory(e.getURL().toString());
                            adress.setText(e.getURL().toString());
                            navigate(0);
		                }
		                catch (IOException e1) {
		                    System.out.println("hyperlink error");
		                    errorFunc();
		                }

		            }
		        }
    		}
    	});
    	c.gridx = 0;
    	c.gridy = 1;
    	c.gridwidth = 6;
    	c.weightx = 0;
    	c.weighty = 1;
    	c.fill = GridBagConstraints.BOTH;
    	JScrollPane scroll = new JScrollPane(pane);
    	framen.add(scroll, c);
    	try {
	    	pane.setPage("http://www.google.com/");
            addHistory("http://www.google.com/");
            adress.setText("http://www.google.com/");
            navNumber = 0;
            navigate(0);

    	}
    	catch (Exception e) {
    		System.out.println("allt gick snett");
            errorFunc();
    	}	
    	
    	framen.pack(); //framen sätts in i fönstret
    	framen.getContentPane().setBackground(new Color(255, 215, 0));
        framen.setVisible(true);
   	}
    /**
     * Displays the error message.
     */
    public void errorFunc() {
        JOptionPane.showMessageDialog(framen, "FATAL ERROR");
    }
    /**
     * Navigates the temporary history tree.
     * @param i The number (-1, 0, or 1) of pages to be navigated. Used to determine the direction of navigation. 
     */
    public void navigate(int i) {
        //System.out.println(navigation + " " + navNumber + " " + navigation.size());
        navNumber += i;
        pbutton.setBackground(Color.WHITE);
        fbutton.setBackground(Color.WHITE);
        if (navNumber == 0) {
            pbutton.setBackground(Color.RED);
        }
        if (navNumber == navigation.size()-1) {
            fbutton.setBackground(Color.RED);
        }
        if (i != 0) {
            try {
                
                System.out.println(navigation.get(navNumber));
                pane.setPage(navigation.get(navNumber));
                adress.setText(navigation.get(navNumber));
            }
            catch (Exception e) {
                System.out.println("navigation error");
                errorFunc();
            }
        }
    }

    /**
     * Runs when a button is clicked. Updates the page and exits the window.
     * @param e The ActionEvent that triggered the function call
     */
	public void actionPerformed( ActionEvent e ) {
		String cmd = e.getActionCommand();
		//System.out.println("." + cmd + ".");
		if ( cmd.equals("x") ) {
			framen.dispose();
		}
		if (cmd.equals("GO")) {
			String text = adress.getText();
			try {
	    		pane.setPage(text);
                addHistory(text);
    		}
    		catch (Exception exep) {
    			System.out.println("allt gick snett här");
                errorFunc();
    		}	
		}
        if ( cmd.equals("<") ) {
            if (navNumber > 0) {
                navigate(-1);
            }
            
        }
        if ( cmd.equals(">") ) {
            if (navNumber < navigation.size()-1) {
                navigate(1);
            }
        }
        if ( cmd.equals("History") ) {
            openHistory();
        }
	}
    /**
     * Opens the browser history for the session.
     */
    private void openHistory() {
        File f = new File("Web.html");
        try {
            pane.setPage(f.toURI().toURL());
        }
        catch (Exception e) {
            System.out.println("history error");
            errorFunc();
        }
    }
    /**
     * Generates the HTML code for the history.
     * @return returns the string.
     */
    private String generateHTML() {
        String test = "<html>\n<body>";
        for (String s : history) { //loopar över history, plockar ut s varje iteration
            test += "\n" + "<a href = \"" + s + "\">" + s + "</a><br>";
        }
        test += "\n</body>\n</html>";
        return test;

    }
    /**
     * Adds the new website to the history.
     * @param sida The URL String
     */
    private void addHistory(String sida){
        File f = new File("history.html");
        history.add(sida);
        String historyhtml = generateHTML();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))) {
            writer.write(historyhtml);
        }
        catch (Exception error) {
            return;
        }
        //if (sida.equals(navigation.get(navigation.size()-1))) {
        if (navNumber < navigation.size()-1) {
            System.out.println("deleting");
            for (int i = navNumber+1; i < navigation.size(); i++) {
                //System.out.println(i + " " + (navNumber+1));
                navigation.remove(navNumber+1);
            }
        }

        navNumber++;
        navigation.add(sida);
        navigate(0);
        //}
        //System.out.println(history);
    }
    /**
     * Runs the program
     * @param args Command-line arguments not used
     */
    public static void main(String[] args) {
        Web nywebb = new Web();

    }
}
