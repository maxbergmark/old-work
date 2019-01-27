import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.System;

public class TetrisThread implements Runnable {
	
	private Tetris tetris;
	private int xsize;
	private int ysize;
	private int mid;
	private int[][][] pieces = {{{-1, 0}, {0, 0}, {1, 0}, {2, 0}}, //long
								{{-1, 0}, {-1, 1}, {0, 1}, {1, 1}}, //L-piece
								{{-1, 1}, {0, 1}, {1, 1}, {1, 0}}, //reverse L-piece
								{{0, 0}, {1, 0}, {0, 1}, {1, 1}}, //square
								{{-1, 1}, {0, 1}, {0, 0}, {1, 0}}, //squiggly
								{{0, 0}, {-1, 1}, {0, 1}, {1, 1}}, //T-piece
								{{-1, 0}, {0, 0}, {0, 1}, {1, 1}}}; //reverse squiggly

	private int[][][][] pieces2 = {{{{-1, 0}, {0, 0}, {1, 0}, {2, 0}}, {{1, -1}, {1, 0}, {1, 1}, {1, 2}}}, //long
								{{{-1, 0}, {-1, 1}, {0, 1}, {1, 1}}, {{1, 0}, {0, 0}, {0, 1}, {0, 2}}, {{1, 2}, {1, 1}, {0, 1}, {-1, 1}}, {{-1, 2}, {0, 2}, {0, 1}, {0, 0}}},   //L-piece
								{{{-1, 1}, {0, 1}, {1, 1}, {1, 0}}, {{0, 0}, {0, 1}, {0, 2}, {1, 2}}, {{1, 1}, {0, 1}, {-1, 1}, {-1, 2}}, {{0, 2}, {0, 1}, {0, 0}, {-1, 0}}},  //reverse L-piece
								{{{0, 0}, {1, 0}, {0, 1}, {1, 1}}}, //square
								{{{-1, 1}, {0, 1}, {0, 0}, {1, 0}}, {{0, 0}, {0, 1}, {1, 1}, {1, 2}}},  //squiggly
								{{{0, 0}, {-1, 1}, {0, 1}, {1, 1}}, {{1, 1}, {0, 0}, {0, 1}, {0, 2}}, {{0, 2}, {1, 1}, {0, 1}, {-1, 1}}, {{-1, 1}, {0, 2}, {0, 1}, {0, 0}}}, //T-piece
								{{{-1, 0}, {0, 0}, {0, 1}, {1, 1}}, {{1, 0}, {1, 1}, {0, 1}, {0, 2}}}}; //reverse squiggly


	private int[][][] minMaxArray = {{{-1, 2}, {1, 1}}, 
									 {{-1, 1}, {0, 1}, {-1, 1}, {-1, 0}}, 
									 {{-1, 1}, {0, 1}, {-1, 1}, {-1, 0}}, 
									 {{0, 1}}, 
									 {{-1, 1}, {0, 1}, {-1, 1}, {-1, 0}}, 
									 {{-1, 1}, {0, 1}, {-1, 1}, {-1, 0}}, 
									 {{-1, 1}, {0, 1}, {-1, 1}, {-1, 0}}};

	private double[][] centers = {{0.5, 0.5}, {0, 1}, {0, 1}, {0.5, 0.5}, {0, 1}, {0, 1}, {0, 1}};
	private byte[] checkRotation = {2, 4, 4, 1, 2, 4, 2};
	private boolean firstGame = true;
	private long totTime = 0;
	private long totCheckTime = 0;
	private long totDecisions = 0;
	private int blocks = 0;
	private int held = 1;
	private int[] freq = new int[7];
	private int[] totFreq = new int[7];
	private int[] totWays = {17, 34, 34, 9, 17, 34, 17};
	private int[] setOnes;
	private static int EMPTY_ROW;
	private byte current;
	private int[] converted;
	private volatile boolean running = false;
	private long score;


