import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;        
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;


public class FileProcess extends JFrame implements ActionListener,Runnable { //Klass som hanterar filöverföring.
    
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
   private volatile boolean running = true;
   public volatile String sendUuid;

   public FileProcess(ChatWindow windowIn) { //Konstruktorn tar in ett chattfönster.
      window = windowIn;
      Container c = getContentPane();

      root = new DefaultMutableTreeNode(katalog); //Skapar filstrukturen på samma sätt som i trädlaborationen. 
      treeModel = new DefaultTreeModel( root );
      tree = new JTree( treeModel );
      MouseListener ml = new MouseAdapter() {
      public void mouseClicked( MouseEvent e ) {
            if ( box.isSelected() )
                showDetails( tree.getPathForLocation( e.getX(), e.getY() ) );
             }
        };
      tree.addMouseListener( ml );
      buildTree();
      controls = new JPanel();
      box = new JCheckBox( showString );
      init(); 
      c.add( controls, BorderLayout.NORTH );
      c.add( tree, BorderLayout.CENTER ); 
      setVisible( true );
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
   
   public void run() {
    sendFileToUser(sendUuid);
   }

   public void terminate() {
    //System.out.println("terminated");
    running = false;
   }

   public void sendFileToUser(String uuid) { 
     window.sendToSpecific(uuid, new Message("<filerequest> " + f.toString() + " " + f.length(), "none", "", window.getUsername(), window.getColor())); //Skickar filrequest till användaren vi vill skicka filen till.
     ChatTimer waitingForKey = new ChatTimer(); //Skapar chatt-timer för att kunna avbryta efter 60 sekunder.
     new Thread(waitingForKey).start(); //Skapar ny tråd som startar chatt-timern.
     try {
      Thread.sleep(10);
     } catch (Exception e) {}
     while(waitingForKey.checkTimer() && running){
      try {
        Thread.sleep(1);
        //System.out.println("waiting");
      } catch (Exception e) {}
     }
      if (!running) {
        //System.out.println("starting");
        new Thread(new FileSend(f, window)).start(); //Får vi ett ja:-svar inom tiden skapar vi en ny instans av FileSend vilken vars run-metod vi startar.
      }

   }
 
   private void showDetails( TreePath p ) { //Metoden som låter oss välja fil.
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
		//System.out.println(location);
		//System.out.println(fileSize);
		UserChooser test = new UserChooser(window, this);

		this.dispose(); 
	  }
   }

   public static void main( String[ ] args ) {
       if(args.length>0) katalog=args[0];
   }


}