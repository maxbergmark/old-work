import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;        
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import mynode.*;

public class DirLife2 extends JFrame implements ActionListener {

   public DirLife2() {
      Container c = getContentPane();
      //*** Build the tree and a mouse listener to handle clicks
      root = new MyNode(katalog);
      treeModel = new DefaultTreeModel( root );
      tree = new JTree( treeModel );
      MouseListener ml = 
        new MouseAdapter() {
          public void mouseClicked( MouseEvent e ) {
            if ( box.isSelected() )
              showDetails( tree.getPathForLocation( e.getX(), 
                                                    e.getY() ) );
          }
        };
      tree.addMouseListener( ml );
      //*** build the tree by adding the nodes
      buildTree();
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
      setSize( 400, 400 );
   }

   private void addButton( String n ) {
      JButton b = new JButton( n );
      b.setFont( new Font( "Dialog", Font.BOLD, 12 ) );
      b.addActionListener( this );
      controls.add( b );
   }

   private void buildTree() {
        try{
            sc = new Scanner(new File("Liv.xml"));
            sc.useDelimiter("(: )|\n|<|>");
        }
        catch(Exception e){
          System.out.println("Error: " + e.toString());
          return;
        }
        String s2; 
        sc.nextLine(); //skippar xml-inforaden
        s2 = sc.next();
        String[] tempstrings = s2.split("namn=");  //["Biosfär", ""Liv""]
        root.setLevel(tempstrings[0]);
        s2 = sc.next();
        root.setText(s2);

        
        while (sc.hasNext()){
          sc.nextLine();
          try {
          s2 = sc.next();
          }
          catch (Exception e) {
            return;
          }
          while (!s2.substring(0,1).equals("/")){  //while strängen inte börjar med ett "/""
            //System.out.println(s2);
            if (s2.contains("namn=")) {
            String[] temparray = s2.split("namn=");
            String temp = temparray[1].replaceAll("\"", "");
            //System.out.println(temp);
            MyNode child = new MyNode(temp);
            String infotext = sc.next();
            //System.out.println(" " + temparray[0]);
            child.setText(infotext);
            child.setLevel(temparray[0]);
            root.add(child);
            root = child;
            }
            sc.nextLine();
            s2 = sc.next();
          }
          //System.out.println("moving up");
          root = (MyNode)root.getParent();
        }
   }



   private void showDetails( TreePath p ) {
      if ( p == null )
        return;
      MyNode f = (MyNode) p.getLastPathComponent(); //getLastPathComponent går att casta till DefaultMutableTreeNode
     
      //File f = new File( p.getLastPathComponent().toString() );
      JOptionPane.showMessageDialog( this, f + 
                                     "\n   " + 
                                     getAttributes( f ) );
   }

   private String getAttributes( MyNode f ) {

      String t = "";
      t += f.getnodeLevel() + "\n  ";
      t += f.getText() + "\n  ";
      return t;
   }

   public static void main( String[ ] args ) {

        
        new DirLife2();
   }
   private Scanner sc;
   private JCheckBox box;
   private JTree tree;
   private MyNode root;
   private DefaultTreeModel treeModel;
   private JPanel controls;
   private static String katalog="Liv";
   private static final String closeString = " Close ";
   private static final String showString = " Show Details ";
}