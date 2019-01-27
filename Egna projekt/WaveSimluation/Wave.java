import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.awt.image.*;


public class Wave extends JFrame implements ChangeListener, ActionListener, MouseListener, MouseMotionListener {

	private int N = 500;
	private int XDIM = 1200;
	private int YDIM = 700;
	private double DX = XDIM/(double)N;
	private double AMP = 10;

	// private double dt = .1;
	// private double dx = .01;
	private double k = .0025;
	private double c = .25;
	private double d = .005;
	private double t = 0;
	private double[][] A;
	private double[] u = new double[N];
	private double[] du = new double[N];
	private double[] d2u = new double[N];
	private double[] addF = new double[N];
	private double[] tF = new double[N];
	private int downX;
	private int downY;
	private boolean mouseDown = false;
	private BufferedImage img;
	private int ndrops = 40;
	private double[][] drop = new double[ndrops][2];
	private double[][] vdrop = new double[ndrops][2];
	private boolean[] ddrop = new boolean[ndrops];

	private JSlider cS;
	private JSlider aS;
	private JSlider kS;
	private JSlider dS;
	private JPanel p1;
	private JPanel p2;
	private GridBagConstraints gbc;


	public Wave() {
		img = new BufferedImage(XDIM, YDIM+50, BufferedImage.TYPE_INT_RGB);
		// createA();
		// System.out.println(Arrays.deepToString(A));
		Timer timer = new Timer(1, this);
		p1 = new JPanel() {
		
		public void paint(Graphics g) {
			long t0 = System.nanoTime();
			super.paint(g);
			Graphics2D g2 = img.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2.setPaint(new GradientPaint(0,0,new Color(47, 86, 233),0, XDIM/2,new Color(0,191,255)));
			g2.fillRect(0, 0, XDIM, YDIM+50);
			g2.setPaint(new GradientPaint(0,YDIM/2-50,new Color(60, 60, 255),0, YDIM,new Color(10, 10, 40)));
			int[] xp = new int[N+3];
			int[] yp = new int[N+3];
			int[] xp2 = new int[2*N+2];
			int[] yp2 = new int[2*N+2];
			xp[0] = 0; xp[N+1] = xp[N+2] = XDIM;
			yp[0] = yp[N+2] = YDIM+50; yp[N+1] = YDIM/2;
			yp2[N] = YDIM/2; yp2[N+1] = YDIM/2+5;
			xp2[N] = xp2[N+1] = XDIM;
			for (int i = 0; i < N; i++) {
				xp[i+1] = (int)(DX*i);
				yp[i+1] = (int) (YDIM/2 + u[i]);
				xp2[i] = xp2[2*N-i+1] = (int)(DX*i);
				yp2[i] = (int) (YDIM/2 + u[i]);
				yp2[2*N-i+1] = (int) (YDIM/2+5 + u[i]);
			}
			// g2.drawPolygon()
	//		for (int i = 0; i < N-1; i++) {
			g2.fillPolygon(xp, yp, N+3);
			g2.setPaint(new Color(0, 0, 100));
			g2.fillPolygon(xp2, yp2, 2*N+2);
			g2.setPaint(new Color(0, 80, 200));
			for (int i = 0; i < ndrops; i++) {
				if (ddrop[i]) {
					g2.fillOval((int)drop[i][0]-5, YDIM/2-(int)(drop[i][1])-5, 10, 10);
				}
			}
	//		}
			g2.setPaint(Color.BLACK);
			g2.drawString("c: "+c, 10, 10);
			g2.drawString("k: "+k, 10, 20);
			g2.drawString("d: "+d, 10, 30);
			g2.drawString("AMP: "+AMP, 10, 40);
			g.drawImage(img,0,0,null);
			Toolkit.getDefaultToolkit().sync();
			long t1 = System.nanoTime();
			// System.out.println((t1-t0)/1e6);

			}
		};
		gbc = new GridBagConstraints();
		p2 = new JPanel();
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(XDIM,YDIM+50));
		p1.setPreferredSize(new Dimension(XDIM,YDIM));
		p2.setPreferredSize(new Dimension(XDIM,50));
		cS = new JSlider();
		cS.addChangeListener(this);
		kS = new JSlider();
		kS.addChangeListener(this);
		aS = new JSlider();
		aS.addChangeListener(this);
		dS = new JSlider();
		dS.addChangeListener(this);
		p2.add(cS);
		p2.add(kS);
		p2.add(dS);
		p2.add(aS);
		add(p1, BorderLayout.CENTER);
		add(p2, BorderLayout.SOUTH);
		pack();
		setVisible(true);

		timer.start();
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public double max(double[] a) {
		double m = 0;
		for (double d : a) {
			if (Math.abs(d) > m) {
				m = Math.abs(d);
			}
		}
		return m;
	}

