import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;
//import java.awt.geom.*;
//import java.util.*;

public class FrameTest extends JPanel {
	JLabel jLabel2;
	public FrameTest() {
		int xsize = 400;
        int ysize = 300;
		JFrame testframe = new JFrame("Maze");
		testframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	testframe.setPreferredSize(new Dimension(xsize+20, ysize+40));
    	String fN = "maze80x80.png";
    	ImageIcon imgThisImg = new ImageIcon("/home/max/Google Drive/Programmering/Egna projekt/Mapsearch/Mazes/"+fN);
    	jLabel2 = new JLabel(imgThisImg);
//    	JLabel jLabel2 = new JLabel("asdf");
//		jLabel2.setPreferredSize(new Dimension(xsize, ysize));
    	add(jLabel2);
    	//revalidate();
    	//repaint();
  		testframe.add(this);
    	testframe.pack();
    	testframe.setVisible(true);


	}

	public void paint(Graphics g) {
		super.paint(g);
	}
	public static void main(String[] args) {
		new FrameTest();
	}
}