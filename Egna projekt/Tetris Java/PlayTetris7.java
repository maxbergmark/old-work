import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.System;

public class PlayTetris7 {
	
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
	private Thread[] tetrisThreads = new Thread[7];
	private TetrisThread[] threadPiece = new TetrisThread[7];

	public PlayTetris7(Tetris tetrisIn) {
		tetris = tetrisIn;
		xsize = tetris.getXSize();
		ysize = tetris.getYSize();
		mid = tetris.getMid();
		EMPTY_ROW = (1<<xsize)-1;
		setOnes = new int[(1<<xsize)];
		for (int i = 0; i < (1<<xsize); i++) {
			setOnes[i] = popcount(i);
		}
		for (byte i = 0; i < 7; i++) {
			threadPiece[i] = new TetrisThread(tetris, i, xsize, ysize, mid);
			tetrisThreads[i] = new Thread(threadPiece[i]);
			tetrisThreads[i].start();
		}
	}

	public int popcount(int v) {
	    v = v - ((v >> 1) & 0x55555555);                // put count of each 2 bits into those 2 bits
	    v = (v & 0x33333333) + ((v >> 2) & 0x33333333); // put count of each 4 bits into those 4 bits  
	    return v = ((v + (v >> 4) & 0xF0F0F0F) * 0x1010101) >> 24;
	}

	public void makeChoice() {
		if (firstGame) {
			tetris.holdMainPiece();
			freq[tetris.getCurrentPiece()]++;
			firstGame = false;
			return;
		}
		byte current = tetris.getCurrentPiece();
		byte next = tetris.getNextPiece();
		byte hold = tetris.getHoldPiece();
		byte[][] tempGrid = addToGrid(tetris.getGrid(), moveShape(pieces[current], new int[] {mid, 1}), (byte) 0);
		//long startTime = System.nanoTime();
		int[] converted = convertGrid(tempGrid);
		long[] nextResult = playGame(converted, current, (byte) -5);
		//long endTime = System.nanoTime();
		//System.out.println((double)(endTime-startTime)/1000000.0);

		long rot;
		long minX;
		long minMove;
		rot = nextResult[1];
		minX = nextResult[2];
		minMove = nextResult[3];

		for (int i = 0; i < rot; i++) {
			tetris.rotateMain();
		}
		//System.out.println(minX + "   " + (minMove-1));
		tetris.movePiece(new int[] {(int) minX, 0});
		// tetris.movePiece(new int[] {(int) minX, (int) minMove-1});
		blocks++;
		//tetris.movePiece(new int[] {minX, 0});
	}

	public void stopThreads() {
		for (int i = 0; i < 7; i++) {
			try {
				tetrisThreads[i].interrupt();
			} catch (Exception e) {}
		}
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
					
					finalConverted = checkRowsConverted(addedConverted, bounds);
					//int[] finalConverted = checkRowsConverted(addToConverted(convertedGrid, shape), bounds);
					//long testTime2 = System.nanoTime();
					//totCheckTime += testTime2-testTime;
					long[] nextResult;
					if (next == -5 || next == -6 || next == -7) {
						//long time1 = System.nanoTime();
						long[] loopMin = {1L<<40, 0, 0, 0};
						long[] loopMax = {0, 0, 0, 0};
						long loopTotal = 0;
						long[] loopCurr;

						for (byte p = 0; p < 7; p++) {

							threadPiece[p].setGrid(finalConverted.clone());
							threadPiece[p].startThread();
							
							synchronized(threadPiece[p]) {
								threadPiece[p].notify();
							}

						}
				
						boolean willbreak = false;
						while (true) {
							for (byte p = 0; p < 7; p++) {
								if (threadPiece[p].isRunning()) {
									break;
								}
								if (p == 6) {
									willbreak = true;
								}
							}
							if (willbreak) {
								//System.out.println("breaking");
								break;
							}
						}

						long[] scores = new long[7];
						for (byte p = 0; p < 7; p++) {
							scores[p] = threadPiece[p].getScore();

						}
						for (byte p = 0; p < 7; p++) {
							if (next == -5 || p == 0 || p == 3 || p == 4 || p == 6) {
								if (scores[p] < loopMin[0]) { 
									loopMin[0] = scores[p];
									loopMin[1] = i;
									loopMin[2] = j;
									loopMin[3] = downDist;
								}
								if (scores[p] > loopMax[0]) {
									loopMax[0] = scores[p];
									loopMax[1] = i;
									loopMax[2] = j;
									loopMax[3] = downDist;
								}
								
								loopTotal += scores[p];
							}
						}
						if (loopTotal < minPoints) {
							minPoints = loopTotal;
							minRotation = i;
							minX = j;
							minMove = downDist;
						}
						//long time2 = System.nanoTime();
						//System.out.println((double)(time2-time1)/1000000.0);
/*
						if (loopMax[0] < minPoints) {
							minPoints = loopMax[0];
							minRotation = i;
							minX = j;
							minMove = downDist;
						}
*/
					}

					else {
						//long testTime = System.nanoTime();
						long currentScore = calcHolesConverted(finalConverted);
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
				mainGet = moveShape(mainGet, new int[] {1, 0});

			}
		}
		return new long[] {minPoints, minRotation, minX, minMove};
		
	}

	public void printConverted(int[] grid) {
		System.out.println();
		for (int i = 0; i < grid.length; i++) {
			//System.out.println(Integer.toString(grid[i], 2));
			System.out.println(String.format("%" + String.valueOf(xsize) + "s", Integer.toBinaryString(grid[i])).replace(' ', '0'));
		}
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
/*
	public byte[][] deepCopy(byte[][] grid) {
		if (grid == null) {
			return null;
		}
		byte[][] retGrid = new byte[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			System.arraycopy(grid[i], 0, retGrid[i], 0, grid[i].length);
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
		}
		return retGrid;
	}
*/
	public int[] checkRowsConverted(int[] grid, int[] bounds) {
		int[] retGrid = new int[grid.length];
		System.arraycopy(grid, bounds[1]+1, retGrid, bounds[1]+1, grid.length-bounds[1]-1);
		int found = 0;
		for (int i = bounds[1]; i >= bounds[0]; i--) {
			while (grid[i-found] == 0) {
				found++;
			}
			retGrid[i] = grid[i-found];
		}
		System.arraycopy(grid, 0, retGrid, found, bounds[0]);
		for (int i = found; i >= 0; i--) {
			retGrid[i] = EMPTY_ROW; //go bitwise
			//retGrid[i] = 1023;
			//retGrid[i] = (int) (Math.pow(2, xsize)-1); //super slow
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
			//retGrid[shape[i][1]] &= ~((int) (Math.pow(2, xsize-1)) >> shape[i][0]); //super slow
			//retGrid[shape[i][1]] &= ~(512 >> shape[i][0]); //a bit faster for xsize = 10, but not general
			retGrid[shape[i][1]] &= ~(1 << (xsize-shape[i][0]-1)); //bitwise is the way to go
		}
		return retGrid;
	}

	public int moveDown(int[] grid, int[][] shape) {
		int minMove = 1<<10;
		for (int i = 0; i < 4; i++) {
			int moveDist = (calcHeight(grid, shape[i][0]) - shape[i][1]);
			//System.out.println(moveDist);
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
	        //(ysize-y)
	        foundHoles += 200*(ysize-y)*setOnes[underMask & line];
	        foundHoles += 2*(ysize-y)*setOnes[lneighborMask & line];
	        foundHoles += 2*(ysize-y)*setOnes[rneighborMask & line];
	    }
	    return foundHoles;
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