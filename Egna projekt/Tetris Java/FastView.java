import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.event.*;
import java.io.*;
import java.util.Arrays;
import javax.swing.text.*;
import java.awt.GridLayout;
import java.util.Random;
import java.math.RoundingMode;
import java.math.BigDecimal;


public class FastView extends JPanel implements Runnable{

	private int xsize;
	private int ysize;
	private int size = 30;
	private int space = 1;
	private int frameTime;

	private JFrame tetrisFrame = new JFrame("Tetris 2.0");
	private FastTetris tetris;
	private int[][][] pieces = {{{-1, 0}, {0, 0}, {1, 0}, {2, 0}}, //long
								{{-1, 0}, {-1, 1}, {0, 1}, {1, 1}}, //L-piece
								{{-1, 1}, {0, 1}, {1, 1}, {1, 0}}, //reverse L-piece
								{{0, 0}, {1, 0}, {0, 1}, {1, 1}}, //square
								{{-1, 1}, {0, 1}, {0, 0}, {1, 0}}, //squiggly
								{{0, 0}, {-1, 1}, {0, 1}, {1, 1}}, //T-piece
								{{-1, 0}, {0, 0}, {0, 1}, {1, 1}}}; //reverse squiggly

	private Color[] colors = {new Color(0, 0, 0), 
							new Color(0, 255, 255), 
							new Color(0, 0, 255), 
							new Color(255, 122, 0), 
							new Color(255, 255, 0), 
							new Color(0, 255, 0), 
							new Color(255, 0, 255), 
							new Color(255, 0, 0)};

	private GridBagConstraints c = new GridBagConstraints();
	private int sliders = 0;
	private InputMap im;
	private ActionMap am;

	private volatile boolean running = true;
	
	public FastView (FastTetris tetrisIn, int frameTimeIn) {
		tetris = tetrisIn;
		frameTime = frameTimeIn;
		xsize = tetris.getXSize();
		ysize = tetris.getYSize();
		tetrisFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		tetrisFrame.setPreferredSize(new Dimension(xsize*size+16+180, (ysize-1)*size+39+35));
		tetrisFrame.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;

		tetrisFrame.add(this, c);
		tetrisFrame.pack();
		tetrisFrame.getContentPane().setBackground(new Color(120, 120, 120));
		tetrisFrame.setVisible(true);
		tetris.generateNext();

		im = this.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		am = getActionMap();

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RightArrow");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LeftArrow");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UpArrow");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "DownArrow");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "Space");