	public TetrisThread(Tetris tetrisIn, byte piece, int xsizeIn, int ysizeIn, int midIn) {
		tetris = tetrisIn;
		current = piece;
		xsize = xsizeIn;
		ysize = ysizeIn;
		mid = midIn;
		EMPTY_ROW = (1<<xsize)-1;
		setOnes = new int[(1<<xsize)];
		for (int i = 0; i < (1<<xsize); i++) {
			setOnes[i] = popcount(i);
		}
	}

	public int popcount(int v) {
	    v = v - ((v >> 1) & 0x55555555);                // put count of each 2 bits into those 2 bits
	    v = (v & 0x33333333) + ((v >> 2) & 0x33333333); // put count of each 4 bits into those 4 bits  
	    return v = ((v + (v >> 4) & 0xF0F0F0F) * 0x1010101) >> 24;
	}

	public void startThread() {
		running = true;
		/*
		synchronized(this) {
			notify();
		}
		*/
		

	}

	public boolean isRunning() {
		return running;
	}

	public void run() {
		//running = true;
		while (true) {
			//System.out.println("start");
			
			try {
				synchronized(this) {
					running = false;
					wait();
				}
			} catch (InterruptedException e) {}
			
			if (running || true) {
				long[] nextResult = playGame(converted, current, (byte) -6);
				score = nextResult[0];
			}
		}
	}

	public void setGrid(int[] gridIn) {
		converted = gridIn;
	}

	public void printConverted(int[] grid) {
		System.out.println();
		for (int i = 0; i < grid.length; i++) {
			//System.out.println(Integer.toString(grid[i], 2));
			System.out.println(String.format("%" + String.valueOf(xsize) + "s", Integer.toBinaryString(grid[i])).replace(' ', '0'));
		}
	}

	public long getScore() {
		return score;
	}

	public long[] playGame(int[] grid, byte currentIn, byte next) {
		byte current = currentIn;
		long minPoints = 1L << 40;
		long minRotation = 0;
		long minX = 0;
		long minMove = 0;
		long decisions = 0;
		

		for (int i = 0; i < checkRotation[current]; i++) {

			int[] convertedGrid = grid;
			int[][] mainGet = pieces2[current][i];
			//int[] minMax = calcMinMax(mainGet);
			int[] minMax = minMaxArray[current][i];
			mainGet = moveShape(mainGet, new int[] {-minMax[0], 0});

			for (int j = -minMax[0]-mid; j < xsize-mid-minMax[1]; j++) {
				int downDist = moveDown(convertedGrid, mainGet);
				int[][] shape = moveShape(mainGet, new int[] {0, downDist});
				int[] finalConverted;
				//if (calcMinMaxY(shape)[0] > 0) {
				if (downDist > 0) {
					int[] bounds = calcMinMaxY(shape);
					//long testTime = System.nanoTime();
					int[] addedConverted = addToConverted(convertedGrid, shape);
					if (bounds[0] < 0) {
						System.out.println(Arrays.toString(bounds));
					}
					finalConverted = checkRowsConverted(addedConverted, bounds);
					//int[] finalConverted = checkRowsConverted(addToConverted(convertedGrid, shape), bounds);
					//long testTime2 = System.nanoTime();
					//totCheckTime += testTime2-testTime;
					long[] nextResult;
					if (next == -5 || next == -6 || next == -5) {
						long[] loopMin = {1L<<40, 0, 0, 0};
						long[] loopMax = {0, 0, 0, 0};
						long loopTotal = 0;
						long[] loopCurr;
						for (byte p = 0; p < 7; p++) {
							if (next == -5 || p == 0 || p == 3 || p == 4 || p == 6 || true) {
								loopCurr = playGame(finalConverted, p, (byte) (next -1));
								if (loopCurr[0] < loopMin[0]) { 
									loopMin[0] = loopCurr[0];
									loopMin[1] = i;
									loopMin[2] = j;
									loopMin[3] = downDist;
								}
								if (loopCurr[0] > loopMax[0]) {
									loopMax[0] = loopCurr[0];
									loopMax[1] = i;
									loopMax[2] = j;
									loopMax[3] = downDist;
								}
								
								loopTotal += loopCurr[0];
							}
						}
						if (loopTotal < minPoints) {
							minPoints = loopTotal;
							minRotation = i;
							minX = j;
							minMove = downDist;
						}
/*
						if (loopMax[0] < minPoints) {
							minPoints = loopMax[0];
							minRotation = i;
							minX = j;
							minMove = downDist;
						}
*/
					}
					else if (next >= 0) {
						nextResult = playGame(finalConverted, next, (byte) -1);
						if (nextResult[0] < minPoints) { 
							minPoints = nextResult[0];
							minRotation = i;
							minX = j;
							minMove = downDist;
						}
						//decisions += nextResult[4];
					}
					else {
						//long testTime = System.nanoTime();
						int currentScore = calcHolesConverted(finalConverted);
						//long testTime2 = System.nanoTime();
						//totCheckTime += testTime2-testTime;
						//decisions++;
						if (currentScore < minPoints) {
							minPoints = currentScore;						
							minRotation = i;
							minX = j;
							minMove = downDist;
						}
						
					}
				}
				moveShape2(mainGet, new int[] {1, 0});

			}
		}
		return new long[] {minPoints, minRotation, minX, minMove};
		
	}

