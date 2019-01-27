import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class MyFrame extends JFrame{
	private Model testmodel;
	private View testview;

	
	public MyFrame(int num){
	
		//testmodel = new Model(num);
		//testview = new View(testmodel);
	}


	public static void main(String[] args) {
		int n;
		if (args.length == 1) {
			n = (int) Integer.parseInt(args[0]);
		}
		else {
			n = 10;
		}
		MyFrame testframe = new MyFrame(n);

	}
}