		am.put("RightArrow", new ArrowAction("RightArrow"));
		am.put("LeftArrow", new ArrowAction("LeftArrow"));
		am.put("UpArrow", new ArrowAction("UpArrow"));
		am.put("DownArrow", new ArrowAction("DownArrow"));
		am.put("Space", new ArrowAction("Space"));
	}

	public class ArrowAction extends AbstractAction {

		private String cmd;
		//private byte[] temp;

		public ArrowAction(String cmdIn) {
			cmd = cmdIn;
		}

		public void actionPerformed(ActionEvent e) {
			int[] temp = new int[2];
			if (cmd.equals("RightArrow")) {
				temp[0] = 1;
				temp[1] = 0;
			} else if (cmd.equals("LeftArrow")) {
				temp[0] = -1;
				temp[1] = 0;
			} else if (cmd.equals("UpArrow")) {
				tetris.rotateMainRight();
			} else if (cmd.equals("DownArrow")) {
				temp[0] = 0;
				temp[1] = 1;
			} else if (cmd.equals("Space")) {
				//tetris.holdMainPiece();
			}
			tetris.moveMainLR(temp[0]);
			tetris.moveMainDown(temp[1]);
			//boolean check = tetris.movePiece(temp);
			/*
			if (cmd.equals("DownArrow") && !check) {
				tetris.setGrid(tetris.checkRows(tetris.getGrid()));
				tetris.spawnPiece(0);
			}
			*/
		}
	}

	public void addSlider(JSlider slider) {
		c.gridx = 0;
		c.gridy = ++sliders; //anger vilken position vår slider ska ha i layouten, och är generaliserad för godtyckligt många sliders.
		c.weighty = 0;
		tetrisFrame.add(slider, c);
		tetrisFrame.setVisible(true);
	}

	public void setDelay(int value) {
		frameTime = value;
	}

	public void terminate() {
		tetrisFrame.dispose();
		running = false;
	}

	public void run() {
		while (running) {
			try {
				Thread.sleep(frameTime);
			} catch (Exception e) {}
			repaint();
		}
	}

	public static double round(double value, int places) {
    	if (places < 0) throw new IllegalArgumentException();

    	BigDecimal bd = new BigDecimal(value);
    	bd = bd.setScale(places, RoundingMode.HALF_UP);
    	return bd.doubleValue();
	}

	public void paint(Graphics g) {
		
		Graphics2D g2D = (Graphics2D)g;
		g2D.setColor(new Color(120, 120, 120));
		g2D.fill(new Rectangle2D.Double(0, 0, xsize*size+500, ysize*size+500));

		for (int x = 0; x < xsize; x++) {
			for (int y = 1; y < ysize; y++) {
				g2D.setColor(colors[(tetris.getGrid()[y] >> (xsize-x-1)) & (1)]);
				g2D.fill(new Rectangle2D.Double(x*size, (y-1)*size, size-space, size-space));
			}
		}
/*
		g2D.setColor(colors[tetris.getNextPiece()+1]);
		for (int i = 0; i < 4; i++) {
			g2D.fill(new Rectangle2D.Double(pieces[tetris.getNextPiece()][i][0]*size+xsize*size+65, pieces[tetris.getNextPiece()][i][1]*size+4*size, size-space, size-space));
		}
		if (tetris.getHoldPiece() >= 0) {
			g2D.setColor(colors[tetris.getHoldPiece()+1]);
			for (int i = 0; i < 4; i++) {
				g2D.fill(new Rectangle2D.Double(pieces[tetris.getHoldPiece()][i][0]*size+xsize*size+65, pieces[tetris.getHoldPiece()][i][1]*size+10*size, size-space, size-space));
			}
		}

		long totalBlocks = tetris.getTotalBlocks();
		long nanoTime = tetris.getNanoTime();
		long score = tetris.getScore();
		long tetrises = tetris.getTetrises();
		long randLines = tetris.getTotalRandomLines();
		String blockFreq = String.valueOf(round((double)(1000000000*totalBlocks/(double)(nanoTime)), 2));
		String lineFreq = String.valueOf(round((double)(1000000000*score/(double)(nanoTime)), 2));
		String blocksPerLine = String.valueOf(round(((double)(totalBlocks)/(double)(Math.max(randLines, 1))), 2));
		String randLinesFreq = String.valueOf(round((double)(1000000000*randLines/(double)(nanoTime)), 2));

		g2D.setColor(colors[0]);
		g2D.drawString("Lines per second: " + lineFreq, size*xsize+5, ysize*size-120);
		g2D.drawString("Blocks per second: " + blockFreq, size*xsize+5, ysize*size-105);
		g2D.drawString("Total lines: " + String.valueOf(score), size*xsize+5, ysize*size-90);
		g2D.drawString("Total blocks: " + String.valueOf(totalBlocks), size*xsize+5, ysize*size-75);
		g2D.drawString("Total tetrises: " + String.valueOf(tetrises), size*xsize+5, ysize*size-60);
		g2D.drawString("Blocks per line: " + blocksPerLine, size*xsize+5, ysize*size-45);
		g2D.drawString("Random per second: " + randLinesFreq, size*xsize+5, ysize*size-30);

		g2D.drawString("NEXT", size*xsize+70, 3*size);
		g2D.drawString("HOLD", size*xsize+70, 9*size);
		*/
		

	}
}