import java.util.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;



public class SearchClass {
	Node[][] nodes;
	String chars = " #SF";
	Queue<Node> pQ;
	Queue<Node> pQ2;
	LinkedList<Node> pQ3;
	LinkedList<Node> pQ4;
	HashSet<Node> hS;
	HashSet<Node> hS2;
	Node startNode, endNode, interNode, interNode2;
	String fileName;
	String dir = "Mazes/";
	int nodesPolled;
	int pathLength;
	int totalNodes;
	long tStep;
	Node nS,nE;
	boolean saving;
	View3 view;
	public boolean ready;
	public boolean completed = false;
	private int dInd;
	private int sInd;
	private int aInd;
	private int wInd;
	private static String[] dists = {"manhattan", "pyth", "orto"};
	private static String[] searchWays = {"StoF", "FtoS", "bidir"};
	private static String[] algorithms = {"BFS", "DFS", "astar", "heuristic", "new"};
	private static String[] walkings = {"4ways", "8ways"};
	

	public SearchClass(BufferedImage img, String fN, String dist, String searchWay, String algorithm, String walking, long tStep, boolean saving) {
		fileName = fN;
		ready = false;
		this.tStep = tStep;
		this.saving = saving;
		dInd = Arrays.asList(dists).indexOf(dist);
		sInd = Arrays.asList(searchWays).indexOf(searchWay);
		aInd = Arrays.asList(algorithms).indexOf(algorithm);
		wInd = Arrays.asList(walkings).indexOf(walking);
	}

	public void setView2(View3 v) {
		this.view = v;
	}

	public void readData() {
		System.out.println("\n   Creating nodes...");
		long t0 = System.nanoTime();

		BufferedImage img;
		int[][] imgM = new int[1][];
		try {
			img = ImageIO.read(new File(dir+fileName + ".png"));
			imgM = convToArray(img);
		} catch (Exception e) {}
		totalNodes = imgM.length*imgM[0].length;
		int[][] imgF = formatArray(imgM);
		createNodes(imgF);
		hS = new HashSet<Node>();

		hS2 = new HashSet<Node>();
		
		t0 = (System.nanoTime()-t0)/1000000;
		System.out.println(String.format("   Nodes created in %d ms!", t0));
		if (sInd == 2) {
			setupData2();
		} else {
			setupData();
		}
		ready = true;

	}

	private void setupData() {
		if (aInd > 1) {
			pQ = new PriorityQueue<Node>(10, new Comparator<Node>() {
	        	public int compare(Node node1, Node node2) {
	        		int d1 = getDistance(node1,endNode) - getDistance(node2, endNode);
	        		if (aInd == 2 | aInd == 3) {return d1;}
	            	int d2 = getDistance(node1,nE) - getDistance(node2, nE);
	            	return d2;
	            	//return d2;

	        	}
	    	});
		}
		pQ3 = new LinkedList<Node>();
	}

	private void setupData2() {
		if (aInd > 1) {
			pQ = new PriorityQueue<Node>(10, new Comparator<Node>() {
	        	public int compare(Node node1, Node node2) {
	        		int d1 = getDistance(node1,endNode) - getDistance(node2, endNode);
	            	if (aInd == 2 | aInd == 3) {return d1;}
	        		int d2 = getDistance(node1,nE) - getDistance(node2, nE);
	            	return d2;
	            	//return d2;

	        	}
	    	});
	   		
			pQ2 = new PriorityQueue<Node>(10, new Comparator<Node>() {
	        	public int compare(Node node1, Node node2) {
	        		int d1 = getDistance(node1,startNode) - getDistance(node2, startNode);
	            	if (aInd == 2 | aInd == 3) {return d1;}
	        		int d2 = getDistance(node1,nS) - getDistance(node2, nS);
	            	return d2;
	            	//return d2;
	        	}
	    	});
		} else {
			pQ3 = new LinkedList<Node>();
			pQ4 = new LinkedList<Node>();

		}
	}

