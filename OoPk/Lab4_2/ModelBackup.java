import java.util.*;

public class ModelBackup {

	private static double[] cos;
	private static double[] sin;
	private static int grain = 36000;
	private static Random r = new Random();
	private double dt;
	private Particle[] particles;
	private int n;
	private int xsize, ysize;

	private static int[] offset;
	private static HashSet<Integer> booleanFreeze;
	private static HashSet<Integer> edgeSet;
	private static HashSet<Integer> depletedSet;
	private static Particle[] grid;
	private static int maxGridDepth = 20;
	private static int[] gridDepth;
	private static int frozen = 0;
	private static int collides = 0;

/*
	private static final int[] OFFSET_SIZES = {8, 12, 16, 32, 28, 40, 40, 48,
	 68, 56, 72, 68, 88, 88, 84, 112, 112, 112, 116, 112, 144, 140, 144, 144, 
	 168, 164, 160, 184, 172, 200, 192, 188, 208, 224, 224, 228, 224, 248, 236, 
	 264, 248, 264, 276, 264, 288, 276, 304, 304, 312, 316, 320, 344, 316, 336, 
	 352, 340, 376, 336, 392, 380, 368, 400, 364, 440, 400, 424, 420, 416, 448, 
	 428, 432, 480, 444, 472, 456, 488, 500, 472, 496, 492, 536, 512, 512, 516, 
	 552, 544, 540, 536, 584, 556, 576, 568, 592, 580, 592, 600, 612, 664, 592};
*/
/*
	private static final int[] OFFSET_SIZES_OLD_2 = {5, 13, 29, 49, 81, 92, 104, 128, 144, 160, 
		164, 180, 196, 212, 228, 244, 260, 268, 284, 308, 324, 332, 348, 360, 388, 388, 
		412, 428, 444, 460, 464, 500, 516, 516, 548, 540, 580, 568, 580, 628, 616, 636, 
		652, 684, 724, 676, 700, 720, 748, 772, 788, 804, 820, 828, 824, 868, 876, 908, 
		892, 908, 948, 960, 948, 980, 992, 1028, 1012, 1044, 1084, 1068, 1084, 1072, 
		1116, 1148, 1156, 1164, 1172, 1204, 1216, 1228, 1244, 1248, 1284, 1276, 1340, 
		1336, 1356, 1356, 1336, 1412, 1396, 1420, 1428, 1420, 1468, 1472, 1476, 1508, 
		1508, 1580, 1556, 1580, 1584, 1596, 1628, 1608, 1660, 1684, 1692, 1692, 1668, 
		1732, 1728, 1740, 1788, 1796, 1836, 1788, 1836, 1856, 1860, 1884, 1872, 1948, 
		1948, 1948, 1928, 1956, 2028, 1992, 1996, 2004, 2068, 2108, 2052, 2092, 2112, 
		2140, 2148, 2136, 2188, 2172, 2164, 2208, 2244, 2292, 2272, 2284, 2316, 2348, 
		2372, 2324, 2356, 2344, 2420, 2388, 2412, 2460, 2444, 2468, 2448, 2508, 2548, 
		2520, 2572, 2540, 2588, 2608, 2572, 2660, 2632, 2692, 2660, 2676, 2748, 2716, 
		2732, 2720, 2748, 2836, 2768, 2788, 2828, 2852, 2880, 2828, 2908, 2912, 2940, 
		2932, 2916, 2988, 2972, 2988, 3008, 3052, 3116, 3060, 3068};

	private static final int[] OFFSET_SIZES_OLD = {5, 13, 29, 49, 81, 113, 149, 197, 253, 
		317, 377, 441, 521, 613, 709, 797, 901, 1009, 1129, 1257, 1373, 1517, 1653, 
		1793, 1961, 2113, 2289, 2453, 2629, 2821, 3001, 3209, 3409, 3625, 3853, 4053, 
		4293, 4513, 4769, 5025, 5261, 5525, 5789, 6077, 6361, 6625, 6921, 7213, 7525, 
		7845, 8173, 8489, 8809, 9145, 9477, 9845, 10189, 10557, 10913, 11289, 11681, 
		12061, 12453, 12853, 13265, 13673, 14073, 14505, 14949, 15373, 15813, 16241, 
		16729, 17193, 17665, 18125, 18605, 19101, 19577, 20081, 20593, 21101, 21629, 
		22133, 22701, 23217, 23769, 24313, 24845, 25445, 25989, 26565, 27145, 27729, 
		28345, 28917, 29525, 30149, 30757, 31417, 32017, 32681, 33317, 33941, 34621, 
		35265, 35953, 36625, 37297, 37981, 38669, 39381, 40089, 40793, 41545, 42265, 
		42973, 43709, 44469, 45225, 45969, 46713, 47485, 48301, 49077, 49861, 50617, 
		51433, 52257, 53069, 53885, 54693, 55557, 56401, 57209, 58089, 58941, 59805, 
		60669, 61529, 62433, 63305, 64201, 65101, 66045, 66957, 67857, 68777, 69729, 
		70681, 71613, 72533, 73517, 74457, 75465, 76417, 77401, 78413, 79381, 80381, 
		81377, 82433, 83457, 84461, 85501, 86525, 87605, 88633, 89665, 90785, 91829, 
		92909, 93981, 95093, 96209, 97273, 98385, 99477, 100621, 101765, 102873, 104009, 
		105185, 106353, 107493, 108637, 109845, 111009, 112193, 113369, 114553, 115781, 
		116965, 118213, 119425, 120649, 121905, 123121, 124381};
*/


