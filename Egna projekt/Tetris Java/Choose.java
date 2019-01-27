import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.System;

public class Choose implements Runnable {

	private Tetris tetris;
	private int xsize;
	private int ysize;
	private int[][][] pieces = {{{-1, 0}, {0, 0}, {1, 0}, {2, 0}}, //long
								{{-1, 0}, {-1, 1}, {0, 1}, {1, 1}}, //L-piece
								{{-1, 1}, {0, 1}, {1, 1}, {1, 0}}, //reverse L-piece
								{{0, 0}, {1, 0}, {0, 1}, {1, 1}}, //square
								{{-1, 1}, {0, 1}, {0, 0}, {1, 0}}, //squiggly
								{{0, 0}, {-1, 1}, {0, 1}, {1, 1}}, //T-piece
								{{-1, 0}, {0, 0}, {0, 1}, {1, 1}}}; //reverse squiggly

	private double[][] centers = {{0.5, 0.5}, {0, 1}, {0, 1}, {0.5, 0.5}, {0, 1}, {0, 1}, {0, 1}};
	private byte[] checkRotation = {2, 4, 4, 1, 2, 4, 2};
	private boolean firstGame = true;
	private byte[][] grid;
	private byte current;
	private byte next;
	public int minHoles = 1000000;
	public int minRotation = 0;
	public int minX = 0;

	private volatile boolean running = true;

	public Choose(Tetris tetrisIn, byte[][] gridIn, byte currentIn, byte nextIn) {
		tetris = tetrisIn;
		xsize = tetris.getXSize();
		ysize = tetris.getYSize();
		grid = gridIn;
		current = currentIn;
		next = nextIn;
	}

	public void terminate() {
		running = false;
	}

	public void run() {
		

		for (int i = 0; i < checkRotation[current]; i++) {
			int[][] mainGet = intDeepCopy(pieces[current]);
			double[] centerGet = centers[current].clone();
			byte[][] tempGrid = deepCopy(grid);

			for (int r = 0; r < i; r++) {
				mainGet = rotateRight(mainGet, centerGet);
			}
			
			int[] minMax = calcMinMax(mainGet);
			int minY = calcMinY(mainGet);
			int[] tempDir = {(int) (-minMax[0]), (int) (-minY)};
			mainGet = moveShape(mainGet, tempDir);

			for (int j = -minMax[0]-4; j < xsize-4-minMax[1]; j++) {
				int[][] shape = moveDown(tempGrid, mainGet);
				byte[][] finalGrid;
				if (calcMinY(shape) > 0) {
					finalGrid = checkRows(addToGrid(tempGrid, shape, (byte) (current+1)));
					int[] nextResult;
					if (next >= 0) {
						nextResult = new Choose(tetris, finalGrid, next, (byte) -1);
						if (nextResult[0] < minHoles) { 
							minHoles = nextResult[0];
							minRotation = i;
							minX = j;
						}
					}
					else {
						int tempHoles = calcHoles(finalGrid);
						if (tempHoles < minHoles) {
							
							minRotation = i;
							minX = j;
							minHoles = tempHoles;
						}
					}
				}
				mainGet = moveShape(mainGet, new int[] {1, 0});

			}
		}
		terminate();
		
	}

	public byte[][] deepCopy(byte[][] grid) {
		if (grid == null) {
			return null;
		}
		byte[][] retGrid = new byte[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			System.arraycopy(grid[i], 0, retGrid[i], 0, grid[i].length);
			//retGrid[i] = grid[i].clone();
		}
		return retGrid;
	}

	public int[][] intDeepCopy(int[][] grid) {
		if (grid == null) {
			return null;
		}
		int[][] retGrid = new int[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			System.arraycopy(grid[i], 0, retGrid[i], 0, grid[i].length);
			//retGrid[i] = grid[i].clone();
		}
		return retGrid;
	}

	public int[][] rotateRight(int[][] shape, double[] center) {

		double[][] transShape = new double[4][2];
		int[][] rotShape = new int[4][2];
		for (int i = 0; i < 4; i++) {
			transShape[i][0] = shape[i][0] - center[0];
			transShape[i][1] = shape[i][1] - center[1];
			rotShape[i][0] = (int) Math.round((double) (center[0] - transShape[i][1]));
			rotShape[i][1] = (int) Math.round((double) (center[1] + transShape[i][0]));
		}

		return rotShape;
	}


