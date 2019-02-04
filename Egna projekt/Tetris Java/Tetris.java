import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Collections;


public class Tetris {

	private int xsize = 10;
	private int ysize = 16;
	private int mid = xsize/2-1;

	private byte[][] playGrid = new byte[ysize][xsize];
	private int[][][] pieces = {{{-1, 0}, {0, 0}, {1, 0}, {2, 0}}, //long
								{{-1, 0}, {-1, 1}, {0, 1}, {1, 1}}, //L-piece
								{{-1, 1}, {0, 1}, {1, 1}, {1, 0}}, //reverse L-piece
								{{0, 0}, {1, 0}, {0, 1}, {1, 1}}, //square
								{{-1, 1}, {0, 1}, {0, 0}, {1, 0}}, //squiggly
								{{0, 0}, {-1, 1}, {0, 1}, {1, 1}}, //T-piece
								{{-1, 0}, {0, 0}, {0, 1}, {1, 1}}}; //reverse squiggly

	private double[][] centers = {{0.5, 0.5}, {0, 1}, {0, 1}, {0.5, 0.5}, {0, 1}, {0, 1}, {0, 1}};
	
	private int[][] mainPiece = new int[4][2];
	private double[] centerPiece = new double[2];
	private byte currentPiece;
	private byte nextPiece;
	private Queue<Integer> nextQueue;
	private byte holdPiece = -1;
	private boolean holdable = true;
	public boolean gameLost = false;
	private boolean benchmark = false;
	public int randSeed;
	private int randomRows = 5;
	private Random rand;
	private long score = 0;
	private long tetrises = 0;
	private long totalBlocks = 0;
	private long totalRandomLines = 0;
	private long timeStarted = System.nanoTime();
	private long currentTime = System.nanoTime();



	public Tetris(int seed){
		randSeed = seed;
		rand = new Random(randSeed);
		nextQueue = new LinkedList<Integer>();
		generateNext();
		if (benchmark) {
			for (int i = 0; i < randomRows; i++) {
				playGrid = addRandomRow(playGrid);
			}
		}
	}


	public synchronized void rotateMain() {
		removePiece(mainPiece);
		mainPiece = rotateRight(playGrid, mainPiece, centerPiece);
	}

	public void holdMainPiece() {
		if (holdable) {
			holdable = false;
			removePiece(mainPiece);
			if (holdPiece == -1) {
				holdPiece = currentPiece;
				spawnPiece(2);
			} else {
				spawnPiece(1);
			}
		}
	}

	public long getNanoTime() {
		return currentTime-timeStarted;
	}

	public long getScore() {
		return score;
	}

	public long getTetrises() {
		return tetrises;
	}

	public long getTotalBlocks() {
		return totalBlocks;
	}

	public long getTotalRandomLines() {
		return totalRandomLines;
	}



	public boolean getHoldable() {
		return holdable;
	}

	public int getXSize() {
		return xsize;
	}

	public int getYSize() {
		return ysize;
	}

	public int getMid() {
		return mid;
	}

	public double[] getCenter() {
		return centerPiece;
	}

	public int[][] getMainPiece() {
		return mainPiece;
	}

	public byte getCurrentPiece() {
		return (byte) (currentPiece-1);
	}

	public byte getHoldPiece() {
		return (byte) (holdPiece-1);
	}

	public byte getNextPiece() {
		return (byte) (nextPiece-1);
	}

	public void removePiece(int[][] shape) {
		for (int i = 0; i < 4; i++) {
			playGrid[shape[i][1]][shape[i][0]] = 0;
		}
	}

	public void gameOver() {
		if (!gameLost) {
			System.out.println("Game Over, seed: " + randSeed + "\nTotal lines: " + score + "\nTotal blocks: " + totalBlocks);
			System.out.println(String.format("Blocks per second: %.2f", 1e9*totalBlocks/(double) (currentTime-timeStarted)));
			gameLost = true;
		}
		//tetrisFrame.dispose();
	}

	public void spawnPiece(int hold) {
		if (hold == 0 || hold == 2) {
			holdable = (boolean) (hold == 0);
			currentPiece = nextPiece;
			generateNext();
			totalBlocks++;
			currentTime = System.nanoTime();
			/*
			if (totalBlocks % 1000 == 0) {
				System.out.println(((double) (1000000000*totalBlocks))/((double) (currentTime-timeStarted)));
			}
			*/
		} else if (hold == 1) {
			byte temp = currentPiece;
			currentPiece = holdPiece;
			holdPiece = temp;
		}

		int[][] piece = pieces[currentPiece-1];
		for (int i = 0; i < 4; i++) {
			if (playGrid[1+piece[i][1]][mid+piece[i][0]] == 0) {
				playGrid[1+piece[i][1]][mid+piece[i][0]] = currentPiece;
			} else {
				gameOver();
			}
			mainPiece[i][0] = mid+piece[i][0];
			mainPiece[i][1] = 1+piece[i][1];
		}
		centerPiece[0] = mid+centers[currentPiece-1][0];
		centerPiece[1] = 1+centers[currentPiece-1][1];
		

	}

	public void generateNext() {
		
		if (nextQueue.isEmpty()) {
			Integer[] tempValues = {1, 2, 3, 4, 5, 6, 7};
			Collections.shuffle(Arrays.asList(tempValues), rand);
			for (int i = 0; i < 7; i++) {
				nextQueue.add(tempValues[i]);
			}
		}
		nextPiece = (byte) (int) nextQueue.remove();
		
		//nextPiece = (byte) (rand.nextInt(7) + 1);
		//nextPiece = (byte) (rand.nextInt(3) + 2);
		//nextPiece = (byte) 2;
	}