	public ModelBackup(int n, double dt, int xsize, int ysize) {
		this.dt = dt;
		this.n = n;
		this.xsize = xsize;
		this.ysize = ysize;
		particles = new Particle[n];
		for (int i = 0; i < n; i++) {
			particles[i] = new Particle(xsize, ysize);
		}
		calcTrig();
		booleanFreeze = new HashSet<>(xsize*ysize);
		edgeSet = new HashSet<>(xsize*ysize);
		depletedSet = new HashSet<>(xsize*ysize);
		grid = new Particle[xsize*ysize*maxGridDepth];
		gridDepth = new int[xsize*ysize];
		offset = new int[] {0, -1, 1, xsize, xsize-1, xsize+1, -xsize, -xsize+1, -xsize-1};
	}

	public Particle[] getParticles() {
		return particles;
	}

/*
	public void calcOffset() {
		int c = 0;
//		offset = new int[OFFSET_SIZES[GRID_GRAIN-1]];
		offset = new int[4*GRID_GRAIN*COLLISION_DISTANCE+1];
		offset[c++] = 0;
		offset[c++] = -GRID_GRAIN*COLLISION_DISTANCE*xsize*GRID_GRAIN;
		offset[c++] = GRID_GRAIN*COLLISION_DISTANCE*xsize*GRID_GRAIN;
		for (int y = -GRID_GRAIN*COLLISION_DISTANCE+1; y <= GRID_GRAIN*COLLISION_DISTANCE-1; y++) {
			double dy = y/(double)GRID_GRAIN;
			for (int x = -GRID_GRAIN*COLLISION_DISTANCE; x <= GRID_GRAIN*COLLISION_DISTANCE; x++) {
				double dx = x/(double)GRID_GRAIN;
				double d = (dx*dx)+(dy*dy);
				if (d <= COLLISION_DISTANCE*COLLISION_DISTANCE) {
					offset[c++] = y*xsize*GRID_GRAIN+x;
					offset[c++] = y*xsize*GRID_GRAIN-x;
					break;
				}
			}
		}
	}
*/
/*
	public void calcOffsetFill() {
		int c = 0;
		offsetFill = new int[OFFSET_SIZES[COLLISION_DISTANCE*GRID_GRAIN-1]];
		double fac = 0;
		if (COLLISION_DISTANCE*GRID_GRAIN > 5) {
			fac = 1-5/(double)(COLLISION_DISTANCE*GRID_GRAIN);
		}

		for (int y = -GRID_GRAIN*COLLISION_DISTANCE; y <= GRID_GRAIN*COLLISION_DISTANCE; y++) {
			double dy = y/(double)GRID_GRAIN;
			for (int x = -GRID_GRAIN*COLLISION_DISTANCE; x <= GRID_GRAIN*COLLISION_DISTANCE; x++) {
				double dx = x/(double)GRID_GRAIN;
				double d = (dx*dx)+(dy*dy);
				if (d <= COLLISION_DISTANCE*COLLISION_DISTANCE && d >= fac*COLLISION_DISTANCE*COLLISION_DISTANCE) {
					offsetFill[c++] = y*xsize*GRID_GRAIN+x;
				}
			}
		}
//		System.out.println(c);
//		System.out.println(offsetFill.length);
//		System.out.println(offset.length);
	}
*/


	public class Particle {

		public double x, y;
		private int xsize, ysize;
		public boolean frozen = false;
		public boolean hitWall = false;

		public Particle(int xsize, int ysize) {
			this.x = Math.random()*xsize;
			this.y = Math.random()*ysize;
			this.xsize = xsize;
			this.ysize = ysize;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		public void move() {
			if (!frozen) {
				int theta = r.nextInt(grain);
				x += sin[theta];
				y += cos[theta];

				if (x < 1 | x > xsize-1) {
					frozen = true;
					hitWall = true;
				}
				if (y < 1 | y > ysize-1) {
					frozen = true;
					hitWall = true;
				}
				double d = (250-x)*(250-x)+(250-y)*(250-y);
				if (d > 15625 & d < 15876) {
					frozen = true;
					hitWall = true;
				}

			}
		}
	}

