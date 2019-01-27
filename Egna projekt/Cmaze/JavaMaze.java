import java.util.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.Color;

public class JavaMaze {

	static Node[][] nodes;
	static Stack<Tuple> s;
	static int[] d = {0, 1, 2, 3};
	static Tuple[] dirs;
	static HashSet<Tuple> visited;
	static int n;

	public static void main(String[] args) {
		long t0 = System.nanoTime();
		n = 10;
		try {
			n = Integer.parseInt(args[0]);
		} catch (Exception e) {

		}
		nodes = new Node[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				nodes[i][j] = new Node();
			}
		}
		long t1 = System.nanoTime();

		System.out.println(String.format("\n  Nodes created in %.3f ms.", (t1-t0)/1e6));

		Tuple start = new Tuple(n/2, n/2, (byte)-1);
		s = new Stack<>();
		visited = new HashSet<>(n*n);
		dirs = new Tuple[4];
		dirs[0] = new Tuple(0, 1, (byte)-1);
		dirs[1] = new Tuple(1, 0, (byte)-1);
		dirs[2] = new Tuple(0, -1, (byte)-1);
		dirs[3] = new Tuple(-1, 0, (byte)-1);

		s.add(start);

		Tuple curr;
		boolean br = false;
		int count = 0;

		long t2 = System.nanoTime();

		System.out.println(String.format("  Data setup in %.3f ms.", (t2-t1)/1e6));
		System.out.println();
		while (true) {
//			System.gc();

			do {
				if (s.isEmpty()) {
					curr = null;
					br = true;
					break;
				}
				curr = null;
				curr = s.pop();
			} while (visited.contains(curr));
			if (br) {
				break;
			}


			visited.add(curr);
			count++;
			if (count % 1000 == 0) {
				printProgress(count/1000, n*n/1000, s.size());
			}

			//System.out.println(String.format("Curr: %d\t%d\t%d", curr.x, curr.y, curr.p));

			if (curr.p != -1) {
//				System.out.println(String.format("Curr: %d\t%d\t%d", curr.x, curr.y, curr.p));
				nodes[curr.x][curr.y].addC((byte)((curr.p+2)%4));
				Tuple prev = curr.add(dirs[(curr.p+2)%4], (byte) -1);
//				System.out.println(String.format("Prev: %d\t%d\t%d", prev.x, prev.y, prev.p));
				nodes[prev.x][prev.y].addC((byte)curr.p);
			}




			shuffleArray(d);

			for (int i : d) {

				Tuple temp = curr.add(dirs[i], (byte) i);
				if (temp.max() < n && temp.min() >= 0) {
					if (!visited.contains(temp)) {
			//			System.out.println(String.format("Added: %d\t%d\t%d", temp.x, temp.y, temp.p));

						s.add(temp);
					}
				}
			}
		}

		long t3 = System.nanoTime();

		System.out.println(String.format("\n\n  Maze created in %.3f ms.\n", (t3-t2)/1e6));

		drawImage();

		long t4 = System.nanoTime();

		System.out.println(String.format("\n\n  File written in %.3f ms.", (t4-t3)/1e6));

		System.out.println(String.format("  Terminated in %.3f ms.\n", (t4-t0)/1e6));

	}

	public static void printProgress(int curr, int max, int number) {
		int width = 40;
		String s = "|";
		double p = width*curr/max;
		for (int i = 0; i < p; i++) {
			s += "=";
		}
		for (int i = 0; i < width-p; i++) {
			s += " ";
		}
		s += "|";
//		System.out.println(100*curr);
//		System.out.print("\r                                                                                ");
		System.out.print("\r   ");
		System.out.print(String.format("%s%.1f%% (%d)          ", 
			s, 100*curr/(double)max, number));

	}

	public static class Tuple{
		public int x;
		public int y;
		public byte p;
		public Tuple(int x, int y, byte p) {
			this.x = x;
			this.y = y;
			this.p = p;
		}

		public int max() {
			return (y < x ? x : y);
		}

		public int min() {
			return (y < x ? y : x);
		}

		public Tuple add(Tuple b, byte dir) {
			return new Tuple(this.x+b.x, this.y+b.y, dir);
		}


		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
					return false;
			}
			final Tuple other = (Tuple) obj;

			if (this.x != other.x) {
					return false;
			}
			if (this.y != other.y) {
					return false;
			}
			return true;
		}


		@Override
		public int hashCode() {
			int hash = 27211;
			hash = 68597 * hash + this.x;
			hash = 92801 * hash + this.y;
			return hash;
		}


	}

	public static class Node {
		byte c1, c2, c3, c4;
		byte c = 0;
		public Node() {

		}
		public void addC(byte conn) {
			if (c == 0) {
				c1 = conn;
				c++;
				return;
			}
			if (c == 1) {
				c2 = conn;
				c++;
				return;
			}
			if (c == 2) {
				c3 = conn;
				c++;
				return;
			}
			if (c == 3) {
				c4 = conn;
				return;
			}
		}

		public byte[] getConns() {
			 byte[] r = new byte[c];
			 if (c > 0) {
			 	r[0] = c1;
			 }
			 if (c > 1) {
			 	r[1] = c2;
			 }
			 if (c > 2) {
			 	r[2] = c3;
			 }
			 if (c > 3) {
			 	r[3] = c4;
			 }
			 return r;
		}
	}

	static void shuffleArray(int[] ar) {
		Random rnd = new Random();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}

	public static void drawImage() {
		BufferedImage image;
		String fileName = "output";
		try {

			image = new BufferedImage(2*n+1, 2*n+1, BufferedImage.TYPE_BYTE_BINARY);
			
			int count = 0;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {

					if (++count % 1000 == 0) {
						printProgress(count/1000, n*n/1000, count);
					}
					image.setRGB(2*i+1, 2*j+1, Color.WHITE.getRGB());
					for (byte b : nodes[i][j].getConns()) {
						Tuple temp = dirs[b];
						image.setRGB(2*i+temp.x+1, 2*j+temp.y+1, Color.WHITE.getRGB());
					}
				}
			}



			File outputFile = new File (fileName + ".gif");
			ImageIO.write(image, "gif", outputFile);
		} catch (Exception e) {
			System.out.println("Error");
		}
	}


}