	public boolean contains(int[][] array, int[] value) {
		for (int[] element : array) {
			if (Arrays.equals(element, value)) {
				return true;
			}
		}
		return false;
	}

	public int[][] rotateRight(byte[][] grid, int[][] shape, double[] center) {

		double[][] transShape = new double[4][2];
		int[][] rotShape = new int[4][2];
		for (int i = 0; i < 4; i++) {
			transShape[i][0] = shape[i][0] - center[0];
			transShape[i][1] = shape[i][1] - center[1];
			rotShape[i][0] = (int) Math.round((double) (center[0] - transShape[i][1]));
			rotShape[i][1] = (int) Math.round((double) (center[1] + transShape[i][0]));
		}
		int[] nullDir = {0, 0};
		int[] tempDir = {0, 0};
		int[][] tempShape = new int[4][2];
		if (!checkUnder(grid, rotShape, nullDir)) {
			return rotShape;
		}
		int[] dirs = {1, -1, 2, -2};
		for (int i = 0; i < 4; i++) {
			tempDir[0] = dirs[i];
			tempShape = moveShape(rotShape, tempDir);
			if (!checkUnder(grid, tempShape, nullDir)) {
				center[0] += dirs[i];
				return tempShape;
			}
		}
		return shape;
	}

	public void printGrid(byte[][] grid) {
		System.out.println();
		for (int i = 0; i < grid.length; i++) {
			System.out.println(Arrays.toString(grid[i]));
		}
	}

	public int[][] moveShape(int[][] shape, int[] direction) {
		int[][] movedShape = new int[4][2];
		for (int i = 0; i < 4; i++) {
			movedShape[i][0] = (shape[i][0] + direction[0]);
			movedShape[i][1] = (shape[i][1] + direction[1]);
		}
		return movedShape;
	}

	public synchronized boolean movePiece(int[] direction) {
		boolean retValue;
		for (int i = 0; i < 4; i++) {
			playGrid[mainPiece[i][1]][mainPiece[i][0]] = 0;
		}

		if (!checkUnder(playGrid, mainPiece, direction)) {
			for (int i = 0; i < 4; i++) {
				mainPiece[i][0] += direction[0];
				mainPiece[i][1] += direction[1];
			}
			centerPiece[0] += direction[0];
			centerPiece[1] += direction[1];
			retValue = true;
		} else {
			retValue = false;
		}
		for (int i = 0; i < 4; i++) {
			playGrid[mainPiece[i][1]][mainPiece[i][0]] = currentPiece;
		}

		return retValue;
	}


	public boolean checkUnder(byte[][] grid, int[][] shape, int[] direction) {

		for (int i = 0; i < 4; i++) {
			int[] tempElement = {(int) (shape[i][0] + direction[0]), (int) (shape[i][1]+direction[1])};
			if (tempElement[0] < 0 || tempElement[0] >= xsize) {
				return true;
			}
			if (tempElement[1] == ysize) {
				return true;
			}
			if (tempElement[1] < 0) {
				return true;
			}

			//printGrid(grid);
			if (grid[shape[i][1]+direction[1]][shape[i][0]+direction[0]] != 0) {
				return true;
			}
		}
		return false;
	}

	public void updateRows() {
		setGrid(checkRows(playGrid));
	}

	public void setGrid(byte[][] grid) {
		playGrid = deepCopy(grid);
	}

	public byte[][] getGrid() {
		return deepCopy(playGrid);
	}

	public void clearGrid() {
		playGrid = new byte[ysize][xsize];
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


	public int[] convert(ArrayList<Integer> intList) {
		int[] retArray = new int[intList.size()];
		for (int i = 0; i < retArray.length; i++) {
			retArray[i] = intList.get(i).intValue();
		}
		return retArray;
	}

	public byte[][] checkRows(byte[][] grid) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		boolean willRemove = false;
		int[] bounds = calcMinMaxY(mainPiece);
		for (int y = bounds[0]; y <= bounds[1]; y++) {
			boolean tempCheck = true;
			for (int x = 0; x < xsize; x++) {
				if (grid[y][x] == 0) {
					tempCheck = false;
					break;
				}
			}
			if (tempCheck) {
				temp.add(y);
				willRemove = true;
			}
		}

		
		if (willRemove) {
			int[] rowArray = convert(temp);
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
		score += total;
		if (benchmark) {
			for (int i = 0; i < randomRows-getHeight(retGrid); i++) {
				retGrid = addRandomRow(retGrid);
				totalRandomLines++;
			}
		}
		if (total == 4) {
			tetrises++;
		}
		return retGrid;
	}

	public byte[][] addRandomRow(byte[][] grid) {
		byte[][] retGrid = new byte[grid.length][grid[0].length];
		for (int i = 0; i < grid.length-1; i++) {
			retGrid[i] = grid[i+1];
		}
		retGrid[grid.length-1] = randomRow();
		return retGrid;
	}

	public byte[] randomRow() {
		byte[] retLine = new byte[xsize];
		//Random tempRand = new Random(randSeed);
		for (int i = 0; i < xsize; i++) {
			retLine[i] = (rand.nextInt(2) == 0 ? (byte) 0 : (byte) (rand.nextInt(7)+1));
		}
		retLine[rand.nextInt(xsize)] = 0;
		return retLine;
	}

	public int getHeight(byte[][] grid) {
		byte[] compare = new byte[grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			if (!Arrays.equals(compare, grid[i])) {
				return grid.length-i;
			}
		}
		return 0;
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



}