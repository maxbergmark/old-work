import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;        
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import mynode.*;

public class DirLife3 extends JFrame implements ActionListener {
  private Scanner sc;
  private String s2;
  private JCheckBox box;
  private JTree tree;
  private MyNode root;
  private Boolean treerror = false;
  private DefaultTreeModel treeModel;
  private JPanel controls;
  private static String katalog="Liv";
  private static final String closeString = " Close ";
  private static final String showString = " Show Details ";
  
  public DirLife3() {

    try{
      sc = new Scanner(new File("Liv.xml"));
      sc.useDelimiter("(: )|\n");
      sc.nextLine();
      s2 = sc.next();
    }
    catch(Exception e){
      System.out.println("Error: " + e.toString());
      return;
    }

    Container c = getContentPane();
      //*** Build the tree and a mouse listener to handle clicks
    root = readNode();
    treeModel = new DefaultTreeModel( root );
    tree = new JTree( treeModel );
    MouseListener ml = 
    new MouseAdapter() {
      public void mouseClicked( MouseEvent e ) {
        if ( box.isSelected() )
          showDetails( tree.getPathForLocation( e.getX(), 
            e.getY() ) ); //hämtar ett träd för koordinaten vi klickat på
      }
    };
    tree.addMouseListener( ml );
      //*** build the tree by adding the nodes
      //buildTree();
      //*** panel the JFrame to hold controls and the tree
    controls = new JPanel();
    box = new JCheckBox( showString );
      init(); //** set colors, fonts, etc. and add buttons
      c.add( controls, BorderLayout.NORTH );
      c.add( tree, BorderLayout.CENTER );   
      setVisible( true ); //** display the framed window
    } 

    public void actionPerformed( ActionEvent e ) {
      String cmd = e.getActionCommand();
      if ( cmd.equals( closeString ) )
        dispose();
    }

    private void init() {
      tree.setFont( new Font( "Dialog", Font.BOLD, 12 ) );
      controls.add( box );
      addButton( closeString );
      controls.setBackground( Color.lightGray );
      controls.setLayout( new FlowLayout() );    
      setSize( 400, 700 );
    }

    private void addButton( String n ) {
      JButton b = new JButton( n );
      b.setFont( new Font( "Dialog", Font.BOLD, 12 ) );
      b.addActionListener( this );
      controls.add( b );
    }

    private MyNode readNode() {
      if (!treerror){
      MyNode retNode = new MyNode("initNode");
      if (!sc.hasNext()) {
        return retNode; //körs aldrig
      }

      if (s2.contains("namn=")) {
        if (!(s2.contains("<") && s2.contains(">"))) {
          if (!treerror) {
            System.out.println("the worst of all time");
            treerror = true;
          }
          //return new MyNode("bajs");
        }
        if (true) {

        }
        String[] temp1 = s2.replaceAll("<", "").split("> ");

        String[] temparray = temp1[0].split("namn=");
       
        if (!(temparray[1].startsWith("\"") && temparray[1].endsWith("\""))) {
          if (!treerror) { //om treerror är false
            System.out.println("super wrong mister"); //om vi har fel citattecken
            treerror = true;
          }
        }
        retNode = new MyNode(temparray[1].replaceAll("\"", ""));
        retNode.setLevel(temparray[0].trim()); //tar bort mellanslag från ändarna av strängen
       
        retNode.setText(temp1[1].trim());

      }

      while (true) {
        sc.nextLine();
        s2 = sc.next();
        if (!(s2.trim().startsWith("<") && s2.trim().contains(">"))) { //matchande <>
          System.out.println(s2.trim() + s2.startsWith("<") + s2.endsWith(">"));
          if (!treerror) {
            System.out.println("the even worse xml file");
            treerror = true;
          }
        }

        if (!s2.startsWith("</")) {


          retNode.add(readNode());

        }
        else {
          if (!s2.replaceAll("<|>|/", "").trim().equals(retNode.getnodeLevel())) { //start och endtagg matchar inte
            if (!treerror) {
              System.out.println("fasansfullt kass xml");
              treerror = true;
            }

          }
          if (!treerror) {
            return retNode;
          }
          else {
            break;
          }
        }

      }
      

      }
      MyNode errorNode = new MyNode("XML ERROR");
      errorNode.setText("everything is out of control");
      errorNode.setLevel("wrong level");
      return errorNode;
    }

    private int countSnuffs(String text, String s) {
      int count = 0;
      for (int i = 0; i < text.length(); i++) {
        if (text.substring(i, i+1).equals(s)) {
          count++;
        }
      }
      return count;
    }


    private void showDetails( TreePath p ) {
      if ( p == null )
        return;

      MyNode f = (MyNode) p.getLastPathComponent(); //getLastPathComponent går att casta till DefaultMutableTreeNode
        //det går ju att köra getParent() hela vägen upp i trädet, men det här kändes som en smärtfri metod.
      String[] testpath = p.toString().replaceAll("[\\[\\]]", "").split(", "); //Lista av typen ["Liv", "Svampar", "Flugsvampar"]
      String testresult = "men allt som";
      for (int i = 0; i < testpath.length; i++) {
        testresult += " är " + testpath[testpath.length-i-1];

      }
      if (testpath.length == 1) {
        testresult += " är bara " + testpath[0] + " och inget annat";
      }
      System.out.println(testresult);


      //File f = new File( p.getLastPathComponent().toString() );
      JOptionPane.showMessageDialog( this, f + "\n" + 
       getAttributes( f ) + testresult);
    }

    private String getAttributes( MyNode f ) {

      String t = "";
      t += f.getnodeLevel() + "\n";
      t += f.getText() + "\n";
      return t;
    }

    public static void main( String[ ] args ) {


      new DirLife3();
    }

  }