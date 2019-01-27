import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.System;

public class PlayTetris5 {
	
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

	public PlayTetris5(Tetris tetrisIn) {
		tetris = tetrisIn;
		xsize = tetris.getXSize();
		ysize = tetris.getYSize();
		mid = tetris.getMid();
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
		int[] holdResult = {1<<20, 0, 0, 0};
		//long startTime = System.nanoTime();
		int[] converted = convertGrid(tempGrid);
		// int[] nextResult = playGame(converted, current, next);
		int[] nextResult = playGame(converted.clone(), current, (byte) -1);
		if (hold >= 0 && hold != current) {
			//holdResult = playGame(converted, hold, next); //slow, but safe
			holdResult = playGame(converted, hold, (byte)-1); //fast, but risky
			totFreq[hold]++;
		}

		int rot;
		int minX;
		int minMove;

		if (holdResult[0] < nextResult[0]) {
			rot = holdResult[1];
			minX = holdResult[2];
			minMove = holdResult[3];
			freq[current]++;
			held++;
			tetris.holdMainPiece();
		} else {
			rot = nextResult[1];
			minX = nextResult[2];
			minMove = nextResult[3];
		}

		for (int i = 0; i < rot; i++) {
			tetris.rotateMain();
		}
		tetris.movePiece(new int[] {minX, minMove-1});
		// tetris.movePiece(new int[] {minX, 0});
	}

	public int[] playGame(int[] grid, byte currentIn, byte next) {
		
		byte current = currentIn;
		int minPoints = 1 << 20;
		int minRotation = 0;
		int minX = 0;
		int minMove = 0;
		int decisions = 0;
		int startScore = calcHolesConverted(grid);
		

		for (int i = 0; i < checkRotation[current]; i++) {

			int[] convertedGrid = grid;
			int[][] mainGet = pieces2[current][i];
			//int[] minMax = calcMinMax(mainGet);
			int[] minMax = minMaxArray[current][i];
			mainGet = moveShape(mainGet, new int[] {-minMax[0], 0});

			for (int j = -minMax[0]-mid; j < xsize-mid-minMax[1]; j++) {
				// if (foundSolution) {
					// break;
				// }
				int downDist = moveDown(convertedGrid, mainGet);
				int[][] shape = moveShape(mainGet, new int[] {0, downDist});
				int[] finalConverted;
				//if (calcMinMaxY(shape)[0] > 0) {
				if (downDist > 0) {

					int[] bounds = calcMinMaxY(shape);
					int[] addedConverted = addToConverted(convertedGrid, shape);
					finalConverted = checkRowsConverted(addedConverted, bounds);					

					int[] nextResult;
					if (next >= 0) {
						nextResult = playGame(finalConverted, next, (byte) -1);
						if (nextResult[0] < minPoints) { 
							minPoints = nextResult[0];
							minRotation = i;
							minX = j;
							minMove = downDist;
						}
					}
					else {
						int currentScore = calcHolesConverted(finalConverted);
						if (currentScore < minPoints) {
							minPoints = currentScore;						
							minRotation = i;
							minX = j;
							minMove = downDist;
							// if (minPoints < startScore) {
								// return new int[] {minPoints, minRotation, minX, minMove};
							// }
						}
						
					}
				}
				// mainGet = moveShape(mainGet, new int[] {1, 0});
				moveShape2(mainGet, new int[] {1, 0});
			}
		}
		return new int[] {minPoints, minRotation, minX, minMove};
		
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
		}
		return retGrid;
	}

	public void moveShape2(int[][] shape, int[] direction) {
		for (int i = 0; i < 4; i++) {
			shape[i][0] = shape[i][0] + direction[0];
			shape[i][1] = shape[i][1] + direction[1];
		}
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
			retGrid[shape[i][1]] &= ~(1 << (xsize-shape[i][0]-1)); //bitwise is the way to go
		}
		return retGrid;
	}

	public void addToConverted2(int[] grid, int[][] shape) {
		for (int i = 0; i < 4; i++) {
			grid[shape[i][1]] &= ~(1 << (xsize-shape[i][0]-1)); //bitwise is the way to go
		}
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
	        foundHoles += (ysize-y)*setOnes[underMask & line];
	        foundHoles += (ysize-y)*setOnes[lneighborMask & line];
	        foundHoles += (ysize-y)*setOnes[rneighborMask & line];
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