	public void compute() {
		System.out.println("\n   Preparing search, drawing image...");
		long t0 = System.nanoTime();
		view.setReady(false);
		while (!view.getReady()) {}
		long t1 = System.nanoTime();
		System.out.println("\n   Done in " + ((t1-t0)/1000000) + "ms!");
		System.out.println("\n   Finding path...");
		long t = System.nanoTime();
		findPath2();

    	t = System.nanoTime()-t;

    	System.out.println(String.format("   Nodes polled in %d ms!", t/1000000));
		//printNodes(false);

		System.out.println("\n   Checking path...");
		t = System.nanoTime();
		checkPath2();

		t = System.nanoTime()-t;
		
    	System.out.println(String.format("   Path checked in %d microseconds!\n", t/1000));
		//printNodes(true);
		System.out.println(String.format("   Total nodes polled: %d out of %d (%.2f)", nodesPolled, totalNodes, nodesPolled/(double)totalNodes));
		System.out.println("   Path length: " + pathLength);

		completed = true;
		view.setReady(false);
		while (!view.completed) {
			try {
				//System.out.println("waiting...");
				Thread.sleep(10);
			} catch (Exception e) {}
		}


		System.out.println("\n   Saving image...");		
		t = System.nanoTime();
		drawImage();
		t = System.nanoTime()-t;
		System.out.println(String.format("   Image saved in %d ms!", t/1000000));

	}

