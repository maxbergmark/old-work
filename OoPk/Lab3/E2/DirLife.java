import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;        
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DirLife extends JFrame implements ActionListener {

   public DirLife() {
      Container c = getContentPane();
      //*** Build the tree and a mouse listener to handle clicks
      root = new DefaultMutableTreeNode(katalog);
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
        }
        catch(Exception e){
          System.out.println("Error: " + e.toString());
          return;
        }
        String s2; 

        sc.useDelimiter("(: )|\n|<|>");
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
            System.out.println(temp);
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(temp);
            root.add(child);
            root = child;
            }
            sc.nextLine();
            s2 = sc.next();
          }
          System.out.println("moving up");
          root = (DefaultMutableTreeNode)root.getParent();
        }
   }



   private void showDetails( TreePath p ) {
      if ( p == null )
        return;
      File f = new File( p.getLastPathComponent().toString() );
      JOptionPane.showMessageDialog( this, f.getPath() + 
                                     "\n   " + 
                                     getAttributes( f ) );
   }

   private String getAttributes( File f ) {
      String t = "";
      if ( f.isDirectory() )
        t += "Directory";
      else
        t += "Nondirectory file";
      t += "\n   ";
      if ( !f.canRead() )
        t += "not ";
      t += "Readable\n   ";
      if ( !f.canWrite() )
        t += "not ";
      t += "Writeable\n  ";
      if ( !f.isDirectory() )
        t += "Size in bytes: " + f.length() + "\n   ";
      else {
        t += "Contains files: \n     ";
        String[ ] contents = f.list();
        for ( int i = 0; i < contents.length; i++ )
           t += contents[ i ] + ", ";
        t += "\n";
      } 
      return t;
   }

   public static void main( String[ ] args ) {

        
        new DirLife();
   }
   private Scanner sc;
   private JCheckBox box;
   private JTree tree;
   private DefaultMutableTreeNode root;
   private DefaultTreeModel treeModel;
   private JPanel controls;
   private static String katalog="Biosfär";
   private static final String closeString = " Close ";
   private static final String showString = " Show Details ";
}