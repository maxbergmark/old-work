import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;        
import java.awt.*;
import java.awt.event.*;

public class DirTree3 extends JFrame implements ActionListener {

   public DirTree3() {
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
      //DefaultMutableTreeNode f=new DefaultMutableTreeNode(katalog, true);
      String[] list = {"Svampar", "Växter", "Djur"};

      for (int i=0; i<list.length; i++ ) {
         DefaultMutableTreeNode temp = new DefaultMutableTreeNode(list[i], true);
         root.add(temp); 
         buildTree(temp, 0, temp.toString());
       }
   }

   private void buildTree(DefaultMutableTreeNode parent, int level, String rike) {
      //System.out.println(parent + " " + level);
      String[][] test = {{"Familjer"}, {"Släkten"}, {"Arter", "Fina %s"}};
          if (level == 3) {
              if (rike == "Djur") {

                if (parent.toString().equals("Arter")) {
              parent.add(new DefaultMutableTreeNode("Hund"));
              parent.add(new DefaultMutableTreeNode("Varg"));
              parent.add(new DefaultMutableTreeNode("Kissekatt"));
            }
            else {
              parent.add(new DefaultMutableTreeNode("Asta"));
            }
            }
            if (rike == "Svampar") {
              if (parent.toString().equals("Arter")) {
              parent.add(new DefaultMutableTreeNode("Trattkantarell.jpeg"));
            }
            else {
              parent.add(new DefaultMutableTreeNode("Hallonskivling.xml"));
            }
            }
            if (rike == "Växter") {
              parent.add(new DefaultMutableTreeNode("Blomma"));

            }
          }

          
          if (level < test.length) {

            for (int j = 0; j < test[level].length; j++) {
              DefaultMutableTreeNode child = new DefaultMutableTreeNode(String.format(test[level][j], rike));
              parent.add(child);
              buildTree(child, level+1, rike);
          }
          
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
       if(args.length>0) katalog=args[0];
       new DirTree3();
   }

   private JCheckBox box;
   private JTree tree;
   private DefaultMutableTreeNode root;
   private DefaultTreeModel treeModel;
   private JPanel controls;
   private static String katalog="Liv";
   private static final String closeString = " Close ";
   private static final String showString = " Show Details ";
}