	private int[][] formatArray(int[][] a) {
		int[][] r = new int[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				int c = a[i][j]+(1<<24);
				int rc = (c>>16)&255;
				int gc = (c>>8)&255;
				int bc = c&255;

				if ((rc == 255 | gc == 255 | bc == 255)&(rc != 0 & gc != 0 & bc != 0)) {
					r[i][j] = 0;
				} else if (gc == 255 & rc == 0 & bc == 0) {
					r[i][j] = 2;
				} else if (rc == 255 & gc == 0 & bc == 0) {
					r[i][j] = 3;
				} else {
					r[i][j] = 1;
				}
			}
		}
		return r;
	}

	private void createNodes(int[][] sMap) {

		nodes = new Node[sMap.length][];
		int c = 0;

		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node[sMap[i].length];
			for (int j = 0; j < sMap[i].length; j++) {
				if (c++ % 1000 == 0) {
					System.out.print("\r   Init: " + c + '/' + totalNodes);
				}
				int m = sMap[i][j];

				nodes[i][j] = new Node(i, j, m);
				if (m == 2) {
					if (sInd == 1) {
						endNode = nodes[i][j];
					} else {
						startNode = nodes[i][j];
					}
				}
				if (m == 3) {
					if (sInd == 1) {
						startNode = nodes[i][j];
					} else {
						endNode = nodes[i][j];
					}
				}
			}
		}
		System.out.print("\r   Init: " + totalNodes + '/' + totalNodes);
		System.out.println();
		connectNodes();
	}

	private void createNodes(String sMap) {

		String[] splMap = sMap.trim().split("\n");
		nodes = new Node[splMap.length][];

		for (int i = 0; i < nodes.length; i++) {
			String sLn = splMap[i];
			nodes[i] = new Node[sLn.length()];
			for (int j = 0; j < sLn.length(); j++) {
				int m = chars.indexOf(sLn.charAt(j));

				//System.out.println(i + "  " + j + "  " + m);
				nodes[i][j] = new Node(i, j, m);
				if (m == 2) {
					startNode = nodes[i][j];	
				}
				if (m == 3) {
					endNode = nodes[i][j];
				}
			}
		}
		connectNodes();
	}

	private void connectNodes() {
		int c = 0;
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[i].length; j++) {
				if (c++ % 1000 == 0) {
					System.out.print("\r   Connect: " + c + '/' + totalNodes);
				}
				if (i > 0 && j < nodes[i-1].length) {
					nodes[i][j].add(nodes[i-1][j]);
				}
				if (i < nodes.length-1 && j < nodes[i+1].length) {
					nodes[i][j].add(nodes[i+1][j]);					
				}
				if (j > 0) {
					nodes[i][j].add(nodes[i][j-1]);
				}
				if (j < nodes[i].length-1) {
					nodes[i][j].add(nodes[i][j+1]);
				}
				if (wInd == 1) {
					if (i > 0 && j+1 < nodes[i-1].length) {
						nodes[i][j].add(nodes[i-1][j+1]);
					}
					if (i < nodes.length-1 && j+1 < nodes[i+1].length) {
						nodes[i][j].add(nodes[i+1][j+1]);					
					}
					if (i > 0 && j-1 >= 0 ) {
						nodes[i][j].add(nodes[i-1][j-1]);
					}
					if (i < nodes.length-1 && j-1 >= 0) {
						nodes[i][j].add(nodes[i+1][j-1]);					
					}
				}
			}
		}
		System.out.print("\r   Connect: " + totalNodes + '/' + totalNodes);
		System.out.println();
	}

	public Node getNode(int y, int x) {
		return nodes[y][x];
	}

	private boolean queuesEmpty() {
		if (sInd < 2) {
			if (aInd > 1) {
				return pQ.isEmpty();
			} else {
				return pQ3.isEmpty();
			}
		}
		if (aInd > 1) {
			return pQ.isEmpty() & pQ2.isEmpty();
		}
		return pQ3.isEmpty() & pQ4.isEmpty();
	}
	public void findPath2() {
		nodesPolled = 0;
		
		hS.add(startNode);
		hS2.add(endNode);
		nE = endNode;

		if (aInd > 1) {
			pQ.add(startNode);
			if (sInd == 2) {pQ2.add(endNode);}
		} else {
			pQ3.add(startNode);
			if (sInd == 2) {pQ4.add(endNode);}
		}
		
		int c = 0;
		HashSet<Node> inter = new HashSet<Node>();
		boolean willBreak = false;
		while (!queuesEmpty()) {
			
			if (aInd > 1) {
				nS = pQ.poll();
				if (sInd == 2) {nE = pQ2.poll();}
			} else {
				if (aInd == 0) {
					nS = pQ3.poll();
					if (sInd == 2) {nE = pQ4.poll();}
				} else {
					nS = pQ3.pollLast();
					if (sInd == 2) {nE = pQ4.pollLast();}
				}
			}

			//hS.add(nS);
			//hS2.add(nE);
			if ((c+=2) % 1000 == 0) {
				System.out.print("\r   Polled: " + c + '/' + totalNodes);
			}

			long t = System.nanoTime();
			while (System.nanoTime()-t < tStep) {}

			if (nS != null) {
				nS.visit(1);
				nS.addChange();
				for (Node nT: nS.getNodes()) {
					if ((nT.getV()==0 | !saving) && nT.canVisit() && (!hS.contains(nT) | !saving)) {
						nodesPolled++;
						nT.poll(nS);
						nT.addChange();
						if (aInd > 1) {
							pQ.add(nT);
						} else {
							pQ3.add(nT);
						}
						hS.add(nT);	
					}
					if (hS2.contains(nT)) {interNode = nS;interNode2 = nT;willBreak = true;break;}
				}
			}
			if (sInd == 2) {
				if (nE != null) {
					nE.visit(2);
					nE.addChange();
					for (Node nT: nE.getNodes()) {
						
						if ((nT.getV()==0 | !saving) && nT.canVisit() && (!hS2.contains(nT) | !saving)) {
							
							nodesPolled++;
							nT.poll(nE);
							nT.addChange();
							if (aInd > 1) {
								pQ2.add(nT);
							} else {
								pQ4.add(nT);
							}
							hS2.add(nT);	
						}
						if (hS.contains(nT)) {interNode = nE;interNode2 = nT;willBreak = true;break;}
					}
				}
			}
			if (willBreak) {break;}
		}
		System.out.println();
	}


	public void checkPath() {
		pathLength = 0;
		Node c = endNode.getPrev();
		while (c.getPrev() != null) {
			pathLength++;
			//System.out.println(c);
			c.isPath();
			//if (c != null) {changed.add(new int[] {c.getY(), c.getX()});}
			c.addChange();
			c = c.getPrev();
			
		}
	}

	public void checkPath2() {
		pathLength = 0;
		Node c1 = interNode;
		Node c2 = interNode2;
		while (c1.getPrev() != null) {
			pathLength++;
			c1.isPath();
			c1.addChange();
			c1 = c1.getPrev();
		}
		while (c2.getPrev() != null) {
			pathLength++;
			c2.isPath();
			c2.addChange();
			c2 = c2.getPrev();
		}
	}

	public void printNodes(boolean fin) {
		String s = "   ";
		//System.out.print("   ");
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[i].length; j++) {
				Node nT = nodes[i][j];
				String t;
				if (fin) {
					t = (nT.ofPath()) ? "." : ""+chars.charAt(nT.getM());
				} else {
					t =  (nT.ofPath()) ? "X" : (nT.getV()>0 & (nT.getM() == 0)) ? "o" : (nT.getP() & (nT.getM() == 0)) ? "." : ""+chars.charAt(nT.getM());
				}
				s += t;
				//System.out.print(t);
			}
			s += "\n   ";
			//System.out.print("\n   ");
		}
		System.out.println(s);
	}



	public int getDistance(Node n1, Node n2) {
		if (dInd == 0) {
			if (aInd == 2) {
				return n1.getC()+Math.abs(n1.getX()-n2.getX()) + Math.abs(n1.getY()-n2.getY());
			} else {
				return Math.abs(n1.getX()-n2.getX()) + Math.abs(n1.getY()-n2.getY());
			}
		}
		if (dInd == 2) {
			int manh = Math.abs(n1.getX()-n2.getX()) + Math.abs(n1.getY()-n2.getY());
			int ort = Math.min(Math.abs(n1.getX()-n2.getX()), Math.abs(n1.getY()-n2.getY()));
			if (aInd == 2) {
				return 100*n1.getC()+100*manh-40*ort;
			}
			return  100*manh-40*ort;
		}
		if (aInd == 2) {
			return 100*n1.getC() + (int)(100*Math.pow(Math.pow(n1.getX() - n2.getX(), 2) + Math.pow(n1.getY() - n2.getY(), 2),.5));
		}
		return (int)(100*Math.pow(Math.pow(n1.getX() - n2.getX(), 2) + Math.pow(n1.getY() - n2.getY(), 2),.5));
		
	}

	public class Node {

		private int x,y,m,v;
		private boolean p,ofP, change;
		private Collection<Node> conn;
		private Node prev;
		private int cost;
		private boolean painted;

		public Node(int x, int y, int m) {
			this.x = x;
			this.y = y; 
			this.m = m;
			this.v = 0;
			this.p = false;
			this.ofP = false;
			this.change = false;
			conn = new ArrayList<Node>();
			cost = 0;
		}

		public void add(Node n) {conn.add(n);}
		public void visit(int n) {v = n;}
		public void poll(Node n) {p = true; if (prev == null) {prev = n;};if (n != null) {cost = 1+n.getC();} else{cost=0;}}
		public void isPath() {ofP = true;}
		public void addChange() {change = true;}
		public int getX() {return x;}
		public int getY() {return y;}
		public int getM() {return m;}
		public int getC() {return cost;}
		public boolean changed() {if (change) {change = false;return true;} return false;}
		public int getV() {return v;}
		public boolean getP() {return p;}
		public boolean ofPath() {return ofP;}
		public Node getPrev() {return prev;}
		//public Node getPrev2() {return prev[1];}		
		public boolean canVisit() {return (m != 1);}
		public Collection<Node> getNodes() {return conn;}
		public String toString() {return x+" "+y+" "+m;}

	}

	private static int[][] convToArray(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int[][] result = new int[height][width];

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				result[row][col] = image.getRGB(col, row);
			}
		}
		return result;
		}


	public void drawImage() {
		BufferedImage image;
		int c = 0;

		try {
			/*
			image = ImageIO.read(new File(dir+fileName));
			//image.createGraphics();
			for (int i = 0; i < nodes.length; i++) {
				for (int j = 0; j < nodes[i].length; j++) {
					if (c++ % 1000 == 0) {
						System.out.print("\r   Checked: " + c + '/' + totalNodes);
					}
					if (nodes[i][j].getP() && !nodes[i][j].ofPath() && nodes[i][j].getM() == 0) {
						image.setRGB(j, i, 0xff8800);
					}
					if (nodes[i][j].getV() > 0) {
						image.setRGB(j, i, 0x00ffff);
					}
					if (nodes[i][j].ofPath()) {
						image.setRGB(j, i, 0xff44dd);	
					}
				}
			}
			*/
			image = view.getImage();
			System.out.println("   Image gotten from view.\n   Saving to file...");
			//String fn = fileName.substring(10, fileName.length()-4);
			File outputFile = new File ("Solved/solved" + fileName + ".gif");
			System.out.println("   File created, writing...");
			ImageIO.write(image, "gif", outputFile);
		} catch (Exception e) {
			System.out.println("Error");
		}
	}

}