	public int[] convertGrid(byte[][] grid) {
		int[] retArray = new int[grid.length];
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				retArray[y] <<= 1;
				retArray[y] ^= (grid[y][x] == 0) ? 1 : 0;
			}
		}
		return retArray;
	}

	public int[] checkRowsConverted(int[] grid, int[] bounds) {
		//System.out.println(Arrays.toString(bounds));
		int[] retGrid = new int[grid.length];
		System.arraycopy(grid, bounds[1]+1, retGrid, bounds[1]+1, grid.length-bounds[1]-1);
		int found = 0;
		for (int i = bounds[1]; i >= bounds[0]; i--) {
			if (i-found < 0) {
				break;
			}
			while (grid[i-found] == 0) {
				found++;
			}
			retGrid[i] = grid[i-found];
		}
		System.arraycopy(grid, 0, retGrid, found, bounds[0]);
		for (int i = found; i >= 0; i--) {
			retGrid[i] = EMPTY_ROW; //go bitwise
		}
		return retGrid;
	}

	public int[][] moveShape(int[][] shape, int[] direction) {
		int[][] movedShape = new int[4][2];
		for (int i = 0; i < 4; i++) {
			movedShape[i][0] = shape[i][0] + direction[0];
			movedShape[i][1] = shape[i][1] + direction[1];
		}
		return movedShape;
	}

	public void moveShape2(int[][] shape, int[] direction) {
		for (int i = 0; i < 4; i++) {
			shape[i][0] = shape[i][0] + direction[0];
			shape[i][1] = shape[i][1] + direction[1];
		}
	}

	public byte[][] addToGrid(byte[][] grid, int[][] shape, byte current) {
		byte[][] retGrid = grid;
		for (int i = 0; i < 4; i++) {
			retGrid[shape[i][1]][shape[i][0]] = (byte) current;
		}
		return retGrid;
	}

	public int[] addToConverted(int[] grid, int[][] shape) {
		int[] retGrid = new int[grid.length];
		System.arraycopy(grid, 0, retGrid, 0, grid.length);
		for (int i = 0; i < 4; i++) {
			retGrid[shape[i][1]] &= ~(1 << (xsize-shape[i][0]-1)); //bitwise is the way to go
		}
		return retGrid;
	}

	public int moveDown(int[] grid, int[][] shape) {
		int minMove = 1<<10;
		for (int i = 0; i < 4; i++) {
			int moveDist = (calcHeight(grid, shape[i][0]) - shape[i][1]);
			if (minMove > moveDist) {
				minMove = moveDist;
			}
		}
		return minMove;
	}


	public int calcHeight(int[] grid, int x) {
		for (int y = 0; y < ysize; y++) {
			if (((grid[y] >> (xsize-x-1))&1) == 0) {
				return y-1;
			}
		}
		return ysize-1;
	}

	public int calcHolesConverted3(int[] grid) {

	    int underMask     = 0;
	    int neighborMask = 0;
	    int foundHoles    = 0;
		int minY = 0;

	    while ( minY < ysize && grid[minY] == EMPTY_ROW ) {
        	minY++;
    	}

	    for (int y = minY; y < ysize; y++) {
	        int line   = grid[y];
	        int filled = ~line & EMPTY_ROW;

	        underMask     |= filled;
	        neighborMask |= (filled << 1) | (filled >> 1);
	        //rneighborMask |= (filled >> 1);

	        foundHoles += (ysize-y)*(ysize-y)*(ysize-y)*(ysize-y)*(ysize-y)*setOnes[underMask & line];
	        foundHoles += (ysize-y)*(ysize-y)*(ysize-y)*(ysize-y)*setOnes[neighborMask & line];
	        //foundHoles += (ysize-y)*(ysize-y)*(ysize-y)*(ysize-y)*setOnes[rneighborMask & line];
	    }
	    return foundHoles;
	}

	public int calcHolesConverted(int[] grid) {

	    int underMask     = 0;
	    int lneighborMask = 0;
	    int rneighborMask = 0;
	    int foundHoles    = 0;
		int minY = 0;

	    while ( minY < ysize && grid[minY] == EMPTY_ROW ) {
        	minY++;
    	}

	    for (int y = minY; y < ysize; y++) {
	        int line   = grid[y];
	        int filled = ~line & EMPTY_ROW;

	        underMask     |= filled;
	        lneighborMask |= (filled << 1);
	        rneighborMask |= (filled >> 1);

	        foundHoles += scoreFun(3, y)*setOnes[underMask & line];
	        foundHoles += scoreFun(2, y)*setOnes[lneighborMask & line];
	        foundHoles += scoreFun(2, y)*setOnes[rneighborMask & line];
	        
	        //foundHoles += (ysize-y+5)*(ysize-y)*(ysize-y)*setOnes[underMask & line];
	        //foundHoles += (ysize-y)*(ysize-y)*setOnes[lneighborMask & line];
	        //foundHoles += (ysize-y)*(ysize-y)*setOnes[rneighborMask & line];
			
	        //foundHoles += (ysize-y+5)*(ysize-y)*(ysize-y)*(ysize-y)*setOnes[underMask & line];
	        //foundHoles += (ysize-y)*(ysize-y)*(ysize-y)*setOnes[lneighborMask & line];
	        //foundHoles += (ysize-y)*(ysize-y)*(ysize-y)*setOnes[rneighborMask & line];
	    }
	    return foundHoles;
	}

	public long scoreFun(int p, int y) {
		long result = 1;
		for (int i = 0; i < p; i++) {
			result *= (ysize-y);
		}
		return result;
	}



	public int calcHolesConverted2(int[] grid) {
		int foundHoles = 0;
		for (int x = xsize-1; x >= 0; x--) {
			for (int y = 1; y < ysize; y++) {

				foundHoles += setOnes[((grid[y] >> x) & 1) == 1 ? (~grid[y] >> (x-1)) & 5 : 0];
				//foundHoles += ((grid[y-1] >> x) & 1) == 0 ? 1 : 0;

			}
		}
		return foundHoles;
	}

	public int[] calcMinMaxY(int[][] shape) {
		int minY = shape[0][1];
		int maxY = shape[0][1];
		for (int i = 0; i < 4; i++) {
			if (minY > shape[i][1]) {
				minY = shape[i][1];
			}
			if (maxY < shape[i][1]) {
				maxY = shape[i][1];
			}
		}
		return new int[] {minY, maxY};
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