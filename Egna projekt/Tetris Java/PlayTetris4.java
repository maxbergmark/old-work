import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.System;

public class PlayTetris4 {
	
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

	private double[][] centers = {{0.5, 0.5}, {0, 1}, {0, 1}, {0.5, 0.5}, {0, 1}, {0, 1}, {0, 1}};
	private byte[] checkRotation = {2, 4, 4, 1, 2, 4, 2};
	private boolean firstGame = true;
	

	public PlayTetris4(Tetris tetrisIn) {
		tetris = tetrisIn;
		xsize = tetris.getXSize();
		ysize = tetris.getYSize();
		mid = tetris.getMid();
	}

	public void makeChoice() {
		if (firstGame) {
			tetris.holdMainPiece();
			firstGame = false;
			return;
		}
		byte current = tetris.getCurrentPiece();
		byte next = tetris.getNextPiece();
		byte hold = tetris.getHoldPiece();
		byte[][] tempGrid = addToGrid(tetris.getGrid(), moveShape(pieces[current], new int[] {mid, 1}), (byte) 0);
		int[] holdResult = {100000, 0, 0};
		long startTime = System.nanoTime();
		int[] nextResult = playGame(convertGrid(tempGrid), current, next);
		long endTime = System.nanoTime();
		//System.out.println("next time: " + (endTime-startTime));
		startTime = System.nanoTime();
		if (hold >= 0 && tetris.getHoldable() && hold != next) {
			//holdResult = playGame(convertGrid(tempGrid), hold, next); //slow, but safe
			holdResult = playGame(convertGrid(tempGrid), hold, (byte)-1); //fast, but risky
		}
		endTime = System.nanoTime();


		int rot;
		int minX;
		int minMove;

		if (holdResult[0] < nextResult[0]) {
			rot = holdResult[1];
			minX = holdResult[2];
			minMove = holdResult[3];
			tetris.holdMainPiece();
		} else {
			rot = nextResult[1];
			minX = nextResult[2];
			minMove = nextResult[3];
		}

		for (int i = 0; i < rot; i++) {
			tetris.rotateMain();
		}
		tetris.movePiece(new int[] {(int) minX, 0});
		tetris.movePiece(new int[] {0, minMove-2});
	}

	public int[] playGame(long[] grid, byte currentIn, byte next) {
		
		byte current = currentIn;	
		int minHoles = 1000000;
		int minRotation = 0;
		int minX = 0;
		int minMove = 0;
		

		for (int i = 0; i < checkRotation[current]; i++) {
			int[][] mainGet = intDeepCopy(pieces[current]);
			double[] centerGet = centers[current].clone();
			long[] convertedGrid = grid;

			for (int r = 0; r < i; r++) {
				mainGet = rotateRight(mainGet, centerGet);
			}
			
			int[] minMax = calcMinMax(mainGet);
			int minY = calcMinMaxY(mainGet)[0];
			int[] tempDir = {(int) (-minMax[0]), (int) (-minY)};
			mainGet = moveShape(mainGet, tempDir);

			for (int j = -minMax[0]-mid; j < xsize-mid-minMax[1]; j++) {
				int downDist = moveDown(convertedGrid, mainGet);
				int[][] shape = moveShape(mainGet, new int[] {0, downDist});
				long[] finalConverted;
				if (calcMinMaxY(shape)[0] > 0) {
					int[] bounds = calcMinMaxY(shape);
					long[] addedConverted = addToConverted(convertedGrid, shape);
					finalConverted = checkRowsConverted(addedConverted, bounds);

					int[] nextResult;
					if (next >= 0) {
						nextResult = playGame(finalConverted, next, (byte) -1);
						if (nextResult[0] < minHoles) { 
							minHoles = nextResult[0];
							minRotation = i;
							minX = j;
							minMove = downDist;
						}
					}
					else {
						int tempHoles = calcHolesConverted(finalConverted);
						if (tempHoles < minHoles) {						
							minRotation = i;
							minX = j;
							minHoles = tempHoles;
							minMove = downDist;
						}
					}
				}
				mainGet = moveShape(mainGet, new int[] {1, 0});

			}
		}
		return new int[] {minHoles, minRotation, minX, minMove};
		
	}

