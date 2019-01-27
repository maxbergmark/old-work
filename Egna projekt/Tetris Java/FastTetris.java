import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Collections;


public class FastTetris {

	private int xsize = 10;
	private int ysize = 22;
	private int mid = xsize/2-1;

	private int[] playGrid = new int[ysize];
	private int[][][] pieces = {{{0, 0b1111 << mid-1, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, //long
								{0b1 << 4, 0b1 << 4, 0b1 << 4, 0b1 << 4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
								{0,0, 0b1111 << mid-1, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0b1 << 5, 0b1 << 5, 0b1 << 5, 0b1 << 5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}}, 
								{{0b1 << 4, 0b111 << 4, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}}, //L-piece
								{{0b1 << 6, 0b111 << 4, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}}, //reverse L-piece
								{{0b11 << 4, 0b11 << 4, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}}, //square
								{{0b11 << 4, 0b11 << 5, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}}, //squiggly
								{{0b1 << 5, 0b111 << 4, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}}, //T-piece
								{{0b11 << 5, 0b11 << 4, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}}}; //reverse squiggly

	private int[][][] pieceLimits = {{{3, 6}, {5, 5}, {3, 6}, {4, 4}},
									{{4, 6}}};

	private int[] mainPiece = new int[ysize];
	private int[] limitsLR = new int[2];
	private int[] translationVector = new int[2];
	private int currentRot = 0;
	private byte currentPiece;
	private byte nextPiece;
	private Queue<Integer> nextQueue;
	private byte holdPiece = -1;
	private boolean holdable = true;
	public boolean gameLost = false;
	private boolean benchmark = false;
	private int randSeed = 4;
	private int randomRows = 5;
	private Random rand = new Random(randSeed);
	private long score = 0;
	private long tetrises = 0;
	private long totalBlocks = 0;
	private long totalRandomLines = 0;
	private long timeStarted = System.nanoTime();
	private long currentTime = System.nanoTime();



	public FastTetris(){
		nextQueue = new LinkedList<Integer>();
		printConverted(pieces[0][0]);
		spawnPiece();
		printConverted(mainPiece);
		mainPiece = rotateRight(playGrid);
		printConverted(mainPiece);
		mainPiece = rotateRight(playGrid);
		printConverted(mainPiece);
		mainPiece = rotateRight(playGrid);
		printConverted(mainPiece);
		mainPiece = rotateRight(playGrid);
		printConverted(mainPiece);
		//generateNext();
		if (benchmark) {
			for (int i = 0; i < randomRows; i++) {
				//playGrid = addRandomRow(playGrid);
			}
		}
	}



	public void printConverted(int[] grid) {
		System.out.println();
		for (int i = 0; i < grid.length; i++) {
			//System.out.println(Integer.toString(grid[i], 2));
			System.out.println(String.format("%" + String.valueOf(xsize) + "s", Integer.toBinaryString(grid[i])).replace(' ', '0'));
		}
	}

	public void moveMainDown(int direction) {
		for (int y = ysize-1; y >= direction; y--) {
			mainPiece[y] = mainPiece[y-direction];
		}
		for (int y = direction-1; y >= 0; y--) {
			mainPiece[y] = 0;
		}
		translationVector[1] += direction;
	}

	public void moveMainLR(int direction) {
		if (direction+limitsLR[1] < xsize && direction+limitsLR[0] >= 0) {
			if (direction != 0) {
				for (int y = 0; y < ysize; y++) {
					if (direction < 0) {
						mainPiece[y] <<= -direction;
					} else {
						mainPiece[y] >>= Math.min(direction, xsize-limitsLR[1]-1);


					}
				}
			}
			translationVector[0] += direction;
			limitsLR[0] += direction;
			limitsLR[1] += direction;
			System.out.println(Arrays.toString(limitsLR));
		}
	}

	public void spawnPiece() {
		mainPiece = pieces[0][0].clone();
		limitsLR = pieceLimits[0][0].clone();
		translationVector = new int[] {0, 0};
		currentRot = 0;
	}

/*
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
*/
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

	public int[] getMainPiece() {
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
/*
	public void removePiece(int[][] shape) {
		for (int i = 0; i < 4; i++) {
			playGrid[shape[i][1]][shape[i][0]] = 0;
		}
	}

	public void gameOver() {
		if (!gameLost) {
			System.out.println("Game Over\nTotal lines: " + score + "\nTotal blocks: " + totalBlocks);
			System.out.println("Blocks per second: " + ((double) (1000000000*totalBlocks))/((double) (currentTime-timeStarted)));
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
			
			if (totalBlocks % 1000 == 0) {
				System.out.println(((double) (1000000000*totalBlocks))/((double) (currentTime-timeStarted)));
			}
			
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
*/
	public void generateNext() {
		
		if (nextQueue.isEmpty()) {
			Integer[] tempValues = {1, 2, 3, 4, 5, 6, 7};
			Collections.shuffle(Arrays.asList(tempValues), rand);
			for (int i = 0; i < 7; i++) {
				nextQueue.add(tempValues[i]);
			}
		}

		nextPiece = (byte) (int) nextQueue.remove();
		nextPiece = 0;
	}



	public int[] movePieceDown(int[] piece, int direction) {
		for (int y = ysize-1; y >= direction; y--) {
			piece[y] = piece[y-direction];
		}
		for (int y = direction-1; y >= 0; y--) {
			piece[y] = 0;
		}
		return piece;
	}

	public int[] movePieceLR(int[] piece, int direction) {
		if (direction != 0) {
			for (int y = 0; y < ysize; y++) {
				if (direction < 0) {
					piece[y] <<= -direction;
				} else {
					piece[y] >>= direction;
				}
			}
		}
		return piece;
	}

	public boolean checkUnder(int[] grid, int[] piece) {
		for (int y = 0; y < ysize; y++) {
			if ((grid[y] & piece[y]) != 0) {
				return false;
			}
		}
		return true;
	}

	public int[] rotateRight(int[] grid) {
		int[] tempPiece = pieces[0][(currentRot+1)%4].clone();
		tempPiece = movePieceLR(movePieceDown(tempPiece, translationVector[1]), translationVector[0]);
		int[] ways = {0, 1, -1, 2, -2};
		for (int i = 0; i < 5; i++) {

			int[] tempTrans = movePieceLR(tempPiece, ways[i]);
			System.out.println(i);
			printConverted(tempTrans);
			if (checkUnder(playGrid, tempTrans)) {
				int tempRot = currentRot + 1;
				int[] tempVector = translationVector.clone();
				tempVector[0] += ways[i];
				int[] templimitsLR = pieceLimits[0][tempRot%4];
				for (int j = 0; j < 2; j++) {
					templimitsLR[j] += tempVector[0];
				}
				if (templimitsLR[0] >= 0 && templimitsLR[1] < xsize) {
					System.out.println("success" + Arrays.toString(templimitsLR));
					translationVector = tempVector.clone();
					limitsLR = templimitsLR.clone();
					currentRot++;
					return tempTrans;
				}
			}
		}
		System.out.println("failed");
		return mainPiece;
	}

	public void rotateMainRight() {
		mainPiece = rotateRight(playGrid);
	}
/*

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


	public void updateRows() {
		setGrid(checkRows(playGrid));
	}

	public void setGrid(byte[][] grid) {
		playGrid = deepCopy(grid);
	}
*/
	public int[] getGrid() {
		return addGrid(playGrid, mainPiece);
	}

	public int[] addGrid(int[] grid, int[] piece) {
		int[] retGrid = new int[ysize];
		for (int y = 0; y < ysize; y++) {
			retGrid[y] = grid[y] | piece[y];
		}
		return retGrid;
	}
/*
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
*/
	public static void main(String[] args) {
		new FastTetris();
	}

}