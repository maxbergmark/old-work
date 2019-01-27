import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.System;

public class PlayTetris3 {
	
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


	private double[][] centers = {{0.5, 0.5}, {0, 1}, {0, 1}, {0.5, 0.5}, {0, 1}, {0, 1}, {0, 1}};
	private byte[] checkRotation = {2, 4, 4, 1, 2, 4, 2};
	private boolean firstGame = true;
	private long totTime = 0;
	private long totDecisions = 0;
	private int blocks = 0;
	private int held = 1;
	private int[] freq = new int[7];
	private int[] totFreq = new int[7];
	private int[] totWays = {17, 34, 34, 9, 17, 34, 17};

	public PlayTetris3(Tetris tetrisIn) {
		tetris = tetrisIn;
		xsize = tetris.getXSize();
		ysize = tetris.getYSize();
		mid = tetris.getMid();
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
		int[] holdResult = {1<<20, 0, 0, 0, 0};
		long startTime = System.nanoTime();
		int[] nextResult = playGame(convertGrid(tempGrid), current, next);
		if (hold >= 0 && hold != current) {
			holdResult = playGame(convertGrid(tempGrid), hold, next); //slow, but safe
			//holdResult = playGame(convertGrid(tempGrid), hold, (byte)-1); //fast, but risky
			totFreq[hold]++;
		}

		long endTime = System.nanoTime();
		blocks++;

		totTime += (endTime-startTime);
		totDecisions += (holdResult[4]);
		/*
		if ((blocks%1000) == 0) {
			System.out.println(totTime/blocks + "   " + (double) (totDecisions)/(double) (blocks) + "   " + (double) held/(double) blocks);
			double mean = 0;
			for(int i = 0; i < 7; i++) {
				System.out.println((double) totFreq[i]/(double) blocks);
				mean += totWays[i]*(double) totFreq[i]/(double) (blocks);
			}
			System.out.println("mean: " + mean);
		}
		*/
		//System.out.println("hold time: " + (endTime-startTime));

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
		tetris.movePiece(new int[] {minX, minMove-2});
		//tetris.movePiece(new int[] {0, minMove-2});
	}

	public int[] playGame(int[] grid, byte currentIn, byte next) {
		
		byte current = currentIn;
		int minPoints = 1 << 20;
		int minRotation = 0;
		int minX = 0;
		int minMove = 0;
		int decisions = 0;
		

		for (int i = 0; i < checkRotation[current]; i++) {

			int[] convertedGrid = grid;
			int[][] mainGet = pieces2[current][i];
			int[] minMax = calcMinMax(mainGet);
			mainGet = moveShape(mainGet, new int[] {-minMax[0], 0});

			for (int j = -minMax[0]-mid; j < xsize-mid-minMax[1]; j++) {
				int downDist = moveDown(convertedGrid, mainGet);
				int[][] shape = moveShape(mainGet, new int[] {0, downDist});
				int[] finalConverted;
				if (calcMinMaxY(shape)[0] > 0) {
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
						decisions += nextResult[4];
					}
					else {
						int currentScore = calcHolesConverted(finalConverted);
						decisions++;
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
		return new int[] {minPoints, minRotation, minX, minMove, decisions};
		
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
			retGrid[i] = (1 << xsize)-1; //go bitwise
			//retGrid[i] = 1023;
			//retGrid[i] = (int) (Math.pow(2, xsize)-1); //super slow
		}
		return retGrid;
	}

	public void printConverted(int[] grid) {
		System.out.println();
		for (int i = 0; i < grid.length; i++) {
			//System.out.println(Integer.toString(grid[i], 2));
			System.out.println(String.format("%" + String.valueOf(xsize) + "s", Integer.toBinaryString(grid[i])).replace(' ', '0'));
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
		byte[][] retGrid = deepCopy(grid);
		for (int i = 0; i < 4; i++) {
			retGrid[shape[i][1]][shape[i][0]] = (byte) current;
		}
		return retGrid;
	}

	public int[] addToConverted(int[] grid, int[][] shape) {
		int[] retGrid = grid.clone();
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
		int foundHoles = 0;
		for (int x = xsize-1; x >= 0; x--) {
			boolean firstFound = false;
			for (int y = 0; y < ysize; y++) {
				if (((grid[y] >> x) & 1) == 0) {
					firstFound = true;
				} else if (firstFound) {
					foundHoles++;
				}
				if (firstFound) {
					foundHoles += (grid[y] >> (x-1)) & (x > 0 ? 1 : 0);
					foundHoles += (grid[y] >> (x+1)) & (x < xsize-1 ? 1 : 0);
				}
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

	public int[] calcMinMaxY2(int[][] shape) {
		int[] minMaxY = {shape[0][1], shape[0][1]};
		for (int i = 0; i < 4; i++) {
			if (minMaxY[0] > shape[i][1]) {
				minMaxY[0] = shape[i][1];
			}
			if (minMaxY[1] < shape[i][1]) {
				minMaxY[1] = shape[i][1];
			}
		}
		return minMaxY;
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