	public long[] convertGrid(byte[][] grid) {
		long[] retArray = new long[grid.length];
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

	public long[] checkRowsConverted(long[] grid, int[] bounds) {
		long[] retGrid = new long[grid.length];
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
			//retGrid[i] = (long) (Math.pow(2, xsize)-1);
			retGrid[i] = (long) ((1L << xsize)-1L); //go bitwise
		}
		return retGrid;
	}

	public byte[][] checkRows(byte[][] grid, int[] bounds) {
		LinkedList<Integer> removalIndices = new LinkedList<Integer>();
		boolean willRemove = false;
		for (int y = bounds[0]; y < bounds[1]+1; y++) {
			boolean noZeroes = true;
			for (int x = 0; x < xsize; x++) {
				if (grid[y][x] == 0) {
					noZeroes = false;
					break;
				}
			}
			if (noZeroes) {
				willRemove = true;
				removalIndices.add(y);
			}
		}
		
		if (willRemove) {
			int[] rowArray = convert(removalIndices);
			return removeRows(grid, rowArray);
		}
		return grid;
		
	}

	public byte[][] removeRows(byte[][] grid, int[] rows) {

		int total = rows.length;
		int current = 0;
		byte[][] retGrid = new byte[grid.length][grid[0].length];
		for (int i = total; i < grid.length; i++) {
			while (current < total && i-total+current == rows[current]) {
				current++;
			}
			//retGrid[i] = grid[i-total+current].clone();
			System.arraycopy(grid[i-total+current], 0, retGrid[i], 0, xsize);
		}
		return retGrid;
	}


	public int[] convert(LinkedList<Integer> intList) {
		int[] retArray = new int[intList.size()];
		int i = 0;
		for (Integer x : intList) {
			retArray[i++] = x.intValue();
		}
		return retArray;
	}


	public void printGrid(byte[][] grid) {
		System.out.println();
		for (int i = 0; i < grid.length; i++) {
			System.out.println(Arrays.toString(grid[i]));
		}
	}

	public void printConverted(int[] grid) {
		System.out.println();
		for (int i = 0; i < grid.length; i++) {
			//System.out.println(Integer.toString(grid[i], 2));
			System.out.println(String.format("%10s", Integer.toBinaryString(grid[i])).replace(' ', '0'));
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

	public long[] addToConverted(long[] grid, int[][] shape) {
		long[] retGrid = grid.clone();
		for (int i = 0; i < 4; i++) {
			//retGrid[shape[i][1]] &= ~((long) (Math.pow(2, xsize-1)) >> shape[i][0]);
			retGrid[shape[i][1]] &= ~(1L << (xsize-shape[i][0]-1)); //bitwise is the way to go
		}
		return retGrid;
	}

	public int moveDown(long[] grid, int[][] shape) {
		int minMove = 1000000;
		for (int i = 0; i < 4; i++) {
			int moveDist = (calcHeight(grid, shape[i][0]) - shape[i][1]);
			//System.out.println(moveDist);
			if (minMove > moveDist) {
				minMove = moveDist;
			}
		}
		return minMove;
	}


	public int calcHeight(long[] grid, int x) {
		//System.out.println("x value: " + x + "   " + (xsize-x-1));
		for (int y = 0; y < ysize; y++) {
			if (((grid[y] >> (xsize-x-1))&1) == 0) {
				return y-1;
			}
		}
		return ysize-1;
	}


	public int calcHolesConverted(long[] grid) {
		int foundHoles = 0;
		int sideHoles = 0;
		for (int x = xsize-1; x >= 0; x--) {
			boolean firstFound = false;
			for (int y = 0; y < ysize; y++) {
				if (((grid[y] >> x) & 1) == 0) {
					firstFound = true;
				} else if (firstFound) {
					foundHoles++;
				}
				if (firstFound) {
					sideHoles += (grid[y] >> (x-1)) & (x > 0 ? 1 : 0);
					sideHoles += (grid[y] >> (x+1)) & (x < xsize-1 ? 1 : 0);
				}
			}
		}
		return foundHoles+sideHoles;
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