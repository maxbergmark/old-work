import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;        
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;


public class FileProcess extends JFrame implements ActionListener {
    
   private JCheckBox box;
   private JTree tree;
   private DefaultMutableTreeNode root;
   private DefaultTreeModel treeModel;
   private JPanel controls;
   private static String katalog="..";
   private static final String closeString = "Close";
   private static final String showString = "Skicka denna fil";
   private String location; 
   private long fileSize;
   private ChatWindow window;
   private File f;
   private volatile boolean fileAnswer = false;

   public FileProcess(ChatWindow windowIn) {
      window = windowIn;
      Container c = getContentPane();
      //*** Build the tree and a mouse listener to handle clicks
      root = new DefaultMutableTreeNode(katalog);
      treeModel = new DefaultTreeModel( root );
      tree = new JTree( treeModel );
      MouseListener ml = new MouseAdapter() {
      public void mouseClicked( MouseEvent e ) {
            if ( box.isSelected() )
                showDetails( tree.getPathForLocation( e.getX(), e.getY() ) );
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
      File f=new File(katalog);
      String[] list = f.list(); 
      for (int i=0; i<list.length; i++ )
         buildTree(new File(f,list[ i ]), root); 
   }

   private void buildTree( File f, DefaultMutableTreeNode parent) {  
      DefaultMutableTreeNode child = 
         new DefaultMutableTreeNode( f.toString() );
      parent.add(child);
      if ( f.isDirectory() ) {
        String list[] = f.list();
        for ( int i = 0; i < list.length; i++ )
           buildTree( new File(f,list[i]), child);            
      }        
   }

   public void sendFileToUser(String uuid) {
     window.sendToSpecific(uuid, new Message("<filerequest> " + f.toString() + " " + f.length(), "none", "", window.getUsername(), window.getColor()));
     ChatTimer waitingForKey = new ChatTimer();
     new Thread(waitingForKey).start();
     while(waitingForKey.checkTimer()){
      Thread.sleep(10);
      if(fileAnswer){
        new Thread(new FileSend(f)).start();
        break;
      }


    }

   }
 
   private void showDetails( TreePath p ) {
      if ( p == null )
        return;
	  f = new File( p.getLastPathComponent().toString() );
	  if (f.isDirectory()) {
		JOptionPane.showMessageDialog(null,"Kan inte skicka mapp, välj fil.","Skicka Fil",JOptionPane.WARNING_MESSAGE);
	  }
      else {	
		location = f.getPath();
		fileSize = f.length(); //Är av formatet long
		//JOptionPane.showMessageDialog( this, f.getPath() +"\n   " + getAttributes( f ) );
		System.out.println(location);
		System.out.println(fileSize);
    UserChooser test = new UserChooser(window, this);

		this.dispose(); //Funkar kanske inte riktigt som jag tänkt mig... 
	  }
   }

   public static void main( String[ ] args ) {
       if(args.length>0) katalog=args[0];
       //new FileProcess();
   }


}