	public byte[][] checkRows(byte[][] grid) {
		LinkedList<Integer> temp = new LinkedList<Integer>();
		boolean willRemove = false;
		for (int y = 0; y < ysize; y++) {
			boolean tempCheck = true;
			for (int x = 0; x < xsize; x++) {
				if (grid[y][x] == 0) {
					tempCheck = false;
					break;
				}
			}
			if (tempCheck) {
				//grid = removeRows(grid, new int[] {y});
				willRemove = true;
				temp.add(y);
			}
		}
		//
		//return grid;
		
		if (willRemove) {
			int[] rowArray = convert(temp);
			return removeRows(grid, rowArray);
		}
		return grid;
		
	}

	public int[] convert(LinkedList<Integer> intList) {
		int[] retArray = new int[intList.size()];
		int i = 0;
		for (Integer x : intList) {
			retArray[i++] = x.intValue();
		}
		return retArray;
	}


	public byte[][] removeRows(byte[][] grid, int[] rows) {

		int total = rows.length;
		int current = 0;
		byte[][] retGrid = new byte[grid.length][grid[0].length];
		for (int i = total; i < grid.length; i++) {
			if (current < total && i-total+current == rows[current]) {
				current++;
			}
			//retGrid[i] = grid[i-total+current].clone();
			System.arraycopy(grid[i-total+current], 0, retGrid[i], 0, xsize);
		}
		return retGrid;
	}

	public static void printGrid(byte[][] grid) {
		System.out.println();
		for (int i = 0; i < grid.length; i++) {
			System.out.println(Arrays.toString(grid[i]));
		}
	}

	public int[][] moveShape(int[][] shape, int[] direction) {
		int[][] movedShape = new int[4][2];
		for (int i = 0; i < 4; i++) {
			movedShape[i][0] = (int) (shape[i][0] + direction[0]);
			movedShape[i][1] = (int) (shape[i][1] + direction[1]);
		}
		return movedShape;
	}

	public byte[][] addToGrid(byte[][] grid, int[][] shape, byte current) {
		byte[][] retGrid = deepCopy(grid);
		for (int i = 0; i < 4; i++) {
			retGrid[shape[i][1]][shape[i][0]] = (byte) current;
		}
		return retGrid;
	}

	public int[][] moveDown(byte[][] grid, int[][] shape) {
		int minMove = 1000000;
		for (int i = 0; i < 4; i++) {
			int moveDist = (calcHeight(grid, shape[i][0]) - shape[i][1]);
			if (minMove > moveDist) {
				minMove = moveDist;
			}
		}
		return moveShape(shape, new int[] {0, minMove});
	}

	public int calcHeight(byte[][] grid, int x) {
		for (int y = 0; y < ysize; y++) {
			if (grid[y][x] != 0) {
				return (int) (y-1);
			}
		}
		return (int) (ysize-1);
	}

	public int calcHoles(byte[][] grid) {
		int foundHoles = 0;
		for (int x = 0; x < xsize; x++) {
			boolean firstFound = false;
			for (int y = 0; y < ysize; y++) {
				if (grid[y][x] != 0) {
					firstFound = true;
					//System.out.println("finding first");
				} else if (firstFound) {
					foundHoles++;
				}
				if (firstFound) {

					if (x > 0) {
						if (grid[y][x-1] == 0) {
							foundHoles++;
						}
					}
					if (x < xsize-1) {
						if (grid[y][x+1] == 0) {
							foundHoles++;
						}
					}
				}
			}
		}
		return foundHoles;
	}

	public int calcMinY(int[][] shape) {
		int minY = shape[0][1];
		for (int i = 0; i < 4; i++) {
			if (minY > shape[i][1]) {
				minY = shape[i][1];
			}
		}
		return minY;
	}

	public int[] calcMinMax(int[][] shape) {
		int[] minMax = {shape[0][0], shape[0][0]};
		for (int i = 0; i < 4; i++) {
			if (minMax[0] > shape[i][0]) {
				minMax[0] = shape[i][0];
			}
			if (minMax[1] < shape[i][0]) {
				minMax[1] = shape[i][0];
			}
		}
		return minMax;
	}
}
	