	public void createA() {
		A = new double[N][N];
		for (int i = -1; i < 2; i++) {
			for (int j = 0; j < N; j++) {
				if (0<=j+i && j+i<N) {
					A[j][j+i] = i%2==0?-c*2:c;
				}
			}
		}
	}

	public double[] mult(double[][] A, double[] x) {
		if (A[0].length != x.length) {
			return null;
		}
		double[] r = new double[A.length];
		for (int i = -1; i < 2; i++) {
			for (int j = 0; j < x.length; j++) {
				if (0<=j+i && j+i<N) {
					r[j] += A[j][j+i]*x[j];
				}
			}
		}
		return r;
	}

	public double[] add(double[] a, double[] b) {
		if (a.length != b.length) {
			return null;
		}
		double[] r = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			r[i] = a[i] + b[i];
		}
		return r;
	}

	public void move() {
		for (int i = 0; i < N; i++) {
			double oldd2u = d2u[i];
			double olddu = du[i];
			double extra = AMP*2*Math.sin(-7*t+i/17.0) 
						+ AMP*4*Math.sin(5*t+i/10.0)
						+ AMP*3*Math.sin(4*t+i/13.0);
			d2u[i] = -k*(u[i]-tF[i]+extra)-d*du[i];
			du[i] +=  (d2u[i]+oldd2u)/2.0;
			u[i] += (du[i]+olddu)/2.0;
		}
	}

	public void propagate() {
		double[] ld = new double[N];
		double[] rd = new double[N];
		for (int i = 0; i < N; i++) {
			
			if (i > 0) {
				ld[i] = -c*(u[i] - u[i-1]);
			} else {
				ld[i] = -c*(u[i]);
			}
			du[i] += ld[i];
			if (i < N-1) {
				rd[i] = -c*(u[i] - u[i+1]);
			} else {
				rd[i] = -c*(u[i]);
			}
			du[i] += rd[i];
			
			// du = add(du, mult(A, u));
		}

		for (int i = 0; i < N; i++) {
			// if (!mouseDown || downX/DX != i) {
				if (i > 0) {
					u[i] += ld[i];
				}
				if (i < N-1) {
					u[i] += rd[i];
				}
			// }
		}

	}

	public void paint(Graphics g) {
		super.paint(g);
	}

	public void mouseMoved(MouseEvent e) {

	}

	public void mouseDragged(MouseEvent e) {
		if ((int)(downX/DX) < N) {
			tF[(int)(downX/DX)] = 0;
		}

		downX = e.getX();
		if ((int)(downX/DX) < N) {
			tF[(int)(downX/DX)] = (e.getY()-downY)*10.0;
		}
		// u[downX/DX] = (e.getY()-downY)/DX.0;
	}

	public void spawnDroplets() {
		for (int i = 0; i < ndrops; i++) {
			if (!ddrop[i] && downX >= 0 && (int)(downX/DX) < N) {
				ddrop[i] = true;
				drop[i][0] = downX;
				drop[i][1] = -u[(int)(downX/DX)];
				double theta = (Math.random())*Math.PI;
				double str = Math.random();
				vdrop[i][0] = 2*str*Math.cos(theta);
				vdrop[i][1] = 5*str*Math.sin(theta);
			}
		}
	}

	public void moveDrops() {
		// System.out.println();
		for (int i = 0; i < ndrops; i++) {
			if (ddrop[i]) {
				drop[i][0] += vdrop[i][0];
				drop[i][1] += vdrop[i][1];
				vdrop[i][1] -= .1;
				if ((int)(drop[i][0]/DX) >= 0 && (int)(drop[i][0]/DX) < N) {
					if (drop[i][1] < -u[(int)(drop[i][0]/DX)]) {
						ddrop[i] = false;
					}
				} else {
					ddrop[i] = false;
				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		mouseDown = true;
		downY = e.getY();
		downX = e.getX();
		// u[downX/DX] = 0;

	}

	public void mouseReleased(MouseEvent e) {
		spawnDroplets();
		mouseDown = false;
		if ((int)(downX/DX) < N) {
			tF[(int)(downX/DX)] = 0;
		}
	}

	public void stateChanged(ChangeEvent e) {
		JSlider src = (JSlider)e.getSource();
		if (src == cS) {
			c = src.getValue()*.5e-2;
		}
		if (src == kS) {
			k = src.getValue()*1e-4;		
		}
		if (src == dS) {
			d = src.getValue()*1e-3;
		}
		if (src == aS) {
			AMP = src.getValue()*1e-1;
		}
	}

	public void actionPerformed(ActionEvent e) {
		// System.out.println(u[N/2]);
		t += .01;
		move();
		for (int i = 0; i < 8; i++) {
			propagate();		
		}
		moveDrops();
		repaint();
	}


	public static void main(String[] args) {
		new Wave();
	}
}