	public void move() {
		for (Particle p : particles) {
			p.move();
			if (p.hitWall) {
				int index0 = (int) p.y*xsize + (int)p.x;
				for (int i : offset) {
					int index1 = index0 + i;
					if (index1 >= 0 & index1 < xsize*ysize) {
						booleanFreeze.add(index1);
					}
				}
			}
		}
		masterCollide();
	}

	public HashSet<Integer> getSet() {
		return booleanFreeze;
	}

	public HashSet<Integer> getDepleted() {
		return depletedSet;
	}


	public void masterCollide() {
		if (collides++ < 5) {
		//if (booleanFreeze.size()*(1+n/20000)/(1+n/100000) < n - frozen) {
			System.out.print("1\t");
			collide();
		} else {
			System.out.print("2\t");
			collide2();
		}
	}

	private void collide3() {
		gridDepth = new int[xsize*ysize];
		ArrayList<Integer> recentlyAdded = new ArrayList<>();
		for (Particle p : particles) {
			if (!p.frozen) {
				int index0 = (int) p.y*xsize + (int)p.x;

				for (int i : offset) {
					int index1 = index0 + i;
					if (index1 >= 0 & index1 < xsize*ysize && booleanFreeze.contains(index1)) {
						recentlyAdded.add(index1);
						booleanFreeze.add(index0);
						p.frozen = true;
					}
				}
			}
		}
	}


	private void collide2() {
		gridDepth = new int[xsize*ysize];
		ArrayList<Integer> recentlyAdded = new ArrayList<>();
		for (Particle p : particles) {
			if (!p.frozen) {
				int index0 = (int) p.y*xsize + (int)p.x;

				if (index0 >= 0 & index0 < xsize*ysize && booleanFreeze.contains(index0)) {
					for (int i : offset) {
						booleanFreeze.add(index0+i);
					}
					p.frozen = true;
				}
			}
		}
	}

	private void collide() {
		//System.out.println(booleanFreeze.size() + "\t" + depletedSet.size() + "\t" + frozen);
		//long t0 = System.nanoTime();
		gridDepth = new int[xsize*ysize];
		ArrayList<Integer> recentlyAdded = new ArrayList<>();
		for (Particle p : particles) {
			if (p.x > 0 & p.x < xsize & p.y > 0 & p.y< ysize) {
				
				if (p.frozen) {
					int index0 = (int) p.y*xsize + (int)p.x;
					
					for (int i : offset) {
						int index1 = index0+i;
						
						if (index1 >= 0 & index1 < xsize*ysize && !depletedSet.contains(index1)) {
							
							if (!booleanFreeze.contains(index1)) {
								recentlyAdded.add(index0);
								booleanFreeze.add(index1);
							}

							
							
						}

					}

				}

				grid[(int) p.y*xsize + (int)p.x + gridDepth[(int) p.y*xsize + (int)p.x]++] = p;
			}
		}
		//long t1 = System.nanoTime();

		//System.out.println(recentlyAdded.size());
		
		ArrayList<Integer> willRemove = new ArrayList<>();
		for (Integer i : recentlyAdded) {
//			for (int o : offset) {
				int c = 0;
				int o = 0;
				for (int k : offset) {
					if (booleanFreeze.contains(i+o+k) || depletedSet.contains(i+o+k)) {
						c++;
					}

				}

				if (c == 9) {
					willRemove.add(i+o);
				}
//			}
		}
		//System.out.println(willRemove.size());

		for (Integer i : willRemove) {
			booleanFreeze.remove(i);
			depletedSet.add(i);
		}
		
		for (Integer i : booleanFreeze) {
			for (int k = 0; k < gridDepth[i]; k++) {
				if (!grid[i+k].frozen) {
					frozen++;
				}
				grid[i+k].frozen = true;
			}
		}
		//long t2 = System.nanoTime();

		//System.out.println(t1-t0);
		//System.out.println(t2-t1);
	}



	// not used
	/*
	private void collide123() {
		System.out.println(booleanFreeze.size() + "\t" + depletedSet.size());
		gridDepth = new int[xsize*ysize];
		for (Particle p : particles) {
			if (p.x > 0 & p.x < xsize & p.y > 0 & p.y< ysize) {
				if (p.frozen) {
					int index0 = (int) p.y*xsize + (int)p.x;
					for (int i : offset) {
						int index1 = index0+i;
						if (index1 >= 0 & index1 < xsize*ysize) {
							booleanFreeze.add(index1);
						}
					}
				}
				grid[(int) p.y*xsize + (int)p.x][gridDepth[(int) p.y*xsize + (int)p.x]++] = p;
			}
		}
		
		
		for (Integer i : booleanFreeze) {
			for (int k = 0; k < gridDepth[i]; k++) {
				grid[i][k].frozen = true;
			}
		}
		
	}

	*/

	private	static void calcTrig() {
		cos = new double[grain];
		sin = new double[grain];
		for (int i = 0; i < grain; i++) {
			cos[i] = Math.cos(i/18000.0*Math.PI);
			sin[i] = Math.sin(i/18000.0*Math.PI);
		}
	}
	
}