import java.util.*;

public class Model {

	private static final int GRAIN = 18000;
	private static final long BYTE_USAGE = 1L<<31;
	// 31=2GB, 30=1GB, 27=128MB, 25=32MB
	private static Random r = new Random();
	private static double[] cos;
	private static double[] sin;
	private static int[] offset;
	
	private int COLLISION_DISTANCE = 1;
	private static int MOVE_DISTANCE = 1;
	private int GRID_GRAIN;
	private Particle[] particles;
	private final int n;
	private final int xsize, ysize;
	private long arraySize;
	private int frozen = 0;
	private boolean[] bFreeze;

	public Model(int n, int xsize, int ysize, int grid_grain, int c_d) {
		this.n = n;
		this.xsize = xsize;
		this.ysize = ysize;
		this.GRID_GRAIN = grid_grain;
		this.COLLISION_DISTANCE = c_d;
		this.arraySize = (long)(xsize*ysize)*(long)(GRID_GRAIN*GRID_GRAIN);
		boolean temp = false;
		while (this.arraySize >= BYTE_USAGE) {

			temp = true;
			GRID_GRAIN--;
			this.arraySize = (long)(xsize*ysize)*(long)(GRID_GRAIN*GRID_GRAIN);

		}
		String frm = "\n Particles: %d\n Dimensions: %dx%d\n"
			+ " Collision distance: %d";
		System.out.println(String.format(frm, n, xsize, ysize, c_d));

		String s;
		calcOffsetCircle();
		int gridNum = offset.length;
		if (temp) {
			s = "Too fine grid grain, decreased to %d (%d elements per check).";
		} else {
			s = "Grid grain set to %d (%d elements per check).";
		}
		System.out.println(String.format("\n " + s, GRID_GRAIN, gridNum));


		Particle.setSize(xsize, ysize);
		particles = new Particle[n];
		for (int i = 0; i < n; i++) {
			particles[i] = new Particle(xsize, ysize);
		}
		calcTrig();
		bFreeze = new boolean[(int)arraySize];
		
		
	}

	public void calcOffsetCircle() {

		String[] tS = new String[2*GRID_GRAIN*COLLISION_DISTANCE+1];
		int k = 0;
		ArrayList<Integer> temp = new ArrayList<>();
		int bound = GRID_GRAIN*COLLISION_DISTANCE;

		for (int y = -bound; y <= bound; y++) {
			tS[k] = "";

			boolean firstfound = false;
			double dy = y/(double)GRID_GRAIN;

			for (int x = -bound; x <= 0; x++) {

				double dx = x/(double)GRID_GRAIN;
				double d = Math.pow((dx*dx)+(dy*dy), 0.5);
				double theta = Math.atan2(dy,dx);
				double fac = 0.5 - Math.cos(theta*4)*0.5;
				fac *= (Math.sqrt(2)-1);
				fac += 1.0;
				fac *= 0.55;

				if (Math.abs(d*GRID_GRAIN - bound) < fac) {
					tS[k] += "X";
					firstfound = true;
					temp.add(y*xsize*GRID_GRAIN+x);
					if (x != 0) {
						temp.add(y*xsize*GRID_GRAIN-x);
					}
				} else if (firstfound) {
					break;
				} else {
					tS[k] += " ";
				}

			}
			k++;
		}

		offset = new int[temp.size()];
		int c = 0;

		for (Integer i : temp) {
			offset[c++] = i;
		}
/*
		for (String s : tS) {
			System.out.println(s);
		}
*/
	}

	public int getFrozen() {
		return frozen;
	}

	public int getN() {
		return n;
	}

	public Particle[] getParticles() {
		return particles;
	}

	public boolean[] getFreeze() {
		return bFreeze;
	}

	public static class Particle {

		public double x, y;
		private static int xsize, ysize;
		public boolean frozen = false;

		public Particle(int xsize, int ysize) {
			this.x = Math.random()*xsize;
			this.y = Math.random()*ysize;
		}

		public static void setSize(int xsizeIn, int ysizeIn) {
			xsize = xsizeIn;
			ysize = ysizeIn;			
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		public void move() {
			if (!frozen) {
				int theta = r.nextInt(GRAIN);
				x += MOVE_DISTANCE*cos[theta];
				y += MOVE_DISTANCE*sin[theta];

				if (x < 1 | x > xsize-1) {
					frozen = true;
				}
				if (y < 1 | y > ysize-1) {
					frozen = true;
				}
				for (int i = 0; i < 3; i++) {
					double dx2 = (250*(1+i)+200*i*i-x)*(250+100*i*i-x);
					double dy2 = (250+200*i-y)*(250-y);
					double d = dx2 + dy2;
					if (d > 15625 & d < 15876) {
						frozen = true;
					}
				}

			}
		}
	}

	public void move() {

		if (frozen < n) {

			for (Particle p : particles) {

				boolean prev = p.frozen;
				p.move();

				if (p.frozen & ! prev) {
					frozen++;
					int index0 = ((int) p.y*xsize*GRID_GRAIN + (int)p.x)*GRID_GRAIN;

					if (index0 >= 0 & index0 < arraySize) {
						bFreeze[index0] = true;
					}

					for (int i : offset) {

						int index1 = index0 + i;

						if (index1 >= 0 & index1 < arraySize) {
							bFreeze[index1] = true;
						}
					}
				}
			}
			collide();
		}
	}

	private void collide() {
		for (Particle p : particles) {
			if (!p.frozen) {

				int index0 = ((int) p.y*xsize*GRID_GRAIN + (int)p.x)*GRID_GRAIN;

				if (index0 >= 0 & index0 < arraySize && bFreeze[index0]) {
					for (int i : offset) {

						int index1 = index0 + i;

						if (index1 >= 0 & index1 < arraySize) {
							bFreeze[index1] = true;
						}
					}
					p.frozen = true;
					frozen++;
				}
			}
		}
	}


	private	static void calcTrig() {

		cos = new double[GRAIN];
		sin = new double[GRAIN];

		for (int i = 0; i < GRAIN; i++) {
			cos[i] = Math.cos(i*Math.PI*2/(double)GRAIN);
			sin[i] = Math.sin(i*Math.PI*2/(double)GRAIN);
		}
	}
	
}