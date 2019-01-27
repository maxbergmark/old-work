import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.event.*;

public class View extends JPanel{

	Model m;
	int xsize, ysize;
	BufferedImage i;
	Graphics2D iG;

	public boolean[] dirKeys;

	public long lastDT;
	private double freq = 60;

	private ArrayList<Entity> entities;
	private int cameraOffset;

	public View(Model m, int xsize, int ysize) {

		this.m = m;
		this.xsize = xsize;
		this.ysize = ysize;
		dirKeys = new boolean[4];
		i = new BufferedImage(xsize, ysize, BufferedImage.TYPE_INT_RGB);
		iG = i.createGraphics();
		setPreferredSize(new Dimension(xsize, ysize));
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(this);
		f.pack();
		f.setVisible(true);

	}


	public void sendEntities(ArrayList<Entity> e) {
		this.entities = e;
		Collections.sort(entities);
	}

	public void action() {
		InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = getActionMap();
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "press.a");
		actionMap.put("press.a", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dirKeys[0] = true;
			}
		});
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "release.a");
		actionMap.put("release.a", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dirKeys[0] = false;
			}
		});

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "press.d");
		actionMap.put("press.d", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dirKeys[1] = true;
			}
		});
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "release.d");
		actionMap.put("release.d", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dirKeys[1] = false;
			}
		});

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "press.w");
		actionMap.put("press.w", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dirKeys[2] = true;
			}
		});
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "release.w");
		actionMap.put("release.w", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dirKeys[2] = false;
			}
		});

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "press.s");
		actionMap.put("press.s", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dirKeys[3] = true;
			}
		});
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "release.s");
		actionMap.put("release.s", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dirKeys[3] = false;
			}
		});



	}

	public void sendKeys() {
		m.updateKeys(dirKeys);
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dirKeys[0] = false;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dirKeys[1] = false;
		}

		if (key == KeyEvent.VK_UP) {
			dirKeys[2] = false;
		}

		if (key == KeyEvent.VK_DOWN) {
		}
		dirKeys[3] = false;
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dirKeys[0] = true;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dirKeys[1] = true;
		}

		if (key == KeyEvent.VK_UP) {
			dirKeys[2] = true;
		}

		if (key == KeyEvent.VK_DOWN) {
			dirKeys[3] = true;
		}
	}

	public void setCamera(int cameraOffset) {
		this.cameraOffset = cameraOffset;
	}



	public void paint(Graphics g) {
		long t0 = System.nanoTime();
		iG.clearRect(0,0,xsize, ysize);
		long startTime = m.getStartTime();
		double x = m.getX();
		double y = m.getY();
		int mx = xsize/2;
		//double freq = 0;
		if (lastDT > 0) {
			freq = .99*freq + .01*1000000000.0/(double)lastDT;
		}
		//System.out.print(String.format("\r   %.3f\t%.3f\t%.3f", x, y, freq));
		for (Entity e : entities) {
			double eY = e.getY();
			if (e.getRepeatable()) {
				for (int i = -2; i < 2; i++) {
					double eX;
					eX = Math.round(x/e.getXDim()*e.getZ())*e.getXDim()/e.getZ()+cameraOffset+i*e.getXDim()/e.getZ();
					double rX = eX-x;
					double xpos = rX*e.getZ();
					if (e.getMiddle()) {
						xpos += mx;
					}
					iG.drawImage(e.getImg(), (int)(xpos), (int)(eY), null);

				}
			} else {

				double eX;
				eX = e.getX()+cameraOffset;
				double rX = eX-x;
				double xpos = rX*e.getZ();
				if (e.getMiddle()) {
					xpos += mx;
				}
				iG.drawImage(e.getImg(), (int)(xpos), (int)(eY), null);
			}
		}
		iG.drawString(String.format("Freq: %.1fHz", freq), 5, 15);
		iG.drawString(String.format("Score: %d", (int)(x/100)), 5, 30);
		double elapsed = 0;
		if (startTime != 0) {
			if (!m.getStopped()) {
				elapsed = (System.nanoTime()-startTime)/1e9;
			} else {
				elapsed = 20;
			}
		}
		iG.drawString(String.format("Time: %.2fs", elapsed), 5, 45);

		g.drawImage(i,0,0,null);
		long t1 = System.nanoTime();
		lastDT = t1-t0;
		//System.out.println((t1-t0)/1000/1000.0);
	}


}